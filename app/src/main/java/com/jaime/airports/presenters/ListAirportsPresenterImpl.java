package com.jaime.airports.presenters;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import com.jaime.airports.database.DatabaseManager;
import com.jaime.airports.interfaces.ListAirportsPresenter;
import com.jaime.airports.loaders.AirportsLoaderManager;

/**
 * Created by usuario on 1/06/17.
 */

/**
 * Clase que implementa la interfaz del presentador correspondiente.
 * También implementa la interfaz LoaderCallbacks<Cursor> que será responsable de la carga de datos
 * en segundo plano.
 */
public class ListAirportsPresenterImpl implements ListAirportsPresenter, LoaderManager.LoaderCallbacks<Cursor>{
    private static final int AIRPORTS = 1;
    private ListAirportsPresenter.View mView;
    private Context mContext;


    public ListAirportsPresenterImpl(ListAirportsPresenter.View view) {
        mView = view;
        mContext = view.getContext();
    }


    /**
     * Este método llamará al método onCreateLoader.
     * Se usa restartLoader, en vez de initLoader, para que se actualice en la lista de la vista.
     */
    @Override
    public void requestAllAirport() {
        ((Activity) mContext).getLoaderManager().restartLoader(AIRPORTS, null, this);
    }


    @Override
    public void requestToDeleteAirport(int id) {
        DatabaseManager.getInstance().deleteAirport(id);
    }


    /**
     * Este método ejecutará el método loadInBackground de nuestra clase AirportsLoaderManager.
     * @param id
     * @param args
     * @return
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader loader = null;

        switch (id) {
            case AIRPORTS:
                loader = new AirportsLoaderManager(mContext);
                break;
        }

        return loader;
    }


    /**
     * Método que se ejecuta cuando terminá de ejecutarse loadInBackground de nuestro Loader.
     * Llamará al método callback setCursor de la vista, pasando por parámetro el cursor con todos los
     * aeropuertos de la base de datos.
     * @param loader
     * @param data
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mView.setCursor(data);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mView.setCursor(null);
    }
}
