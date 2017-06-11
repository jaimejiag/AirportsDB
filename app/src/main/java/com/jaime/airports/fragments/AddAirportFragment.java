package com.jaime.airports.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.jaime.airports.MainActivity;
import com.jaime.airports.R;
import com.jaime.airports.interfaces.AddAirportPresenter;
import com.jaime.airports.pojo.Airport;
import com.jaime.airports.presenters.AddAirportPresenterImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Fragment encargado de añadir y actualizar un aeropuerto.
 * En el caso de actualizar se desabilita la posibilidad de modificar el código del aeropuerto.
 * Implementa la interfaz OnDateSetListener que gestiona la fecha que has elegido en el DatePickerDialog.
 */
public class AddAirportFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    public static final String UPDATE_KEY = "updatekey";
    public static final String UPDATE_NOTIFICATION_KEY = "updatenotificationkey";
    //El valor de esta constante debe coincidir con el valor del Action del Receiver en el manifest.
    public static final String BROADCAST_UPDATE_AIRPORT = "com.jaime.airports.UPDATE_AIRPORT";

    private EditText edtCode;
    private EditText edtCountry;
    private EditText edtNotes;
    private Button btnDate;
    private Button btnAdd;

    private DatePickerDialog mDatePickerDialog;
    private Date mDate;
    private AddAirportPresenter mPresenter;
    private AddAirportListener mCallback;


    /**
     * Interfaz con los métodos callback que se sobreescibirán en el MainActivity.
     */
    public interface AddAirportListener {
        void onListAirportsListener();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (AddAirportListener) activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_add_airport, container, false);

        edtCode = (EditText) viewRoot.findViewById(R.id.edtCode);
        edtCountry = (EditText)viewRoot.findViewById(R.id.edtCity);
        edtNotes = (EditText) viewRoot.findViewById(R.id.edtNotes);
        btnDate = (Button) viewRoot.findViewById(R.id.btnDate);
        btnAdd = (Button) viewRoot.findViewById(R.id.btnAdd);

        mDatePickerDialog = new DatePickerDialog(getActivity(), this, 2017, 0, 1);
        mDate = null;
        mPresenter = new AddAirportPresenterImpl();

        return viewRoot;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            Airport airport = getArguments().getParcelable(UPDATE_KEY);
            edtCode.setText(airport.getCode());
            edtCode.setEnabled(false);
            edtCountry.setText(airport.getCountry());
            mDate = airport.getDate();
            edtNotes.setText(airport.getNotes());
            btnAdd.setText("Actualizar");
        }

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatePickerDialog.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
            }
        });
    }


    /**
     * Este método se encargará de guardar el valor de la fecha elegida en el DatePickerDialog.
     * @param datePicker
     * @param year
     * @param month el mes empieza por el valor 0 (Enero = 0).
     * @param day
     */
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String date = day + "/" + (month + 1) + "/" + year;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        try {
            mDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    /**
     * Comprueba que los datos han sido introducidos correctamente.
     */
    private void checkData() {
        ArrayList<String> errors = new ArrayList<>();

        if (getArguments() == null) {
            if (edtCode.getText().toString().isEmpty())
                errors.add("CODIGO vacío");
            else if (edtCode.getText().toString().length() != 3)
                errors.add("CODIGO debe tener 3 carácteres");
            else if (mPresenter.isThisCodeTaken(edtCode.getText().toString()))
                errors.add("CODIGO ya está dado de alta");
        }

        if (edtCountry.getText().toString().isEmpty())
            errors.add("PAIS vacío");

        if (mDate == null)
            errors.add("FECHA vacía");
        else if (new Date().after(mDate)) {
            if (mDate.getDate() != new Date().getDate())
                errors.add("FECHA es anterior a la actual");
        }

        if (!errors.isEmpty())
            showErrorsDialog(errors);
        else
            prepareAirport();
    }


    /**
     * Si los datos no han sido introducidos correctamente en la vista, mostrará este cuadro de diálogo.
     * @param errors
     */
    private void showErrorsDialog(ArrayList<String> errors) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String errorsMessage = "";

        for (int i = 0; i < errors.size(); i++)
            errorsMessage += errors.get(i) + "\n";

        builder.setTitle("Información no válida")
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setMessage(errorsMessage)
                .show();
    }


    /**
     * Este método se encarga de añadir o actualizar el aeropuerto en la base de datos, llamando al
     * método del presenter encargado de las operaciones pertinentes.
     * Si esté fragment no tiene asociado ningún dato (argument) añadirá, sino actualizará.
     * En el caso de añadir se creará una notificación.
     * En el caso de actualizar se enviará un Broadcast.
     */
    private void prepareAirport() {
        Airport airport = new Airport(edtCode.getText().toString(), edtCountry.getText().toString(),
                mDate, edtNotes.getText().toString());

        if (getArguments() == null) {
            mPresenter.requestToAddAirport(airport);
            sendNotification(airport);
        } else {
            mPresenter.requestToUpdateAirport(airport);

            Intent intent = new Intent();
            intent.setAction(BROADCAST_UPDATE_AIRPORT);
            getActivity().sendBroadcast(intent);
        }

        mCallback.onListAirportsListener();
    }


    /**
     * Método encargardo de enviar una notificación.
     * Cuando se pulse en la notificación volverá a cargarse este fragment para actualizar los datos
     * del aeropuerto recien creado.
     * @param airport
     */
    private void sendNotification(Airport airport) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setSmallIcon(R.drawable.ic_aeropuerto)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText("Aeropuerto - " + airport.getCode())
                .setContentInfo("Ir al aeropuerto");

        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra(UPDATE_NOTIFICATION_KEY, airport);

        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),
                MainActivity.NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }
}
