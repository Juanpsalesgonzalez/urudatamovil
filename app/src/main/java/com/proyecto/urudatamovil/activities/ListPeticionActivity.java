package com.proyecto.urudatamovil.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.proyecto.urudatamovil.R;
import com.proyecto.urudatamovil.adapters.PeticionAdapter;
import com.proyecto.urudatamovil.objects.PeticionWebClient;
import com.proyecto.urudatamovil.services.AndrCalendarService;
import com.proyecto.urudatamovil.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListPeticionActivity extends AppCompatActivity {


    private ListView list;

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
        return super.onCreateOptionsMenu(menu);
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

        if (item.getItemId() == R.id.menu_main_action_close) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.peticiones_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Accion a tomar segun el menu contextual

        PeticionWebClient peticion;
        // Si es en el submenu no puede crear la peticion.
        if (item.getItemId()==R.id.peticiones_submenu_calendars){

            return insertInCalendar(item);
        }
        // Si es el primer menu contextual, se puede ver la peticion.
        peticion = peticionFromMenu(item);
        this.savePeticion(peticion);

        switch (item.getItemId()) {
            case R.id.peticiones_menu_certificado:
                if (peticion != null) {
                    // Llama al selector de fotos.
                    Intent certIntent = new Intent(this, PhotoActivity.class);
                    startActivityForResult(certIntent, Constants.PHOTO_REQUEST_CODE);
                    // Recuerda la peticion.
                    this.savePeticion(peticion);
                }
                return true;
            case R.id.peticiones_menu_detalles:
                if (peticion != null) {
                    //Cargar detalles (peticion)
                    Intent petDetail = new Intent(this, PeticionDetailActivity.class);
                    petDetail.putExtra("peticion", peticion);
                    startActivity(petDetail);
                }//finish();
                return true;
            case R.id.peticiones_menu_calendario:
                Menu submenuCal = item.getSubMenu();
                submenuCal.clear();
                HashMap<String, String> listCal =
                        new AndrCalendarService(getContentResolver()).listCalendars();
                for (Object o : listCal.entrySet()) {
                    Map.Entry mapEntry = (Map.Entry) o;
                    int index = Integer.parseInt(mapEntry.getKey().toString());
                    submenuCal.add(0, R.id.peticiones_submenu_calendars, 0, mapEntry.getValue().toString());
                }
                return true;
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == Constants.PHOTO_REQUEST_CODE) {
            if (data != null) {
                String cert = data.getExtras().getString("photo");
                PeticionWebClient pet = this.getPeticion();
                if (pet != null) {
                    pet.setCertificado(cert);
                    Intent certConnect = new Intent(this, CertificadoConnectActivity.class);
                    certConnect.putExtra("peticion", pet);
                    certConnect.putExtra("user", this.getUser());
                    certConnect.putExtra("pass", this.getPass());
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
        PeticionWebClient pet = this.getPeticion();
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
        return currentIntent.getParcelableExtra("peticion");
    }

    private PeticionWebClient peticionFromMenu(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int posicion = info.position;
        return (PeticionWebClient) list.getAdapter().getItem(posicion);
    }
    private int calendarFromMenu(MenuItem item) {
        String key = null;
        String calDesc = (String) item.getTitle();
        HashMap<String,String> listCal =
                new AndrCalendarService(getContentResolver()).listCalendars();
        for (String k : listCal.keySet()) {
            if (listCal.get(k).equals(calDesc)) {
                key=k;
            }
        }
        if (key!=null) {
            return Integer.parseInt(key);
        }else {
            return 0;
        }
    }
    private boolean insertInCalendar(MenuItem item) {

        String message;
        long calIdL = (long) calendarFromMenu(item);
        PeticionWebClient peticion = this.getPeticion();
        String t = peticion.getEstado();
        boolean calResult=true;
        if (calIdL != 0) {
            if (peticion != null && t.equals("Aprobado")) {
                calResult = new AndrCalendarService(this.getContentResolver())
                        .addPeticionToCalendar(peticion, calIdL);
                if (!calResult) {
                    message = "No se pudo agregar al calendario";
                } else {
                    message = "Licencia agregada al calendario";
                }
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder
                        .setMessage(message)
                        .setCancelable(false)
                        .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
                alertDialogBuilder.show();
            }
        }
        return calResult;

    }

}