package com.jaime.airports;

import android.app.Application;
import android.content.Context;

import com.jaime.airports.database.DatabaseManager;

/**
 * Created by jaime on 27/05/2017.
 */

public class AirportApplication extends Application {
    private static AirportApplication mInstance;


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
