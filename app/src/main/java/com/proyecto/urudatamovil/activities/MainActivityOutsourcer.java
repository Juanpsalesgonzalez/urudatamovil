package com.proyecto.urudatamovil.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
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
import com.proyecto.urudatamovil.utils.PopupUtilities;


public class MainActivityOutsourcer extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */

    private static boolean isQuit = false;
    private static boolean firstRun = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }
        if (savedInstanceState != null) {
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
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        Intent currIntent=getIntent();
        currIntent.putExtras(savedInstanceState);

    }

    @SuppressWarnings("WeakerAccess")
    public static void setQuit(boolean flag) {
        isQuit = flag;
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentsUtils.normalizaIntent(this.getIntent());
        TextView textName = (TextView) findViewById(R.id.textName);
        String datos;
        final ImageView imageSem = (ImageView) findViewById(R.id.imageView_Semaforo);
        final Switch switchES = (Switch) findViewById(R.id.switchEntradaSalida);

        if ("ROLE_OUTSOURCER".equals(getRole())) {
            String marcaE, marcaS, estado;
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
            textName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            datos = getName() + "\n" +  getCliente() + "\n" + "Entrada: " + marcaE + "\n" + "Salida: " + marcaS + "\n" + "Saldo de Licencia: " + getSaldo() +" días";
            switchES.setTextOn("Entrada");
            switchES.setTextOff("Salida");
            switchES.setTrackResource(R.drawable.abc_switch_thumb_material);
            if (firstRun) {

                if (estado.equals("out")) {
                    imageSem.setImageResource(R.drawable.rojo28);
                    switchES.setChecked(true);
                } else {
                    imageSem.setImageResource(R.drawable.verde28);
                    switchES.setChecked(false);
                }
                firstRun = false;
            }
            if (estado.equals("out")) {
                imageSem.setImageResource(R.drawable.rojo28);
            } else {
                imageSem.setImageResource(R.drawable.verde28);
            }

            switchES.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    marcarES();
                }
            });
        } else {

            datos = getName() + "\n" + "Saldo de Licencia: " + getSaldo() + " días";
            switchES.setEnabled(false);
            switchES.setVisibility(View.GONE);
            imageSem.setImageResource(R.drawable.home);
        }
        textName.setText(datos);

    }
    public void marcarES() {
        Intent marcaIntent = new Intent(this, ConfirmActivity.class);
        marcaIntent.putExtra("cookie", getCookie());
        marcaIntent.putExtra("user", getUser());
        startActivityForResult(marcaIntent,Constants.ACTION_MARCA,null);
    }

    public void solicitarLicencia(View view) {
        Intent licIntent = new Intent(this, LicenceActivity.class);
        Intent currIntent = getIntent();
        IntentsUtils.copyExtras(currIntent, licIntent);
        startActivityForResult(licIntent, Constants.ACTION_LICENCIA, null);
    }

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
            PopupUtilities.errorMessage("Error", this);
        } else {
            switch (requestCode) {
                case Constants.ACTION_MARCA:
                    if (resultCode != Constants.RESULT_OK) {
                        Log.v(Constants.TAG, "Error al volver de action marca");
                    } else {
                        if (data!=null){
                            IntentsUtils.copyExtras(data,this.getIntent());
                        }
                    }
                    break;
                case Constants.ACTION_LICENCIA:
                    break;
                case Constants.ACTION_PETICION:
                    Log.v(Constants.TAG, "Peticion");
                    break;
            }

        }
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

    private String getRole(){
        return getIntent().getStringExtra("role");
    }


    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main, container, false);

        }
    }
}
