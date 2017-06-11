package com.jaime.airports.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.jaime.airports.pojo.Airport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by jaime on 27/05/2017.
 */

/**
 * Clase encargada de ejecutar las operaciones CRUD de la base de datos.
 */
public class DatabaseManager {
    private static DatabaseManager mInstance;


    /**
     * Cuando se cree un objeto de esta clase, se abrirá la base de datos.
     * Sólo es necesario la primera vez que se vaya a crear la base de datos, sino es posible de que
     * no se cree.
     * Es el encargado de crear el DatabaseHelper.
     */
    public DatabaseManager() {
        DatabaseHelper.getInstance().openDatabase();
    }


    /**
     * Devuelve una instancia de esta clase, se sincroniza ya que puede haber varios hilos que quieran
     * acceder y gestionar la base de datos.
     * @return
     */
    public static synchronized DatabaseManager getInstance() {
        if (mInstance == null)
            throw new IllegalStateException("DatabaseManager haven't been initialized, call initialize");

        return mInstance;
    }


    /**
     * Inicializa la base de datos. Se llama una sola vez y crea una instancia de esta clase,
     * que a su vez crea una instancia de la clase DatabaseHelper en el constructor de esta clase,
     * al contructor de esta clase se llama cuando creas el objeto DatabaseManager por parámetro en este método.
     * Este método es llamado en la clase AirportApplication que se ejecutará al iniciar la app.
     * @param databaseManager
     */
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


    /**
     * Devuelve un cursor con todos lo aeropuertos que tengan el mismo código.
     * @param code
     * @return
     */
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


    public void deleteAirport(int id) {
        SQLiteDatabase database = DatabaseHelper.getInstance().openDatabase();
        String whereClause = DatabaseContract.AirportsEntry._ID + " = ?";
        String[] whereArgs = new String[] { String.valueOf(id) };

        try {
            database.delete(DatabaseContract.AirportsEntry.TABLE_NAME, whereClause, whereArgs);
        } catch (SQLiteException e) {
            Log.d("Delete exception", e.getMessage());
        }
    }


    public void updateAirport(Airport airport) {
        SQLiteDatabase database = DatabaseHelper.getInstance().openDatabase();
        String whereClause = DatabaseContract.AirportsEntry.COLUMN_CODE + " = ?";
        String[] whereArgs = new String[] { airport.getCode()};
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        ContentValues values = new ContentValues();

        try {
            values.put(DatabaseContract.AirportsEntry.COLUMN_CODE, airport.getCode());
            values.put(DatabaseContract.AirportsEntry.COLUMN_COUNTRY, airport.getCountry());
            values.put(DatabaseContract.AirportsEntry.COLUMN_DATE, format.format(airport.getDate()));
            values.put(DatabaseContract.AirportsEntry.COLUMN_NOTES, airport.getNotes());

            database.update(DatabaseContract.AirportsEntry.TABLE_NAME, values, whereClause, whereArgs);

        } catch (SQLiteException e) {
            Log.d("Update exception", e.getMessage());
        }
    }
}
