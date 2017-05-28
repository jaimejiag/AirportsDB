package com.jaime.airports.database;

/**
 * Created by jaime on 27/05/2017.
 */

public class DatabaseManager {
    private static DatabaseManager mInstance;


    public DatabaseManager() {
        DatabaseHelper.getInstance().openDatabase();
    }


    public static synchronized DatabaseManager getInstance() {
        if (mInstance == null)
            throw new IllegalStateException("DatabaseManager haven't been initialized, call initialize");

        return mInstance;
    }


    public static synchronized void initialize(DatabaseManager databaseManager) {
        if (mInstance == null)
            mInstance = databaseManager;
    }
}
