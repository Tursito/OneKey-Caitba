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
                  //EditText nombreForm = findViewById(R.id.nombre);
                  //String nombre = nombreForm.toString();
                  //EditText telefonoForm = findViewById(R.id.telefono);
                  //int telefono = Integer.parseInt(telefonoForm.toString());
                  //EditText codeForm = findViewById(R.id.licencia);
                  //String code = codeForm.toString();
                  //String licencia = "F672D1E5-52CD-470A-BC10-B8609D7E509D";
              class AsyncT extends AsyncTask<Void,Void,Void>{

                  @Override
                  protected Void doInBackground(Void... params) {

                      try {
                          URL url = new URL("https://www.icuadre.com/UAPI/set_api.ashx"); //Enter URL here
                          HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                          httpURLConnection.setDoOutput(true);
                          httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
                          httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
                          httpURLConnection.connect();

                          JSONObject jsonObject = new JSONObject();
                          jsonObject.put("para_1", "arg_1");

                          DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                          wr.writeBytes(jsonObject.toString());
                          wr.flush();
                          wr.close();
                          Log.d(String.valueOf(wr), "doInBackground: ");
                      } catch (MalformedURLException e) {
                          e.printStackTrace();
                      } catch (IOException e) {
                          e.printStackTrace();
                      } catch (JSONException e) {
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








