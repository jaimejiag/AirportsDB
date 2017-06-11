package com.jaime.airports.interfaces;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by usuario on 1/06/17.
 */

public interface ListAirportsPresenter {

    /**
     * Interfaz con los métodos necesarios para la vista.
     */
    interface View {
        Context getContext();
        void setCursor(Cursor cursor);
    }

    void requestAllAirport();
    void requestToDeleteAirport(int id);
}
