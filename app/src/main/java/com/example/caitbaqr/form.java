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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


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
                  String nombre = nombreForm.getText().toString();
                  EditText telefonoForm = findViewById(R.id.telefono);
              String tel = telefonoForm.getText().toString();
                  EditText codeForm = findViewById(R.id.licencia);
              String code = codeForm.getText().toString();
                  String licencia = "F672D1E5-52CD-470A-BC10-B8609D7E509D";



              class AsyncT extends AsyncTask<Void,Void,Void>{

                  @Override
                  protected Void doInBackground(Void... params) {
                      StringBuffer xmlData = new StringBuffer();


                      xmlData.append("<DATA>");

                      xmlData.append("<type>");
                      xmlData.append("50");
                      xmlData.append("</type>");

                      xmlData.append("<product>");
                      xmlData.append("CLOUDCAITBA");
                      xmlData.append("</product>");

                      xmlData.append("<licence>");
                      xmlData.append(licencia);
                      xmlData.append("</licence>");

                      xmlData.append("<token>");
                      xmlData.append("");
                      xmlData.append("</token>");

                      xmlData.append("<comand>");
                      xmlData.append("REGISTER");
                      xmlData.append("</comand>");

                      xmlData.append("<arguments>");

                      xmlData.append("<argument>");
                      xmlData.append("<name>");
                      xmlData.append("Nombre");
                      xmlData.append("</name>");
                      xmlData.append("<value>");
                      xmlData.append("<![CDATA["+nombre+"]]>");
                      xmlData.append("</value>");
                      xmlData.append("<type>");
                      xmlData.append("String");
                      xmlData.append("</type>");
                      xmlData.append("</argument>");

                      xmlData.append("<argument>");
                      xmlData.append("<name>");
                      xmlData.append("Telefono");
                      xmlData.append("</name>");
                      xmlData.append("<value>");
                      xmlData.append(tel);
                      xmlData.append("</value>");
                      xmlData.append("<type>");
                      xmlData.append("Integer");
                      xmlData.append("</type>");
                      xmlData.append("</argument>");

                      xmlData.append("<argument>");
                      xmlData.append("<name>");
                      xmlData.append("device-id");
                      xmlData.append("</name>");
                      xmlData.append("<value>");
                      xmlData.append("<![CDATA["+code+"]]>");
                      xmlData.append("</value>");
                      xmlData.append("<type>");
                      xmlData.append("String");
                      xmlData.append("</type>");
                      xmlData.append("</argument>");

                      xmlData.append("<argument>");
                      xmlData.append("<name>");
                      xmlData.append("device-id");
                      xmlData.append("</name>");
                      xmlData.append("<value>");
                      xmlData.append("<![CDATA["+deviceID+"]]>");
                      xmlData.append("</value>");
                      xmlData.append("<type>");
                      xmlData.append("String");
                      xmlData.append("</type>");
                      xmlData.append("</argument>");

                      xmlData.append("</arguments>");

                      xmlData.append("</DATA>");
                      try {
                          URL url = new URL("https://www.icuadre.com/UAPI/set_api.ashx"); //Enter URL here
                          HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                          httpURLConnection.setDoOutput(true);
                          httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
                          httpURLConnection.setRequestProperty("Content-Type", "application/xml"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
                          httpURLConnection.connect();


                            String datos = xmlData.toString();
                          DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());

                          // Write XML
                          OutputStream outputStream = httpURLConnection.getOutputStream();
                          byte[] b = datos.toString().getBytes("UTF-8");
                          outputStream.write(b);
                          outputStream.flush();
                          outputStream.close();
                          String pregunta = xmlData.toString();
                          Log.d(String.valueOf(wr), pregunta);

                          // Read XML
                          InputStream inputStream = httpURLConnection.getInputStream();
                          byte[] res = new byte[4096];
                          int i = 0;
                          StringBuilder response = new StringBuilder();
                          while ((i = inputStream.read(res)) != -1) {
                              response.append(new String(res, 0, i));
                          }
                          inputStream.close();

                          String respuesta = response.toString();
                          Log.d(String.valueOf(wr), respuesta);
                      } catch (MalformedURLException e) {
                          e.printStackTrace();
                      } catch (IOException e) {
                          e.printStackTrace();

                      }

                      return null;
                  }


              }

              AsyncT asyncT = new AsyncT();
              asyncT.execute();

              //crearXML xml = new crearXML(nombre,licencia,deviceID,code,telefono);

              //xml.getXML();

              //try {
              //  xml.httpsRequest("https://www.icuadre.com/UAPI/set_api.ashx", xml.getXML().toString());
              //  Toast toast = Toast.makeText(getApplicationContext(),"res",Toast.LENGTH_LONG);
              //  toast.show();
              //} catch (IOException e) {
              //  e.printStackTrace();
              //}






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








