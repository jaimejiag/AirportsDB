package com.jaime.airports.database;

import android.provider.BaseColumns;

/**
 * Created by jaime on 27/05/2017.
 */

/**
 * Clase que contiene todas las constantes con las sentencias SQL que crean la base de datos
 * y realizan las operaciones de insercción de los datos.
 *
 * Por cada tabla de la BD se crea una clase interna que corresponde con cada tabla.
 *
 * Todas las clases internas heredan de BaseColumns porque tiene automáticamente
 * el id necesario para trabajar con SQLite.
 */
public class DatabaseContract {

    private DatabaseContract () {}


    public static class AirportsEntry implements BaseColumns {
        public static final String TABLE_NAME = "airports";
        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_COUNTRY = "country";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_NOTES = "notes";

        public static final String SQL_CREATE_ENTRIES = String.format("CREATE TABLE %s (" +
                "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "%s TEXT UNIQUE NOT NULL, " +
                "%s TEXT NOT NULL, " +
                "%s TEXT NOT NULL, " +
                "%s TEXT NOT NULL)",
                TABLE_NAME, BaseColumns._ID,
                COLUMN_CODE,
                COLUMN_COUNTRY,
                COLUMN_DATE,
                COLUMN_NOTES);

        public static final String SQL_DELETE_ENTRIES = String.format("DROP TABLE %s", TABLE_NAME);

        //Constante para mockear la base de datos en su creación, para así mostrar un datos al menos.
        public static final String SQL_INSERT_ENTRY = String.format("INSERT INTO %s " +
                "(%s, %s, %s, %s) VALUES ('AGP', 'España', '27/05/2017', 'Un aeropuerto boquerón')",
                TABLE_NAME,
                COLUMN_CODE,
                COLUMN_COUNTRY,
                COLUMN_DATE,
                COLUMN_NOTES);

        public static final String[] ALL_COLUMNS = new String[] {BaseColumns._ID, COLUMN_CODE,
                COLUMN_COUNTRY, COLUMN_DATE, COLUMN_NOTES};
    }
}
