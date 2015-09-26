package com.proyecto.urudatamovil.utils;

import android.content.Intent;
import android.os.Bundle;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by juan on 26/09/15.
 */
public class IntentsUtils {
    public static Intent copyExtras(Intent oldI, Intent newI){
        Bundle extras = oldI.getExtras();
        if (extras != null) {
            Set<String> keys = extras.keySet();
            Iterator<String> it = keys.iterator();
            //Log.e(LOG_TAG, "Dumping Intent start");
            while (it.hasNext()) {
                String key = it.next();
                newI.putExtra(key,oldI.getStringExtra(key));
            }
        }
        return newI;
    }
}
