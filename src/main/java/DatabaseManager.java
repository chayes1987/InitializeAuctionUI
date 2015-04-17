import org.lightcouch.CouchDbClient;

/*
    @author Conor Hayes
 */

public class DatabaseManager {

    public static AuctionItem getItemDetails(String id) {
        AuctionItem item;
        try{
            CouchDbClient client = new CouchDbClient("couchdb.properties");
            item = client.find(AuctionItem.class, id);
        }catch(Exception e) {
            System.out.println("Error retrieving item");
            return null;
        }
        return item;
    }
}
