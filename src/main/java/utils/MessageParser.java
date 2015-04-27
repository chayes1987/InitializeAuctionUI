package utils;

/**
 * @author Conor Hayes
 * Message Parser
 */
public class MessageParser {

    /**
     * Parses message received
     * @param message The received message
     * @param startTag The starting delimiter
     * @param endTag The ending delimiter
     * @return The required string
     */
    public static String parseMessage(String message, String startTag, String endTag){
        int startIndex = message.indexOf(startTag) + startTag.length();
        String substring = message.substring(startIndex);
        return substring.substring(0, substring.lastIndexOf(endTag));
    }
}
