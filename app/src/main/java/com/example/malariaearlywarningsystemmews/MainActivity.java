package com.example.malariaearlywarningsystemmews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*-----------------Variables--------------------*/
        EditText etEmail, etPassword;
        Button btnLogin;
        ImageView ivLogo;
        TextInputLayout tilUserName, tilEnterYourPassword;
        TextView tvCreateAccount, tvForgotPassword, tvLogo, tvSlogan;
        View progressBarLayout, contentLayout;


    }
}