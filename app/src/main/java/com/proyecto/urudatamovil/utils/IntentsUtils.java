package com.proyecto.urudatamovil.utils;

import android.content.Intent;
import android.os.Bundle;

import com.proyecto.urudatamovil.objects.OutsourcerWebClient;

import java.util.Set;

/**
 * Created by juan on 26/09/15.
 */
public class IntentsUtils {

    public static Intent copyExtras(Intent oldI, Intent newI){
        Bundle extras = oldI.getExtras();
        if (extras != null) {
            Set<String> keys = extras.keySet();
            for (String key : keys) {
                newI.putExtra(key, oldI.getStringExtra(key));
            }
            newI.putExtra("copiado","Copiado");
            return newI;
        }
        return emptyIntent();
    }

    public static Intent normalizaIntent(Intent intent) {

        Bundle extras = intent.getExtras();
        if (extras != null) {
            Set<String> valores = extras.keySet();
            for (String valor : valores) {
                if (intent.getStringExtra(valor) == null) {
                    intent.putExtra(valor, "");
                }
            }
            return intent;
        }
        return emptyIntent();
    }

    public static Intent outsourcerToIntent(OutsourcerWebClient out){

        Intent result = new Intent();

        result.putExtra("name",out.getNombre());
        result.putExtra("id",out.getId());
        result.putExtra("saldo","10");

        String marca = out.getMarkIn();
        if (marca== null || marca.equals("0")){
            marca="";
        }
        result.putExtra("marcaE",marca);

        marca = out.getMarkOut();
        if (marca== null || marca.equals("0")){
            marca="";
        }
        result.putExtra("marcaS",marca);

        result.putExtra("dir",out.getDireccion());
        result.putExtra("cel", out.getCel());
        result.putExtra("cliente", out.getCliente());

        return result;

    }

    @SuppressWarnings("WeakerAccess")
    public static Intent emptyIntent(){
        Intent result= new Intent();
        result.putExtra("name","Don Nadie");
        result.putExtra("id","0");
        result.putExtra("saldo","0");
        result.putExtra("marcaE","");
        result.putExtra("marcaS","");
        result.putExtra("dir","Sin domicilio fijo");
        result.putExtra("cel", "0000000000");
        result.putExtra("cliente", "Sin Cliente");

        return result;

    }


}
