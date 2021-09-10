package com.monta.atourguide;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.monta.atourguide.Adapters.MessageschattingAdapter;
import com.monta.atourguide.Models.Guide;
import com.monta.atourguide.Models.Messageschats;
import com.monta.atourguide.Models.Tourist;

import java.util.ArrayList;
import java.util.List;

public class MessageActivityChat extends AppCompatActivity {
    ImageView profileimg;
    TextView username;
    FirebaseUser fUserguide;
    DatabaseReference DRef;
    ImageButton btnsend;
    EditText missagesend;

    MessageschattingAdapter  messageschattingAdapter;
    List<Messageschats> Messageschattinglist;
    RecyclerView RcConversation;
    Intent intent;
    private Toolbar toolbar;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_chat);
        toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RcConversation = findViewById(R.id.RcConversation);

        RcConversation.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        RcConversation.setLayoutManager(linearLayoutManager);

        profileimg = findViewById(R.id.profileimg);
        username = findViewById(R.id.username);
        btnsend = findViewById(R.id.BtnSend);
        missagesend = findViewById(R.id.MsgToSend);


        intent = getIntent();

        final String userid = intent.getStringExtra("userid");
        final String usertid = intent.getStringExtra("usertid");

        //3
        fUserguide = FirebaseAuth.getInstance().getCurrentUser();
        //2


          if (userid!=null){
              btnsend.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      String msg = missagesend.getText().toString();
                      if (!msg.equals("")) {
                          sendmessages(fUserguide.getUid(), userid, msg);
                      } else {
                          Toast.makeText(MessageActivityChat.this, "you can't send empty message", Toast.LENGTH_SHORT).show();
                      }
                      missagesend.setText("");
                  }
              });



              DRef = FirebaseDatabase.getInstance().getReference("guide").child(userid);
              DRef.addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                      Guide guide = snapshot.getValue(Guide.class);

                          username.setText(guide.getName() + " " + guide.getFullname());
                          // if(guide.getImgGd().equals("default")){
                          profileimg.setImageResource(R.mipmap.ic_launcher);
                          //  }else{
                          //   Glide.with(MessageActivityChat.this).load(guide.getImgGd()).into(profileimg);
                          //   }




                      readMsg(fUserguide.getUid(),userid,guide.getImgGd());

                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {

                  }
              });



          }else{
              btnsend.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      String msg = missagesend.getText().toString();
                      if (!msg.equals("")) {
                          sendmessages(fUserguide.getUid(),usertid, msg);
                      } else {
                          Toast.makeText(MessageActivityChat.this, "you can't send empty message", Toast.LENGTH_SHORT).show();
                      }
                      missagesend.setText("");
                  }
              });

              DRef = FirebaseDatabase.getInstance().getReference("tourist").child(usertid);
              // Read from the database
              DRef.addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot) {
                      // This method is called once with the initial value and again
                      // whenever data at this location is updated.
                      Tourist tourist = dataSnapshot.getValue(Tourist.class);
                      username.setText(tourist.getName());
                      profileimg.setImageResource(R.drawable.bbb);

                      readMsg(fUserguide.getUid(),usertid,tourist.getImage());

                  }

                  @Override
                  public void onCancelled(DatabaseError error) {
                      // Failed to read value
                      Log.w(TAG, "Failed to read value.", error.toException());
                  }
              });
          }




    }

    //1
    private void sendmessages(String sender, String receiver, String messge) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Messageschats messageschats = new Messageschats(sender, receiver, messge);
        databaseReference.child("messageschats").push().setValue(messageschats);

    }

    private void readMsg(String myId, String IdP, String IMG_URL) {
        Messageschattinglist = new ArrayList<>();
        DRef = FirebaseDatabase.getInstance().getReference("messageschats");
        DRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Messageschattinglist.clear();
                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                    Messageschats messageschats = messageSnapshot.getValue(Messageschats.class);
                    if (messageschats.getReceiver().equals(myId) && messageschats.getSender().equals(IdP) ||
                            messageschats.getReceiver().equals(IdP) && messageschats.getSender().equals(myId)) {
                        Messageschattinglist.add(messageschats);
                    }
                    messageschattingAdapter = new MessageschattingAdapter(MessageActivityChat.this,  Messageschattinglist,IMG_URL);
                    RcConversation.setAdapter( messageschattingAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}