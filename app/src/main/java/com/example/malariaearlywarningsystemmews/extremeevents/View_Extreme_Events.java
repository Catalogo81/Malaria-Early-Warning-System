package com.example.malariaearlywarningsystemmews.extremeevents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.malariaearlywarningsystemmews.R;
import com.example.malariaearlywarningsystemmews.adapters.ExtremeEventsAdapter;
import com.example.malariaearlywarningsystemmews.adapters.ObserversAdapter;
import com.example.malariaearlywarningsystemmews.classes.ObservedExtremeEvents;
import com.example.malariaearlywarningsystemmews.classes.ObservedIndicators;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class View_Extreme_Events extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ObservedExtremeEvents> eventsList;

    DatabaseReference databaseReference;
    ExtremeEventsAdapter extremeEventsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__extreme__events);

        recyclerView = findViewById(R.id.rvObservedExtremeEvents);

        databaseReference = FirebaseDatabase.getInstance().getReference("ObservedExtremeEvents");
        eventsList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        extremeEventsAdapter = new ExtremeEventsAdapter(this, eventsList);
        recyclerView.setAdapter(extremeEventsAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    ObservedExtremeEvents observedExtremeEvents = dataSnapshot.getValue(ObservedExtremeEvents.class);
                    eventsList.add(observedExtremeEvents);
                }
                extremeEventsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //closes the activity when the user presses the phone 'back' button
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}