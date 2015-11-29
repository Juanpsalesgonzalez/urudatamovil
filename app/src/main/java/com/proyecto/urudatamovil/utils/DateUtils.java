package com.proyecto.urudatamovil.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by juan on 07/03/15.
 * Utilidades de fecha
 */
public class DateUtils {

    public static String currDate() {

        String date;
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        date = day + "/" + month + "/" + year;
        return date;
    }

    public static boolean validaFecha(String fecha) {
        return true;
    }

    public static Date str2Date(String d) {

        boolean Ok = true;
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");

        Date fecha = new Date();
        if (d == null) {
            return null;
        }
        try {
            fecha = ft.parse(d);
        } catch (ParseException e) {
            Ok = false;
        }
        if (!Ok) {
            Ok = true;
            ft = new SimpleDateFormat("yyyy-MM-dd");
            try {
                fecha = ft.parse(d);
            } catch (ParseException e) {
                Ok = false;
            }
        }
        if (!Ok) {
            Ok = true;
            ft = new SimpleDateFormat("yyyy-dd-MM");
            try {
                fecha = ft.parse(d);
            } catch (ParseException e) {
                Ok = false;
            }
        }
        if (Ok) {
            return fecha;
        } else {
            return null;
        }
    }


    @SuppressWarnings("WeakerAccess")
    public static int dayOfWeekNumeric(String dow) {
        String[] days = {"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};

        if (dow.equalsIgnoreCase("Sábado")) {
            dow = "Sabado";
        }

        int ndow;
        for (ndow = 0; ndow < 7; ndow++) {
            if (dow.equalsIgnoreCase(days[ndow])) {
                return ndow;
            }
        }
        return -1;
    }



    public static Date getNextOccurenceOfDay(String dayOfWeek) {

        int dayOfWeekInt=dayOfWeekNumeric(dayOfWeek);
        Calendar cal = Calendar.getInstance();
        int dow = cal.get(Calendar.DAY_OF_WEEK);
        int numDays = 7 - ((dow - dayOfWeekInt) % 7 + 7) % 7;
        cal.add(Calendar.DAY_OF_YEAR, numDays);
        return cal.getTime();
    }
}
