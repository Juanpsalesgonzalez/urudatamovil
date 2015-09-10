
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
            String marca="Entrada";

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

            switchES.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {

                    if (isChecked) {
                        imageSem.setImageResource(R.drawable.rojo24);
                    } else {
                        imageSem.setImageResource(R.drawable.verde24);
                    }

                }
            });

    }


    // Llamado al presionar Marcar
    public void outMark(View view) {

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
            int action = this.getIntent().getIntExtra("action", 0);
            switch (action) {
                case Constants.ACTION_MARCA:
                    if (resultCode != Constants.RESULT_OK) {
                        System.out.println("hola");
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
        return getIntent().getStringExtra("Id");
    }
    private String getMarkIn(){
        return getIntent().getStringExtra("MarkIn");
    }

    private String getCel(){
        return getIntent().getStringExtra("cel");
    }
    private String getDir(){
        return getIntent().getStringExtra("dir");
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
