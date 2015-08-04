package com.proyecto.urudatamovil.tasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.proyecto.urudatamovil.activities.LicenceConnectActivity;
import com.proyecto.urudatamovil.objects.PeticionWebClient;
import com.proyecto.urudatamovil.services.WSLoginServices;
import com.proyecto.urudatamovil.services.WSPeticionServices;

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

        cookie= WSLoginServices.getCookie(WSLoginServices.loginToWS(user, pass));
        if (cookie ==null){
            return null;
        }

        PeticionWebClient peticion = WSPeticionServices.setLicense(user, cookie, initDate, endDate, comment);
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

