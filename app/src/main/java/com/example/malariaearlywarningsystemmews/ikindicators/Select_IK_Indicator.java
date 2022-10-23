package com.example.malariaearlywarningsystemmews.ikindicators;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.malariaearlywarningsystemmews.R;
import com.example.malariaearlywarningsystemmews.classes.Adapter;
import com.example.malariaearlywarningsystemmews.classes.Indicators;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Select_IK_Indicator extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__i_k__indicator);

        recyclerView = findViewById(R.id.rvIndicators);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Indicators> options =
                new FirebaseRecyclerOptions.Builder<Indicators>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Indicators"), Indicators.class)
                        .build();

        adapter = new Adapter(options);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}