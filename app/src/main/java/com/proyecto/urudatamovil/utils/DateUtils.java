package com.proyecto.urudatamovil.utils;

import java.util.Calendar;

/**
 * Created by juan on 07/03/15.
 */
public class DateUtils {

    public static String currDate(){

        String date;
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;  //Empiezan de 0
        int day = c.get(Calendar.DAY_OF_MONTH);
        date=day + "/" + month + "/" + year;
        return date;
    }
// TO DO.

    public boolean validaFecha(String fecha){
        return true;
    }

}
