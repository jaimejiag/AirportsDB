package com.jaime.airports.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

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


    public Cursor getAllAirports() {
        SQLiteDatabase database = DatabaseHelper.getInstance().openDatabase();
        Cursor cursor = null;

        try {
            cursor = database.query(DatabaseContract.AirportsEntry.TABLE_NAME,
                    DatabaseContract.AirportsEntry.ALL_COLUMNS, null, null, null, null, null);
        } catch (SQLiteException e) {
            Log.d("Database ERROR", "Error on getAllAirports()");
        }

        return cursor;
    }
}
