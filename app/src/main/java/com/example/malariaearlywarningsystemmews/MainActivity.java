package com.example.malariaearlywarningsystemmews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.malariaearlywarningsystemmews.login.Login;
import com.example.malariaearlywarningsystemmews.register.Register;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView tvFullName, tvEmail;
    Button btnLogout;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvFullName = findViewById(R.id.tvFullName);
        tvEmail = findViewById(R.id.tvEmail);
        btnLogout = findViewById(R.id.btnLogout);

        mAuth = FirebaseAuth.getInstance();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, Login.class));

            }
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
}