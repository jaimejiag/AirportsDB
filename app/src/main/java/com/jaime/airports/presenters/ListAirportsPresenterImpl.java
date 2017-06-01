package com.jaime.airports.presenters;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import com.jaime.airports.interfaces.ListAirportsPresenter;
import com.jaime.airports.loaders.AirportsLoaderManager;

/**
 * Created by usuario on 1/06/17.
 */

public class ListAirportsPresenterImpl implements ListAirportsPresenter, LoaderManager.LoaderCallbacks<Cursor>{
    private static final int AIRPORTS = 1;
    private ListAirportsPresenter.View mView;
    private Context mContext;


    public ListAirportsPresenterImpl(ListAirportsPresenter.View view) {
        mView = view;
        mContext = view.getContext();
    }


    @Override
    public void requestAllAirport() {
        ((Activity) mContext).getLoaderManager().restartLoader(AIRPORTS, null, this);
    }


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


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mView.setCursor(data);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mView.setCursor(null);
    }
}
