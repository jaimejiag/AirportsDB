package com.jaime.airports.loaders;

import android.content.Context;
import android.database.Cursor;
import android.content.CursorLoader;

import com.jaime.airports.database.DatabaseManager;

/**
 * Created by usuario on 1/06/17.
 */

public class AirportsLoaderManager extends CursorLoader {

    public AirportsLoaderManager(Context context) {
        super(context);
    }


    @Override
    public Cursor loadInBackground() {
        return DatabaseManager.getInstance().getAllAirports();
    }
}
