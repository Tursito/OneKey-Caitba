package com.example.caitbaqr;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;

import java.util.Date;


public class MainActivity extends AppCompatActivity {


    String deviceID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Recogemos el AID (solo una vez)
        deviceID = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);

        //Creamos el hilo y le asignamos una clase.
        //Este hilo se usa para el QR.
        Thread myThread = null;
        Runnable runnable = new CountDownRunner();
        myThread= new Thread(runnable);
        myThread.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void getMenuInflater(int main_menu, Menu menu) {
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.config:
                //Ir a la actividad de form.class
                Intent myIntent = new Intent(getApplicationContext(), form.class);
                startActivityForResult(myIntent, 0);
                return true;
            case R.id.ubi:
                //Ir a activity_location.class

                Intent myIntent1 = new Intent(getApplicationContext(), activity_location.class);
                startActivityForResult(myIntent1, 0);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //En el hilo estaremos actualizando la fecha y hora constantemente, al hacer esto
    //La imagen QR también se actualiza
    public void doWork() {
        runOnUiThread(new Runnable() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            public void run() {
                try{
                    //Fecha y hora
                    
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss"), Locale,getDefault;
                    Date date = new Date();
                    String fecha = dateFormat.format(date);


                    //QR
                    ImageView imgQr = findViewById(R.id.qrCode);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();

                    String trama =deviceID.toString()+fecha.toString();//String a encriptar
                    String tramaEncriptada = Utils.AES256.encrypt(trama);// Encriptado


                    String qrDesencriptado = Utils.AES256.decrypt(tramaEncriptada);//Desencriptado
                    Bitmap bitmap = barcodeEncoder.encodeBitmap(tramaEncriptada, BarcodeFormat.QR_CODE, 500, 500);// mapa QR
                    imgQr.setImageBitmap(bitmap);//Creamos el QR



                }catch (Exception e) {}
            }
        });
    }

    //Clase para activar el código de arriba, cuando se ejecuta espera X milisegundos
    //Y la vuelve a ejecutar hasta interrumpir el hilo
    class CountDownRunner implements Runnable{
        // @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    doWork();
                    Thread.sleep(5000);//Cada 5 segundos recarga el QR.
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }catch(Exception e){
                }
            }
        }
    }

}