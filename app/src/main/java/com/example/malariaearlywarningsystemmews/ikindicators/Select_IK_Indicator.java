package com.example.malariaearlywarningsystemmews.ikindicators;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.malariaearlywarningsystemmews.MainActivity;
import com.example.malariaearlywarningsystemmews.R;
import com.example.malariaearlywarningsystemmews.adapters.AutumnIndicatorsAdapter;
import com.example.malariaearlywarningsystemmews.adapters.SpringIndicatorsAdapter;
import com.example.malariaearlywarningsystemmews.adapters.SummerIndicatorsAdapter;
import com.example.malariaearlywarningsystemmews.adapters.WinterIndicatorsAdapter;
import com.example.malariaearlywarningsystemmews.classes.Adapter;
import com.example.malariaearlywarningsystemmews.classes.AutumnIndicators;
import com.example.malariaearlywarningsystemmews.classes.SpringIndicators;
import com.example.malariaearlywarningsystemmews.classes.SummerIndicators;
import com.example.malariaearlywarningsystemmews.classes.WinterIndicators;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Select_IK_Indicator extends AppCompatActivity {

    RecyclerView recyclerView, summerRecyclerView, winterRecyclerView, autumnRecyclerView,  springRecyclerView;
    Adapter adapter;

    static SummerIndicatorsAdapter summerIndicatorsAdapter;
    static WinterIndicatorsAdapter winterIndicatorsAdapter;
    static AutumnIndicatorsAdapter autumnIndicatorsAdapter;
    static SpringIndicatorsAdapter springIndicatorsAdapter;

    CheckBox cbSummer, cbWinter, cbSpring, cbAutumn;
    Button btnShowChecked;
    Spinner spinnerSeasons;

    String season;

    List<String> mySeasons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__i_k__indicator);

//        cbSummer = findViewById(R.id.cbSummer);
//        cbWinter = findViewById(R.id.cbWinter);
//        cbSpring = findViewById(R.id.cbSpring);
//        cbAutumn = findViewById(R.id.cbAutumn);
//        btnShowChecked = findViewById(R.id.btnShowChecked);
//        spinnerSeasons = findViewById(R.id.spinnerSeasons);
        //btnSummerIndicators = findViewById(R.id.btnSummerIndicators);

        recyclerView = findViewById(R.id.rvIndicators);
        summerRecyclerView = findViewById(R.id.rvSummerIndicators);
        winterRecyclerView = findViewById(R.id.rvWinterIndicators);
        autumnRecyclerView = findViewById(R.id.rvAutumnIndicators);
        springRecyclerView = findViewById(R.id.rvSpringIndicators);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        summerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        winterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        autumnRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        springRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        mySeasons = new ArrayList<>();
//
//        mySeasons.add("Summer");
//        mySeasons.add("Winter");
//        mySeasons.add("Autumn");
//        mySeasons.add("Spring");
//
//        spinnerSeasons.setAdapter(new ArrayAdapter<>(Select_IK_Indicator.this,
//                R.layout.support_simple_spinner_dropdown_item, mySeasons));
//
//        spinnerSeasons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                season = spinnerSeasons.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        FirebaseRecyclerOptions<SummerIndicators> summerOptions =
                new FirebaseRecyclerOptions.Builder<SummerIndicators>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("SummerIndicators"), SummerIndicators.class)
                        .build();

        FirebaseRecyclerOptions<WinterIndicators> winterOptions =
                new FirebaseRecyclerOptions.Builder<WinterIndicators>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("WinterIndicators"), WinterIndicators.class)
                        .build();

        FirebaseRecyclerOptions<AutumnIndicators> autumnOptions =
                new FirebaseRecyclerOptions.Builder<AutumnIndicators>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("AutumnIndicators"), AutumnIndicators.class)
                        .build();

        FirebaseRecyclerOptions<SpringIndicators> springOptions =
                new FirebaseRecyclerOptions.Builder<SpringIndicators>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("SpringIndicators"), SpringIndicators.class)
                        .build();

//        cbWinter.setOnCheckedChangeListener((compoundButton, b) -> {
//            if(b) {
//                season = "Winter";
//            }
//            else {
//                season = null;
//            }
//        });
//
//        cbAutumn.setOnCheckedChangeListener((compoundButton, b) -> {
//            if(b) {
//                season = "Autumn";
//            }
//            else {
//                season = null;
//            }
//        });
//
//        cbSummer.setOnCheckedChangeListener((compoundButton, b) -> {
//            if(b) {
//                season = "Summer";
//            }
//            else {
//                season = null;
//            }
//        });
//
//        cbSpring.setOnCheckedChangeListener((compoundButton, b) -> {
//            if(b) {
//                season = "Spring";
//            }
//            else {
//                season = null;
//            }
//        });

//        btnShowChecked.setOnClickListener(view -> {
//            Toast.makeText(Select_IK_Indicator.this, "Clicked on " + season, Toast.LENGTH_SHORT).show();

