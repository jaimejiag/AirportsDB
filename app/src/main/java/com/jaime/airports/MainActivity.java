package com.jaime.airports;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jaime.airports.fragments.AddAirportFragment;
import com.jaime.airports.fragments.ListAirportFragment;
import com.jaime.airports.pojo.Airport;


/**
 * Activity principal encargador de cargar los fragments.
 * Implementa las interfaces callback de los fragments.
 */
public class MainActivity extends AppCompatActivity implements ListAirportFragment.ListAirportListener,
        AddAirportFragment.AddAirportListener {
    public static final int NOTIFICATION_REQUEST_CODE = 1;


    /**
     * Crea el activity. Si el intent asociado tiene un objeto aeropuerto, llamará al método encargado
     * de cargar el fragment para editarlo, esto sucederá si el usuario toca la notificación que emergerá
     * al añadir un aeropuerto nuevo, sino cargará el fragment encargado de listar los aeropuetos.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getParcelableExtra(AddAirportFragment.UPDATE_NOTIFICATION_KEY) != null) {
            Airport airport = getIntent().getParcelableExtra(AddAirportFragment.UPDATE_NOTIFICATION_KEY);
            onUpdateAirportListener(airport);
        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ListAirportFragment fragment = new ListAirportFragment();
            transaction.replace(R.id.layout_main, fragment).commit();
        }
    }


    /**
     * Método callback que cargará el fragment encargado de añadir un aeropuerto.
     */
    @Override
    public void onAddAirportListener() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        AddAirportFragment fragment = new AddAirportFragment();
        transaction.replace(R.id.layout_main, fragment).commit();
    }


    /**
     * Método callback que cargará el fragment encargado de editar un aeropuerto.
     * @param airport
     */
    @Override
    public void onUpdateAirportListener(Airport airport) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        AddAirportFragment fragment = new AddAirportFragment();
        Bundle bundle = new Bundle();

        bundle.putParcelable(fragment.UPDATE_KEY, airport);
        fragment.setArguments(bundle);
        transaction.replace(R.id.layout_main, fragment).commit();
    }


    /**
     * Método callback que cargará el fragment encargado de listar los aeropuertos.
     */
    @Override
    public void onListAirportsListener() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ListAirportFragment fragment = new ListAirportFragment();
        transaction.replace(R.id.layout_main, fragment).commit();
    }
}
