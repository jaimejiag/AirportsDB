package com.jaime.airports;

import android.app.Application;
import android.content.Context;

import com.jaime.airports.database.DatabaseManager;

/**
 * Created by jaime on 27/05/2017.
 */

public class AirportApplication extends Application {
    private static AirportApplication mInstance;


    /**
     * Al abrir la app se inicializará DatabaseManager, que es la clase encargada del manejo de los
     * métodos CRUD de la base de datos.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseManager.initialize(new DatabaseManager());
    }


    public AirportApplication() {
        mInstance = this;
    }


    public static Context getContext() {
        return mInstance;
    }
}
