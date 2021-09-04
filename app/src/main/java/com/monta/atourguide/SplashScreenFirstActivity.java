package com.monta.atourguide;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.monta.atourguide.Models.Guide;

public class SplashScreenFirstActivity extends AppCompatActivity {
    pl.droidsonroids.gif.GifImageView giflogo, giftext;
    com.airbnb.lottie.LottieAnimationView lottiett;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_first);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FirebaseAuth mAuth;
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();




        /*FirebaseUser currentUserTourist = mAuth.getCurrentUser();

        DatabaseReference myReff = database.getReference("tourist").child(currentUserTourist.getUid());


       *//* if (currentUserTourist!= null) {
            Intent intent = new Intent(SplashScreenFirstActivity.this, HomeTouristActivity.class);
            startActivity(intent);


        }*/

        giflogo = findViewById(R.id.intrologo);
        giftext = findViewById(R.id.introtext);
        lottiett = findViewById(R.id.introlottie);

        giflogo.animate().translationY(-2500).setDuration(1000).setStartDelay(5000);
        giftext.animate().translationX(2000).setDuration(1000).setStartDelay(5000);
        lottiett.animate().translationY(1500).setDuration(1000).setStartDelay(5000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentUser != null) {

                    DatabaseReference myRef = database.getReference("guide").child(currentUser.getUid());
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.


                            Guide guide = dataSnapshot.getValue(Guide.class);
                            if (guide != null) {
                                Intent intent = new Intent(SplashScreenFirstActivity.this, HomeGuide.class);
                                startActivity(intent);
                            }else{

                                Intent intent = new Intent(SplashScreenFirstActivity.this,HomeTouristActivity.class);
                                startActivity(intent);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(TAG, "Failed to read value.", error.toException());

                        }

                    });
                }else {

                    Intent intent = new Intent(SplashScreenFirstActivity.this,MainActivity.class);
                    startActivity(intent);
                }

            }
        }, 5640);
    }

}