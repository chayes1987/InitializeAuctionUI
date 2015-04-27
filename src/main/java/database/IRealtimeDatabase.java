package database;

import models.AuctionItem;

/**
 * @author Conor Hayes
 * Realtime Database Interface
 */
public interface IRealtimeDatabase {

    /**
     * Initialize User Interface
     * @param item The item to write
     * @param url The Firebase URL
     */
    void initializeUI(AuctionItem item, String url);
}
