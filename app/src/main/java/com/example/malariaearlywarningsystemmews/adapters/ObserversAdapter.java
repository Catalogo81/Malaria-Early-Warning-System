package com.example.malariaearlywarningsystemmews.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malariaearlywarningsystemmews.R;
import com.example.malariaearlywarningsystemmews.classes.ObservedIndicators;

import java.util.ArrayList;

public class ObserversAdapter extends RecyclerView.Adapter<ObserversAdapter.MyViewHolder> {

    Context context;
    ArrayList<ObservedIndicators> list;

    public ObserversAdapter(Context context, ArrayList<ObservedIndicators> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.observedindicators, parent, false);


        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ObservedIndicators observedIndicators = list.get(position);

        holder.observerDescription.setText(observedIndicators.getDescription());
        holder.date.setText(observedIndicators.getDateCaptured());
        holder.observerStatus.setText(observedIndicators.getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView observerDescription, date, observerStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            observerDescription = itemView.findViewById(R.id.tvObserverDescription);
            date = itemView.findViewById(R.id.observerDate);
            observerStatus = itemView.findViewById(R.id.observerStatus);


        }
    }
}
