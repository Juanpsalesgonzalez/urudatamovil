package com.proyecto.urudatamovil.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.proyecto.urudatamovil.R;
import com.proyecto.urudatamovil.objects.PeticionWebClient;

public class PeticionDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peticion_detail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_peticion_detail, menu);
        return true;
    }
    @Override
    public void onStart() {
        super.onStart();
        mostrarDetalles();
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
    private void mostrarDetalles(){
        Intent i = this.getIntent();
        PeticionWebClient peticion = (PeticionWebClient) i.getParcelableExtra("peticion");
        TextView editText;

        Long petId=peticion.getIdPeticion();
        editText = (TextView)findViewById(R.id.value_id);
        if (petId!=null) {
            editText.setText(petId.toString());
        }

        String fechaIni=peticion.getInicio();
        editText = (TextView) findViewById(R.id.value_fecha_inicio);
        if (fechaIni!=null) {
            editText.setText(fechaIni);
        }else {
            editText.setText("");
        }

        String fechafin =peticion.getFin();
        editText = (TextView) findViewById(R.id.value_fecha_fin);
        if (fechafin!=null) {
            editText.setText(fechafin);
        }else {
            editText.setText("");
        }

        String desc=peticion.getDescripcion();
        editText = (TextView) findViewById(R.id.value_descripcion);
        if (desc!=null) {
            editText.setText(desc);
        }else {
            editText.setText("");
        }

        String estado=peticion.getEstado();
        editText = (TextView) findViewById(R.id.value_estado);
        if (estado!=null) {
            editText.setText(estado);
        }else {
            editText.setText("EE");
        }
    }
}
