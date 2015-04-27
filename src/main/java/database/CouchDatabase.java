package database;

import models.AuctionItem;
import org.lightcouch.CouchDbClient;

/*
    CouchDB -> http://www.lightcouch.org/lightcouch-guide.html#setup
    Coding Standards -> http://www.oracle.com/technetwork/java/codeconvtoc-136057.html
 */

/**
 * @author Conor Hayes
 * Mongo Database
 */
public class CouchDatabase implements IDatabase{

    /**
     * Retrieve the item details from the database
     * @param id The ID of the auction
     * @return The item
     */
    public AuctionItem getItemDetails(String id) {
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
