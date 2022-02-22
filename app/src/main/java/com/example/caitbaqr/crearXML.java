package com.example.caitbaqr;

import android.util.Log;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class crearXML {
    private String nombre;
    private String licencia;
    private String Device_ID;
    private int code;
    private String telefono;
    private String respuesta;

    private String RespuestaParseada;

    public crearXML(String nombre, String licencia, String device_ID, int code, String telefono) {
        super();
        this.nombre = nombre;
        this.licencia = licencia;
        Device_ID = device_ID;
        this.code = code;
        this.telefono = telefono;
    }

    public crearXML(String device_ID){
        super();
        Device_ID = device_ID;
    }


    public crearXML() {
        super();

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
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRespuesta() {
        return respuesta;
    }

public String getRespuestaParseada(){return RespuestaParseada;}

    public StringBuffer getXMLRegister () {

        StringBuffer xmlData = new StringBuffer();




        xmlData.append("<data>");

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

        xmlData.append("<command>");
        xmlData.append("REGISTER");
        xmlData.append("</command>");

        xmlData.append("<arguments>");

        xmlData.append("<argument>");
        xmlData.append("<name>");
        xmlData.append("nombre");
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
        xmlData.append("mov");
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
        xmlData.append("code");
        xmlData.append("</name>");
        xmlData.append("<value>");
        xmlData.append(code);
        xmlData.append("</value>");
        xmlData.append("<type>");
        xmlData.append("Integer");
        xmlData.append("</type>");
        xmlData.append("</argument>");

        xmlData.append("<argument>");
        xmlData.append("<name>");
        xmlData.append("device_id");
        xmlData.append("</name>");
        xmlData.append("<value>");
        xmlData.append("<![CDATA["+Device_ID+"]]>");
        xmlData.append("</value>");
        xmlData.append("<type>");
        xmlData.append("String");
        xmlData.append("</type>");
        xmlData.append("</argument>");

        xmlData.append("</arguments>");

        xmlData.append("</data>");


        return xmlData;

    }


    public StringBuffer getXMLLoad () {
        StringBuffer xmlData = new StringBuffer();
        xmlData.append("<data>");

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

        xmlData.append("<command>");
        xmlData.append("LOAD_ACCESS");
        xmlData.append("</command>");

        xmlData.append("<arguments>");
        xmlData.append("<argument>");
        xmlData.append("<name>");
        xmlData.append("device_id");
        xmlData.append("</name>");
        xmlData.append("<value>");
        xmlData.append("<![CDATA["+Device_ID+"]]>");
        xmlData.append("</value>");
        xmlData.append("<type>");
        xmlData.append("String");
        xmlData.append("</type>");
        xmlData.append("</argument>");
        xmlData.append("</arguments>");

        xmlData.append("</data>");
        return xmlData;

    }


    public String httpsRequest(String datos) throws IOException {

        try {
            URL url = new URL("https://www.icuadre.com/UAPI/set_apimov.ashx"); //API de Juan
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST"); //  "POST", "PUT", "GET", "DELETE" etc.
            httpURLConnection.setRequestProperty("Content-Type", "application/xml"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
            httpURLConnection.connect();



            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());

            // Write XML
            OutputStream outputStream = httpURLConnection.getOutputStream();
            byte[] b = datos.getBytes("UTF-8");
            outputStream.write(b);
            outputStream.flush();
            outputStream.close();
            String pregunta = datos;
            Log.d(String.valueOf(wr), pregunta);

            // Read XML
            InputStream inputStream = httpURLConnection.getInputStream();
            byte[] res = new byte[4096];
            int i = 0;
            StringBuilder response = new StringBuilder();
            while ((i = inputStream.read(res)) != -1) {
                response.append(new String(res, 0, i));
            }
            inputStream.close();

            this.respuesta = response.toString();
            Log.d(String.valueOf(wr), respuesta);




        } catch (
                MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }

        return respuesta;
    }

    public String ubicaciones(String respuesta){
        DocumentBuilder db = null;
        try {
            db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(respuesta));

        Document doc = null;
        try {
            doc = db.parse(is);
        } catch (SAXException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("Root element: " + doc.getDocumentElement().getNodeName()); //Mostramos el elemento root.
        NodeList nodes = doc.getElementsByTagName("install");

        System.out.println(" ");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);

            NodeList name = element.getElementsByTagName("name");
            Element line1 = (Element) name.item(0);

            NodeList fecha_desde = element.getElementsByTagName("fecha_desde");
            Element line = (Element) fecha_desde.item(0);

            NodeList fecha_hasta = element.getElementsByTagName("fecha_hasta");
            Element line2 = (Element) fecha_hasta.item(0);

            NodeList perfil = element.getElementsByTagName("perfil_accesos");
            Element line3 = (Element) perfil.item(0);
            NodeList estado = element.getElementsByTagName("estado");
            Element line4 = (Element) estado.item(0);


            System.out.println("Nombre: " + getCharacterDataFromElement(line1));
            System.out.println("Fecha desde: " + getCharacterDataFromElement(line));
            System.out.println("Fecha hasta: " + getCharacterDataFromElement(line2));
            System.out.println("Perfil de accceso: " + getCharacterDataFromElement(line3));
            System.out.println("Estado : " + getCharacterDataFromElement(line4));


             RespuestaParseada =
                    "Nombre: " + getCharacterDataFromElement(line1)+"\n"+
                            "Fecha desde: " + getCharacterDataFromElement(line)+"\n"+
                            "Fecha hasta: " +getCharacterDataFromElement(line2)+"\n"+
                            "Perfil de accceso: " + getCharacterDataFromElement(line3)+"\n"+
                            "Estado : " + getCharacterDataFromElement(line4)
                    ;



        }

        return RespuestaParseada;

    }



    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }

    }















