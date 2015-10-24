package com.proyecto.urudatamovil.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.proyecto.urudatamovil.R;
import com.proyecto.urudatamovil.tasks.WSConfirmTask;
import com.proyecto.urudatamovil.utils.Constants;

public class ConfirmActivity extends AppCompatActivity {

    private MainActivityOutsourcer actividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_name);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }

        Intent intent = getIntent();
        String cookie = intent.getStringExtra("cookie");
        String user = intent.getStringExtra("user");
        new WSConfirmTask(this).execute(cookie,user);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_out_name, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void confirmTaskFinished(Intent intent) {

        if (intent==null){
            setResult(Constants.RESULT_FAILED);
            finish();
        } else {
            setResult(Constants.RESULT_OK, intent);
            finish();

        }
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
            return inflater.inflate(R.layout.fragment_out_name,
                    container, false);
        }
    }
}


