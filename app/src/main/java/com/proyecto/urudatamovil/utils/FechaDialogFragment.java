package com.proyecto.urudatamovil.utils;

/**
 * Created by juan on 04/04/15.
 */

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import com.proyecto.urudatamovil.activities.LicenceActivity;

import java.util.Calendar;


public  class FechaDialogFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {

    private LicenceActivity actividadPadre;
    private TextView textoPadre;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void setActividadPadre(LicenceActivity l){
        actividadPadre=l;
    }
    public void setTextoPadre(TextView t){

        textoPadre=t;
    }

    public void onDateSet(DatePicker view, int a, int m, int d) {
        actividadPadre.setDate(textoPadre,a,m,d);
  }
}
