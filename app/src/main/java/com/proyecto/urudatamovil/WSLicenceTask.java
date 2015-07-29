package com.proyecto.urudatamovil;

import android.app.Activity;
import android.os.AsyncTask;

/**
 * Created by juan on 26/04/15.
 */
public class WSLicenceTask extends AsyncTask <String, String, PeticionWebClient>  {

    private LicenceConnectActivity actividad;

    public WSLicenceTask(Activity a){
        System.out.println(a.toString());
        actividad = (LicenceConnectActivity) a;
    }

    @Override
    protected PeticionWebClient doInBackground(String... params) {

        String user, pass,endDate,initDate,comment, cert, cookie;


        user = params[0];
        pass = params[1];
        endDate = params[2];
        initDate = params[3];
        comment = params[4];
        cert= params[5];

        cookie=WSServices.getCookie(WSServices.loginToWS(user, pass));
        if (cookie ==null){
            return null;
        }

        PeticionWebClient peticion = WSServices.setLicense(user,cookie,initDate,endDate,comment);
        if (peticion ==null){
            return null;
        }
        return peticion;

    }
    @Override
    protected void onPostExecute(PeticionWebClient peticion) {
       actividad.confirmMessage(peticion);

    }
}

