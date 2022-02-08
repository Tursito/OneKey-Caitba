package com.example.caitbaqr;



import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import java.io.IOException;



public class form extends AppCompatActivity {

    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form2);


        String deviceID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        layout = findViewById(R.id.form_main);


        //Enviar información
        Button enviar = findViewById(R.id.enviar);
        enviar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              EditText nombreForm = findViewById(R.id.nombre);
              String nombre = nombreForm.toString();
              EditText telefonoForm = findViewById(R.id.telefono);
              int telefono = Integer.parseInt(telefonoForm.toString());
              EditText codeForm = findViewById(R.id.licencia);
              String code = codeForm.toString();
              String licencia = "F672D1E5-52CD-470A-BC10-B8609D7E509D";


              crearXML xml = new crearXML(nombre,licencia,deviceID,code,telefono);

              xml.getXML();

              try {
                  xml.httpsRequest("https://www.icuadre.com/UAPI/set_api.ashx", xml.getXML().toString());
              } catch (IOException e) {
                  e.printStackTrace();
              }

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








