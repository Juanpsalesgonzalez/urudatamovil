package com.proyecto.urudatamovil.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.proyecto.urudatamovil.R;
import com.proyecto.urudatamovil.utils.Constants;


public class MainActivity extends AppCompatActivity {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_main_action_close) {
            System.out.println("Cerrando  el MAIN....");
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (isQuit) {
            finish();
        }
    }

// Metodos propios de la app

    public static void setQuit(boolean flag) {
        isQuit = flag;
    }

    // Llamado al presionar Marcar
    public void outById(View view) {
        if (verificaDatos(view)==true) {
            Intent i = saveViewStatus(view, Constants.ACTION_MARCA);
            if (i != null) {
                //verificarUsuario(view);
                marcaVerificada();
            }
        }
    }

    // Llamado al presionar Licencia
    public void licencia(View view) {
        if (verificaDatos(view)==true) {
            Intent i = saveViewStatus(view, Constants.ACTION_LICENCIA);
            if (i != null) {
                verificarUsuario(view);

            }
        }
    }

    // Llamado al presionar Peticiones,
    public void verPeticiones(View view) {
        if (verificaDatos(view)==true) {
            Intent i = saveViewStatus(view, Constants.ACTION_PETICION);
            if (i != null) {
               // verificarUsuario(view);
                peticionVerificada();
            }
        }
    }

    // Verifica usuario y pass antes de continuar
    public void verificarUsuario(View v) {
        String user = getUser(v);
        String pass = getPass(v);
        if(!(user.equals("") || pass.equals(""))) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.putExtra("user", user);
            loginIntent.putExtra("pass", pass);
            startActivityForResult(loginIntent, Constants.LOGIN_REQUEST, null);
        }
    }

    // Llamado luego de verificar el user y pass.
    // Llamado desde postExecute del LoginTask
    public void licenciaVerificada() {
        Intent currIntent = this.getIntent();
        Intent intent = new Intent(this, LicenceActivity.class);
        intent = copyIntent(currIntent, intent);
        if (intent != null) {
            startActivity(intent);
        }
    }

    // Llamado luego de verificar el user y pass.
    // Llamado desde postExecute del LoginTask
    public void peticionVerificada() {
        Intent currIntent = this.getIntent();
        Intent intent = new Intent(this, ListPeticionActivity.class);
        intent = copyIntent(currIntent, intent);
        if (intent != null) {
            startActivity(intent);
        }
    }

    public void marcaVerificada() {
        Intent currIntent = this.getIntent();
        Intent intent = new Intent(this, OutsourcerActivity.class);
        intent = copyIntent(currIntent, intent);
        if (intent != null) {
            startActivityForResult(intent, Constants.ACTION_MARCA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.LOGIN_FAILED) {
            loginError();
        } else {
            int action = this.getIntent().getIntExtra("action", 0);
            switch (action) {
                case Constants.ACTION_MARCA:
                    if (resultCode != Constants.RESULT_OK) {
                        marcaVerificada();
                    } else {
                        finish();
                    }
                    break;
                case Constants.ACTION_LICENCIA:
                    licenciaVerificada();
                    break;
                case Constants.ACTION_PETICION:
                    peticionVerificada();
                    break;
            }

        }
    }

    public void loginError(){
         AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
         alertDialogBuilder.setTitle("Usuario o clave Invalidos");
         alertDialogBuilder
                 .setMessage("")
                 .setCancelable(false)
                 .setPositiveButton("Cancelar",new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog,int id) {
                         MainActivity.this.finish();
                     }
                 })
                 .setNegativeButton("Reintentar",new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog,int id) {
                         dialog.cancel();
                     }
                 });
        alertDialogBuilder.show();
     }

     // Metodos auxiliares

    private boolean verificaDatos(View view){
        if (getUser(view).equals("") || getPass(view).equals("")){
            return false;
        }else {
            return true;
        }
    }
    private Intent saveViewStatus(View view, int actionCode){
        Intent intent = new Intent(this,MainActivity.class);
        intent=loadIntent(intent,view);
        if (intent != null) {
            intent.putExtra("action", actionCode);
            this.setIntent(intent);
        }
        return intent;

    }
    private String getUser(View v) {
        EditText editTextName = (EditText) findViewById(R.id.id_outsourcer);
        String user = editTextName.getText().toString();
        return user;
    }
    private String getPass(View v){
        EditText editTextPass = (EditText) findViewById(R.id.pass_outsourcer);
        String pass = editTextPass.getText().toString();
        return  pass;
    }
    private Intent copyIntent(Intent currentI, Intent newI){
        newI.putExtra("user",currentI.getStringExtra("user"));
        newI.putExtra("pass", currentI.getStringExtra("pass"));
        return newI;
    }
    private Intent loadIntent(Intent intent, View view){
         String name = getUser(view);
         String pass = getPass(view);
         if (name.equals("") || pass.equals("")){
             intent = null;
         } else {
             intent.putExtra("user", name);
             intent.putExtra("pass", pass);
         }
        return intent;
    }




// Fragmento de pantalla

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
