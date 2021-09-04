package com.monta.atourguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

    public void goGiude(View view) {
        startActivity(new Intent( MainActivity.this, LoginGuide.class));
    }


    public void gohomep(View view) {
        startActivity(new Intent( MainActivity.this,HomeTouristActivity.class));
    }
}