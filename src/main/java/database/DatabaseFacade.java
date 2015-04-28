package database;

import utils.Constants;

/**
 * @author Conor Hayes
 * Database Facade
 */
public class DatabaseFacade {
    private static IDatabase database;
    private static IRealtimeDatabase realtime_database;

    /**
     * Initializes the database
     * @return The database to be used
     */
    public static IDatabase getDatabase(){
        if (Constants.DATABASE == Database_Type.CouchDB){
            database = new CouchDatabase();
        }
        // Other Databases may be added
        return database;
    }

    /**
     * Initializes the realtime database
     * @return The database to be used
     */
    public static IRealtimeDatabase getRealtimeDatabase(){
        if (Constants.REALTIME_DATABASE == Database_Type.Firebase){
            realtime_database = new Firebase();
        }
        // Other Databases may be added
        return realtime_database;
    }
}
