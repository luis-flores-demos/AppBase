package com.example.luisfm.appbase.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.example.luisfm.appbase.R;
import com.example.luisfm.appbase.helpers.Utilities;
import com.example.luisfm.appbase.interfaz.IAlertConfiguration;
import com.example.luisfm.appbase.interfaz.IGenericEvents;
import com.example.luisfm.appbase.model.CActivity;
import com.example.luisfm.appbase.model.CEditText;
import com.example.luisfm.appbase.model.CSpinner;
import com.example.luisfm.appbase.widget.LEditText;

import java.util.ArrayList;

public class MainActivity extends CActivity {

    Button validate;
    LEditText edit_prueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        validate = (Button) findViewById(R.id.validate);
        edit_prueba = (LEditText) findViewById(R.id.edit_prueba);
        edit_prueba.activateFormatCurrency();
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_prueba.isValid();
            }
        });

    }

    public void ejemploEditText(){

        //cEditText1.setCurrencyFormat("$", 1000, 2500);

        //cEditText2.setCurrencyFormat("", 50, 500);

    }

    public void ejemploSpinner(){


        ArrayList<CSpinner.ItemSpiner> datos = new ArrayList<CSpinner.ItemSpiner>();
        datos.add(new CSpinner.ItemSpiner("Seleccionar"));

        for (int i = 5; i <= 20; i++)
            datos.add(new CSpinner.ItemSpiner(i , "Item =>" + i));

        /*cSpinner.setAdpter(context, true, datos, new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, "Actualizar => " + cSpinner.getItemId(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

    }

    public void ejemploAlerta(){

        Utilities.launchAlert(context, "Prueba de Alerta",
                getResources().getString(R.string.text_message_alert), false, null, null, true,
                new IAlertConfiguration() {
                    @Override
                    public void toAccept() {
                        Utilities.longOperation(new IGenericEvents() {
                            @Override
                            public Object doInBackground() {
                                int i = 0;

                                while ( i < 50000000)
                                    i++;

                                return null;
                            }

                            @Override
                            public void onPostExecute(Object result) {
                                blockScreen(false);
                            }

                            @Override
                            public void onPreExecute() {
                                blockScreen(true);
                            }

                            @Override
                            public void onProgressUpdate() {

                            }

                            @Override
                            public void onPreExecuteError() {

                            }
                        });
                    }

                    @Override
                    public void toCancel() {

                    }});
    }

}
