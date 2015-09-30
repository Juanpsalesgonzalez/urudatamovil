package com.proyecto.urudatamovil.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.proyecto.urudatamovil.R;
import com.proyecto.urudatamovil.objects.PeticionWebClient;
import com.proyecto.urudatamovil.tasks.WSLicenceTask;


public class LicenceConnectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licence_connect);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        Intent conIntent = getIntent();
        String initDate = conIntent.getStringExtra("init");
        String endDate = conIntent.getStringExtra("end");
        String user = conIntent.getStringExtra("user");
        String cookie = conIntent.getStringExtra("cookie");
        String pass = conIntent.getStringExtra("pass");
        String descripcion = conIntent.getStringExtra("descripcion");
        String cert = conIntent.getStringExtra("cert");
        if (cert==null){
            cert = "";
        }
        new WSLicenceTask(this).execute(user, pass, cookie, endDate, initDate, descripcion, cert);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_licence_connect, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void confirmTaskFinished(PeticionWebClient p) {
        if (p != null) {
               Intent petDetail = new Intent(this,PeticionDetailActivity.class);
               petDetail.putExtra("peticion",p);
               startActivity(petDetail);
               finish();
        }
        finish();
    }

    public void onOkButtonClicked(View v){
        finish();
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
            return inflater.inflate(R.layout.fragment_licence_connect, container, false);
        }
    }
}
