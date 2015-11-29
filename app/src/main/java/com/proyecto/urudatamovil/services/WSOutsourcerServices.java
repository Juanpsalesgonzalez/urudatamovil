package com.proyecto.urudatamovil.services;

import android.util.Log;

import com.proyecto.urudatamovil.objects.OutsourcerWebClient;
import com.proyecto.urudatamovil.utils.Constants;
import com.proyecto.urudatamovil.utils.HttpResponseUtils;

import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

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

    @SuppressWarnings("WeakerAccess")
    private OutsourcerWebClient getOutsourcer(String cookie, String user, String url) {

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
        } catch (Exception e) {
            Log.v(Constants.TAG, e.getMessage());
        }
        if (response == null) {
            return null;
        }
        return HttpResponseUtils.responseToOut(response);
    }

}
