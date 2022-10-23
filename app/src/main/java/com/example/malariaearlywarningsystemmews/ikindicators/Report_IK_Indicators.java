package com.example.malariaearlywarningsystemmews.ikindicators;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.malariaearlywarningsystemmews.R;
import com.example.malariaearlywarningsystemmews.extremeevents.Report_Extreme_Events;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Report_IK_Indicators extends AppCompatActivity {

    ImageView ivIndicatorImage;
    EditText etIndicatorDescription, etIndicatorLocation;
    Button btnSubmitIndicator;

    String myCountry, myLatitude, myLongitude;

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_ik__indicators);

        ivIndicatorImage = findViewById(R.id.ivIndicatorImage);
        etIndicatorDescription = findViewById(R.id.etIndicatorDescription);
        etIndicatorLocation = findViewById(R.id.etIndicatorLocation);
        btnSubmitIndicator = findViewById(R.id.btnSubmitIndicator);

        //Initialize fusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getCurrentLocation();


        btnSubmitIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void getCurrentLocation() {

        //Check permissions
        //Toast.makeText(this, "Getting Location...", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(Report_IK_Indicators.this,
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
                            Geocoder geocoder = new Geocoder(Report_IK_Indicators.this, Locale.getDefault());

                            //Initialize address list
                            List<Address> addresses = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1
                            );
                            //Set latitude on EditText
                            //Toast.makeText(Report_Extreme_Events.this, "Latitude: " + addresses.get(0).getLatitude(), Toast.LENGTH_LONG).show();
                            myLatitude = String.valueOf(addresses.get(0).getLatitude());
                            myCountry = String.valueOf(addresses.get(0).getCountryName());
                            myLongitude = String.valueOf(addresses.get(0).getLongitude());
                            etIndicatorLocation.setText(Html.fromHtml("Lat(" + myLatitude + ")" + ", Lon(" + myLongitude + ")" ));
//                            etEventLocation.setText(Html.fromHtml("Current Location: " + myLatitude));
//                            etEventLocation.setText(Html.fromHtml("Current Location: " + myLatitude));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(Report_IK_Indicators.this
                    , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }


}