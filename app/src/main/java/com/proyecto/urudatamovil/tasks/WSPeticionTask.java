package com.proyecto.urudatamovil.tasks;

import android.os.AsyncTask;

import com.proyecto.urudatamovil.activities.ListPeticionActivity;
import com.proyecto.urudatamovil.objects.PeticionWebClient;
import com.proyecto.urudatamovil.services.WSPeticionServices;

import java.util.ArrayList;

/**
 * Created by juan on 06/08/15.
 */

public class WSPeticionTask extends AsyncTask <String, String, ArrayList<PeticionWebClient>> {

    private final ListPeticionActivity actividad;
//    private WSLoginServices wsLoginServices ;
    private final WSPeticionServices wsPeticionServices;

    public WSPeticionTask(ListPeticionActivity a){
        actividad=a;
//        wsLoginServices = new WSLoginServices();
        wsPeticionServices = new WSPeticionServices();
    }

    @Override
    public ArrayList<PeticionWebClient> doInBackground(String... params){

        ArrayList<PeticionWebClient> listaPet = new ArrayList<>();
        String user, pass, fechaIni, fechaFin, estado;
        String cookie;

        user=params[0];
        pass=params[1];
        cookie=params[2];
        fechaIni=params[3];
        fechaFin=params[4];
        estado=params[5];

       // cookie= wsLoginServices.getCookie(wsLoginServices.loginToWS(user, pass));
       // if (cookie ==null){
       //     return null;
       // }
        listaPet= wsPeticionServices.listaPet(user, cookie, fechaIni, fechaFin, estado);
        return listaPet;
    }

    @Override
    protected void onPostExecute(ArrayList<PeticionWebClient> a) {
        if (a == null) {
            actividad.confirmTaskFinished(null);
        } else
            actividad.setModel(a);
    }

}

