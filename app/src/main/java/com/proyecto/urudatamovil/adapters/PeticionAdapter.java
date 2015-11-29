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

public class PeticionAdapter extends ArrayAdapter<PeticionWebClient> {

    private final Context context;

    public PeticionAdapter(Context c, List<PeticionWebClient> model ){
        super(c, R.layout.item_lista_pet, model);
        this.context = c;
        ArrayList<PeticionWebClient> peticionesAL = (ArrayList) model;
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

        String s = p.getEstado();
        if (s==null){
            s="Esperando Aprobacion";
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

