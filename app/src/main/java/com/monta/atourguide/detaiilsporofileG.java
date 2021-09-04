package com.monta.atourguide;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.monta.atourguide.Adapters.ProfileGAdapter;
import com.monta.atourguide.Models.Post;

import java.util.ArrayList;
import java.util.List;

public class detaiilsporofileG extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    RecyclerView recyclerView;
    ProfileGAdapter profileGAdapter;
    List<Post> posts;

    ImageView imageProfile;
    TextView textName,textFullName,textEmail,textCity,textnumber,textprice,textsex,textage,textdescd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaiilsporofile_g);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C89EC")));


        String nameGuide = getIntent().getStringExtra("nameGuide");
        String fullnameGuide = getIntent().getStringExtra("fullnameGuide");
        String emailGuide = getIntent().getStringExtra("emailGuide");
        String cityGuide = getIntent().getStringExtra("cityGuide");
        String descriptionDetailsGuide = getIntent().getStringExtra("descriptionDetailsGuide");
        String sexGuide = getIntent().getStringExtra("sexGuide");
        Integer ageGuide = getIntent().getIntExtra("ageGuide", 00);
        Integer numberGuide = getIntent().getIntExtra("numberGuide", 00000000);
        Integer imageGuide = getIntent().getIntExtra("imageGuide", R.drawable.backgroud);
        Float priceGuide = getIntent().getFloatExtra("priceGuide", 000);


        imageProfile=findViewById(R.id.imageProfile);
        textName=findViewById(R.id.textName);
        textFullName=findViewById(R.id.textFullName);
        textEmail=findViewById(R.id.textEmail);
        textCity=findViewById(R.id.textCity);
        textnumber=findViewById(R.id.textnumber);
        textprice=findViewById(R.id.textprice);
        textsex=findViewById(R.id.textsex);
        textage=findViewById(R.id.textage);
        textdescd=findViewById(R.id.textdescd);

        imageProfile.setImageResource(imageGuide);
        textName.setText(nameGuide);
        textFullName.setText(fullnameGuide);
        textEmail.setText(emailGuide);
        textCity.setText(cityGuide);
        textnumber.setText(numberGuide.toString());
        textprice.setText(priceGuide.toString());
        textsex.setText(sexGuide);
        textage.setText(ageGuide.toString());
        textdescd.setText(descriptionDetailsGuide);


















        recyclerView = findViewById(R.id.recyclerViewProfileGDd);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("post");
        //final List<Post> postList = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                List<Post> postList = new ArrayList<>();

                for (DataSnapshot psotSnapshot : dataSnapshot.getChildren()) {
                    Post post = psotSnapshot.getValue(Post.class);
                    postList.add(post);

                }
                profileGAdapter = new ProfileGAdapter(detaiilsporofileG.this, postList);
                recyclerView.setAdapter(profileGAdapter);

                Toast.makeText(detaiilsporofileG.this, "size" + postList.size(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Value is: " + postList);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        /*for (int i = 0; i < 10; i++) {
            posts.add(new Post("title", " ergnjerhb ijreighe h:", R.drawable.lgog));
            posts.add(new Post("tunis", "fkrb birb viu bvb ihebl iebvhbvijbf ibei h", R.drawable.avatargratuit));
        }*/







    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.porifile_details_rofile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.retour:


                Intent intent = new Intent(detaiilsporofileG.this, HomeTouristActivity.class);
                startActivity(intent);
                break;
        }

        return (super.onOptionsItemSelected(item));
    }
}