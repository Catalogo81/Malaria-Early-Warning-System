package com.example.malariaearlywarningsystemmews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.malariaearlywarningsystemmews.classes.User;
import com.example.malariaearlywarningsystemmews.extremeevents.Report_Extreme_Events;
import com.example.malariaearlywarningsystemmews.extremeevents.View_Extreme_Events;
import com.example.malariaearlywarningsystemmews.ikindicators.Report_IK_Indicators;
import com.example.malariaearlywarningsystemmews.ikindicators.Select_IK_Indicator;
import com.example.malariaearlywarningsystemmews.ikindicators.View_IK_Indicators;
import com.example.malariaearlywarningsystemmews.login.Login;
import com.example.malariaearlywarningsystemmews.register.Register;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView tvFullName, tvEmail, tv_header_fullName, tv_header_email;
    Button btnReportIndicator, btnReportEvents, btnViewIndicators, btnViewEvents;

    //Navigation variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    View viewHeader;
    Toolbar toolbar;

    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference reference;

    String userID, location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        tvFullName = findViewById(R.id.tvFullName);
        tvEmail = findViewById(R.id.tvEmail);

        //for testing
//        btnViewEvents = findViewById(R.id.btnViewEvents);
//        btnViewIndicators = findViewById(R.id.btnViewIndicators);
//        btnReportEvents = findViewById(R.id.btnReportEvents);
//        btnReportIndicator = findViewById(R.id.btnReportIndicator);


        mAuth = FirebaseAuth.getInstance();
        //user = FirebaseAuth.getInstance().getCurrentUser();
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        //get user details from firebase
//        if(mAuth.getCurrentUser().getUid() != null) {
//            userID = mAuth.getCurrentUser().getUid();
//        }

        //tests if there is a user is already active and email is verified
//        if(user != null /*&& user.isEmailVerified()*/)
//        {
//            startActivity(new Intent(MainActivity.this, Home.class));
//        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("Malaria Early Warning System");

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        viewHeader = navigationView.getHeaderView(0);

        /*--- Navigation Drawer Menu----*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        tv_header_fullName = viewHeader.findViewById(R.id.tv_header_fullName);
        tv_header_email = viewHeader.findViewById(R.id.tv_header_email);

        //get user details from firebase
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        //Toast.makeText(MainActivity.this, "User ID: " + user.getEmail(), Toast.LENGTH_LONG).show();

        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //User object
                User userProfile = snapshot.getValue(User.class);

                //check if user profile exists
                if(userProfile != null)
                {
                    String name = userProfile.getName();
                    String surname = userProfile.getSurname();
                    String email = userProfile.getEmail();
                    String number = userProfile.getPhoneNumber();

                    tv_header_fullName.setText(name + " " + surname);
                    tv_header_email.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //for testing
//        btnViewEvents.setOnClickListener(view ->
//                startActivity(new Intent(getApplicationContext(), View_Extreme_Events.class)));
//
//        btnReportEvents.setOnClickListener(view ->
//                startActivity(new Intent(getApplicationContext(), Report_Extreme_Events.class)));
//
//        btnReportIndicator.setOnClickListener(view ->
//                startActivity(new Intent(getApplicationContext(), Report_IK_Indicators.class)));
//
//        btnViewIndicators.setOnClickListener(view ->
//                startActivity(new Intent(getApplicationContext(), View_IK_Indicators.class)));


//        if(userID != null)
//        {
//            userID = user.getUid();
//
//            reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                    //User object
//                    User userProfile = snapshot.getValue(User.class);
//
//                    //check if user profile exists
//                    if(userProfile != null)
//                    {
//                        String name = userProfile.getName();
//                        String surname = userProfile.getSurname();
//                        String email = userProfile.getEmail();
//                        String number = userProfile.getPhoneNumber();
//
//                        //set the details to the view
//                        tvFullName.setText("Welcome, " + name + " " + surname);
//                        tvEmail.setText("Logged in as: " + email);
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                    Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        //checks if user is currently logged in or not
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null)
        {
            startActivity(new Intent(MainActivity.this, Login.class));
        }
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.nav_select_ik_indicator:
                startActivity(new Intent(MainActivity.this, Select_IK_Indicator.class));
                break;

            case R.id.nav_view_extreme_events:
                startActivity(new Intent(getApplicationContext(), View_Extreme_Events.class));
                break;

            case R.id.nav_report_extreme_events:
                startActivity(new Intent(getApplicationContext(), Report_Extreme_Events.class));
                break;

            case R.id.nav_view_ik_indicators:
                startActivity(new Intent(getApplicationContext(), View_IK_Indicators.class));
                break;

            case R.id.nav_report_ik_indicator:

                startActivity(new Intent(getApplicationContext(), Report_IK_Indicators.class));
                break;

            case R.id.nav_logout:
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, Login.class));
               // startActivity(new Intent(MainActivity.this, Report_IK_Indicators.class));
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}