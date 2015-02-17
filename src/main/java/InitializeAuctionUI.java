import java.util.HashMap;
import java.util.Map;
import org.jeromq.ZMQ;
import com.firebase.client.*;
import org.lightcouch.CouchDbClient;

/*
    @author Conor Hayes
 */
public class InitializeAuctionUI {
    private ZMQ.Context context = ZMQ.context();
    private ZMQ.Socket publisher = context.socket(ZMQ.PUB);

    public static void main(String[] args) { new InitializeAuctionUI().subscribe(); }

    private void subscribe(){
        ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
        subscriber.connect(Constants.SUB_ADR);
        subscriber.subscribe(Constants.TOPIC.getBytes());
        System.out.println("SUB: " + Constants.TOPIC);
        publisher.bind(Constants.PUB_ADR);

        while(true){
            String auctionRunningEvt = new String(subscriber.recv());
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

    private void initializeUI(AuctionItem item){
        Firebase fb = new Firebase(Constants.FIREBASE_URL + "/" + item.get_id());
        Map<String,Object> auction = new HashMap<String,Object>();
        auction.put("_id", item.get_id());
        auction.put("name", item.getName());
        auction.put("image", item.getImage());
        auction.put("current_bid", item.getStarting_bid());
        auction.put("status", "Pending");
        auction.put("winner", "");
        try{
            fb.setValue(auction);
            Thread.sleep(2000);
            System.out.println("Auction UI initialized");
        }catch(Exception e) {
            System.out.println(e.toString());
        }
    }

    private String parseMessage(String message, String startTag, String endTag){
        int startIndex = message.indexOf(startTag) + startTag.length();
        String substring = message.substring(startIndex);
        return substring.substring(0, substring.lastIndexOf(endTag));
    }

    private void publishAcknowledgement(String message){
        String acknowledgements = "ACK: " + message;
        publisher.send(acknowledgements.getBytes());
        System.out.println("ACK SENT...");
    }
}
