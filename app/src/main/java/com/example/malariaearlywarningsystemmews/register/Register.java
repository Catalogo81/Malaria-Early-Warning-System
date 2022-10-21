package com.example.malariaearlywarningsystemmews.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.malariaearlywarningsystemmews.R;
import com.example.malariaearlywarningsystemmews.classes.User;
import com.example.malariaearlywarningsystemmews.login.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText etRegisterName, etRegisterSurname, etRegisterEmailAddress, etRegisterPassword,
            etRegisterConfirmPassword;
    Button btnRegisterDetails;
    TextView tvBackToLogin;

    ProgressBar progressBar;
    String userID;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegisterName = findViewById(R.id.etRegisterName);
        etRegisterSurname = findViewById(R.id.etRegisterSurname);
        etRegisterEmailAddress = findViewById(R.id.etRegisterEmailAddress);
        etRegisterPassword = findViewById(R.id.etRegisterPassword);
        etRegisterConfirmPassword = findViewById(R.id.etRegisterConfirmPassword);
        tvBackToLogin = findViewById(R.id.tvBackToLogin);
        btnRegisterDetails = findViewById(R.id.btnRegisterDetails);

        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        btnRegisterDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        tvBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
        
        
    }

    private void createUser() {

        //get the editText details and store it
        String name = etRegisterName.getText().toString().trim();
        String surname = etRegisterSurname.getText().toString().trim();
        String email = etRegisterEmailAddress.getText().toString().trim();
        String password = etRegisterPassword.getText().toString().trim();
        String confirmPassword = etRegisterConfirmPassword.getText().toString().trim();

        //validation
        if(TextUtils.isEmpty(name))
        {
            etRegisterName.setError("Name is Required");
            etRegisterName.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(surname))
        {
            etRegisterSurname.setError("Surname is Required");
            etRegisterSurname.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(email))
        {
            etRegisterEmailAddress.setError("Email address is Required");
            etRegisterEmailAddress.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword))
        {
            etRegisterPassword.setError("Password is Required");
            etRegisterConfirmPassword.setError("Password is Required");
            return;
        }
        else if(password.length() < 6)
        {
            etRegisterPassword.setError("Password has to be >= 6 Characters");
            etRegisterPassword.requestFocus();
            return;
        }
        else if(password.equals(confirmPassword))
        {
            etRegisterConfirmPassword.setHint("Passwords match");
            etRegisterConfirmPassword.requestFocus();
        }
        else
        {
            etRegisterConfirmPassword.setError("Passwords do not match");
            etRegisterConfirmPassword.requestFocus();
        }

        progressBar.setVisibility(View.VISIBLE);

        //will now register the use into Firebase

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    //creating the User object and save to database
                    User user = new User(name, surname, email);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(Register.this, "Successfully registered!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.VISIBLE);
                                //userID = mAuth.getCurrentUser().getUid();

                                startActivity(new Intent(Register.this, Login.class));

                            }
                            else
                            {
                                Toast.makeText(Register.this, "Registration Unsuccessful!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });

                }
                else
                {
                    Toast.makeText(Register.this, "Registration Unsuccessful!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });
    }
}