package com.monta.atourguide;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.monta.atourguide.Models.Guide;
import com.monta.atourguide.Models.Tourist;

import java.util.HashMap;

public class loginTourist extends AppCompatActivity {
    EditText editTextTOURPassword, editTextemailT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_tourist);

        SharedPreferences Sharedfile = getSharedPreferences("personInformation", Context.MODE_PRIVATE);

        boolean is_connected = Sharedfile.getBoolean("is_conn", true);

        /*if (is_connected) {

            Intent intent = new Intent(loginTourist.this, HomeTouristActivity.class);
            startActivity(intent);

        }*/
        FirebaseAuth mAuth;
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUserTourist = mAuth.getCurrentUser();
        if( currentUserTourist != null ) {
            Intent intent = new Intent(loginTourist.this, HomeTouristActivity.class);
            startActivity(intent);


        }




        Button btnregisterT = findViewById(R.id.btnregisterT);
        btnregisterT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextemailT = findViewById(R.id.editTextemailT);
                editTextTOURPassword = findViewById(R.id.editTextTOURPassword);


                String email = editTextemailT.getText().toString();
                String password = editTextTOURPassword.getText().toString();


                //   String emailformgeriT= Sharedfile.getString("emailTo","emailno fount");
                //  String passregiT =Sharedfile.getString("passwordt","pass no fount");
                String namegiT = Sharedfile.getString("nameT", "name no fount");

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(loginTourist.this, "You left an empty field", Toast.LENGTH_SHORT).show();


                } else {


                    FirebaseAuth mAuth;
                    // ...
                    // Initialize Firebase Auth
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(loginTourist.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser userTourist = mAuth.getCurrentUser();



                                        FirebaseDatabase databaseR = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = databaseR.getReference("tourist").child(  userTourist.getUid());

                                        // Read from the database
                                        myRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                Tourist tourist= dataSnapshot.getValue(Tourist.class);
                                                Log.d(TAG, "Value is: " +  tourist);


                                                // Toast.makeText(LoginGuide.this, "nfull"+ guide.getFullname(), Toast.LENGTH_SHORT).show();

                                                //  Toast.makeText(LoginGuide.this, "name:"+ guide.getCity(), Toast.LENGTH_SHORT).show();

                                                FirebaseFirestore database = FirebaseFirestore.getInstance();
                                                HashMap<String, Object> data = new HashMap<>();
                                                data.put("nom et prenom",tourist.getName());

                                                database.collection("usersGuide").add(data).addOnSuccessListener(documentReference -> {
                                                    Toast.makeText(loginTourist.this, "information insérée avec succès", Toast.LENGTH_SHORT).show();

                                                }).addOnFailureListener(exception -> {
                                                    Toast.makeText(loginTourist.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                                                });

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError error) {
                                                // Failed to read value
                                                Log.w(TAG, "Failed to read value.", error.toException());
                                            }
                                        });


                                        Toast.makeText(loginTourist.this, "logged in successfully.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(loginTourist.this, HomeTouristActivity.class);
                                        intent.putExtra("nameT", namegiT);
                                        intent.putExtra("email", email);

                                        //intent.putExtra("password",password);
                                        startActivity(intent);
                                       /* SharedPreferences.Editor edit = Sharedfile.edit();
                                        edit.putBoolean("is_conn", true);
                                        edit.commit();*/
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(loginTourist.this, "check your pass or email.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });


                }
            }
        });
        TextView forgetpassg = findViewById(R.id.forgetpassgiude);
        forgetpassg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginTourist.this, forgetpassgiude.class);
                startActivity(intent);
            }
        });
        Button signUpTou = findViewById(R.id.signUpTou);
        signUpTou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginTourist.this, registertourist.class);
                startActivity(intent);
            }
        });
        String email = getIntent().getStringExtra("emailT");
        String password = getIntent().getStringExtra("passwordt");

        TextView temail = findViewById(R.id.editTextemailT);
        temail.setText(email);
        TextView tpassword = findViewById(R.id.editTextTOURPassword);
        tpassword.setText(password);
    }
}