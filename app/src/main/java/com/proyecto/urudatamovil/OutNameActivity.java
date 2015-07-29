package com.proyecto.urudatamovil;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OutNameActivity extends ActionBarActivity {

    String name;
    private String pass;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_name);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }

        Intent intent = getIntent();
        user = intent.getStringExtra("name_outsourcer");
        pass = intent.getStringExtra("pass_outsourcer");
        new WSOutsourcerTask(this).execute(user, pass);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_out_name, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

            switch(item.getItemId()){

                case R.id.action_close:
                    salir();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void salir(){
        finish();
        MainActivity.setQuit(true);
    }

    public void botonSalir(View view){
        salir();
    }

    public void setName(String n){
        TextView t =(TextView)this.findViewById(R.id.name_value);
        t.setText(n);
    }

    public void setId(String i){
        TextView t=(TextView)this.findViewById(R.id.id_value);
        t.setText(i);
    }

    public void setStatus(String s){
        TextView t=(TextView)this.findViewById(R.id.label_status);
        t.setText(s);

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
            View rootView = inflater.inflate(R.layout.fragment_out_name,
                    container, false);
            return rootView;
        }
    }
}


