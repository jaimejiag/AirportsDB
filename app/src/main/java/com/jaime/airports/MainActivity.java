package com.jaime.airports;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jaime.airports.fragments.ListAirportFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ListAirportFragment fragment = new ListAirportFragment();
        transaction.replace(R.id.layout_main, fragment).commit();
    }
}
