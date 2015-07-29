
package com.proyecto.urudatamovil;

import android.app.Activity;
import android.os.AsyncTask;

/**
 * Created by juan on 26/04/15.
 */
public class WSCertificadoTask extends AsyncTask <String, String, PeticionWebClient> {

    private LicenceActivity actividad;

    public WSCertificadoTask(Activity a) {
        System.out.println(a.toString());
        actividad = (LicenceActivity) a;
    }


//    @Override
    protected PeticionWebClient doInBackground(String... params) {

        String pId, cert,user, pass;
        String cookie;

        user=params[0];
        pass=params[1];
       // pId = params[2];
        pId="40474";
        cert = params[3];

        cookie=WSServices.getCookie(WSServices.loginToWS(user, pass));
        if (cookie ==null){
            return null;
        }

       PeticionWebClient peticion = WSServices.setCertificate(cookie,pId, cert);
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
