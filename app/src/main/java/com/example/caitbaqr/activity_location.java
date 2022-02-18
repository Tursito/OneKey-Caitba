package com.example.caitbaqr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class activity_location extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);


        String deviceID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);




        //Enviar información
        Button enviar = findViewById(R.id.enviar);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                //Clase asíncrona para la conexión con la API
                class AsyncT extends AsyncTask<Void,Void,Void> {

                    @Override
                    public Void doInBackground(Void... params) {

                        crearXML xml1 = new crearXML(deviceID);

                        xml1.getXMLLoad();
                        try {
                            xml1.httpsRequest(xml1.getXMLLoad().toString());
                            //Alterar la vista en una asyncTask
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    // Stuff that updates the UI
                                    String respuestaAPI = xml1.getRespuesta();
                                    TextView respuesta = findViewById(R.id.respuesta);
                                    respuesta.setText(respuestaAPI);
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        return null;
                    }



                }

                AsyncT asyncT = new AsyncT();
                asyncT.execute();

            }});



        //Volver al main_activity
        Button volver = findViewById(R.id.volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });




    }


}

