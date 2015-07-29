package com.proyecto.urudatamovil;

    import android.support.v7.app.ActionBarActivity;
    import android.content.Intent;
    import android.os.Bundle;
    import android.view.Menu;
    import android.support.v4.app.Fragment;
    import android.view.MenuInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.EditText;
    import android.view.LayoutInflater;
    import android.view.ViewGroup;



 public class MainActivity extends ActionBarActivity {

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

             if (item.getItemId() ==  R.id.action_close) {
                 System.out.println("Cerrando  el MAIN....");
                 finish();
             }
             return super.onOptionsItemSelected(item);

          }
         @Override
         protected void onRestart() {
                     super.onRestart();
                     if( isQuit ){
                         finish();
                     }
                 }

// Metodos propios de la app

     public static void setQuit(boolean flag){
         isQuit=flag;
     }

     public void outById(View view){

         EditText e = (EditText)findViewById(R.id.id_outsourcer);
         e.setText("Conectando");
         Intent intent = new Intent(this,OutNameActivity.class);
         loadIntent(intent, view);
         startActivity(intent);
     }

     public void licencia(View view){

         Intent intent = new Intent(this,LicenceActivity.class);
         loadIntent(intent, view);
         startActivity(intent);
     }

     private void loadIntent(Intent intent, View view){

         EditText editTextName = (EditText) findViewById(R.id.id_outsourcer);
         String name = editTextName.getText().toString();
         EditText editTextPass = (EditText) findViewById(R.id.pass_outsourcer);
         String pass = editTextPass.getText().toString();
         intent.putExtra("name_outsourcer", name);
         intent.putExtra("pass_outsourcer", pass);
        // return intent;
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
