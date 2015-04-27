package utils;

import broker.MESSAGE_BROKER;
import database.DATABASE_TYPE;

/**
 * @author Conor Hayes
 * Constants class
 */
public class Constants {
    public static MESSAGE_BROKER BROKER = MESSAGE_BROKER.ZeroMq;
    public static DATABASE_TYPE DATABASE = DATABASE_TYPE.CouchDB;
    public static DATABASE_TYPE REALTIME_DATABASE = DATABASE_TYPE.Firebase;
}
