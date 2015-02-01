import org.jeromq.ZMQ;

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
        }
    }

    private void publishAcknowledgement(String message){
        String acknowledgements = "ACK: " + message;
        publisher.send(acknowledgements.getBytes());
        System.out.println("ACK SENT...");
    }
}
