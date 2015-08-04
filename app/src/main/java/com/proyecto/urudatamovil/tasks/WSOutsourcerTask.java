package com.proyecto.urudatamovil.tasks;

/**
 * Created by juan on 15/02/15.
 */


import android.app.Activity;
import android.os.AsyncTask;

import com.proyecto.urudatamovil.activities.OutsourcerActivity;
import com.proyecto.urudatamovil.objects.OutsourcerWebClient;
import com.proyecto.urudatamovil.services.WSLoginServices;
import com.proyecto.urudatamovil.services.WSOutsourcerServices;


public class WSOutsourcerTask extends AsyncTask <String, String, OutsourcerWebClient> {

    private OutsourcerActivity actividad;

    public WSOutsourcerTask(Activity a){
        actividad = (OutsourcerActivity) a;
    }


    @Override
    protected OutsourcerWebClient doInBackground(String... params){

        String cookie;
        String  user, pass;

        user=params[0];
        pass=params[1];

        cookie= WSLoginServices.getCookie(WSLoginServices.loginToWS(user, pass));
        if (cookie ==null){

            return null;
        }
        OutsourcerWebClient outsourcer = WSOutsourcerServices.outByName(cookie, user);
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

