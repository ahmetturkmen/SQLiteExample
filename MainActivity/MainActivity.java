package com.ahmetturkmen.android.sqliteexample.MainActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;


import com.ahmetturkmen.android.sqliteexample.adapter.carsAdapter;
import com.ahmetturkmen.android.sqliteexample.database.Database;
import com.ahmetturkmen.android.sqliteexample.R;
public class MainActivity extends AppCompatActivity {

    private EditText nameEditText,surnameEditText,tcknoEditText,addressEditText;
    private Spinner carsSpinner;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        addCars();

        carsSpinner.setAdapter(new carsAdapter(getApplicationContext()));

    }

    private void addCars(){
        db.addCarToDatabase("CHEVROLET","CRUZE",0,2017);
        db.addCarToDatabase("HYUNDAI","I20",45788,2014);
        db.addCarToDatabase("RENAULT","TOROS",189543,1997);
        db.addCarToDatabase("KIA","RIO",135987,2006);
        db.addCarToDatabase("SEAT","LEON",121767,2014);
        db.addCarToDatabase("FIAT","DOBLO",54900,2010);
    }

    private void initViews(){
        nameEditText = (EditText) findViewById(R.id.activity_main_nameEditText);
        surnameEditText = (EditText) findViewById(R.id.activity_main_surnameEditText);
        tcknoEditText = (EditText) findViewById(R.id.activity_main_tcknoEditText);
        addressEditText = (EditText) findViewById(R.id.activity_main_addressEditText);
        carsSpinner = (Spinner) findViewById(R.id.activity_main_carsSpinner);
        db = new Database(getApplicationContext());
    }
}
