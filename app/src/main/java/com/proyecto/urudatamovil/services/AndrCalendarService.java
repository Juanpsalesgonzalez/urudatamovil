package com.proyecto.urudatamovil.services;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

import com.proyecto.urudatamovil.objects.PeticionWebClient;
import com.proyecto.urudatamovil.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by juan on 19/08/15.
 */
public class AndrCalendarService {

    private ContentResolver contentResolver;

    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    private static final String[] EVENT_PROJECTION = new String[]{
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
    };

    public AndrCalendarService(ContentResolver cr) {
        contentResolver = cr;
    }

    public HashMap<String, String> listCalendars() {

        HashMap<String, String> listCal = new HashMap<String, String>();
        Cursor cur = null;
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        long calIdL = 0;
        String calId;
        String calDisplayName = null;
        String calAccountName = null;
        String calOwnerName = null;

        cur = contentResolver.query(uri, EVENT_PROJECTION, null, null, null);
        while (cur.moveToNext()) {

            calIdL = cur.getLong(PROJECTION_ID_INDEX);
            calId = Long.toString(calIdL);
            calDisplayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
            calAccountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
            calOwnerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);
            String c = calDisplayName + " - " + calAccountName + " - " + calOwnerName;
            listCal.put(calId, c);
        }
        return listCal;
    }

    public boolean addPeticionToCalendar(PeticionWebClient pet, Long calId) {

        if (pet.getEstado().equals("Aprobado")) {
            String title = pet.getDescripcion();
            String descripcion = "Solicitud : " + pet.getIdPeticion().toString();

            Date initDate = DateUtils.str2Date(pet.getInicio());
            Date endDate = DateUtils.str2Date(pet.getFin());
            if (initDate==null || endDate==null){
                return false;
            }
            long startMillis = 0;
            long endMillis = 0;
            Calendar beginTime = Calendar.getInstance();
            beginTime.setTime(initDate);
            startMillis = beginTime.getTimeInMillis();
            Calendar endTime = Calendar.getInstance();
            endTime.setTime(endDate);
            endMillis = endTime.getTimeInMillis();

            ContentValues val = new ContentValues();
            val.put(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
            val.put(CalendarContract.Events.DTSTART, startMillis);
            val.put(CalendarContract.Events.DTEND, endMillis);
            val.put(CalendarContract.Events.TITLE, title);
            val.put(CalendarContract.Events.DESCRIPTION, descripcion);
            val.put(CalendarContract.Events.EVENT_TIMEZONE, "GMT+3");
            val.put(CalendarContract.Events.CALENDAR_ID, calId);
            try {
                Uri uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, val);
            }catch (Exception e){
                e.printStackTrace();
            }
            return true;
        }else{
            return false;
        }
    }

}

