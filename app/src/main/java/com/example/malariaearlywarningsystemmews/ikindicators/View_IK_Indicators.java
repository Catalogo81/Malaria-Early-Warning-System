package com.example.malariaearlywarningsystemmews.ikindicators;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.malariaearlywarningsystemmews.R;

public class View_IK_Indicators extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__i_k__indicators);
    }

    //closes the activity when the user presses the phone 'back' button
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

}