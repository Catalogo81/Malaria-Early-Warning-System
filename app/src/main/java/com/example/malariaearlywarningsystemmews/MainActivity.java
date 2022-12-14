package com.example.malariaearlywarningsystemmews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
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
    Button btnWebLink;

    //Navigation variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    View viewHeader;
    Toolbar toolbar;
    Menu nav_Menu;

    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference reference;

    String userID, location, role, name, surname;
    Intent nameIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        tvFullName = findViewById(R.id.tvFullName);
        tvEmail = findViewById(R.id.tvEmail);
        btnWebLink = findViewById(R.id.btnWebLink);


        mAuth = FirebaseAuth.getInstance();
        //user = FirebaseAuth.getInstance().getCurrentUser();
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("Malaria Early Warning System");


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        viewHeader = navigationView.getHeaderView(0);
        nav_Menu = navigationView.getMenu();

        /*--- Navigation Drawer Menu----*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        tv_header_fullName = viewHeader.findViewById(R.id.tv_header_fullName);
        tv_header_email = viewHeader.findViewById(R.id.tv_header_email);
        nameIntent = new Intent(MainActivity.this, Report_Extreme_Events.class);

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
                    name = userProfile.getName();
                    surname = userProfile.getSurname();
                    String email = userProfile.getEmail();
                    String number = userProfile.getPhoneNumber();
                    role = userProfile.getRole();
                    //Toast.makeText(MainActivity.this, "" + name, Toast.LENGTH_SHORT).show();

                    tv_header_fullName.setText(name + " " + surname);
                    tv_header_email.setText(email);

                    //Intent nameIntent = new Intent(MainActivity.this, Report_Extreme_Events.class);
                    //nameIntent.putExtra("user_name", name);
//                    if(name != null)
//                        startActivity(nameIntent);


                    Toast.makeText(MainActivity.this, "User role: " + role, Toast.LENGTH_SHORT).show();
                    if(role.equals("Normal User"))
                    {
                        //hide the Role navigation items
                        nav_Menu.findItem(R.id.nav_report_ik_indicator).setVisible(false);
                        nav_Menu.findItem(R.id.nav_report_extreme_events).setVisible(false);
                        nav_Menu.findItem(R.id.reports_nav_items).setVisible(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnWebLink.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("https://interimdigitalsolutions.co.za/CUTReseach/"));
            startActivity(intent);
        });

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
            finish();
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
//            case R.id.nav_select_ik_indicator:
//                startActivity(new Intent(MainActivity.this, Select_IK_Indicator.class));
//                break;

            case R.id.nav_view_extreme_events:
                startActivity(new Intent(getApplicationContext(), View_Extreme_Events.class));
                break;

            case R.id.nav_report_extreme_events:
                //startActivity(new Intent(getApplicationContext(), Report_Extreme_Events.class));
                nameIntent.putExtra("user_name", name);
                nameIntent.putExtra("user_surname", surname);
                    if(name != null)
                        startActivity(nameIntent);
                //Toast.makeText(MainActivity.this, "" + name, Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_view_ik_indicators:
                startActivity(new Intent(getApplicationContext(), View_IK_Indicators.class));
                break;

            case R.id.nav_report_ik_indicator:
                startActivity(new Intent(getApplicationContext(), Select_IK_Indicator.class));
                break;

            case R.id.nav_logout:
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, Login.class));
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}