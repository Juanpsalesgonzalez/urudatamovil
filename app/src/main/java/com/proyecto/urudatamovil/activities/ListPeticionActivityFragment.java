package com.proyecto.urudatamovil.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.proyecto.urudatamovil.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListPeticionActivityFragment extends Fragment {

    public ListPeticionActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listPeticion, container, false);
    }
}
