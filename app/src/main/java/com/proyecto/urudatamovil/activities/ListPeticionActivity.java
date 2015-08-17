package com.proyecto.urudatamovil.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.proyecto.urudatamovil.R;
import com.proyecto.urudatamovil.adapters.PeticionAdapter;
import com.proyecto.urudatamovil.objects.PeticionWebClient;
import com.proyecto.urudatamovil.utils.Constants;

import java.util.ArrayList;

public class ListPeticionActivity extends AppCompatActivity {


    private ListView list;
    private ArrayAdapter<PeticionWebClient> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            setContentView(R.layout.activity_list_peticion);
        }
        Intent conIntent = getIntent();
        String initDate = null;
        String endDate = null;
        String user = conIntent.getStringExtra("user");
        String pass = conIntent.getStringExtra("pass");
        String comment = null;
        String certificado = null;
        new com.proyecto.urudatamovil.tasks.WSPeticionTask(this).execute(user, pass, endDate, initDate, comment, certificado);

    }

    public void setModel(ArrayList<PeticionWebClient> a) {
        list = (ListView) findViewById(R.id.listPeticiones);
        list.setAdapter(new PeticionAdapter(this, a));
        registerForContextMenu(list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_peticion, menu);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.peticiones_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Accion a tomar segun el menu contextual

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int posicion = info.position;
        PeticionWebClient peticion = (PeticionWebClient) list.getAdapter().getItem(posicion);
        if (peticion != null) {
            Long petIdL = peticion.getIdPeticion();

            switch (item.getItemId()) {
                case R.id.peticiones_menu_certificado:
                    // Llama al selector de fotos.
                    Intent certIntent = new Intent(this, PhotoActivity.class);
                    //certIntent.putExtra("user", this.getUser());
                    //certIntent.putExtra("pass", this.getPass());
                    //certIntent.putExtra("petId", petIdL.toString());
                    startActivityForResult(certIntent, Constants.PHOTO_REQUEST_CODE);
                    // Recuerda la peticion.
                    this.savePeticion(peticion);
                    return true;
                case R.id.peticiones_menu_detalles:
                    //Cargar detalles (peticion)
                    Intent petDetail = new Intent(this, PeticionDetailActivity.class);
                    petDetail.putExtra("peticion", peticion);
                    startActivity(petDetail);
                    //finish();
                    return true;
            }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String cert = null;
        if (resultCode == RESULT_OK && requestCode == Constants.PHOTO_REQUEST_CODE) {
            if (data != null) {
                cert = data.getExtras().getString("photo");
                PeticionWebClient pet=this.getPeticion();
                if (pet != null){
                    pet.setCertificado(cert);
                    Intent certConnect = new Intent(this, CertificadoConnectActivity.class);
                    certConnect.putExtra("peticion",pet);
                    certConnect.putExtra("user",this.getUser());
                    certConnect.putExtra("pass",this.getPass());
                    startActivityForResult(certConnect, Constants.ACTION_CERTIFICATE);
                }
            }
        }
    }

    public void confirmTaskFinished(PeticionWebClient pet) {

        if (pet == null) {
            setResult(Constants.LOGIN_FAILED, null);
            finish();
        }
    }

        // Auxiliares

    private String getUser() {
        Intent currentIntent = getIntent();
        return currentIntent.getStringExtra("user");
    }

    private String getPass() {
        Intent currentIntent = getIntent();
        return currentIntent.getStringExtra("pass");
    }

    private Long getPetId() {
        PeticionWebClient pet=this.getPeticion();
        if (pet != null) {
            return pet.getIdPeticion();
        }
        return null;
    }

    private void savePeticion(PeticionWebClient pet) {
        Intent currentIntent = getIntent();
        currentIntent.putExtra("peticion", pet);
    }

    private PeticionWebClient getPeticion() {
        Intent currentIntent = getIntent();
        PeticionWebClient pet = currentIntent.getParcelableExtra("peticion");
        return pet;
    }


}