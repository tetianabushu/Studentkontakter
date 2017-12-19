package com.example.bushu.studentkontakter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bushu on 12.10.2017.
 */

public class KontaktAdapter extends ArrayAdapter<Kontakt> {
    public KontaktAdapter(@NonNull Context context, @NonNull List<Kontakt> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Kontakt kontakt = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_student, parent, false);
        }
        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.studentName);
        // Populate the data into the template view using the data object
        name.setText(kontakt.getFornavn()+" "+ kontakt.getEtternavn());
        convertView.setTag(kontakt._ID);

        return convertView;
    }
}
