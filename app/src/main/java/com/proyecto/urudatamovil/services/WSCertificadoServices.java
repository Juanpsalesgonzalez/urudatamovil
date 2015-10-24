package com.proyecto.urudatamovil.services;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

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


   public PeticionWebClient setCertificate(String cookie, String petId, String cert) {
       setCert(cookie,petId,cert);
       return new PeticionWebClient(petId);
   }

    public String setCert(String cookie, String petId, String cert) {

       Bitmap bitmap;
       File f= new File(cert);
       ByteArrayOutputStream bos = new ByteArrayOutputStream();
       BitmapFactory.Options options = new BitmapFactory.Options();
       options.inPreferredConfig = Bitmap.Config.ARGB_8888;

       try {
           bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
           bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
       } catch (FileNotFoundException e) {
           Log.v(Constants.TAG, e.getMessage());
       }

       String filename = "Certificado_" + petId + ".png";
       ContentBody contentPart = new ByteArrayBody(bos.toByteArray(), filename);


       MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
       reqEntity.addPart("picture", contentPart);
       try {
           URL url = new URL(Constants.URL_UPLOAD_CERT + "?petId=" + petId );

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
           if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
               return readStream(conn.getInputStream());
           }

       } catch (Exception e) {

           Log.e(Constants.TAG, "multipart post error ");
       }
       return null;
   }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            Log.v(Constants.TAG, e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.v(Constants.TAG, e.getMessage());
                }
            }
        }
        return builder.toString();
    }


}
