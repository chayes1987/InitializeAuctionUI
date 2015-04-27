package broker;

import utils.Constants;

/**
 * @author Conor Hayes
 * Broker Facade
 */
public class BrokerFacade {
    private static IBroker broker;

    /**
     * Initializes the broker
     * @return The broker to be used
     */
    public static IBroker getBroker(){
        if (Constants.BROKER == MESSAGE_BROKER.ZeroMq){
            broker = new ZeroMqBroker();
        }
        // Other Brokers may be added
        return broker;
    }
}