package com.proyecto.urudatamovil.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.proyecto.urudatamovil.R;
import com.proyecto.urudatamovil.tasks.WSLoginTask;
import com.proyecto.urudatamovil.utils.Constants;

public class LoginConnectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent i = getIntent();
        String user = i.getStringExtra("user");
        String pass = i.getStringExtra("pass");
        String action = i.getStringExtra("action");
        new WSLoginTask(this).execute(user, pass, action);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
    public void loginResult(Intent resultIntent) {
        if (resultIntent == null) {
            setResult(Constants.LOGIN_FAILED,null);
        } else {
            setResult(Constants.LOGIN_OK,resultIntent);
        }
        finish();
    }
}
