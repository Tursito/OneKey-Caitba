package com.caitba.caitbaqr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class acercaDe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);

        String ver ="Versión 1.0";
        String nombreEmpresa ="CAITBA";
        String mailEmpresa = "comercial@caitba.com";

        TextView Empresa = findViewById(R.id.nombreEmpresa);
        Empresa.setText(nombreEmpresa);
        TextView mail = findViewById(R.id.mailEmpresa);
        mail.setText(mailEmpresa);

        TextView version = findViewById(R.id.version);
        version.setText(ver);

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