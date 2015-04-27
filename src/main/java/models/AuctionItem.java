package models;

/**
 * @author Conor Hayes
 * Auction Item
 */
public class AuctionItem {
    private String _id, _rev, name, image;
    private double starting_bid;

    /**
     * Get Revision
     * @return The revision
     */
    public String get_rev() { return _rev; }

    /**
     * Get ID
     * @return The ID
     */
    public String get_id() { return _id; }

    /**
     * Get Starting Bid
     * @return The starting bid
     */
    public double getStarting_bid() { return starting_bid; }

    /**
     * Get Image
     * @return The image
     */
    public String getImage() { return image; }

    /**
     * Get Name
     * @return The name
     */
    public String getName() { return name; }
}
