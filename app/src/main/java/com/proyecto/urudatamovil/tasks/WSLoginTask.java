package com.proyecto.urudatamovil.tasks;

import android.content.Intent;
import android.os.AsyncTask;

import com.proyecto.urudatamovil.activities.LoginConnectActivity;
import com.proyecto.urudatamovil.objects.OutsourcerWebClient;
import com.proyecto.urudatamovil.services.WSLoginServices;
import com.proyecto.urudatamovil.services.WSOutsourcerServices;

/**
 * Created by juan on 14/08/15.
 */
public class WSLoginTask extends AsyncTask<String,String,Intent> {

    //Verifica Login. Devuelve un objeto Outsourcer o null

    LoginConnectActivity actividad;
    WSLoginServices wsLoginServices;
    WSOutsourcerServices wsOutsourcerServices;

    public WSLoginTask(LoginConnectActivity a) {
        actividad = a;
        wsLoginServices = new WSLoginServices();
        wsOutsourcerServices = new WSOutsourcerServices();
    }

    protected Intent doInBackground(String... params) {

        String cookie, user, pass;
        user = params[0];
        pass = params[1];


        cookie = wsLoginServices.getCookie(wsLoginServices.loginToWS(user, pass));
        if (cookie == null) {
            return null;
        }
        OutsourcerWebClient outsourcer = wsOutsourcerServices.outByName(cookie, user);
        if (outsourcer ==null){
            return null;
        }

        Intent result = new Intent();
        result.putExtra("cookie",cookie);
        result.putExtra("user",user);
        result.putExtra("pass",pass);
        result.putExtra("name",outsourcer.getNombre());
        result.putExtra("id",outsourcer.getId());
        result.putExtra("Saldo","10");

        return result;
    }

    protected void onPostExecute(Intent result){
        actividad.loginResult(result);
    }
}
