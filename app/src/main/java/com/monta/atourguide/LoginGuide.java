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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.monta.atourguide.Adapters.ProfileGAdapter;
import com.monta.atourguide.Models.Guide;
import com.monta.atourguide.Models.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LoginGuide extends AppCompatActivity {
    EditText editTextmailg, editTextpasswordg;
    TextView TextViewerror;
    private LoginButton loginButton;
    private FirebaseAnalytics mFirebaseAnalytics;


    CallbackManager callbackManager = CallbackManager.Factory.create();
    private static final String EMAIL = "email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginguide);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        SharedPreferences Sharedfile = getSharedPreferences("personInformation", Context.MODE_PRIVATE);

        boolean is_connected = Sharedfile.getBoolean("is_conn", true);

      /*  if (is_connected) {

            Intent intent = new Intent(LoginGuide.this, HomeGuide.class);
            startActivity(intent);

        }*/
        FirebaseAuth mAuth;
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUserGuide = mAuth.getCurrentUser();


        if (currentUserGuide != null) {
            Intent intent = new Intent(LoginGuide.this, HomeGuide.class);
            startActivity(intent);


        }


        Button btnlog = findViewById(R.id.btnregistergiude);
        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBar simpleProgressBar =findViewById(R.id.progbar);
                simpleProgressBar.setVisibility(View.VISIBLE);



                // Bundle bundle = new Bundle();
                //   bundle.putString(FirebaseAnalytics.Param.METHOD, method);
                // mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);


                editTextmailg = findViewById(R.id.editTextemail);
                editTextpasswordg = findViewById(R.id.editTextgiudePassword);
                TextViewerror = findViewById(R.id.zone_eruur_missage);

                String email = editTextmailg.getText().toString();
                String password = editTextpasswordg.getText().toString();


                // String emailformgeri = Sharedfile.getString("email", "emailno fount");
                // String passregi = Sharedfile.getString("password", "pass no fount");
                String namegi = Sharedfile.getString("name", "name no fount");
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginGuide.this, "You left an empty field", Toast.LENGTH_SHORT).show();


                } else {
                /*if (email.isEmpty() || (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) ) {
                    editTextmailg.setError("il faut  remplire le email\n" +
                            "le type email (@...com)");

                    TextViewerror.setText("😲😲😲😲verif pls");
                    TextViewerror.setTextColor(Color.parseColor("#FF0000"));
                } else if ((password.isEmpty()) || (password.length() < 8)) {
                    editTextpasswordg.setError("il faut remplire le password " +
                            " min 8 caracter");

                    TextViewerror.setText("😡😡😡😡verif pls");
                    // TextViewerror.setTextColor(ContextCompat.getColor(Context,R.color.red));
                    TextViewerror.setTextColor(Color.parseColor("#FF0000"));


                } */
                    SharedPreferences.Editor edit = Sharedfile.edit();
                    edit.putBoolean("is_conn", true);
                    edit.commit();


                   /* FirebaseAuth mAuth;
                    // ...
                    // Initialize Firebase Auth
                    mAuth = FirebaseAuth.getInstance();*/
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginGuide.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");


                                        FirebaseUser userGuide = mAuth.getCurrentUser();

                                        FirebaseDatabase databaseR = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = databaseR.getReference("guide").child( userGuide.getUid());

                                        // Read from the database
                                        myRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                Guide guide= dataSnapshot.getValue(Guide.class);
                                                Log.d(TAG, "Value is: " +  guide);


                                               // Toast.makeText(LoginGuide.this, "nfull"+ guide.getFullname(), Toast.LENGTH_SHORT).show();

                                              //  Toast.makeText(LoginGuide.this, "name:"+ guide.getCity(), Toast.LENGTH_SHORT).show();

                                                /*FirebaseFirestore database = FirebaseFirestore.getInstance();
                                                HashMap<String, Object> data = new HashMap<>();
                                                data.put("prenom",guide.getName());
                                                data.put("nom", guide.getFullname());
                                                database.collection("usersGuide").add(data).addOnSuccessListener(documentReference -> {
                                                    Toast.makeText(LoginGuide.this, "information insérée avec succès", Toast.LENGTH_SHORT).show();

                                                }).addOnFailureListener(exception -> {
                                                    Toast.makeText(LoginGuide.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                                                });*/

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError error) {
                                                // Failed to read value
                                                Log.w(TAG, "Failed to read value.", error.toException());
                                            }
                                        });







                                        Toast.makeText(LoginGuide.this, "logged in successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginGuide.this, HomeGuide.class);
                                        /*intent.putExtra("namegi", namegi);
                                        intent.putExtra("email", email);


                                        intent.putExtra("password",password);*/
                                        startActivity(intent);

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginGuide.this, "Verify your email or password!", Toast.LENGTH_SHORT).show();

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
                Intent intent = new Intent(LoginGuide.this, forgetpassgiude.class);
                startActivity(intent);
            }
        });


        Button signUpg = findViewById(R.id.signUpguid);
        signUpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginGuide.this, registergiude.class);
                startActivity(intent);
            }
        });




       /* ImageView imageViewFcbk=findViewById(R.id.fclogin);
        imageViewFcbk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com"));
                startActivity(intent);
            }
        });*/

        FacebookSdk.sdkInitialize(getApplicationContext());


        loginButton = (LoginButton) findViewById(R.id.login_fb);
        loginButton.setReadPermissions("email", "public_profile", "user_friends");


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(LoginGuide.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginGuide.this, "Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(LoginGuide.this, "Error" + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

       /* FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("email");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);

                EditText temail = findViewById(R.id.editTextemail);
                temail.setText(value);
             //   Toast.makeText(LoginGuide.this, value, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

*/


        // String email = getIntent().getStringExtra("email");
        //  String password = getIntent().getStringExtra("password");

        // TextView temail = findViewById(R.id.editTextemail);
        //temail.setText(email);
        ///  TextView tpassword = findViewById(R.id.editTextgiudePassword);
        //tpassword.setText(password);

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}