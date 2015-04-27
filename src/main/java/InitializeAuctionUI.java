import java.io.FileInputStream;
import java.util.Properties;
import broker.BrokerFacade;
import broker.IBroker;

/**
 * @author Conor Hayes
 */

/*
    Config -> http://www.mkyong.com/java/java-properties-file-examples/
    Coding Standards -> http://www.oracle.com/technetwork/java/codeconvtoc-136057.html
 */

/**
 * Initialize Auction UI
 */
public class InitializeAuctionUI {

    /**
     * Main Method
     * @param args Command line args
     */
    public static void main(String[] args){
        InitializeAuctionUI ia = new InitializeAuctionUI();
        Properties config = ia.readConfig();

        // Check the configuration
        if(config != null) {
            // Create Pub/Sub manager
            IBroker broker = BrokerFacade.getBroker();
            broker.subscribeToHeartbeat(config);
            broker.subscribeToAuctionRunningEvt(config);
        }
    }

    /**
     * Read the configuration file
     * @return The contents of the file in a properties object, null if exception
     */
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
}
