package com.example.caitbaqr;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class crearXML {
    private String nombre,licencia,Device_ID,code;
    private int telefono;

    public crearXML(String nombre, String licencia, String device_ID, String code, int telefono) {
        super();
        this.nombre = nombre;
        this.licencia = licencia;
        Device_ID = device_ID;
        this.code = code;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLicencia() {
        return licencia;
    }
    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }
    public String getDevice_ID() {
        return Device_ID;
    }
    public void setDevice_ID(String device_ID) {
        Device_ID = device_ID;
    }
    public int getTelefono() {
        return telefono;
    }
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    public StringBuffer getXML () {

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
        xmlData.append("<![CDATA["+Device_ID+"]]>");
        xmlData.append("</value>");
        xmlData.append("<type>");
        xmlData.append("String");
        xmlData.append("</type>");
        xmlData.append("</argument>");

        xmlData.append("</arguments>");

        xmlData.append("</DATA>");



        return xmlData;

    }



    public void httpsRequest(String URL, String datos) throws IOException {
        java.net.URL url = new URL(URL);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();


        connection.setDoOutput(true);

        connection.setRequestMethod("POST");

        // Write XML
        OutputStream outputStream = connection.getOutputStream();
        byte[] b = datos.toString().getBytes("UTF-8");
        outputStream.write(b);
        outputStream.flush();
        outputStream.close();

        // Read XML
        InputStream inputStream = connection.getInputStream();
        byte[] res = new byte[4096];
        int i = 0;
        StringBuilder response = new StringBuilder();
        while ((i = inputStream.read(res)) != -1) {
            response.append(new String(res, 0, i));
        }
        inputStream.close();

        String respuesta = response.toString();



    }




}
