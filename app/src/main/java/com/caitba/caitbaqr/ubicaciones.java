package com.caitba.caitbaqr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import java.io.IOException;

public class ubicaciones extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizaciones);


        String deviceID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        //Clase asíncrona para la conexión con la API
        class AsyncT extends AsyncTask<Void,Void,Void> {

            @Override
            public Void doInBackground(Void... params) {

                XML xml1 = new XML(deviceID);

                xml1.getXMLLoad();
                try {
                    xml1.httpsRequest(xml1.getXMLLoad().toString());

                    //Alterar la vista en una asyncTask
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            // Alteramos la vista (UI)

                            String respuestaAPI = xml1.getRespuesta();
                            xml1.ubicaciones(respuestaAPI);
                            String respuestaParseada = xml1.getRespuestaParseada();



                            TextView respuesta = findViewById(R.id.respuesta);




                            respuesta.setText(respuestaParseada);
                            System.out.println("resp "+respuestaParseada);
                            ;
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

