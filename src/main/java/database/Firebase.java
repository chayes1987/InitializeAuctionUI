package database;

import models.AuctionItem;
import java.util.HashMap;
import java.util.Map;

/*
    The official documentation was consulted for the third party library Firebase used in this class
    Firebase -> https://www.firebase.com/docs/java-api/javadoc/com/firebase/client/Firebase.html
 */

/**
 * @author Conor Hayes
 * Firebase
 */
public class Firebase implements IRealtimeDatabase {

    /**
     * Initializes the UI
     * @param item The item to write
     * @param url The Firebase URL
     */
    @Override
    public void initializeUI(AuctionItem item, String url) {
        // Create the reference to Firebase
        com.firebase.client.Firebase fb = new com.firebase.client.Firebase(url)
                .child("auctions/" + item.get_id());
        // Create map object to write
        Map<String, Object> auction = new HashMap<>();
        auction.put("_id", item.get_id());
        auction.put("name", item.getName());
        auction.put("image", item.getImage());
        auction.put("current_bid", item.getStarting_bid());
        auction.put("status", "Pending");
        auction.put("winner", "");
        // Write the record to Firebase
        try {
            fb.updateChildren(auction);
            // Allow time for the write to execute (Issue in Java)
            Thread.sleep(2000);
            System.out.println("Auction UI initialized");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
