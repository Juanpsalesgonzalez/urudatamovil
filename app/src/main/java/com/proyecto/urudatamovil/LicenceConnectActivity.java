package com.proyecto.urudatamovil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class LicenceConnectActivity extends ActionBarActivity {

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
        String user = conIntent.getStringExtra("name");
        String pass = conIntent.getStringExtra("pass");
        String comment = conIntent.getStringExtra("comment");
        String certificado = conIntent.getStringExtra("certificado");
        System.out.println(this.toString());
        new WSLicenceTask(this).execute(user, pass, endDate, initDate, comment, certificado);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_licence_connect, menu);
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

    public void confirmMessage(PeticionWebClient p) {
        TextView status = (TextView) this.findViewById(R.id.textView_status);
        if (p!=null) {
            status.setText("Solicitud Realizada : " + p.getIdPeticion().toString());
        }   else {
            status.setText("Error, Reintente");
        }
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
            View rootView = inflater.inflate(R.layout.fragment_licence_connect, container, false);
            return rootView;
        }
    }
}
