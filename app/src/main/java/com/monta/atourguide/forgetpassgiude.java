package com.monta.atourguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class forgetpassgiude extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassgiude);


        Button btNnexToverif=findViewById(R.id.btNnexToverif);
        btNnexToverif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(forgetpassgiude.this,verifactionMail.class);
                Toast.makeText(forgetpassgiude.this,"verification",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

    }

    public void gobackLogin(View view) {
        startActivity( new Intent(forgetpassgiude.this, LoginGuide.class));
    }


}