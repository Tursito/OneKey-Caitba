package com.example.caitbaqr;



import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class form extends AppCompatActivity {

    RelativeLayout layout;

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
              //Recogemos la información del formulario
                  EditText nombreForm = findViewById(R.id.nombre);
                    String nombre = nombreForm.getText().toString();
                  EditText telefonoForm = findViewById(R.id.telefono);
                    String tel = telefonoForm.getText().toString();
                  EditText codeForm = findViewById(R.id.code);
                    String  code = codeForm.getText().toString();

                  String licencia = "F672D1E5-52CD-470A-BC10-B8609D7E509D";
              if(TextUtils.isEmpty(nombre)) {
                  nombreForm.setError("El campo nombre no puede estar vacio");
                  return;
              }

              if(TextUtils.isEmpty(tel)) {
                  telefonoForm.setError("El campo Teléfono no puede estar vacio");
                  return;
              }

              if(TextUtils.isEmpty(code)) {
                  codeForm.setError("El campo Código de seguridad no puede estar vacio");
                  return;
              }




                  crearXML xml1 = new crearXML(nombre,licencia,deviceID,code,tel);

//Clase asíncrona para la conexión con la API
                  class AsyncT extends AsyncTask<Void,Void,Void>{

                      @Override
                      public Void doInBackground(Void... params) {


                          xml1.getXMLRegister();
                          try {
                              xml1.httpsRequest(xml1.getXMLRegister().toString());
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








