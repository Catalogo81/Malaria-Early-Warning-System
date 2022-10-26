package com.example.malariaearlywarningsystemmews.extremeevents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.malariaearlywarningsystemmews.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

public class Report_Extreme_Events extends AppCompatActivity {

    ImageView ivEventImage;
    EditText etEventDescription, etEventLocation;
    Button btnSubmitEvent;
    TextView tvLocation;
    Spinner eventSpinner;

    List<String> items;

    String myCountry, myLatitude, myLongitude, eventLevel;

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report__extreme__events);

        ivEventImage = findViewById(R.id.ivEventImage);
        etEventDescription = findViewById(R.id.etEventDescription);
        etEventLocation = findViewById(R.id.etEventLocation);
        btnSubmitEvent = findViewById(R.id.btnSubmitEvent);
        tvLocation = findViewById(R.id.tvLocation);
        eventSpinner = findViewById(R.id.eventSpinner);

        //ArrayAdapter<String> myAdapter = new ArrayAdapter<>(Report_Extreme_Events.this,
                //android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.event_term));
        //myAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        //eventSpinner.setAdapter(myAdapter);

        items = new ArrayList<>();

        items.add("Moderate");
        items.add("Severe");
        items.add("Mild");

        eventSpinner.setAdapter(new ArrayAdapter<>(Report_Extreme_Events.this,
                R.layout.support_simple_spinner_dropdown_item, items));

        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                eventLevel = eventSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Initialize fusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getCurrentLocation();

        btnSubmitEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void getCurrentLocation() {

        //Check permissions
        //Toast.makeText(this, "Getting Location...", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(Report_Extreme_Events.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //when granted
            //getCurrentLocation();

            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {

                    //Initialize Location
                    Location location = task.getResult();

                    if (location != null)
                    {
                        try {
                            //Initialize geoCoder
                            Geocoder geocoder = new Geocoder(Report_Extreme_Events.this, Locale.getDefault());

                            //Initialize address list
                            List<Address> addresses = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1
                            );
                            //Set latitude on EditText
                            //Toast.makeText(Report_Extreme_Events.this, "Latitude: " + addresses.get(0).getLatitude(), Toast.LENGTH_LONG).show();
                            myLatitude = String.valueOf(addresses.get(0).getLatitude());
                            myCountry = String.valueOf(addresses.get(0).getCountryName());
                            myLongitude = String.valueOf(addresses.get(0).getLongitude());
                            etEventLocation.setText(Html.fromHtml("Lat(" + myLatitude + ")" + ", Lon(" + myLongitude + ")" ));
//                            etEventLocation.setText(Html.fromHtml("Current Location: " + myLatitude));
//                            etEventLocation.setText(Html.fromHtml("Current Location: " + myLatitude));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(Report_Extreme_Events.this
                    , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    //closes the activity when the user presses the phone 'back' button
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

}