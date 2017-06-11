package com.jaime.airports.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jaime.airports.R;
import com.jaime.airports.adapters.AirportsAdapter;
import com.jaime.airports.interfaces.ListAirportsPresenter;
import com.jaime.airports.pojo.Airport;
import com.jaime.airports.presenters.ListAirportsPresenterImpl;


/**
 * Fragment encargado de listar aeropuertos, implementa la interfaz View del presenter.
 */
public class ListAirportFragment extends Fragment implements ListAirportsPresenter.View {
    private ListView lvAirports;
    private ListAirportsPresenter mPresenter;
    private AirportsAdapter mAdapter;
    private ListAirportListener mCallback;


    /**
     * Interfaz con los callbacks sobre el MainActivity.
     */
    public interface ListAirportListener {
        void onAddAirportListener();
        void onUpdateAirportListener(Airport airport);
    }


    /**
     * Antes de crear este fragment se asocia la interfaz ListAirportListener al MainActivity
     * que implementa esa misma interfaz, con la posibilidad de ejecutar los callbacks.
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (ListAirportListener) activity;
    }


    /**
     * Al crear el fragment indicamos que va a gestionar un menú propio en el action bar
     * con el método setHasOptionsMenu(true).
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }


    /**
     * Asociamos todos los campos view de este fragment con los componentes de la vista.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_airport, container, false);

        lvAirports = (ListView) rootView.findViewById(R.id.lv_airports);
        mPresenter = new ListAirportsPresenterImpl(this);
        mAdapter = new AirportsAdapter(getActivity());

        return rootView;
    }


    /**
     * Una vez asociados los campos de este fragment con los componentes de la vista, gestionamos
     * su uso.
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvAirports.setAdapter(mAdapter);

        //Registramos un menú contextual al ListView.
        registerForContextMenu(lvAirports);

        lvAirports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Airport airport = (Airport) adapterView.getItemAtPosition(i);

                //Este método de la interfaz llamará al mismo método en el MainActivity.
                mCallback.onUpdateAirportListener(airport);
            }
        });
    }


    /**
     * Después de haber creado toda la vista se ejecuta este método.
     * Aquí es donde se llamará al método del presentador encargado de obtener todos los aeropuertos
     * de la base de datos.
     */
    @Override
    public void onStart() {
        super.onStart();
        mPresenter.requestAllAirport();
    }


    /**
     * Crea el menú del action bar.
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    /**
     * Gestiona la selección del menú en el action bar.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_add:
                mCallback.onAddAirportListener();
                break;
        }

        return true;
    }


    /**
     * Crea el menú contextual, en este caso asociado al ListView que lanzará el menú contextual
     * al hacer una pulsación alargada sobre él.
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Selecciona");
        getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }


    /**
     * Gestiona la seleción del menú contextual.
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete:
                //Con AdapterContextMenuInfo obtenemos toda la información sobre qué item hemos pulsado, del adapter.
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Airport airport = (Airport) mAdapter.getItem(info.position);
                showDeleteDialog(airport.getId());
        }

        return true;
    }


    /**
     * Método de la interfaz View del presenter que está implementado en este fragment.
     * Se encarga de cambiar los cursores del adapter y cargar sus datos.
     * @param cursor
     */
    @Override
    public void setCursor(Cursor cursor) {
        mAdapter.changeCursor(cursor);
    }


    /**
     * Muestra un cuadro de diálogo que pide confirmación sobre si eliminar o no un aeropuerto.
     * @param id
     */
    private void showDeleteDialog(final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Eliminar")
                .setMessage("¿Estás seguro que deseas eliminar el aeropuerto?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.requestToDeleteAirport(id);
                        mPresenter.requestAllAirport();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
}
