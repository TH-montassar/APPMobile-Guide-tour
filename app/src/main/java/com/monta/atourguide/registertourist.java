package com.monta.atourguide;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.monta.atourguide.Models.Tourist;

public class registertourist extends AppCompatActivity {
    EditText editTName, editTextemailTo, editTextTPassword, editgiPwdConfirme,editTextnumberTo;
    Button btnregisterTo;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registertourist);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        btnregisterTo = findViewById(R.id.btnregisterTo);
        btnregisterTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTName = findViewById(R.id.editTName);
                editTextemailTo = findViewById(R.id.editTextemailTo);
                editTextTPassword = findViewById(R.id.editTextTPassword);
                editgiPwdConfirme = findViewById(R.id.editgiPwdConfirme);
                editTextnumberTo=findViewById(R.id.editTextnumberTo);

                String name_fullname = editTName.getText().toString();

                String email = editTextemailTo.getText().toString();
                String phoneNumber = editTName.getText().toString();

                String password = editTextTPassword.getText().toString();
                String pwdConfirme = editgiPwdConfirme.getText().toString();


                if (name_fullname.isEmpty() || name_fullname.length() < 10) {

                    editTName.setError("The minimum possible number is 10 characters ");
                } else if (email.isEmpty() || (!Patterns.EMAIL_ADDRESS.matcher(email).matches())) {

                    Toast.makeText(registertourist.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();


                    editTextemailTo.setError(" il foux rrecpect les Patterns de email ");
                }
                else if (phoneNumber.isEmpty() || phoneNumber.length() < 8) {
                    Toast.makeText(registertourist.this, "phone 8 min", Toast.LENGTH_SHORT).show();


                    editTextnumberTo.setError(" il foux remplir et min 4 ");
                }


                else if (password.isEmpty() || password.length() < 8) {

                    editTextTPassword.setError("The minimum possible number is 10 characters ");
                } else if (!password.equals(pwdConfirme)) {
                    Toast.makeText(registertourist.this, "Password Not matching", Toast.LENGTH_SHORT).show();
                    editgiPwdConfirme.setError(" il foux rrecpect les Patterns de email ");

                } else {


                    FirebaseAuth mAuth;
                    // ...
                    // Initialize Firebase Auth
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(registertourist.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser userTourist = mAuth.getCurrentUser();
                                        Tourist tourist =new Tourist(name_fullname,email,phoneNumber);
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = database.getReference("tourist").child(userTourist.getUid());
                                        myRef.setValue( tourist).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                                Toast.makeText(registertourist.this, "Your account has been successfully created", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(registertourist.this, loginTourist.class);

                                                startActivity(intent);

                                            }
                                        });




                                       // intent.putExtra("emailT", email);
                                        //intent.putExtra("passwordt", password);

                                      /*  SharedPreferences Sharedfile = getSharedPreferences("personInformation", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = Sharedfile.edit();
                                        editor.putString("nameT", name_fullname);

                                        editor.putString("emailTo", email);
                                        editor.putString("passwordt", password);
                                        editor.putString("pwdConfirmet", pwdConfirme);

                                        editor.commit();*/


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(registertourist.this, "Your email or password has already been used.", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });


                }
            }
        });
    }
}