package com.jaime.airports.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jaime.airports.R;
import com.jaime.airports.adapters.AirportsAdapter;
import com.jaime.airports.interfaces.ListAirportsPresenter;
import com.jaime.airports.presenters.ListAirportsPresenterImpl;


public class ListAirportFragment extends Fragment implements ListAirportsPresenter.View {
    private ListView lvAirports;
    private ListAirportsPresenter mPresenter;
    private AirportsAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_airport, container, false);

        lvAirports = (ListView) rootView.findViewById(R.id.lv_airports);
        mPresenter = new ListAirportsPresenterImpl(this);
        mAdapter = new AirportsAdapter(getActivity());

        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvAirports.setAdapter(mAdapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        mPresenter.requestAllAirport();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_list, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setCursor(Cursor cursor) {
        mAdapter.changeCursor(cursor);
    }
}
