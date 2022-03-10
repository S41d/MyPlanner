package com.myplanner.myplanner.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myplanner.myplanner.R;
import com.myplanner.myplanner.model.Tache;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    RecyclerViewInterface recyclerViewInterface;
    private Context context;
    private ArrayList<Tache> taches;

    public RecyclerViewAdapter(Context context, ArrayList<Tache> taches, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.taches = taches;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_tache, parent, false);
        return new RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.titre.setText(taches.get(position).getTitreTache());
        holder.description.setText(taches.get(position).getDescriptionTache());
        holder.date.setText(taches.get(position).getJourTache());
        holder.heure.setText(taches.get(position).getHeureTache());
    }

    @Override
    public int getItemCount() {
        return taches.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterList(ArrayList<Tache> filtered) {
        this.taches = filtered;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titre, description, date, heure;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            titre = itemView.findViewById(R.id.titre_text);
            description = itemView.findViewById(R.id.description_text);
            date = itemView.findViewById(R.id.date_text);
            heure = itemView.findViewById(R.id.heure_text);

            itemView.setOnClickListener(view -> {
                if(recyclerViewInterface != null){
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(position);
                    }
                }
            });
        }
    }
}
