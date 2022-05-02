


package com.caitba.caitbaqr;




        import androidx.appcompat.app.AppCompatActivity;


        import android.content.Intent;
        import android.os.Bundle;

        import android.provider.Settings;

        import android.text.TextUtils;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.RelativeLayout;


        import java.io.BufferedWriter;
        import java.io.File;
        import java.io.FileWriter;
        import java.math.BigInteger;
        import java.security.MessageDigest;


public class registro_usuario extends AppCompatActivity {

    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);


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

                EditText pwdForm = findViewById(R.id.pwd);
                String pwd =getSHA256( pwdForm.getText().toString());//Password sha
                EditText RepPwdForm = findViewById(R.id.repetir_pwd);
                String Repetirpwd =getSHA256( RepPwdForm.getText().toString());//Repetir Password sha


                String licencia = "F672D1E5-52CD-470A-BC10-B8609D7E509D";


                if(TextUtils.isEmpty(pwd)) {
                    pwdForm.setError("El campo Contraseña no puede estar vacio");
                    return;
                }
                if(TextUtils.isEmpty(Repetirpwd)) {
                    RepPwdForm.setError("El campo Repetir contraseña no puede estar vacio");
                    return;
                }
                if(TextUtils.equals(pwd,Repetirpwd)){

                }
                else{
                    pwdForm.setError("Las contraseñas no son iguales.");
                    return;
                }



                crearTXT(pwd);


            }});



        //Volver al main_activity
        Button volver = findViewById(R.id.volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), login_usuario.class);
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

    public void crearTXT(String pwd){

        try {
            File internalStorageDir = getFilesDir();
            File file = new File(internalStorageDir, "password.txt");
            //File file = new File("/storage/emulated/0/Download/password.txt");
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(pwd);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}










