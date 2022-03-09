package com.myplanner.myplanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.myplanner.myplanner.R;
import com.myplanner.myplanner.model.Tache;

import java.util.List;

public class TacheListAdapter extends ArrayAdapter<Tache> {

    private Context context;

    public TacheListAdapter(Context context, int resource, List<Tache> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String titreTache = getItem(position).getTitreTache();
        String descriptionTache = getItem(position).getDescriptionTache();
        String jourTache = getItem(position).getJourTache();
        String heureTache = getItem(position).getHeureTache();
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_tache, parent, false);
        }

        TextView titreTextView = convertView.findViewById(R.id.titreTache);
        TextView descriptionTextView = convertView.findViewById(R.id.descriptionTache);
        TextView jourTextView = convertView.findViewById(R.id.jourTache);
        TextView heureTextView = convertView.findViewById(R.id.heureTache);

        titreTextView.setText(titreTache);
        descriptionTextView.setText(descriptionTache);
        jourTextView.setText(jourTache);
        heureTextView.setText(heureTache);

        return convertView;
    }
}
