
package com.proyecto.urudatamovil.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.proyecto.urudatamovil.R;
import com.proyecto.urudatamovil.utils.Constants;


public class MainActivityOutsourcer extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */

    private static boolean isQuit = false;
// Metodos de Android, se sobrescriben,

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_main_action_close) {
            MainActivityOutsourcer.setQuit(true);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static void setQuit(boolean flag) {
        isQuit = flag;
    }
    @Override
    protected void onResume() {
            super.onResume();
            if (isQuit) {
                finish();
            }
            TextView textName= (TextView) findViewById(R.id.textName);
            textName.setText(getName() + "\n" + getCel() + "\n" + getDir());
            String markIn, markOut;
            String estado=null;
            String marca = null;
            markIn=getMarkIn();
            markOut=getMarkOut();
            if (markIn==null){
                estado="out";
            }else {
                if (markOut==null){
                    estado="in";
                }
            }

            final ImageView imageSem = (ImageView) findViewById(R.id.imageView_Semaforo);
            final Switch switchES = (Switch) findViewById(R.id.switchEntradaSalida);

            switchES.setTextOn("Entrada");
            switchES.setTextOff("Salida");
            switchES.setTrackResource(R.drawable.abc_switch_thumb_material);

            if (marca.equals("Entrada")) {
                imageSem.setImageResource(R.drawable.rojo24);
            }else {
                imageSem.setImageResource(R.drawable.verde24);
            }
            if (estado.equals("in")){
                switchES.setChecked(true);
            }else{
                switchES.setChecked(false);
            }
            switchES.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    marcarES();
                    if (isChecked) {
                        imageSem.setImageResource(R.drawable.rojo24);
                    } else {
                        imageSem.setImageResource(R.drawable.verde24);
                    }

                }
            });
    }

    private void cambiarSwitch(){
        final Switch switchES = (Switch) findViewById(R.id.switchEntradaSalida);
        if (switchES.isChecked()){
            switchES.setChecked(false);
        }else {
            switchES.setChecked(true);
        }
    }

    // Llamado al presionar Marcar
    public void marcarES() {
        Intent marcaIntent = new Intent(this, OutsourcerActivity.class);
        marcaIntent.putExtra("cookie", getCookie());
        marcaIntent.putExtra("user",getUser());
        marcaIntent.putExtra("pass",getPass());
        startActivityForResult(marcaIntent,Constants.ACTION_MARCA,null);
    }

    // Llamado al presionar Licencia
    public void licencia(View view) {

    }

    // Llamado al presionar Peticiones,
    public void verPeticiones(View view) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.LOGIN_FAILED) {
            errorMessage("Error");
        } else {
            switch (requestCode) {
                case Constants.ACTION_MARCA:
                    if (resultCode != Constants.RESULT_OK) {
                        cambiarSwitch();
                    } else {
                        finish();
                    }
                    break;
                case Constants.ACTION_LICENCIA:
                    break;
                case Constants.ACTION_PETICION:
                    break;
            }

        }
    }

    public void errorMessage(String m) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(m);
        alertDialogBuilder
                .setMessage("")
                .setCancelable(false)
                .setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivityOutsourcer.this.finish();
                    }
                })
                .setNegativeButton("Reintentar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alertDialogBuilder.show();
    }

    private String getName(){
        return getIntent().getStringExtra("name");
    }
    private String getSaldo(){
        return getIntent().getStringExtra("saldo");
    }
    private String getId(){
        return getIntent().getStringExtra("id");
    }
    private String getMarkIn(){
        return getIntent().getStringExtra("markIn");
    }
    private String getMarkOut(){
        return getIntent().getStringExtra("markOut");
    }
    private String getCookie() {
        return getIntent().getStringExtra("cookie");
    }
    private String getCel(){
        return getIntent().getStringExtra("cel");
    }
    private String getDir(){
        return getIntent().getStringExtra("dir");
    }
    private String getUser(){
        return getIntent().getStringExtra("user");
    }
    private String getPass(){
        return getIntent().getStringExtra("Pass");
    }

// Fragmento de pantalla

    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;

        }
    }
}
