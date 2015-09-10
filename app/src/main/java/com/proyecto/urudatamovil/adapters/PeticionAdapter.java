package com.proyecto.urudatamovil.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.proyecto.urudatamovil.R;
import com.proyecto.urudatamovil.objects.PeticionWebClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juan on 09/08/15.
 */
public class PeticionAdapter extends ArrayAdapter<PeticionWebClient> {

    private ArrayList<PeticionWebClient> peticionesAL;
    private final Context context;

    public PeticionAdapter(Context c, List<PeticionWebClient> model ){
        super(c, R.layout.item_lista_pet, model);
        this.context = c;
        this.peticionesAL = (ArrayList) model;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.item_lista_pet, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.firstLine);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        PeticionWebClient p= getItem(position);
        String line = p.getIdPeticion() + " - " + p.getDescripcion() + " - " + p.getInicio();
        textView.setText(line);

        // Change icon based on name
        String s = p.getEstado();
        if (s==null){
            s="Esperando Aprobacion"; // El estado no puede ser nulo
        }
        if (s.equals("Aprobado")) {
                imageView.setImageResource(R.drawable.checked_32);
            } else if (s.equals("No Aprobado")) {
                imageView.setImageResource(R.drawable.cancel_32);
            } else if (s.equals("Esperando Aprobacion")) {
                imageView.setImageResource(R.drawable.process_32);
            } else {
                imageView.setImageResource(R.drawable.question_32);
            }
        return rowView;
    }


}

