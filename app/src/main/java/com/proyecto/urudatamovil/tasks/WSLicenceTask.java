package com.proyecto.urudatamovil.tasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.proyecto.urudatamovil.activities.LicenceConnectActivity;
import com.proyecto.urudatamovil.objects.PeticionWebClient;
import com.proyecto.urudatamovil.services.WSCertificadoServices;
import com.proyecto.urudatamovil.services.WSLoginServices;
import com.proyecto.urudatamovil.services.WSPeticionServices;

/**
 * Created by juan on 26/04/15.
 */
public class WSLicenceTask extends AsyncTask <String, String, PeticionWebClient>  {

    private LicenceConnectActivity actividad;
    private WSLoginServices wsLoginServices;
    private WSPeticionServices wsPeticionServices;
    private WSCertificadoServices wsCertificadoServices;

    public WSLicenceTask(Activity a){
        actividad = (LicenceConnectActivity) a;
        wsLoginServices = new WSLoginServices();
        wsPeticionServices = new WSPeticionServices();
        wsCertificadoServices = new WSCertificadoServices();
    }

    @Override
    protected PeticionWebClient doInBackground(String... params) {

        String user, pass,endDate,initDate,comment, cert, cookie, petId;
        Long petIdL;


        user = params[0];
        pass = params[1];
        endDate = params[2];
        initDate = params[3];
        comment = params[4];
        cert= params[5];

        cookie= wsLoginServices.getCookie(wsLoginServices.loginToWS(user, pass));
        if (cookie ==null){
            return null;
        }

        PeticionWebClient peticion = wsPeticionServices.setLicense(user, cookie, initDate, endDate, comment);
        if (peticion ==null){
            return null;
        }
        petIdL = peticion.getIdPeticion();
        petId=petIdL.toString();
        if (cert!=null){
            wsCertificadoServices.setCertificate(cookie,petId,cert);
        }

        return peticion;

    }
    @Override
    protected void onPostExecute(PeticionWebClient peticion) {
       actividad.confirmTaskFinished(peticion);

    }
}

