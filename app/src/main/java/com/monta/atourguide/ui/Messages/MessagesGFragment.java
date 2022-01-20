package com.monta.atourguide.ui.Messages;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
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
import com.monta.atourguide.Adapters.ViewPagerAdapter;
import com.monta.atourguide.Models.Guide;
import com.monta.atourguide.R;
import com.monta.atourguide.ui.TouristFragmentChat;
import com.monta.atourguide.ui.chatFragment;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


public class MessagesGFragment extends Fragment {

    CircleImageView profileImage;
    TextView username;
    FirebaseUser currentUser;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    // Create a Cloud Storage reference from the app
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages_g, container, false);

        profileImage = view.findViewById(R.id.profile_imgG);
        username = view.findViewById(R.id.usernameG);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        String iduser = currentUser.getUid();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("guide").child(iduser);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Guide guide = snapshot.getValue(Guide.class);
                username.setText(guide.getName() + " " + guide.getFullname());
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
                        profileImage.setImageBitmap(bitmap);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        TabLayout tablayout = view.findViewById(R.id.tab_layout);
        ViewPager viewpage = view.findViewById(R.id.viewpage);

        ViewPagerAdapter viewpagerAdapter=new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewpagerAdapter.addFragment(new chatFragment(),"chats");
        viewpagerAdapter.addFragment(new TouristFragmentChat(),"user");

        viewpage.setAdapter(viewpagerAdapter);
        tablayout.setupWithViewPager(viewpage);


        return view;

    }

}