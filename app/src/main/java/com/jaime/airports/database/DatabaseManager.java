package com.jaime.airports.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.jaime.airports.pojo.Airport;

import java.text.SimpleDateFormat;

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


    public Cursor getAllAirportsCodes(String code) {
        SQLiteDatabase database = DatabaseHelper.getInstance().openDatabase();
        Cursor cursor = null;
        String selection = DatabaseContract.AirportsEntry.COLUMN_CODE + " = ?";
        String[] selecttionArgs = new String[] { code };

        try {
            cursor = database.query(DatabaseContract.AirportsEntry.TABLE_NAME,
                    DatabaseContract.AirportsEntry.ALL_COLUMNS, selection, selecttionArgs,
                    null, null, null);
        } catch (SQLiteException e) {
            Log.d("Database error", "Error on getAllAirportsCodes()");
        }

        return cursor;
    }


    public void addAirport(Airport airport) {
        SQLiteDatabase database = DatabaseHelper.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        try {
            values.put(DatabaseContract.AirportsEntry.COLUMN_CODE, airport.getCode());
            values.put(DatabaseContract.AirportsEntry.COLUMN_COUNTRY, airport.getCountry());
            values.put(DatabaseContract.AirportsEntry.COLUMN_DATE, format.format(airport.getDate()));
            values.put(DatabaseContract.AirportsEntry.COLUMN_NOTES, airport.getNotes());

            database.insert(DatabaseContract.AirportsEntry.TABLE_NAME, null, values);
        } catch (SQLiteException e) {
            Log.d("Insert exception", e.getMessage());
        }
    }
}
