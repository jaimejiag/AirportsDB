package com.jaime.airports.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jaime.airports.AirportApplication;

/**
 * Created by jaime on 27/05/2017.
 */

/**
 * Clase encargada de crear la base de datos, añadir tablas o eliminarlas.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    //Aumentar el valor de esta constante cuando se haga una actualización en la estructura de la base de datos
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


    /**
     * Abre la base de datos y devuelve un objeto SQLiteDatabase para hacer la gestión pertinente
     * hacia la base de datos.
     * @return
     */
    public SQLiteDatabase openDatabase() {
        mDatabase = getWritableDatabase();
        return mDatabase;
    }


    /**
     * Al crear la base de datos se llama a este método.
     * Aquí es donde debe hacerse las operaciones de creación e inserción de una tabla.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Siempre hay que hacer una transacción cuando se ejecute más de una operación hacia la base de datos.
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


    /**
     * Método que se ejecutará cuando se aumente la versión de la base de datos,
     * para ello hay que modificar el valor de la constante DATABASE_VERSION.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseContract.AirportsEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }


    /**
     * Método que se ejecutará cuando se decremente la versión de la base de datos,
     * modificando el valor de la constante DATABASE_VERSION.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        onUpgrade(db, newVersion, oldVersion);
    }
}
