package com.proyecto.urudatamovil.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.proyecto.urudatamovil.R;
import com.proyecto.urudatamovil.utils.Constants;
import com.proyecto.urudatamovil.utils.DateUtils;
import com.proyecto.urudatamovil.utils.FechaDialogFragment;
import com.proyecto.urudatamovil.utils.IntentsUtils;


public class LicenceActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licencia);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        Intent i = getIntent();
        i.putExtra("descripcion", "Licencia Reglamentaria");
    }

    @Override
    protected void onStart(){
        super.onStart();
        String hoy = DateUtils.currDate();
        setDisplayFechaIni(hoy);
        setDisplayFechaFin(hoy);
        checkRadioButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_licencia, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.menu_licencia_action_close:
                finish();
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setDisplayFechaFin(String date) {
        EditText editText = (EditText) this.findViewById(R.id.editText_fechaFin);
        editText.setText(date);
    }

    private void setDisplayFechaIni(String date) {
        EditText editText = (EditText) this.findViewById(R.id.editText_fechaIni);
        editText.setText(date);
    }

    private String getDisplayFechaIni() {
        EditText editText = (EditText) this.findViewById(R.id.editText_fechaIni);
        return (editText.getText().toString());
    }

    private String getDisplayFechaFin() {
        EditText editText = (EditText) this.findViewById(R.id.editText_fechaFin);
        return (editText.getText().toString());
    }

    public void cargaFechaFin(View v) {
        TextView t = (TextView) this.findViewById(R.id.editText_fechaIni);
        mostrarDatePicker(v, t);
    }

    public void cargaFechaInicio(View v) {
        TextView t = (TextView) this.findViewById(R.id.editText_fechaFin);
        mostrarDatePicker(v, t);
    }

    private void mostrarDatePicker(View v, TextView t) {
        t.setText(DateUtils.currDate());
        FechaDialogFragment fechaFragment = new FechaDialogFragment();
        fechaFragment.setActividadPadre(this);
        fechaFragment.setTextoPadre(t);
        fechaFragment.show(getFragmentManager(), "Calendario");
    }

    public void setDate(TextView t, int ano, int mes, int dia) {
            t.setText(new StringBuilder().append(dia).append("/")
                    .append(mes + 1).append("/")
                    .append(ano).append(" "));

        }

    public void checkRadioButton(){
        RadioGroup r= (RadioGroup)findViewById(R.id.radioGroup);
        r.check(R.id.radio_lic_comun);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String descripcion=null;
        switch (view.getId()) {
            case R.id.radio_lic_comun:
                if (checked) {
                    descripcion = "Reglamentaria";
                    break;
                }
            case R.id.radio_lic_enfermedad:
                if (checked) {
                    descripcion = "Enfermedad";
                    break;
                }
            case R.id.radio_lic_estudio:
                if (checked) {
                    descripcion = "Estudio";
                    break;
                }
            case R.id.radio_lic_otros:
                if (checked) {
                    descripcion = "Otros";
                    break;
                }
        }
        this.getIntent().putExtra("Descripcion", descripcion);
    }

    public void confirmError() {
        Log.v(Constants.TAG, "Error al confirmar licencia");
    }


    public void SeleccionaCertificado(View v) {

        Intent certIntent = new Intent(this,PhotoActivity.class);
        certIntent=loadIntent(certIntent);
        startActivityForResult(certIntent, Constants.PHOTO_REQUEST_CODE);
    }

    public void confirmaLicencia(View v){

        /* Es necesario crear un Intent y un Activity para poder llamar
         al Async Task. El Async Task solo se puede llamar del main */
        Intent conIntent = new Intent(this,LicenceConnectActivity.class);
        conIntent=loadIntent(conIntent);
        startActivityForResult(conIntent, Constants.CONFIRM_LICENCE_CODE);
    }

    private Intent loadIntent(Intent newIntent){

            Intent currIntent = getIntent();
            newIntent.putExtra("init",getDisplayFechaIni());
            newIntent.putExtra("end", getDisplayFechaFin());
            IntentsUtils.copyExtras(currIntent,newIntent);
            /*Bundle extras = currIntent.getExtras();
            if (extras != null) {
                Set<String> keys = extras.keySet();
                Iterator<String> it = keys.iterator();
                while (it.hasNext()) {
                    String key = it.next();
                    newIntent.putExtra(key,currIntent.getStringExtra(key));
                }
            }*/
            return newIntent;
        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == Constants.PHOTO_REQUEST_CODE) {
                String photo = null;
                if (data != null) {
                    photo = data.getExtras().getString("photo");
                }
                if (photo != null) {
                    this.getIntent().putExtra("cert", photo);
                }
                confirmaLicencia(this.getCurrentFocus());
            }
        }
        }
   /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_licencia, container, false);
        }
    }
}


