import org.junit.Assert;
import org.junit.Test;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @author Conor Hayes
 */

/**
 * This class tests some of the methods in the InitializeAuctionUI class
 */
public class TestInitializeAuctionUI {

    /**
     * Tests the parseMessage method
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    public void testParseMessage() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        InitializeAuctionUI ui = new InitializeAuctionUI();
        // Use reflection to gain access to private method
        Method parseMessage = InitializeAuctionUI.class.getDeclaredMethod("parseMessage",
                String.class, String.class, String.class);
        parseMessage.setAccessible(true);
        // Call method and perform assertion
        String returnValue = (String) parseMessage.invoke(ui, "StartAuction <id>1</id>",
                "<id>", "</id>");
        Assert.assertEquals("1", returnValue);
        returnValue = (String) parseMessage.invoke(ui, "BidPlaced <params>2000</params>",
                "<params>", "</params>");
        Assert.assertEquals("2000", returnValue);
    }

    /**
     * Tests the readConfig method
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    public void testReadConfig() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        InitializeAuctionUI ui = new InitializeAuctionUI();
        // Use reflection to gain access to private method
        Method readConfig = InitializeAuctionUI.class.getDeclaredMethod("readConfig");
        readConfig.setAccessible(true);
        // Call method and perform assertion
        Properties returnValue = (Properties) readConfig.invoke(ui);
        Assert.assertNotEquals(null, returnValue);
        Assert.assertEquals("InitializeAuctionUI", returnValue.getProperty("SERVICE_NAME"));
        Assert.assertEquals("AuctionRunning", returnValue.getProperty("TOPIC"));
    }
}
