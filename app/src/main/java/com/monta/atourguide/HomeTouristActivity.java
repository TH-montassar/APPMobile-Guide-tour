package com.monta.atourguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.monta.atourguide.databinding.ActivityHomeTouristBinding;

public class HomeTouristActivity extends AppCompatActivity {

    private ActivityHomeTouristBinding binding;
    private AppBarConfiguration appBarConfiguration;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityHomeTouristBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C89EC")));

        BottomNavigationView navView = findViewById(R.id.nav_view);


        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_profilet, R.id.navigation_messages, R.id.navigation_Post)

                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home_tourist);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.barhometourist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logOutt:
               /* SharedPreferences Sharedfile = getSharedPreferences("personInformation", Context.MODE_PRIVATE);
                boolean is_connected = Sharedfile.getBoolean("is_conn", false);
                SharedPreferences.Editor edit = Sharedfile.edit();
                edit.putBoolean("is_conn", false);
                edit.commit();*/
                FirebaseAuth mAuth;
                // ...
                // Initialize Firebase Auth
                mAuth = FirebaseAuth.getInstance();
                FirebaseAuth.getInstance().signOut();

                Toast.makeText(this, " Signed out successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(HomeTouristActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.action_signin:
               // Toast.makeText(this, "signin clicked", Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(HomeTouristActivity.this,loginTourist.class);
                startActivity(intent1);

                break;


        }


        /*if (item.getItemId() == R.id.action_logOutt) {
            SharedPreferences Sharedfile = getSharedPreferences("personInformation", Context.MODE_PRIVATE);
            boolean is_connected= Sharedfile.getBoolean("is_conn",false);
            SharedPreferences.Editor edit=Sharedfile.edit();
            edit.putBoolean("is_conn",false);
            edit.commit();
            Toast.makeText(this, " Signed out successfully", Toast.LENGTH_SHORT).show();

            Intent intent=new Intent(HomeTouristActivity.this, MainActivity.class);
            startActivity(intent);

            return(true);
        }
        else if (item.getItemId() == R.id.action_signin){
            Toast.makeText(this, "signin clicked", Toast.LENGTH_SHORT).show();


        }*/
        return (super.onOptionsItemSelected(item));
    }

}