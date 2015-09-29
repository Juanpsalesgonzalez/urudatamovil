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
import com.proyecto.urudatamovil.utils.IntentsUtils;


public class MainActivityOutsourcer extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */

    private static boolean isQuit = false;
    private static boolean firstRun = true;
// Metodos de Android, se sobrescriben,

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            Intent currIntent=getIntent();
            currIntent.putExtras(savedInstanceState);
        }
        firstRun=true;
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Intent currIntent = getIntent();
        savedInstanceState=currIntent.getExtras();
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        Intent currIntent=getIntent();
        currIntent.putExtras(savedInstanceState);

    }

    public static void setQuit(boolean flag) {
        isQuit = flag;
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentsUtils.normalizaIntent(this.getIntent());
        String s =this.getIntent().getStringExtra("copiado");
        if (s==null){
            s="No Copiado";
        }
        System.out.println(s);
        TextView textName = (TextView) findViewById(R.id.textName);
        textName.setText(getName() + "\n" + getCliente() + "\n" + getSaldo());
        String marcaE, marcaS;
        String estado = null;
        marcaE = getMarcaE();
        marcaS = getMarcaS();
        if (marcaE.equals("")) {
            estado = "out";
        } else {
            if (marcaS.equals("")) {
                estado = "in";
            } else {
                estado = "out";
            }
        }

        final ImageView imageSem = (ImageView) findViewById(R.id.imageView_Semaforo);
        final Switch switchES = (Switch) findViewById(R.id.switchEntradaSalida);

        switchES.setTextOn("Entrada");
        switchES.setTextOff("Salida");
        switchES.setTrackResource(R.drawable.abc_switch_thumb_material);
            if (estado.equals("out")) {
                if (firstRun){
                    switchES.setChecked(true);
                    firstRun=false;
                }
                imageSem.setImageResource(R.drawable.rojo24);
            } else {
                imageSem.setImageResource(R.drawable.verde24);
                //switchES.setChecked(false);
            }
        switchES.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
               marcarES();
                /*if (isChecked) {
                    imageSem.setImageResource(R.drawable.verde24);
                } else {
                    imageSem.setImageResource(R.drawable.rojo24);
                }*/
            }
        });

    }
    public void marcarES() {
        Intent marcaIntent = new Intent(this, ConfirmActivity.class);
        marcaIntent.putExtra("cookie", getCookie());
        marcaIntent.putExtra("user",getUser());
        startActivityForResult(marcaIntent,Constants.ACTION_MARCA,null);
    }

    // Llamado al presionar Licencia
    public void solicitarLicencia(View view) {
        Intent licIntent = new Intent(this, LicenceActivity.class);
        Intent currIntent = getIntent();
        IntentsUtils.copyExtras(currIntent, licIntent);
        startActivityForResult(licIntent, Constants.ACTION_LICENCIA, null);
    }

    // Llamado al presionar Peticiones,
    public void verPeticiones(View view) {
        Intent petIntent = new Intent(this, ListPeticionActivity.class);
        Intent currIntent = getIntent();
        IntentsUtils.copyExtras(currIntent, petIntent);
        startActivityForResult(petIntent, Constants.ACTION_PETICION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.LOGIN_FAILED) {
            errorMessage("Error");
        } else {
            int action = this.getIntent().getIntExtra("action", 0);
            switch (requestCode) {
                case Constants.ACTION_MARCA:
                    if (resultCode != Constants.RESULT_OK) {
                        System.out.println("ERROR");
                    } else {
                        if (data!=null){
                            IntentsUtils.copyExtras(data,this.getIntent());
                            String s =this.getIntent().getStringExtra("copiado");
                            if (s==null){
                                s="No Copiado";
                            }
                            s =this.getIntent().getStringExtra("marcaS");
                            if (s==null){
                                s="";
                            }
                        }
                    }
                    break;
                case Constants.ACTION_LICENCIA:
                    break;
                case Constants.ACTION_PETICION:
                    System.out.println("Peticiones");
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

    private String getName() {
        return (getIntent().getStringExtra("name"));
    }

    private String getSaldo() {
        return getIntent().getStringExtra("saldo");
    }

    private String getId() {
        return getIntent().getStringExtra("Id");
    }

    private String getMarcaE() {
        return getIntent().getStringExtra("marcaE");
    }

    private String getMarcaS() {
        return getIntent().getStringExtra("marcaS");
    }
    private String getCliente() {
        return getIntent().getStringExtra("cliente");
    }

    private String getCel() {
        return getIntent().getStringExtra("cel");
    }

    private String getDir() {
        return getIntent().getStringExtra("dir");
    }

    private String getCookie() {
        return getIntent().getStringExtra("cookie");
    }
    private String getUser() {
        return getIntent().getStringExtra("user");
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
