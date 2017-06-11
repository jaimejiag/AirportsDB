package com.jaime.airports.loaders;

import android.content.Context;
import android.database.Cursor;
import android.content.CursorLoader;

import com.jaime.airports.database.DatabaseManager;

/**
 * Created by usuario on 1/06/17.
 */

/**
 * Clase que hereda de CursorLoader responsable de cargar los aeropuerto en segundo plano.
 */
public class AirportsLoaderManager extends CursorLoader {

    public AirportsLoaderManager(Context context) {
        super(context);
    }


    /**
     * Método que se ejecuta en segundo plano y que devuelve un cursor con todos los aeropuertos
     * que hay en la base de datos.
     * @return
     */
    @Override
    public Cursor loadInBackground() {
        return DatabaseManager.getInstance().getAllAirports();
    }
}
