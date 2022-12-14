package com.example.malariaearlywarningsystemmews.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malariaearlywarningsystemmews.R;
import com.example.malariaearlywarningsystemmews.classes.ObservedExtremeEvents;

import java.util.ArrayList;

public class ExtremeEventsAdapter extends RecyclerView.Adapter<ExtremeEventsAdapter.MyEventsViewHolder>{

    Context context;
    ArrayList<ObservedExtremeEvents> eventsList;

    public ExtremeEventsAdapter(Context context, ArrayList<ObservedExtremeEvents> eventsList) {
        this.context = context;
        this.eventsList = eventsList;
    }

    @NonNull
    @Override
    public ExtremeEventsAdapter.MyEventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.observedevents, parent, false);


        return new ExtremeEventsAdapter.MyEventsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExtremeEventsAdapter.MyEventsViewHolder holder, int position) {

        ObservedExtremeEvents observedExtremeEvents = eventsList.get(position);

        holder.eventDescription.setText(observedExtremeEvents.getEventDescription());
        holder.eventDateReported.setText(observedExtremeEvents.getEventDate());
        holder.eventReportedBy.setText(observedExtremeEvents.getEventUser());

        holder.eventLevel.setText(observedExtremeEvents.getEventLevel());
        if(holder.eventLevel.getText().toString().equals("Moderate"))
            holder.eventLevel.setTextColor(Color.parseColor("#398703"));
        else if(holder.eventLevel.getText().toString().equals("Mild"))
            holder.eventLevel.setTextColor(Color.parseColor("#FF8F00"));
        else if(holder.eventLevel.getText().toString().equals("Severe"))
            holder.eventLevel.setTextColor(Color.parseColor("#EF1111"));

    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public static class MyEventsViewHolder extends RecyclerView.ViewHolder{

        TextView eventDescription, eventLevel, eventDateReported, eventReportedBy;

        public MyEventsViewHolder(@NonNull View itemView) {
            super(itemView);

            eventDescription = itemView.findViewById(R.id.tvEventDescription);
            eventLevel = itemView.findViewById(R.id.observerLevel);
            eventDateReported = itemView.findViewById(R.id.tvEventDateReported);
            eventReportedBy = itemView.findViewById(R.id.tvReportedBy);

        }
    }

}
