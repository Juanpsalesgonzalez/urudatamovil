package com.proyecto.urudatamovil.activities;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.proyecto.urudatamovil.R;

import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity {
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    private static final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.ACCOUNT_NAME,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.OWNER_ACCOUNT
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        ArrayList<calItem> list=listCalendars();

        menu.setHeaderTitle("Seleccione Calendario");
        for (calItem temp : list) {
            menu.add(temp.itemString());
        }
    }


    @SuppressWarnings("WeakerAccess")
    public ArrayList<calItem> listCalendars(){

        ArrayList<calItem> listCal = new ArrayList<>();
        Cursor cur;
        ContentResolver cr = getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        long calIdL;
        int calId;
        String calDisplayName;
        String calAccountName;
        String calOwnerName;

        cur = cr.query(uri, EVENT_PROJECTION, null, null, null);
        while (cur.moveToNext()) {

            calIdL = cur.getLong(PROJECTION_ID_INDEX);
            calId=(int) calIdL;
            calDisplayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
            calAccountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
            calOwnerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);
            calItem c = new calItem(calId,calDisplayName,calAccountName,calOwnerName);
            listCal.add(calId,c);
        }
        cur.close();
        return listCal;
    }

    private class calItem{
        final int calId;
        final String calDisplayName;
        final String calAccountName;
        final String calOwnerName;

        public calItem(int id, String dName, String accName,String ownName) {

            calId = id;
            calDisplayName = dName;
            calAccountName = accName;
            calOwnerName = ownName;
        }

        public String itemString() {
            return calDisplayName + " - " + calAccountName + " - " + calOwnerName;
        }

        public int getId(){
            return calId;
        }

        }
    }

