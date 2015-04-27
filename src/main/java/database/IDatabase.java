package database;

import models.AuctionItem;

import java.util.Properties;

/**
 * @author Conor Hayes
 * IDatabase
 */
public interface IDatabase {

    /**
     * Get Item
     * @param id The auction ID
     * @return The item matching the ID
     */
    AuctionItem getItemDetails(String id);
}
