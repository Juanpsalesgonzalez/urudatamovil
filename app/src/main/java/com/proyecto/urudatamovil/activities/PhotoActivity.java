package com.proyecto.urudatamovil.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.proyecto.urudatamovil.adapters.PhotoAdapter;
import com.proyecto.urudatamovil.R;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class PhotoActivity extends AppCompatActivity{


    private RecyclerView recyclerView;
    private PhotoAdapter photoAdapter;

    private final ArrayList<String> selectedPhotos = new ArrayList<>();

    public final static int PHOTO_REQUEST_CODE = 123;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        photoAdapter = new PhotoAdapter(this, selectedPhotos);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);

        PhotoPickerIntent intent = new PhotoPickerIntent(PhotoActivity.this);
        intent.setPhotoCount(1);
        intent.setShowCamera(true);
        startActivityForResult(intent, PHOTO_REQUEST_CODE);
    }

    public void previewPhoto(Intent intent) {
        startActivityForResult(intent, PHOTO_REQUEST_CODE);
    }


    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //  Carga lista con fotos seleccionadas.
        List<String> photos = null;
        if (resultCode == RESULT_OK && requestCode == PHOTO_REQUEST_CODE) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (photos != null) {

                selectedPhotos.addAll(photos);
            }
            //photoAdapter.notifyDataSetChanged();

            // Devuelve la primera foto selecionada.
            Bundle b = new Bundle();
            b.putString("photo",selectedPhotos.get(0));
            Intent i = getIntent(); //gets the intent that called this intent
            i.putExtras(b);
            i.putExtra("photo",selectedPhotos.get(0));
            setResult(Activity.RESULT_OK, i);
            finish();
        }
    }


}

