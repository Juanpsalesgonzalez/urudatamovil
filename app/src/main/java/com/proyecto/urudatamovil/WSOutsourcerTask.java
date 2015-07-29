package com.proyecto.urudatamovil;

/**
 * Created by juan on 15/02/15.
 */


import android.app.Activity;
import android.os.AsyncTask;


public class WSOutsourcerTask extends AsyncTask <String, String, OutsourcerWebClient> {

    private OutNameActivity actividad;

    public WSOutsourcerTask(Activity a){
        actividad = (OutNameActivity) a;
    }


    @Override
    protected OutsourcerWebClient doInBackground(String... params){

        String cookie;
        String  user, pass;

        user=params[0];
        pass=params[1];

        cookie=WSServices.getCookie(WSServices.loginToWS(user, pass));
        if (cookie ==null){

            return null;
        }
        OutsourcerWebClient outsourcer = WSServices.outByName(cookie, user);
        if (outsourcer ==null){
            return null;
        }
        return outsourcer;

    }


    @Override
    protected void onPostExecute(OutsourcerWebClient outsourcer) {
        if (outsourcer == null) {
            actividad.setStatus("Error de Login");
        }else {
            String nombre = outsourcer.getNombre();
            String id = outsourcer.getId();
            actividad.setName(nombre);
            actividad.setId(id);
            actividad.setStatus("Marca realizada");

        }

    }
}

