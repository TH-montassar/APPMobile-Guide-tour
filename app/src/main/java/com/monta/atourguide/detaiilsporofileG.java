package com.monta.atourguide;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.monta.atourguide.Adapters.ProfileGAdapter;
import com.monta.atourguide.Models.Guide;
import com.monta.atourguide.Models.Post;
import com.monta.atourguide.Models.Tourist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class detaiilsporofileG extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    RecyclerView recyclerView;
    ProfileGAdapter profileGAdapter;
    List<Post> posts;

    ImageView imageProfile;
    ImageView missagechat;
    TextView textName,textFullName,textEmail,textCity,textnumber,textprice,textsex,textage,textdescd;
    private Toolbar toolbarr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detaiilsporofile_g);
        toolbarr= findViewById(R.id.toolbarr);
        setSupportActionBar(toolbarr);
        getSupportActionBar().setTitle("ATourGuide");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarr.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
       /* getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2C89EC")));*/

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
        String id =getIntent().getStringExtra("userid");



        DatabaseReference DRef;

        StorageReference mstorageRef = FirebaseStorage.getInstance().getReference();
        DRef = FirebaseDatabase.getInstance().getReference("guide").child(id);
        // Read from the database
        DRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Guide guide = dataSnapshot.getValue(Guide.class);

                File localfile = null;
                try {
                    localfile = File.createTempFile("images", "jpg");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                final File finallocalfile = localfile;
                StorageReference reversRef = mstorageRef.child(guide.getImgGd());
                reversRef.getFile(finallocalfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(finallocalfile.getAbsolutePath());
                        imageProfile.setImageBitmap(bitmap);
                    }
                });



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });





        imageProfile=findViewById(R.id.imageProfileG);
        textName=findViewById(R.id.textName);
        textFullName=findViewById(R.id.textFullName);
        textEmail=findViewById(R.id.textEmail);
        textCity=findViewById(R.id.textCity);
        textnumber=findViewById(R.id.textnumber);
        textprice=findViewById(R.id.textprice);
        textsex=findViewById(R.id.textsex);
        textage=findViewById(R.id.textage);
        textdescd=findViewById(R.id.textdescd);

      //  imageProfile.setImageResource(imageGuide);
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

        Intent intent;
        intent = getIntent();

        final String userid = intent.getStringExtra("userid");

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
                    String idpost=post.getIduser();
                    if (userid.equals(idpost)){
                        postList.add(post);

                    }



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

        missagechat=findViewById(R.id.missagechat);
        missagechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FirebaseAuth mAuth;
                // Initialize Firebase Auth
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUserGuide = mAuth.getCurrentUser();

                DatabaseReference myReff = database.getReference("guide");
                myReff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        Guide guide = snapshot.getValue(Guide.class);


                        Intent intent=new Intent(detaiilsporofileG.this,MessageActivityChat.class);
                        intent.putExtra("userid", guide.getId());
                        intent.putExtra("imagereciver",guide.getImgGd());
                        startActivity(intent);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(detaiilsporofileG.this, "The image could not be loaded", Toast.LENGTH_SHORT).show();
                    }
                });




            }
        });






    }

   /* @Override
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
    }*/
}