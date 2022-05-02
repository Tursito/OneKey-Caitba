package com.caitba.caitbaqr;



import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.provider.Settings;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;




import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;


public class registro_ubicaciones extends AppCompatActivity {

    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_ubicaciones);


        String deviceID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);




        String sha256 = getSHA256(deviceID).toUpperCase();

        layout = findViewById(R.id.form_main);



        //Enviar información
        Button enviar = findViewById(R.id.enviar);
        String finalSha256 = sha256;
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Recogemos la información del formulario
                EditText nombreForm = findViewById(R.id.nombre);
                String nombre = nombreForm.getText().toString();
                EditText telefonoForm = findViewById(R.id.telefono);
                String tel = telefonoForm.getText().toString();
                EditText codeForm = findViewById(R.id.code);
                String  code = codeForm.getText().toString().toUpperCase();



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





                XML xml1 = new XML(nombre,licencia, finalSha256,code,tel);

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


                                    xml1.registro(respuestaAPI);
                                    String respuestaParseada = xml1.getRespuestaParseada();



                                    String codeError = xml1.CodeRegistro(respuestaAPI);
                                    int codeErrorParseado = Integer.parseInt (codeError);
                                    System.out.println("Codigo de error : "+codeError);

                                    Toast toast = Toast.makeText(getApplicationContext(), respuestaParseada, Toast.LENGTH_LONG);
                                    toast.show();

                                    if (codeErrorParseado == 1){//1 Significa que se ha registrado con éxito
                                        System.out.println("El registro funciona");
                                        Intent intent = new Intent(view.getContext(), registroOK.class);
                                        startActivityForResult(intent, 0);
                                    }

                                    else{System.out.println("Hay un problema con el registro");

                                          Intent intent = new Intent(view.getContext(), registroNotOK.class);
                                          startActivityForResult(intent, 0);
                                    }


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
    public static String getSHA256(String input){

        String toReturn = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update(input.getBytes("utf8"));
            toReturn = String.format("%064x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return toReturn;
    }

}








