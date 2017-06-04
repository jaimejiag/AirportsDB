package com.jaime.airports.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jaime.airports.AirportApplication;

/**
 * Created by jaime on 27/05/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "airports.db";
    private static DatabaseHelper mInstance;
    private SQLiteDatabase mDatabase;


    public DatabaseHelper() {
        super(AirportApplication.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }


    public static DatabaseHelper getInstance() {
        if (mInstance == null)
            mInstance = new DatabaseHelper();

        return mInstance;
    }


    public SQLiteDatabase openDatabase() {
        mDatabase = getWritableDatabase();
        return mDatabase;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();

        try {
            db.execSQL(DatabaseContract.AirportsEntry.SQL_CREATE_ENTRIES);
            db.execSQL(DatabaseContract.AirportsEntry.SQL_INSERT_ENTRY);

            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.d("DB ERROR", "Fallo en la creación e inserción de tablas");
        } finally {
            db.endTransaction();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseContract.AirportsEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }


    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        onUpgrade(db, newVersion, oldVersion);
    }
}
