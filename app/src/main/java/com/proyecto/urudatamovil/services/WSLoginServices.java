package com.proyecto.urudatamovil.services;

import com.proyecto.urudatamovil.utils.Constants;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by juan on 01/08/15.
 */
public class WSLoginServices {

    public ResponseEntity loginToWS(String user, String pass) {

        String url = Constants.URL_LOGIN_PROCESS;
        RestTemplate rT = new RestTemplate(true); //Construye con Default Content Handlers

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "application/x-www-form-urlencoded");
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("username", user);
        body.add("password", pass);

        HttpEntity entity = new HttpEntity(body, headers);
        ResponseEntity<ResponseEntity>response = null;

        try {
            response = rT.exchange(url, HttpMethod.POST, entity, ResponseEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
         if(response == null) {
            return null;
        }
        HttpStatus httpstatus = response.getStatusCode();
        if (httpstatus.value() != 302) {
            return null;
        }

        HttpHeaders receivedHeaders = response.getHeaders();
        List<String> headerFieldValue = receivedHeaders.get("Location");
        String Field = headerFieldValue.get(0);
        if (Field.contains("login_error")){
            return null;
        }

        return response;
    }

    public String getCookie(ResponseEntity response) {

        String cookieField, cookieValue;
        String[] cookieSubFields;
        if (response!=null) {
            try {
                HttpHeaders headers = response.getHeaders();
                List<String> headerFieldValue = headers.get("Set-Cookie");
                cookieField = headerFieldValue.get(0);
                cookieSubFields = cookieField.split(";");//
                cookieValue = cookieSubFields[0];  // Devuelve en formato JSESSIONID=xxxxxxxxx
                return cookieValue;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;

    }
}
