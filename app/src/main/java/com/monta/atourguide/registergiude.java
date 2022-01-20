package com.monta.atourguide;

import static com.airbnb.lottie.L.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.monta.atourguide.Models.Guide;

import de.hdodenhof.circleimageview.CircleImageView;

public class registergiude extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    public static final int SELECT_PICTURE = 1, PICK_IMAGE = 2;
    Uri imageUri_nav;
    CircleImageView addilmg;
    ProgressBar simpleProgressBar;
    StorageReference mStorageRef, imageProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registergiude);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        addilmg = findViewById(R.id.addilmg);
        addilmg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });


        Button btnregiG = findViewById(R.id.btnregistergiude);
        btnregiG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextname, editfullname, editemail, editPassword, editPwdConfirme, editphone, editcity,PRIX,description;


                editTextname = findViewById(R.id.editgiudeName);
                editfullname = findViewById(R.id.editTextfullname);
                editemail = findViewById(R.id.editTextemail);
                editPassword = findViewById(R.id.editTextgiudePassword);

                editPwdConfirme = findViewById(R.id.editTextgiPwdConfirme);
                editphone = findViewById(R.id.editTextgphone);
                editcity = findViewById(R.id.editTextgicity);
                PRIX = findViewById(R.id.PRIX);
                description = findViewById(R.id.description);


                String name = editTextname.getText().toString();
                String fullname = editfullname.getText().toString();
                String email = editemail.getText().toString();
                String password = editPassword.getText().toString();
                String pwdConfirme = editPwdConfirme.getText().toString();
                String phone = editphone.getText().toString();
                String city = editcity.getText().toString();
                String prixSer =PRIX.getText().toString();
                String descri =description.getText().toString();



                if (name.isEmpty() || name.length() < 4) {

                    editTextname.setError(" il foux remplir et min 4 ");
                } else if (fullname.isEmpty() || fullname.length() < 4) {

                    editfullname.setError(" il foux remplir et min 4 ");
                } else if (email.isEmpty() || (!Patterns.EMAIL_ADDRESS.matcher(email).matches())) {

                    Toast.makeText(registergiude.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();


                    editemail.setError(" il foux rrecpect les Patterns de email ");
                } else if (password.isEmpty() || password.length() < 8) {

                    editPassword.setError(" il foux remplir et min 8 ");
                } else if (!password.equals(pwdConfirme)) {
                    Toast.makeText(registergiude.this, "Password Not matching", Toast.LENGTH_SHORT).show();
                    editPwdConfirme.setError(" il foux rrecpect les Patterns de email ");

                } else if (phone.isEmpty() || phone.length() < 8) {
                    Toast.makeText(registergiude.this, "phone 8 min", Toast.LENGTH_SHORT).show();


                    editphone.setError(" il foux remplir et min 4 ");
                } else if (city.isEmpty()) {
                    Toast.makeText(registergiude.this, "city is imp", Toast.LENGTH_SHORT).show();


                } else {
                    simpleProgressBar = findViewById(R.id.progbar);
                    simpleProgressBar.setVisibility(View.VISIBLE);
                    btnregiG.setVisibility(View.INVISIBLE);



              /* SharedPreferences Sharedfile = getSharedPreferences("personInformation", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = Sharedfile.edit();
                editor.putString("name", name);
                editor.putString("fullname", fullname);
                editor.putString("email", email);
                editor.putString("password", password);
                editor.putString("pwdConfirme", pwdConfirme);
                editor.putString("phone", phone);
                editor.putString("city", city);
                editor.commit();*/


                    FirebaseAuth mAuth;
                    mAuth = FirebaseAuth.getInstance();


                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(registergiude.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {


                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");

                                        FirebaseUser currentUser = mAuth.getCurrentUser();
                                        assert currentUser != null;
                                        String userid = currentUser.getUid();

                                        ProgressDialog progressDialog
                                                = new ProgressDialog(registergiude.this);
                                        progressDialog.setTitle("Uploading...");
                                        progressDialog.show();

                                        mStorageRef = FirebaseStorage.getInstance().getReference();
                                        imageProfile = mStorageRef.child("guidemedia/profile_img/" +userid+ ".jpg");


                                        imageProfile.putFile(imageUri_nav).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                String pathImage = taskSnapshot.getMetadata().getPath();

                                                Guide guide = new Guide(userid, name, fullname, email, city, Integer.parseInt(phone),pathImage,Float.parseFloat(prixSer),descri);


                                                // Write a message to the database
                                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                DatabaseReference myRef = database.getReference("guide").child(userid);
                                                myRef.setValue(guide).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        simpleProgressBar.setVisibility(View.INVISIBLE);
                                                        btnregiG.setVisibility(View.VISIBLE);

                                                        Toast.makeText(registergiude.this, "Authentication success.", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(registergiude.this, LoginGuide.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                                        intent.putExtra("email", email);
                                                        // intent.putExtra("password", password);
                                                        startActivity(intent);


                                                    }
                                                });
                                            }
                                        });






                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(registergiude.this, "Your email or password has already been used.", Toast.LENGTH_SHORT).show();

                                        simpleProgressBar.setVisibility(View.INVISIBLE);
                                        btnregiG.setVisibility(View.VISIBLE);

                                    }
                                }
                            });

                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE) {
            if (data != null) {
                imageUri_nav = data.getData();
                addilmg.setImageURI(imageUri_nav);


            }
        }
    }
}