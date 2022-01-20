package com.monta.atourguide;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
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
import com.google.firebase.storage.UploadTask;
import com.monta.atourguide.Models.Guide;
import com.monta.atourguide.Models.Post;
import com.monta.atourguide.databinding.ActivityHomepageBinding;

import java.io.File;
import java.io.IOException;

public class HomeGuide extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomepageBinding binding;
    private FirebaseAnalytics mFirebaseAnalytics;
    EditText titleId, descId;
    Button btn_post, btn_cancel;
    ImageView insert_imgId, profile_guide_nav_header;
    public static final int SELECT_PICTURE = 1, PICK_IMAGE = 2;
    Uri imageUri_post, imageUri_nav;


    // Create a Cloud Storage reference from the app
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        setSupportActionBar(binding.appBarHomepage.toolbar);


        binding.appBarHomepage.addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showdialog();


            }
        });


   /* String email=getIntent().getStringExtra("email");
        String name=getIntent().getStringExtra("namegi");
        NavigationView mNavigationView = findViewById(R.id.nav_view);
        View headerView = mNavigationView.getHeaderView(0);
        TextView userEmail = headerView.findViewById(R.id.emailid);
        TextView userUser = headerView.findViewById(R.id.nameheader);
        userEmail.setText(email);
        userUser.setText(name);*/


        // get user name and email textViews


        FirebaseAuth mAuth;
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUserGuide = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("guide").child(currentUserGuide.getUid());

        NavigationView mNavigationView = findViewById(R.id.nav_view);
        View headerView = mNavigationView.getHeaderView(0);
        profile_guide_nav_header = headerView.findViewById(R.id.profile_guide_nav_header);
        profile_guide_nav_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeGuide.this, "you need to fixe problm intent to profile fragmentt", Toast.LENGTH_SHORT).show();
              /* Intent intent = new Intent(, ProfileFragment.class);
                startActivity(intent)*/
                ;


            }
        });

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Guide guide = dataSnapshot.getValue(Guide.class);
                //  Log.d(TAG, "Value is: " + guide);


                TextView userEmail = headerView.findViewById(R.id.emailid);
                TextView userUser = headerView.findViewById(R.id.nameheader);
                userEmail.setText(guide.getEmail());
                userUser.setText(guide.getName());
                File localfile = null;
                try {
                    localfile = File.createTempFile("images", "jpg");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                final File finallocalfile = localfile;
                StorageReference reversRef = storageRef.child(guide.getImgGd());
                reversRef.getFile(finallocalfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(finallocalfile.getAbsolutePath());
                        profile_guide_nav_header.setImageBitmap(bitmap);
                    }
                });


                //   Toast.makeText(LoginGuide.this, value, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_message, R.id.nav_contact)


                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_homepage);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    private void showdialog() {

        AlertDialog.Builder alert;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {

            alert = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            alert = new AlertDialog.Builder(this);
        }
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_addpostalert, null);
        titleId = view.findViewById(R.id.titleId);
        descId = view.findViewById(R.id.descId);
        btn_post = view.findViewById(R.id.btn_post);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        insert_imgId = view.findViewById(R.id.insert_imgId);
        insert_imgId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });


        alert.setView(view);
        alert.setCancelable(false);
        AlertDialog dialog = alert.create();
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth;
                // Initialize Firebase Auth
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUserGuide = mAuth.getCurrentUser();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("post");

                String Autoid = myRef.push().getKey();
                String id=currentUserGuide.getUid();


                //Toast.makeText(getApplicationContext(), "Authentication success.", Toast.LENGTH_SHORT).show();
                StorageReference imageposte = storageRef.child("guidemedia/postimage/" + Autoid + ".jpg");
                imageposte.putFile(imageUri_post).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String pathmg = taskSnapshot.getMetadata().getPath();

                        String title = titleId.getText().toString();

                        String desc = descId.getText().toString();
                        Post post = new Post(title, desc, pathmg,id,Autoid);

                        myRef.child(Autoid).setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                dialog.dismiss();
                                // Intent intent =new Intent(HomeGuide.this, PostFragment.class);
                                //startActivity(intent);
                            }
                        });


                    }
                });


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(), "Authentication", Toast.LENGTH_SHORT).show();
                //dialog.cancel();
                dialog.dismiss();
              /*  FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("post");
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

                        Toast.makeText(getApplicationContext(), "size" +postList.size(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Value is: " +postList);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
*/

            }
        });

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE) {
            if (data != null) {
                imageUri_post = data.getData();
                insert_imgId.setImageURI(data.getData());


            }


        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logOut) {
            /*SharedPreferences Sharedfile = getSharedPreferences("personInformation", Context.MODE_PRIVATE);
            boolean is_connected= Sharedfile.getBoolean("is_conn",false);
            SharedPreferences.Editor edit=Sharedfile.edit();
            edit.putBoolean("is_conn",false);
            edit.commit();*/
            FirebaseAuth mAuth;
            // ...
            // Initialize Firebase Auth
            mAuth = FirebaseAuth.getInstance();
            FirebaseAuth.getInstance().signOut();

            Toast.makeText(this, " Signed out successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(HomeGuide.this, MainActivity.class);
            startActivity(intent);

            return (true);
        } else if (item.getItemId() == R.id.action_settings) {
            Toast.makeText(this, "stting clicked", Toast.LENGTH_SHORT).show();


        }
        return (super.onOptionsItemSelected(item));
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_homepage);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /*public void gohame(MenuItem item) {
        SharedPreferences Sharedfile = getSharedPreferences("personInformation", Context.MODE_PRIVATE);
        boolean is_connected= Sharedfile.getBoolean("is_conn",false);
        SharedPreferences.Editor edit=Sharedfile.edit();
        edit.putBoolean("is_conn",false);
        edit.commit();



        Intent intent =new Intent(HomeGuide.this,LoginGuide.class);
        startActivity(intent);}*/
}