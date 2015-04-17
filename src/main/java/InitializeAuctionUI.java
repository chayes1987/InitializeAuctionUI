import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import com.firebase.client.*;
import org.lightcouch.CouchDbClient;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.*;

/*
    @author Conor Hayes
    The official documentation was consulted for the third party libraries 0mq and Firebase used in this class
    0mq pub -> https://github.com/zeromq/jeromq/blob/master/src/test/java/guide/pathopub.java
    0mq sub -> https://github.com/zeromq/jeromq/blob/master/src/test/java/guide/pathosub.java
    Firebase -> https://www.firebase.com/docs/java-api/javadoc/com/firebase/client/Firebase.html
    Config -> http://www.mkyong.com/java/java-properties-file-examples/
 */

public class InitializeAuctionUI {
    private Context _context = ZMQ.context(1);
    
    private Socket _publisher = _context.socket(ZMQ.PUB);
    private static Properties _config;

    public static void main(String[] args){
        InitializeAuctionUI ia = new InitializeAuctionUI();
        _config = ia.readConfig();

        if(_config != null) {
            ia.subToHeartbeat();
            ia.subToAuctionRunningEvt();
        }
    }

    private Properties readConfig() {
        Properties config = new Properties();
        try {
            config.load(new FileInputStream("properties/config.properties"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return config;
    }

    private void subToAuctionRunningEvt(){
        Socket auctionRunningSub = _context.socket(ZMQ.SUB);
        auctionRunningSub.connect(_config.getProperty("SUB_ADR"));
        String auctionRunningTopic = _config.getProperty("TOPIC");
        auctionRunningSub.subscribe(auctionRunningTopic.getBytes());
        System.out.println("SUB: " + auctionRunningTopic);
        _publisher.bind(_config.getProperty("PUB_ADR"));

        while(true){
            String auctionRunningEvt = new String(auctionRunningSub.recv());
            System.out.println("REC: " + auctionRunningEvt);
            publishAcknowledgement(auctionRunningEvt);
            String id = parseMessage(auctionRunningEvt, "<id>", "</id>");
            initializeUI(getItemDetails(id));
        }
    }

    private AuctionItem getItemDetails(String id) {
        CouchDbClient client = new CouchDbClient("couchdb.properties");
        return client.find(AuctionItem.class, id);
    }

    private void initializeUI(AuctionItem item) {
        Firebase fb = new Firebase(_config.getProperty("FIREBASE_URL"))
                .child("auctions/" + item.get_id());
        Map<String, Object> auction = new HashMap<String, Object>();
        auction.put("_id", item.get_id());
        auction.put("name", item.getName());
        auction.put("image", item.getImage());
        auction.put("current_bid", item.getStarting_bid());
        auction.put("status", "Pending");
        auction.put("winner", "");
        try {
            fb.updateChildren(auction);
            Thread.sleep(2000);
            System.out.println("Auction UI initialized");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    private String parseMessage(String message, String startTag, String endTag){
        int startIndex = message.indexOf(startTag) + startTag.length();
        String substring = message.substring(startIndex);
        return substring.substring(0, substring.lastIndexOf(endTag));
    }

    private void publishAcknowledgement(String message){
        String acknowledgement = "ACK " + message;
        _publisher.send(acknowledgement.getBytes());
        System.out.println("PUB: " + acknowledgement);
    }

    private void subToHeartbeat(){
        new Thread(
                () -> {
                    Socket heartbeatSub = _context.socket(ZMQ.SUB);
                    heartbeatSub.connect(_config.getProperty("HEARTBEAT_ADR"));
                    String heartbeatTopic = _config.getProperty("CHECK_HEARTBEAT_TOPIC");
                    heartbeatSub.subscribe(heartbeatTopic.getBytes());

                    while(true){
                        String checkHeartbeatEvt = new String(heartbeatSub.recv());
                        System.out.println("REC: " + checkHeartbeatEvt);
                        String heartbeatResponse = _config.getProperty("CHECK_HEARTBEAT_TOPIC_RESPONSE") +
                                " <params>" + _config.getProperty("SERVICE_NAME") + "</params>";
                        _publisher.send(heartbeatResponse.getBytes());
                        System.out.println("PUB: " + heartbeatResponse);
                    }
                }
        ).start();
    }
}
