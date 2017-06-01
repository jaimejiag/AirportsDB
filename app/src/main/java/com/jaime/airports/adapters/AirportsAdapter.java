package com.jaime.airports.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.jaime.airports.R;
import com.jaime.airports.pojo.Airport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by usuario on 1/06/17.
 */

public class AirportsAdapter extends CursorAdapter {

    public AirportsAdapter(Context context) {
        super(context, null, FLAG_REGISTER_CONTENT_OBSERVER);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.airport_item, parent, false);
        AirportHolder holder = new AirportHolder();

        holder.txvCode = (TextView) rootView.findViewById(R.id.txv_airport_code);
        holder.txvCountry = (TextView) rootView.findViewById(R.id.txv_airport_country);
        holder.txvDate = (TextView) rootView.findViewById(R.id.txv_airport_date);
        rootView.setTag(holder);

        return rootView;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        AirportHolder holder = (AirportHolder) view.getTag();

        holder.txvCode.setText(cursor.getString(1));
        holder.txvCountry.setText(cursor.getString(2));
        holder.txvDate.setText(cursor.getString(3));
    }


    @Override
    public Object getItem(int position) {
        getCursor().moveToPosition(position);
        Date date = null;

        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            date = format.parse(getCursor().getString(3));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Airport airport = new Airport(getCursor().getInt(0),
                getCursor().getString(1),
                getCursor().getString(2),
                date,
                getCursor().getString(4));

        return airport;
    }


    private class AirportHolder {
        TextView txvCode;
        TextView txvCountry;
        TextView txvDate;
    }
}
