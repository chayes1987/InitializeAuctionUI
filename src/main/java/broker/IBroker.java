package broker;

import java.util.Properties;

/**
 * @author Conor Hayes
 * IBroker interface
 */
public interface IBroker {

    /**
     * Subscribe To Heartbeat
     * @param config The configuration file
     */
    void subscribeToHeartbeat(Properties config);

    /**
     * Subscribe To Auction Running Event
     * @param config The configuration file
     */
    void subscribeToAuctionRunningEvt(Properties config);

    /**
     * Publish Acknowledgement
     * @param message The message to publish
     */
    void publishAcknowledgement(String message);
}
