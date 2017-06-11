package com.jaime.airports.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


/**
 * Created by jaime on 11/06/2017.
 */

/**
 * Clase que maneja los BroadcastReceiver, se debe declarar en el Manifest.
 */
public class UpdateAirportReceiver extends BroadcastReceiver {

    /**
     * Método encargado de gestionar los Action que recibe nuestro BroadcastReceiver.
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Datos insertados con éxito", Toast.LENGTH_SHORT).show();
    }
}
