import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestInitializeAuctionUI {
    
    @Test
    public void testParseMessage() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        InitializeAuctionUI ui = new InitializeAuctionUI();
        Method parseMessage = InitializeAuctionUI.class.getDeclaredMethod("parseMessage", String.class, String.class, String.class);
        parseMessage.setAccessible(true);
        String returnValue = (String) parseMessage.invoke(ui, "StartAuction <id>1</id>", "<id>", "</id>");
        Assert.assertEquals("1", returnValue);
        returnValue = (String) parseMessage.invoke(ui, "BidPlaced <params>2000</params>", "<params>", "</params>");
        Assert.assertEquals("2000", returnValue);
    }
}
