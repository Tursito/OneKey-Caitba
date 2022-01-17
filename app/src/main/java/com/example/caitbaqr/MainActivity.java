package com.example.caitbaqr;




import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;


import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {


    long ahora =System.currentTimeMillis();
    String deviceID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Recogemos el AID (solo una vez)
        deviceID = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);


        //Pasar a la actividad de formulario
        Button pasarForm = findViewById(R.id.pasarForm);
        pasarForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), form.class);
                startActivityForResult(intent, 0);
            }
        });



        //Creamos el hilo y le asignamos una clase.
        Thread myThread = null;
        Runnable runnable = new CountDownRunner();
        myThread= new Thread(runnable);
        myThread.start();

    }
    //En el hilo estaremos actualizando la fecha y hora constantemente, al hacer esto
    //La imagen QR también se actualiza
    public void doWork() {
        runOnUiThread(new Runnable() {
            public void run() {
                try{
                    //Fecha y hora
                    TextView txtCurrentTime= (TextView)findViewById(R.id.textView2);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss"), Locale,getDefault;
                    Date date = new Date();
                    String fecha = dateFormat.format(date);
                    txtCurrentTime.setText(deviceID+fecha);

                    //QR
                    ImageView imgQr = findViewById(R.id.qrCode);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.encodeBitmap(deviceID.toString()+fecha.toString(), BarcodeFormat.QR_CODE, 500, 500);
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
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }catch(Exception e){
                }
            }
        }
    }

}