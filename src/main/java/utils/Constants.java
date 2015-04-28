package utils;

import broker.Message_Broker;
import database.Database_Type;

/**
 * @author Conor Hayes
 * Constants class
 */
public class Constants {
    public static Message_Broker BROKER = Message_Broker.ZeroMq;
    public static Database_Type DATABASE = Database_Type.CouchDB;
    public static Database_Type REALTIME_DATABASE = Database_Type.Firebase;
}
