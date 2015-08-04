package com.proyecto.urudatamovil.services;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.proyecto.urudatamovil.objects.PeticionWebClient;
import com.proyecto.urudatamovil.utils.Constants;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Autor: JPS - 26/04/15.
 * Servicios de cliente Web Services
 */


public class WSCertificadoServices {


   public static PeticionWebClient setCertificate(String cookie, String petId, String cert) {
       setCert(cookie,petId,cert);
       PeticionWebClient p = new PeticionWebClient(petId);
       return p;
   }

    public static String setCert(String cookie, String petId, String cert) {

       // Convertir a png comprimido.
       Bitmap bitmap=null;
       File f= new File(cert);
       ByteArrayOutputStream bos = new ByteArrayOutputStream();
       BitmapFactory.Options options = new BitmapFactory.Options();
       options.inPreferredConfig = Bitmap.Config.ARGB_8888;

       try {
           bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
           bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }

       String filename = "Certificado_" + petId + ".png";
       ContentBody contentPart = new ByteArrayBody(bos.toByteArray(), filename);

       // Convertido

       MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
       reqEntity.addPart("picture", contentPart);
 //      String response = multipost("http://server.com", reqEntity);
       try {
           URL url = new URL(Constants.URL_UPLOAD_CERT + "?petId=" + petId );
           //  HttpResponse response = null;

           HttpURLConnection conn = (HttpURLConnection) url.openConnection();

           conn.setReadTimeout(10000);
           conn.setConnectTimeout(15000);
           conn.setRequestMethod("POST");
           conn.setUseCaches(false);
           conn.setDoInput(true);
           conn.setDoOutput(true);

           conn.setRequestProperty("Cookie", cookie);

           conn.setRequestProperty("Connection", "Keep-Alive");
           conn.addRequestProperty("Content-length", reqEntity.getContentLength()+"");
           conn.addRequestProperty(reqEntity.getContentType().getName(), reqEntity.getContentType().getValue());

           OutputStream os = conn.getOutputStream();
           reqEntity.writeTo(conn.getOutputStream());
           os.close();
           conn.connect();
            String s = conn.getResponseMessage().toString();
           if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
               return readStream(conn.getInputStream());
           }

       } catch (Exception e) {
           e.printStackTrace();
          // Log.e(TAG, "multipart post error " + e + "(" + urlString + ")");
       }
       return null;
   }

    private static String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }


}
