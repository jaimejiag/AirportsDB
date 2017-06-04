package com.jaime.airports.presenters;

import android.database.Cursor;

import com.jaime.airports.database.DatabaseManager;
import com.jaime.airports.interfaces.AddAirportPresenter;
import com.jaime.airports.pojo.Airport;

/**
 * Created by jaime on 04/06/2017.
 */

public class AddAirportPresenterImpl implements AddAirportPresenter {

    @Override
    public boolean isThisCodeTaken(String code) {
        Cursor cursor = DatabaseManager.getInstance().getAllAirportsCodes(code);
        return cursor.getCount() > 0;
    }


    @Override
    public void requestToAddAirport(Airport airport) {
        DatabaseManager.getInstance().addAirport(airport);
    }
}
