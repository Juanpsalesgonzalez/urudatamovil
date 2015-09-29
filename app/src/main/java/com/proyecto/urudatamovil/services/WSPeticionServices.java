package com.proyecto.urudatamovil.services;

import com.proyecto.urudatamovil.objects.PeticionWebClient;
import com.proyecto.urudatamovil.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by juan on 01/08/15.
 * Modulo para las Peticiones. Centraliza en acceso a los datos de peticiones
 */
public class WSPeticionServices {

    @SuppressWarnings("unchecked")
    public PeticionWebClient setLicense(String user, String cookie, String initDate, String endDate, String descripcion) {
            String url;
            String url_user, url_idate, url_edate, url_desc;
            String sep = "?";    //url_separator
            String fsep = "&"; //url_field_separator
            url_user = "username=" + user;
            url_idate = "fechaini=" + initDate;
            url_edate = "fechafin=" + endDate;
            url_desc = "desc=" + descripcion;

            url = Constants.URL_SET_LICENSE;
            url = url + sep + url_user + fsep + url_idate + fsep + url_edate + fsep + url_desc;
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
            String estado="";
            try {
                JSONObject j = new JSONObject(s);
                petId=j.getLong("id");
                outId=j.getLong("idOutsourcer");
                estado=j.getString("status");
                descripcion=j.getString("descripcion");

            } catch (Exception e) {
                e.printStackTrace();
            }

            PeticionWebClient p = new PeticionWebClient(petId, initDate, endDate, outId, descripcion);
            p.setEstado(estado);
            p.setDescripcion(descripcion);
            return p;
        }
    @SuppressWarnings("unchecked")
    public  ArrayList<PeticionWebClient> listaPet(String user,String  cookie,String  fechaIni,String fechaFin,String  estado){

        /*String url = Constants.URL_LIST_PET;
        url = url + "?user=" + user;
        if (fechaIni != null){
            url=url + "&fechaIni" + fechaIni;
        }
        if (fechaFin != null){
            url=url + "&fechaFin" + fechaFin;
        }
        if (estado != null){
            url=url + "&estado" + estado;
        } */

        String url = Constants.URL_LIST_PET+"?user=" + user;

        RestTemplate rT = new RestTemplate(true);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        headers.set("Content-type", "application/x-www-form-urlencoded");
        headers.setContentEncoding(ContentCodingType.ALL);
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<ArrayList> response = null;
        rT.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        try {

            response = rT.exchange(url, HttpMethod.GET, requestEntity, ArrayList.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (response == null) {
            System.out.println("Response NULL");
            return null;
        }

        return  responseToArray(response);
    }

    public ArrayList<PeticionWebClient> responseToArray(ResponseEntity<ArrayList> response) {

        ArrayList peticionesBody = response.getBody();
        JSONArray petJSONArray = new JSONArray(peticionesBody);
        ArrayList<PeticionWebClient> peticiones = new ArrayList<>();


        for (int i = 0; i < petJSONArray.length(); i++) {
            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> petHashMap = (Map<String, Object>) petJSONArray.get(i);
                PeticionWebClient p = hashMapToPeticion(petHashMap);
                peticiones.add(i,p);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return peticiones;
    }

    public PeticionWebClient hashMapToPeticion(Map<String, Object> petHashMap){

        Long idLong,idOutsourcerLong;
        Integer id, idOutsourcer;
        String ids, descripcion, inicia, fin, status, comentarios, notas;
        System.out.println("hola");

        Object o = petHashMap.get("id");
        Class c=o.getClass();

        System.out.println("hola");
        id = (Integer) petHashMap.get("id");
        idLong = id.longValue();
        idOutsourcer= (Integer) petHashMap.get("idOutsourcer");
        idOutsourcerLong=idOutsourcer.longValue();
        descripcion= (String) petHashMap.get("descripcion");
        inicia= (String) petHashMap.get("inicia");
        fin= (String) petHashMap.get("fin");
        status= (String) petHashMap.get("status");

        PeticionWebClient pet = new PeticionWebClient(idLong,inicia,fin, idOutsourcerLong,descripcion);
        pet.setEstado(status);
        return pet;
    }
}