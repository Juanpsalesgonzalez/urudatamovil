
package com.proyecto.urudatamovil.tasks;

import android.os.AsyncTask;

import com.proyecto.urudatamovil.activities.CertificadoConnectActivity;
import com.proyecto.urudatamovil.objects.PeticionWebClient;
import com.proyecto.urudatamovil.services.WSCertificadoServices;

/**
 * Created by juan on 26/04/15.
 */
public class WSCertificadoTask extends AsyncTask <String, String, PeticionWebClient> {

    private final CertificadoConnectActivity actividad;
    private final WSCertificadoServices wsCertificadoServices= new WSCertificadoServices();

    public WSCertificadoTask(CertificadoConnectActivity a) {
        actividad = a;
    }

    @Override
    protected PeticionWebClient doInBackground(String... params) {

        String petId, cert,user, pass;
        String cookie;

        cookie=params[2];
        petId=params[3];
        cert=params[4];


       PeticionWebClient peticion = wsCertificadoServices.setCertificate(cookie, petId, cert);
        if (peticion ==null){
            return null;
        }
        return peticion;

    }
    @Override
    protected void onPostExecute(PeticionWebClient peticion) {
        actividad.confirmTaskFinished(peticion);

    }
}
