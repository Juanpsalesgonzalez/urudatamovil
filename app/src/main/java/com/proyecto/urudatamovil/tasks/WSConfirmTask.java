package com.proyecto.urudatamovil.tasks;

/**
 * Created by juan on 15/02/15.
 */


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.proyecto.urudatamovil.activities.ConfirmActivity;
import com.proyecto.urudatamovil.objects.OutsourcerWebClient;
import com.proyecto.urudatamovil.services.WSOutsourcerServices;
import com.proyecto.urudatamovil.utils.IntentsUtils;

/* Task para marcar. */

public class WSConfirmTask extends AsyncTask <String, String, Intent> {

    private final ConfirmActivity actividad;
    private final WSOutsourcerServices wsOutsourcerServices;

    public WSConfirmTask(Activity a){
        actividad = (ConfirmActivity) a;
        wsOutsourcerServices = new WSOutsourcerServices();
    }


    @Override
    protected Intent doInBackground(String... params){

        String cookie;
        String  user;
        cookie=params[0];
        user=params[1];

        if (cookie ==null){
            return null;
        }
        OutsourcerWebClient outsourcer = wsOutsourcerServices.confirma(cookie, user);
        if (outsourcer==null){
            return null;
        }
        Intent result = IntentsUtils.outsourcerToIntent(outsourcer);
        result.putExtra("cookie",cookie);
        result.putExtra("user",user);
        return result;
    }

    @Override
    protected void onPostExecute(Intent i){
        actividad.confirmTaskFinished(i);
    }
}

