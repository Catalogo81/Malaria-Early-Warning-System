package com.example.malariaearlywarningsystemmews.ikindicators;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.malariaearlywarningsystemmews.MainActivity;
import com.example.malariaearlywarningsystemmews.R;
import com.example.malariaearlywarningsystemmews.adapters.AutumnIndicatorsAdapter;
import com.example.malariaearlywarningsystemmews.adapters.SpringIndicatorsAdapter;
import com.example.malariaearlywarningsystemmews.adapters.SummerIndicatorsAdapter;
import com.example.malariaearlywarningsystemmews.adapters.WinterIndicatorsAdapter;
import com.example.malariaearlywarningsystemmews.classes.Adapter;
import com.example.malariaearlywarningsystemmews.classes.AutumnIndicators;
import com.example.malariaearlywarningsystemmews.classes.SpringIndicators;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Select_IK_Indicator extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;
    SummerIndicatorsAdapter summerIndicatorsAdapter;
    WinterIndicatorsAdapter winterIndicatorsAdapter;
    AutumnIndicatorsAdapter autumnIndicatorsAdapter;
    SpringIndicatorsAdapter springIndicatorsAdapter;

    CheckBox cbSummer, cbWinter, cbSpring, cbAutumn;
    Button btnShowChecked;

    String season;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__i_k__indicator);

        cbSummer = findViewById(R.id.cbSummer);
        cbWinter = findViewById(R.id.cbWinter);
        cbSpring = findViewById(R.id.cbSpring);
        cbAutumn = findViewById(R.id.cbAutumn);
        btnShowChecked = findViewById(R.id.btnShowChecked);

        recyclerView = findViewById(R.id.rvIndicators);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        FirebaseRecyclerOptions<Indicators> options =
//                new FirebaseRecyclerOptions.Builder<Indicators>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Indicators"), Indicators.class)
//                        .build();

//        FirebaseRecyclerOptions<SummerIndicators> summerOptions =
//                new FirebaseRecyclerOptions.Builder<SummerIndicators>()
//                .setQuery(FirebaseDatabase.getInstance().getReference().child("SummerIndicators"), SummerIndicators.class)
//                .build();

//        FirebaseRecyclerOptions<WinterIndicators> winterOptions =
//                new FirebaseRecyclerOptions.Builder<WinterIndicators>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("WinterIndicators"), WinterIndicators.class)
//                        .build();

//        FirebaseRecyclerOptions<AutumnIndicators> autumnOptions =
//                new FirebaseRecyclerOptions.Builder<AutumnIndicators>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("AutumnIndicators"), AutumnIndicators.class)
//                        .build();

        FirebaseRecyclerOptions<SpringIndicators> springOptions =
                new FirebaseRecyclerOptions.Builder<SpringIndicators>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("SpringIndicators"), SpringIndicators.class)
                        .build();

        //adapter = new Adapter(options);
        //recyclerView.setAdapter(adapter);
//        summerIndicatorsAdapter = new SummerIndicatorsAdapter(summerOptions);
//        recyclerView.setAdapter(summerIndicatorsAdapter);
//        winterIndicatorsAdapter = new WinterIndicatorsAdapter(winterOptions);
//        recyclerView.setAdapter(winterIndicatorsAdapter);
//        autumnIndicatorsAdapter = new AutumnIndicatorsAdapter(autumnOptions);
//        recyclerView.setAdapter(autumnIndicatorsAdapter);
        springIndicatorsAdapter = new SpringIndicatorsAdapter(springOptions);
        recyclerView.setAdapter(springIndicatorsAdapter);


//        btnShowChecked.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                StringBuilder season = new StringBuilder();
//                FirebaseRecyclerOptions<Indicators> options = null;
//
//                season.append("Season: ");
//                if(cbSummer.isChecked())
//                {
//                    season.append(cbSummer.getText().toString()).append(" ");
//                    options = new FirebaseRecyclerOptions.Builder<Indicators>()
//                                    .setQuery(FirebaseDatabase.getInstance().getReference().child("SummerIndicators"), SummerIndicators.class)
//                                    .build();
//
//                    adapter = new Adapter(options);
//                    recyclerView.setAdapter(adapter);
//                }
//                if(cbWinter.isChecked())
//                {
//                    season.append(cbWinter.getText().toString()).append(" ");
//                    options = new FirebaseRecyclerOptions.Builder<Indicators>()
//                            .setQuery(FirebaseDatabase.getInstance().getReference().child("WinterIndicators"), Indicators.class)
//                            .build();
//                }
//                if(cbAutumn.isChecked())
//                {
//                    season.append(cbAutumn.getText().toString()).append(" ");
//                    options = new FirebaseRecyclerOptions.Builder<Indicators>()
//                            .setQuery(FirebaseDatabase.getInstance().getReference().child("AutumnIndicators"), Indicators.class)
//                            .build();
//                }
//                if(cbSpring.isChecked())
//                {
//                    season.append(cbSpring.getText().toString()).append(" ");
//                    options = new FirebaseRecyclerOptions.Builder<Indicators>()
//                            .setQuery(FirebaseDatabase.getInstance().getReference().child("SpringIndicators"), Indicators.class)
//                            .build();
//                }
//
//                Toast.makeText(Select_IK_Indicator.this, season.toString(), Toast.LENGTH_SHORT).show();
//                adapter = new Adapter(options);
//                recyclerView.setAdapter(adapter);
//            }
//        });


//        adapter = new Adapter(options);
//        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter != null)
            adapter.startListening();
        if(summerIndicatorsAdapter != null)
            summerIndicatorsAdapter.startListening();
        if(winterIndicatorsAdapter != null)
            winterIndicatorsAdapter.startListening();
        if(autumnIndicatorsAdapter != null)
            autumnIndicatorsAdapter.startListening();
        if(springIndicatorsAdapter != null)
            springIndicatorsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
        summerIndicatorsAdapter.stopListening();
        winterIndicatorsAdapter.stopListening();
        autumnIndicatorsAdapter.stopListening();
        springIndicatorsAdapter.stopListening();
    }

    //closes the activity when the user presses the phone 'back' button
    @Override
    public void onBackPressed() {
        startActivity(new Intent(Select_IK_Indicator.this, MainActivity.class));
        finish();
        super.onBackPressed();
    }
}