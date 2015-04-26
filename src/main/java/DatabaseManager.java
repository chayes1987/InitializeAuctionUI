import org.lightcouch.CouchDbClient;

/**
 * @author Conor Hayes
 */

/*
    CouchDB -> http://www.lightcouch.org/lightcouch-guide.html#setup
    Coding Standards -> http://www.oracle.com/technetwork/java/codeconvtoc-136057.html
 */

/**
 * This class handles all database interaction
 */
public class DatabaseManager {

    /**
     * Retrieve the item details from the database
     * @param id The ID of the auction
     * @return The item
     */
    public static AuctionItem getItemDetails(String id) {
        AuctionItem item;
        try{
            // Uses the couchdb.properties file to setup connection
            CouchDbClient client = new CouchDbClient("couchdb.properties");
            item = client.find(AuctionItem.class, id);
        }catch(Exception e) {
            System.out.println("Error retrieving item");
            return null;
        }
        return item;
    }
}
