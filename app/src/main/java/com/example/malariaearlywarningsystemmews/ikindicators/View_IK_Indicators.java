package com.example.malariaearlywarningsystemmews.ikindicators;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.malariaearlywarningsystemmews.MainActivity;
import com.example.malariaearlywarningsystemmews.R;
import com.example.malariaearlywarningsystemmews.adapters.ObserversAdapter;
import com.example.malariaearlywarningsystemmews.classes.ObservedIndicators;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class View_IK_Indicators extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ObservedIndicators> list;

    DatabaseReference databaseReference;
    ObserversAdapter observersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__i_k__indicators);

        recyclerView = findViewById(R.id.rvObservedIndicators);

        databaseReference = FirebaseDatabase.getInstance().getReference("ObservedIndicators");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        observersAdapter = new ObserversAdapter(this, list);
        recyclerView.setAdapter(observersAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    ObservedIndicators observedIndicators = dataSnapshot.getValue(ObservedIndicators.class);
                    list.add(observedIndicators);
                }
                observersAdapter.notifyDataSetChanged();
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
        startActivity(new Intent(View_IK_Indicators.this, MainActivity.class));
        super.onBackPressed();
    }

}