//            switch (season)
//            {
//                case "Winter":
//                    autumnRecyclerView.setVisibility(View.GONE);
//                    summerRecyclerView.setVisibility(View.GONE);
//                    springRecyclerView.setVisibility(View.GONE);
//                    break;
//
//                case "Summer":
//                    autumnRecyclerView.setVisibility(View.GONE);
//                    winterRecyclerView.setVisibility(View.GONE);
//                    springRecyclerView.setVisibility(View.GONE);
//                    break;
//
//                case "Autumn":
//                    winterRecyclerView.setVisibility(View.GONE);
//                    summerRecyclerView.setVisibility(View.GONE);
//                    springRecyclerView.setVisibility(View.GONE);
//                    break;
//
//                case "Spring":
//                    autumnRecyclerView.setVisibility(View.GONE);
//                    summerRecyclerView.setVisibility(View.GONE);
//                    winterRecyclerView.setVisibility(View.GONE);
//                    break;
//
//                case "":
//                    autumnRecyclerView.setVisibility(View.VISIBLE);
//                    summerRecyclerView.setVisibility(View.VISIBLE);
//                    springRecyclerView.setVisibility(View.VISIBLE);
//                    winterRecyclerView.setVisibility(View.VISIBLE);
//                    break;
//
//                default:
//                    Toast.makeText(this, "One season available for filtering", Toast.LENGTH_SHORT).show();
//                    break;
//            }

//            boolean valid = true;
//            int i = 1;
//            while (i > 0)
//            {
//                if(season == "Winter") {
//                    autumnRecyclerView.setVisibility(View.GONE);
//                    summerRecyclerView.setVisibility(View.GONE);
//                    springRecyclerView.setVisibility(View.GONE);
//                }
//                if(season == "Summer") {
//                    autumnRecyclerView.setVisibility(View.GONE);
//                    winterRecyclerView.setVisibility(View.GONE);
//                    springRecyclerView.setVisibility(View.GONE);
//                }
//                if(season == "Autumn") {
//                    winterRecyclerView.setVisibility(View.GONE);
//                    summerRecyclerView.setVisibility(View.GONE);
//                    springRecyclerView.setVisibility(View.GONE);
//                }
//                if(season == "Spring") {
//                    autumnRecyclerView.setVisibility(View.GONE);
//                    summerRecyclerView.setVisibility(View.GONE);
//                    winterRecyclerView.setVisibility(View.GONE);
//                }
                //if(season == null) {
                    autumnRecyclerView.setVisibility(View.VISIBLE);
                    summerRecyclerView.setVisibility(View.VISIBLE);
                    springRecyclerView.setVisibility(View.VISIBLE);
                    winterRecyclerView.setVisibility(View.VISIBLE);
//                }
//                if(season == "Spring" && season == "Summer" && season == "Autumn" && season == "Winter")
//                {
//                    Toast.makeText(this, "One season available for filtering", Toast.LENGTH_SHORT).show();
//                    //i = 0;
//                }
//
//                i++;
//            }
//            if(season == "Winter") {
//                autumnRecyclerView.setVisibility(View.GONE);
//                summerRecyclerView.setVisibility(View.GONE);
//                springRecyclerView.setVisibility(View.GONE);
//            }
//            else if(season == "Summer") {
//                autumnRecyclerView.setVisibility(View.GONE);
//                winterRecyclerView.setVisibility(View.GONE);
//                springRecyclerView.setVisibility(View.GONE);
//            }
//            else if(season == "Autumn") {
//                winterRecyclerView.setVisibility(View.GONE);
//                summerRecyclerView.setVisibility(View.GONE);
//                springRecyclerView.setVisibility(View.GONE);
//            }
//            else if(season == "Spring") {
//                autumnRecyclerView.setVisibility(View.GONE);
//                summerRecyclerView.setVisibility(View.GONE);
//                winterRecyclerView.setVisibility(View.GONE);
//            }
//            else if(season == null) {
//                autumnRecyclerView.setVisibility(View.VISIBLE);
//                summerRecyclerView.setVisibility(View.VISIBLE);
//                springRecyclerView.setVisibility(View.VISIBLE);
//                winterRecyclerView.setVisibility(View.VISIBLE);
//            }
//            else
//            {
//                Toast.makeText(this, "One season available for filtering", Toast.LENGTH_SHORT).show();
//            }
        //});


        summerIndicatorsAdapter = new SummerIndicatorsAdapter(summerOptions);
        summerRecyclerView.setAdapter(summerIndicatorsAdapter);
        winterIndicatorsAdapter = new WinterIndicatorsAdapter(winterOptions);
        winterRecyclerView.setAdapter(winterIndicatorsAdapter);
        autumnIndicatorsAdapter = new AutumnIndicatorsAdapter(autumnOptions);
        autumnRecyclerView.setAdapter(autumnIndicatorsAdapter);
        springIndicatorsAdapter = new SpringIndicatorsAdapter(springOptions);
        springRecyclerView.setAdapter(springIndicatorsAdapter);
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
        if(adapter != null)
            adapter.stopListening();

        if(summerIndicatorsAdapter != null)
            summerIndicatorsAdapter.stopListening();

        if(winterIndicatorsAdapter != null)
            winterIndicatorsAdapter.stopListening();

        if(autumnIndicatorsAdapter != null)
            autumnIndicatorsAdapter.stopListening();

        if(springIndicatorsAdapter != null)
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