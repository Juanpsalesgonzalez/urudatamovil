package com.proyecto.urudatamovil.tasks;

import android.content.Intent;
import android.os.AsyncTask;

import com.proyecto.urudatamovil.activities.LoginConnectActivity;
import com.proyecto.urudatamovil.objects.OutsourcerWebClient;
import com.proyecto.urudatamovil.services.WSLoginServices;
import com.proyecto.urudatamovil.services.WSOutsourcerServices;
import com.proyecto.urudatamovil.utils.IntentsUtils;

/**
 * Created by juan on 14/08/15.
 */
public class WSLoginTask extends AsyncTask<String,String,Intent> {

    //Verifica Login. Devuelve un objeto Outsourcer o null

    final LoginConnectActivity actividad;
    private final WSLoginServices wsLoginServices;
    private final WSOutsourcerServices wsOutsourcerServices;

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
        OutsourcerWebClient outsourcer = wsOutsourcerServices.outByUser(cookie, user);
        if (outsourcer ==null){
            return null;
        }
        Intent result = IntentsUtils.outsourcerToIntent(outsourcer);
        result.putExtra("cookie",cookie);
        result.putExtra("user",user);
        result.putExtra("pass",pass);
        return result;
    }

    protected void onPostExecute(Intent result){
        actividad.loginResult(result);
    }
}
