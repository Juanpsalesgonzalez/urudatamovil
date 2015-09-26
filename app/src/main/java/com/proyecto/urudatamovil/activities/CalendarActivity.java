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
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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

   // Devuelve la lista de los calendarios disponbles en el movil.

    public ArrayList<calItem> listCalendars(){

        ArrayList<calItem> listCal = new ArrayList<>();
        Cursor cur = null;
        ContentResolver cr = getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        long calIdL = 0;
        int calId;
        String calDisplayName = null;
        String calAccountName = null;
        String calOwnerName = null;

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
        return listCal;
    }

    private class calItem{
        int calId;
        String calDisplayName;
        String calAccountName;
        String calOwnerName;

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

