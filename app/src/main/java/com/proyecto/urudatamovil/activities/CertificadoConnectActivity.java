package com.proyecto.urudatamovil.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.proyecto.urudatamovil.R;
import com.proyecto.urudatamovil.objects.PeticionWebClient;
import com.proyecto.urudatamovil.tasks.WSCertificadoTask;
import com.proyecto.urudatamovil.utils.Constants;

public class CertificadoConnectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificado_connect);
        Intent currentIntent =getIntent();
        PeticionWebClient pet= currentIntent.getParcelableExtra("peticion");
        String petId=pet.getIdPeticion().toString();
        String cert=pet.getCertificado();
        String user=currentIntent.getStringExtra("user");
        String cookie=currentIntent.getStringExtra("cookie");
        String pass=currentIntent.getStringExtra("pass");
        new WSCertificadoTask(this).execute(user, pass, cookie, petId, cert);

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
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
    public void confirmTaskFinished(PeticionWebClient pet){
        TextView t = (TextView) findViewById(R.id.text1);
        t.setText("Archivo transferido");
        setResult(Constants.RESULT_OK);
    }
}
