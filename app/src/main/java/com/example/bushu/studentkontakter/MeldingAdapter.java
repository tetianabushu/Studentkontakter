package com.example.bushu.studentkontakter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MeldingAdapter extends ArrayAdapter<Melding> {
    public MeldingAdapter(@NonNull Context context, @NonNull List<Melding> obj) {
        super(context, 0, obj);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Melding melding = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_melding, parent, false);
        }
        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.vismelding);
        TextView dag = (TextView) convertView.findViewById(R.id.visdag);
        TextView tid = (TextView) convertView.findViewById(R.id.vistid);

        name.setText(melding.getMelding());

        if(melding.erSendt == 1) {
            dag.setText(melding.datoSendt.substring(0,8));
            tid.setText(melding.datoSendt.substring(9,14));
        }
        else {
            dag.setText("");
            tid.setText("Ikke sendt");
        }

        convertView.setTag(melding.getId());

        // Return the completed view to render on screen
        return convertView;
    }
}
