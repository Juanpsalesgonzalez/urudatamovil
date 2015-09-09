package com.proyecto.urudatamovil.tasks;

import android.os.AsyncTask;

import com.proyecto.urudatamovil.activities.LoginConnectActivity;
import com.proyecto.urudatamovil.services.WSLoginServices;

/**
 * Created by juan on 14/08/15.
 */
public class WSLoginTask extends AsyncTask<String,String,String> {

    //Verifica Login. Devuelve un objeto Outsourcer o null

    LoginConnectActivity actividad;
    WSLoginServices wsLoginServices;

    public WSLoginTask(LoginConnectActivity a) {
        actividad = a;
        wsLoginServices = new WSLoginServices();
    }

    protected String doInBackground(String... params) {

        String cookie, user, pass, action;
        user = params[0];
        pass = params[1];


        cookie = wsLoginServices.getCookie(wsLoginServices.loginToWS(user, pass));
        if (cookie == null) {
            return null;
        }

        return cookie;
    }

    protected void onPostExecute(String cookie){
        actividad.loginResult(cookie);
    }
}
