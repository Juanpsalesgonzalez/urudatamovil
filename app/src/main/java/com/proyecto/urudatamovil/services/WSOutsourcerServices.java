package com.proyecto.urudatamovil.services;

import com.proyecto.urudatamovil.objects.OutsourcerWebClient;
import com.proyecto.urudatamovil.utils.Constants;

import org.json.JSONObject;
import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by juan on 01/08/15.
 */
public class WSOutsourcerServices {

    public OutsourcerWebClient confirma(String cookie, String user){
        String url=Constants.URL_CONFIRM;
        OutsourcerWebClient o =  getOutsourcer(cookie, user,url);
        if (o==null){
            return null;
        }
        return o;
    }

    public OutsourcerWebClient outByUser(String cookie, String user){
        String url=Constants.URL_OUTBYUSER;
        OutsourcerWebClient o =  getOutsourcer(cookie, user,url);
        if (o==null){
            return null;
        }
        return o;
    }

    public OutsourcerWebClient getOutsourcer(String cookie, String user, String url) {

        url = url + "?username=" + user;
        RestTemplate rT = new RestTemplate(true);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        headers.set("Content-type", "application/x-www-form-urlencoded");
        headers.setContentEncoding(ContentCodingType.ALL);
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<String> response = null;
        rT.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        try {
            response = rT.exchange(url, HttpMethod.GET, requestEntity, String.class);
            // TO DO  JACKSON Converter
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response == null) {
            return null;
        }
        return responseToOut(response);
    }
/* Convierte un httpresponse en un outsourcer */

     public OutsourcerWebClient responseToOut(ResponseEntity<String> response ){

         if (response == null) {
             return null;
         }
         String body = response.getBody();
         String nombre  = null;
         String id      = null;
         String markIn  = null;
         String markOut = null;
         String cel     = null;
         String dir     = null;
         String saldo   = null;
         String cliente = null;
         try {
             JSONObject outJSON = new JSONObject(body);

             nombre  =  outJSON.get("nombre").toString();
             id      =  outJSON.get("id").toString();
             markIn  =  outJSON.get("markIn").toString();
             markOut =  outJSON.get("markOut").toString();
             dir     =  outJSON.get("direccion").toString();
             cel     =  outJSON.get("celular").toString();
             saldo   =  outJSON.get("saldo").toString();
             cliente =  outJSON.get("cliente").toString();
             //saldo = "10";
             //cliente = "consejo de formacion en educacion";
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }

         OutsourcerWebClient out = new OutsourcerWebClient(nombre, id);
         out.setMarkIn(markIn);
         out.setMarkOut(markOut);
         out.setCel(cel);
         out.setDireccion(dir);
         out.setSaldo(saldo);
         out.setCliente(cliente);
         return out;
     }
}
