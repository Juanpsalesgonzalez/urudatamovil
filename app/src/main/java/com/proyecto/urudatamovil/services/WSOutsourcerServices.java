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

    public OutsourcerWebClient outByName(String cookie, String user) {

        String url;

        url = Constants.URL_CONFIRM;
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
        String body = response.getBody();
        String nombre = null;
        String id = null;
        String markIn = null;
        String markOut = null;
        String cel = null;
        String dir=null;
        try {
            JSONObject j = new JSONObject(body);
            nombre = j.get("nombre").toString();
            id = j.get("id").toString();
            markIn = j.get("markIn").toString();
            markOut = j.get("markOut").toString();
            dir=j.get("direccion").toString();
            cel=j.get("celular").toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        OutsourcerWebClient out = new OutsourcerWebClient(nombre, id);
        out.setMarkIn(markIn);
        out.setMarkOut(markOut);
        out.setCel(cel);
        out.setDireccion(dir);
        return out;

    }
}
