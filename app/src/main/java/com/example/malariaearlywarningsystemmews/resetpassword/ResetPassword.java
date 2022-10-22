package com.example.malariaearlywarningsystemmews.resetpassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.malariaearlywarningsystemmews.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    EditText etResetEmail;
    Button btnReset;

    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        etResetEmail = findViewById(R.id.etResetEmail);
        btnReset = findViewById(R.id.btnReset);

        /*------------------Firebase authentication Hooks-----------------------*/
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //test if the user entered a email address or not
                if(etResetEmail.getText().toString().isEmpty())
                {
                    Toast.makeText(ResetPassword.this, getString(R.string.please_enter_your_email_address), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //hides the progressbar and displays the content layout of Student Home Activity
                    progressBar.setVisibility(View.VISIBLE);

                    //send a request to firebase to reset the users password
                    firebaseAuth.sendPasswordResetEmail(etResetEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            //if task of sending 'forgot password' request to firebase is successful,
                            //- progressbar will disappear
                            //- Student Homepage content will appear
                            //a toast will appear stating that the password is sent successfully to the users email address
                            if(task.isSuccessful())
                            {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(ResetPassword.this, getString
                                                (R.string.password_successfully_sent_to_your_email_address),
                                        Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                //if task of sending 'forgot password' request to firebase is unsuccessful,
                                //- progressbar will disappear
                                //- Student Homepage content will appear
                                //a toast will appear stating that the password not sent to the users email address stating reason why
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(ResetPassword.this, getString
                                                (R.string.error) + task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });
    }
}