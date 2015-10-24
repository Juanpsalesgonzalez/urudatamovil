package com.proyecto.urudatamovil.utils;

import android.util.Log;

import com.proyecto.urudatamovil.objects.OutsourcerWebClient;
import com.proyecto.urudatamovil.objects.PeticionWebClient;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by juan on 29/09/15.
 */
public class HttpResponseUtils {
    /* Convierte un httpresponse en un outsourcer */

    public static OutsourcerWebClient responseToOut(ResponseEntity<String> response ){

        if (response == null) {
            return null;
        }
        String body = response.getBody();
        String nombre;
        String id;
        String markIn;
        String markOut;
        String cel;
        String dir;
        String saldo;
        String cliente;
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
        } catch (Exception e) {
            Log.v(Constants.TAG, e.getMessage());
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

    public static ArrayList<PeticionWebClient> responseToArray(ResponseEntity<ArrayList> response) {

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
                Log.v(Constants.TAG, e.getMessage());
                return null;
            }
        }
        return peticiones;
    }

    public static PeticionWebClient hashMapToPeticion(Map<String, Object> petHashMap){

        Long idLong,idOutsourcerLong;
        Integer id, idOutsourcer;
        String  descripcion, inicia, fin, status ;

        Object o = petHashMap.get("id");

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
