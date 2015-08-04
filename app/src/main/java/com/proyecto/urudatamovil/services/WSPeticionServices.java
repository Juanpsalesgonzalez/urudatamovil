package com.proyecto.urudatamovil.services;

import com.proyecto.urudatamovil.objects.PeticionWebClient;
import com.proyecto.urudatamovil.utils.Constants;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.json.JSONObject;
import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by juan on 01/08/15.
 */
public class WSPeticionServices {

    public static PeticionWebClient setLicense(String user, String cookie, String initDate, String endDate, String comment) {
            String url;
            String url_user, url_idate, url_edate, url_comm;
            String sep = "?";    //url_separator
            String fsep = "&"; //url_field_separator
            url_user = "username=" + user;
            url_idate = "fechaini=" + initDate;
            url_edate = "fechafin=" + endDate;
            url_comm = "comm=" + comment;

            url = Constants.URL_SET_LICENSE;
            url = url + sep + url_user + fsep + url_idate + fsep + url_edate + fsep + url_comm;
            System.out.println("URL " + url);
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
                e.printStackTrace();
            }

            if (response == null) {
                System.out.println("Response NULL");
                return null;
            }
            System.out.println(response.toString());
            String s = response.getBody();
            Long outId = null;
            Long petId = null;
            try {
                JSONObject j = new JSONObject(s);
                petId=j.getLong("id");
                outId=j.getLong("idOutsourcer");
            } catch (Exception e) {
                e.printStackTrace();
            }

            PeticionWebClient p = new PeticionWebClient(petId, initDate, endDate, outId, comment);

            return p;
        }
}
