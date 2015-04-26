/**
 * @author Conor Hayes
 */

/*
    Coding Standards -> http://www.oracle.com/technetwork/java/codeconvtoc-136057.html
 */

/**
 * This class is a model that represents an item in the database
 */
public class AuctionItem {
    private String _id, _rev, name, image;
    private double starting_bid;
    public String get_rev() { return _rev; }

    public String get_id() { return _id; }

    public double getStarting_bid() { return starting_bid; }

    public String getImage() { return image; }

    public String getName() { return name; }
}
