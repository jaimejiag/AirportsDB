package com.jaime.airports.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.jaime.airports.R;
import com.jaime.airports.interfaces.AddAirportPresenter;
import com.jaime.airports.pojo.Airport;
import com.jaime.airports.presenters.AddAirportPresenterImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class AddAirportFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private EditText edtCode;
    private EditText edtCountry;
    private EditText edtNotes;
    private Button btnDate;
    private Button btnAdd;

    private DatePickerDialog mDatePickerDialog;
    private Date mDate;
    private AddAirportPresenter mPresenter;
    private AddAirportListener mCallback;


    public interface AddAirportListener {
        void onListAirportsListener();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (AddAirportListener) activity;
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


    private void checkData() {
        ArrayList<String> errors = new ArrayList<>();

        if (edtCode.getText().toString().isEmpty())
            errors.add("CODIGO vacío");
        else if (edtCode.getText().toString().length() != 3)
            errors.add("CODIGO debe tener 3 carácteres");
        else if (mPresenter.isThisCodeTaken(edtCode.getText().toString()))
            errors.add("CODIGO ya está dado de alta");

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
            prepareToAddAirport();
    }


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


    private void prepareToAddAirport() {
        Airport airport = new Airport(edtCode.getText().toString(), edtCountry.getText().toString(),
                mDate, edtNotes.getText().toString());

        mPresenter.requestToAddAirport(airport);
        mCallback.onListAirportsListener();
    }
}
