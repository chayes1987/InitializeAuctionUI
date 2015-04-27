package broker;

import database.DatabaseFacade;
import database.IDatabase;
import database.IRealtimeDatabase;
import org.zeromq.ZMQ;
import utils.MessageParser;
import models.AuctionItem;
import java.util.Properties;

/*
    The official documentation was consulted for the third party library 0mq used in this class
    0mq pub -> https://github.com/zeromq/jeromq/blob/master/src/test/java/guide/pathopub.java
    0mq sub -> https://github.com/zeromq/jeromq/blob/master/src/test/java/guide/pathosub.java
 */

/**
 * @author Conor Hayes
 * 0mq Broker
 */
public class ZeroMqBroker implements IBroker {
    private ZMQ.Context _context = ZMQ.context(1);
    private ZMQ.Socket _publisher = _context.socket(ZMQ.PUB);

    /**
     * Subscribes to the CheckHeartbeat command and publishes an acknowledgement
     * @param config The configuration file
     */
    @Override
    public void subscribeToHeartbeat(Properties config){
        new Thread(
                () -> {
                    ZMQ.Socket heartbeatSub = _context.socket(ZMQ.SUB);
                    // Connect and subscribe to the topic - CheckHeartbeat
                    heartbeatSub.connect(config.getProperty("HEARTBEAT_ADR"));
                    String heartbeatTopic = config.getProperty("CHECK_HEARTBEAT_TOPIC");
                    heartbeatSub.subscribe(heartbeatTopic.getBytes());

                    while(true){
                        String checkHeartbeatEvt = new String(heartbeatSub.recv());
                        System.out.println("REC: " + checkHeartbeatEvt);
                        // Build and send the response
                        String heartbeatResponse = config.getProperty("CHECK_HEARTBEAT_TOPIC_RESPONSE") +
                                " <params>" + config.getProperty("SERVICE_NAME") + "</params>";
                        _publisher.send(heartbeatResponse.getBytes());
                        System.out.println("PUB: " + heartbeatResponse);
                    }
                }
        ).start();
    }

    /**
     * Subscribes to the Auction Running event
     * @param config The configuration file
     */
    @Override
    public void subscribeToAuctionRunningEvt(Properties config){
        ZMQ.Socket auctionRunningSub = _context.socket(ZMQ.SUB);
        // Connect and subscribe to the topic - AuctionRunning
        auctionRunningSub.connect(config.getProperty("SUB_ADR"));
        String auctionRunningTopic = config.getProperty("TOPIC");
        auctionRunningSub.subscribe(auctionRunningTopic.getBytes());
        System.out.println("SUB: " + auctionRunningTopic);
        // Bind the publisher for acknowledgements
        _publisher.bind(config.getProperty("PUB_ADR"));

        while(true){
            String auctionRunningEvt = new String(auctionRunningSub.recv());
            System.out.println("REC: " + auctionRunningEvt);
            publishAcknowledgement(auctionRunningEvt);
            // Get the item details and write them to Firebase
            String id = MessageParser.parseMessage(auctionRunningEvt, "<id>", "</id>");
            IDatabase database = DatabaseFacade.getDatabase();
            AuctionItem item = database.getItemDetails(id);
            if(item != null){
                IRealtimeDatabase db = DatabaseFacade.getRealtimeDatabase();
                String url = config.getProperty("FIREBASE_URL");
                db.initializeUI(item, url);
            }
        }
    }

    /**
     * Publishes the acknowledgement of the NotifyBidders command
     * @param message The received message
     */
    @Override
    public void publishAcknowledgement(String message){
        String acknowledgement = "ACK " + message;
        _publisher.send(acknowledgement.getBytes());
        System.out.println("PUB: " + acknowledgement);
    }
}
