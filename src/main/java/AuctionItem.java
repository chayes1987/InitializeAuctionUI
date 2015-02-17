/*
    @author Conor Hayes
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
