package com.caitba.caitbaqr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Scanner;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Enviar información
        Button enviar = findViewById(R.id.enviar);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                EditText pwdForm = findViewById(R.id.pwd);
                String pwd = getSHA256(pwdForm.getText().toString());
                System.out.println("Contraseña del form "+ pwd);




                String pwdTxt="";

                File internalStorageDir = getFilesDir();

                try {
                    pwdTxt = new Scanner(new File(internalStorageDir, "password.txt")).useDelimiter("\\Z").next();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println("Contraseña del txt "+pwdTxt);

                if(pwd.equals(pwdTxt)){
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    startActivityForResult(intent, 0);
                }
                else{pwdForm.setError("La contraseña no es correcta.");}

            }


        });


        Button registro = findViewById(R.id.registro);
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Register.class);
                startActivityForResult(intent, 0);
            }
        });
        Button volverRegistro = findViewById(R.id.volverRegistro);
        volverRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), advertencia_volver_registro.class);
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
    }}