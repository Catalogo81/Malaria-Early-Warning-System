package com.example.malariaearlywarningsystemmews.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.malariaearlywarningsystemmews.MainActivity;
import com.example.malariaearlywarningsystemmews.R;
import com.example.malariaearlywarningsystemmews.register.Register;
import com.example.malariaearlywarningsystemmews.resetpassword.ResetPassword;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    TextView tvLogo;
    EditText etEmailAddress, etPassword;
    Button btnLogin, btnRegister, btnResetPassword;

    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmailAddress = findViewById(R.id.etEmailAddress);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnResetPassword = findViewById(R.id.btnResetPassword);

        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

         btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ResetPassword.class));
            }
        });
    }

    private void loginUser() {

        //get the editText details and store it
        String email = etEmailAddress.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        //validation
        if(TextUtils.isEmpty(email))
        {
            etEmailAddress.setError("Email is Required");
            etEmailAddress.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            etEmailAddress.setError("Email pattern not valid");
            etEmailAddress.requestFocus();
        }
        if(TextUtils.isEmpty(password))
        {
            etPassword.setError("Password is Required");
            etEmailAddress.requestFocus();
            return;
        }
        if(password.length() < 6)
        {
            etPassword.setError("Min password length is 6 characters");
            etPassword.requestFocus();
        }
        else
        {
            progressBar.setVisibility(View.VISIBLE);

            // authenticating the user
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(task.isSuccessful())
                    {

                        Toast.makeText(Login.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, MainActivity.class));
                        progressBar.setVisibility(View.GONE);

//                        if(user.isEmailVerified())
//                        {
//                            Toast.makeText(Login.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(Login.this, MainActivity.class));
//                        }
//                        else
//                        {
//                            user.sendEmailVerification();
//                            Toast.makeText(Login.this, "Check your email to verify your account", Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);
//                        }

                    }
                    else
                    {
                        Toast.makeText(Login.this, "Login Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }

    }
}