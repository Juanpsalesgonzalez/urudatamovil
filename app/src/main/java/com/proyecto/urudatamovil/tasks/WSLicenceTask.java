package com.proyecto.urudatamovil.tasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.proyecto.urudatamovil.activities.LicenceConnectActivity;
import com.proyecto.urudatamovil.objects.PeticionWebClient;
import com.proyecto.urudatamovil.services.WSCertificadoServices;
import com.proyecto.urudatamovil.services.WSPeticionServices;

/**
 * Created by juan on 26/04/15.
 */
public class WSLicenceTask extends AsyncTask <String, String, PeticionWebClient>  {

    private final LicenceConnectActivity actividad;
    private final WSPeticionServices wsPeticionServices;
    private final WSCertificadoServices wsCertificadoServices;

    public WSLicenceTask(Activity a){
        actividad = (LicenceConnectActivity) a;
        wsPeticionServices = new WSPeticionServices();
        wsCertificadoServices = new WSCertificadoServices();
    }

    @Override
    protected PeticionWebClient doInBackground(String... params) {

        String user,pass, endDate,initDate,comment, cert, cookie, petId;
        Long petIdL;

        user=params[0];
        pass=params[1];
        cookie = params[2];
        endDate = params[3];
        initDate = params[4];
        comment = params[5];
        cert= params[6];


        PeticionWebClient peticion = wsPeticionServices.setLicense(user, cookie, initDate, endDate, comment);
        if (peticion ==null){
            return null;
        }
        petIdL = peticion.getIdPeticion();
        petId=petIdL.toString();
        if (!cert.equals("")){
            wsCertificadoServices.setCertificate(cookie,petId,cert);
        }

        return peticion;

    }
    @Override
    protected void onPostExecute(PeticionWebClient peticion) {
       actividad.confirmTaskFinished(peticion);

    }
}

