package com.example.caitbaqr;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;



public class form extends AppCompatActivity {

    LinearLayout layout;

   // String nombre_fichero = "/storage/emulated/0/Documents/prueba.xml";

    String server = "https://icuadre.com/uapi/set_api.ashx";//API

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form2);

        String deviceID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        layout = findViewById(R.id.form_main);

        EditText nombreForm = findViewById(R.id.nombre);
        EditText telefonoForm = findViewById(R.id.telefono);
        EditText licenciaForm = findViewById(R.id.licencia);





    //Enviar información
                Button enviar = findViewById(R.id.enviar);
                enviar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Guardamos los datos
                        String nombre = nombreForm.getText().toString();
                        String telefono = telefonoForm.getText().toString();
                        String licencia = licenciaForm.getText().toString();

                        //Creamos un buffer con el xml dentro

                        StringBuffer xmlData = new StringBuffer();

                        xmlData.append("<DATA>");

                        xmlData.append("<type>");
                        xmlData.append("98");
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

                        xmlData.append("<command>");
                        xmlData.append("REGISTER");
                        xmlData.append("</command>");

                        xmlData.append("<arguments>");

                        xmlData.append("<argument>");
                        xmlData.append("<name>");
                        xmlData.append("Nombre");
                        xmlData.append("</name>");
                        xmlData.append("<value>");
                        xmlData.append(nombre);
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
                        xmlData.append(telefono);
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
                        xmlData.append(deviceID);
                        xmlData.append("</value>");
                        xmlData.append("<type>");
                        xmlData.append("String");
                        xmlData.append("</type>");

                        xmlData.append("</argument>");

                        xmlData.append("</DATA>");





                        //Lo enviamos al servidor




                        //Mensaje de enviado
                        Snackbar snackbar = Snackbar.make(layout, "Enviado al servidor.", BaseTransientBottomBar.LENGTH_SHORT);
                        snackbar.show();
    }
});








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