package com.caitba.caitbaqr;




import static com.caitba.caitbaqr.form.getSHA256;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

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
    String milis = "1000";
    int cnt = 30;
    boolean inf = true;

    String fSalt = "kdsjh/8sdjhsdjhsd";
    private static final String SECRET_KEY = "NJ3rjs8nfJD67nmcJdNS78d9";


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
    //Menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
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
            case R.id.acercaDe:
                //Ir a activity_location.class

                Intent myIntent2 = new Intent(getApplicationContext(), acercaDe.class);
                startActivityForResult(myIntent2, 0);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    /*En el hilo estaremos actualizando la fecha y hora constantemente,
     al hacer esto la imagen QR también se actualiza*/
    public void doWork() {
        runOnUiThread(new Runnable() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            public void run() {


                if (cnt >4){

                    try {

                        Utils utils = new Utils();
                        //Fecha y hora
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmmss"), Locale,getDefault;
                        Date date = new Date();
                        String fecha = dateFormat.format(date);

                        //QR
                        ImageView imgQr = findViewById(R.id.qrCode);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();


                        String deviceIDSHA = getSHA256(deviceID).toUpperCase();

                        String trama =deviceIDSHA.toString()+" "+fecha.toString();//String a encriptar

                        String tramaEncriptada = utils.encrypt(SECRET_KEY, fSalt, trama);

                        //String qrDesencriptado = utils.decrypt(SECRET_KEY, fSalt, tramaEncriptada);

                        Bitmap bitmap = barcodeEncoder.encodeBitmap(tramaEncriptada, BarcodeFormat.QR_CODE, 600, 600);// mapa QR
                        imgQr.setImageBitmap(bitmap);//Creamos el QR
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                    cnt = 0;

                }

                try{
                    //Fecha y hora para el usuario
                    SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
                    Date date1 = new Date();
                    String fecha1 = dateFormat1.format(date1);

                    TextView t =findViewById(R.id.hora);
                    t.setText(fecha1);
                    cnt +=1;

                }catch (Exception e) {
                    e.printStackTrace();
                }

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
                    Thread.sleep(Long.parseLong(milis));//Cada x segundos recarga el QR.
                    cnt+=1;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }catch(Exception e){
                }
            }
        }
    }




}