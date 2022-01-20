package com.monta.atourguide.ui.profileG;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.storage.UploadTask;
import com.monta.atourguide.Adapters.ProfileGAdapter;
import com.monta.atourguide.Models.Guide;
import com.monta.atourguide.Models.Post;
import com.monta.atourguide.R;
import com.monta.atourguide.databinding.FragmentProfileBinding;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    RecyclerView recyclerView;
    ProfileGAdapter profileGAdapter;
    List<Post> posts;
    CircleImageView imageProfileG;
    TextView tCity, textName, textEmail, textmumber, textprice, textdescd;
    public static final int SELECT_PICTUR = 1;
    StorageReference storageRef;


    private ProflieViewModel profileViewModel;

    private FragmentProfileBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProflieViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        tCity = root.findViewById(R.id.tCity);
        textName = root.findViewById(R.id.textName);

        textEmail = root.findViewById(R.id.textEmail);
        textmumber = root.findViewById(R.id.textmumber);

        textprice = root.findViewById(R.id.textprice);
        textdescd = root.findViewById(R.id.textdescd);


        imageProfileG = root.findViewById(R.id.imageProfileG);
        imageProfileG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);*/


                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, SELECT_PICTUR);
            }
        });

        recyclerView = root.findViewById(R.id.recyclerViewProfileGDd);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setHasFixedSize(true);
        //  posts =new ArrayList<>();
     /*   for (int i=0;i<10;i++){
        posts.add(new Post("title"," ergnjerhb ijreighe h:", R.drawable.lgog));
        posts.add(new Post("tunis","fkrb birb viu bvb ihebl iebvhbvijbf ibei h", R.drawable.avatargratuit));}*/

        FirebaseAuth mAuth;
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUserGuide = mAuth.getCurrentUser();
        String idcurrentuser = currentUserGuide.getUid();

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
                    String idPost = post.getIduser();
                    if (idcurrentuser.equals(idPost)) {

                        postList.add(post);
                    }


                }
                Collections.reverse(postList);
                profileGAdapter = new ProfileGAdapter(getContext(), postList);
                recyclerView.setAdapter(profileGAdapter);
/*
                Toast.makeText(getContext(), "size" + postList.size(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Value is: " + postList);*/

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        DatabaseReference myReff = database.getReference("guide").child(currentUserGuide.getUid());
        myReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                Guide guide = snapshot.getValue(Guide.class);
                String cety = guide.getCity();
                String Name = guide.getName();
                String fName = guide.getFullname();
                String Email = guide.getEmail();
                Integer mumber = guide.getNumber();
                Float price = guide.getPrice();
                String descd = guide.getDescription();

                tCity.setText(cety);
                textName.setText(Name + " " + fName);
                textEmail.setText(Email);
                textmumber.setText(mumber.toString());
                textprice.setText(price.toString());
                textdescd.setText(descd);


                File localfile = null;
                try {
                    localfile = File.createTempFile("images", "jpg");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                final File finallocalfile = localfile;

                storageRef = FirebaseStorage.getInstance().getReference();
                StorageReference reversRef = storageRef.child(guide.getImgGd());
                reversRef.getFile(finallocalfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(finallocalfile.getAbsolutePath());
                        imageProfileG.setImageBitmap(bitmap);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "The image could not be loaded", Toast.LENGTH_SHORT).show();
            }
        });













       /*final TextView textView = binding;
        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();


        if (requestCode == SELECT_PICTUR) {
            if (data != null) {
                ProgressDialog progressDialog
                        = new ProgressDialog(getContext());
                progressDialog.setTitle("Uploading...");
                progressDialog.show();
                Uri imageSelcted = data.getData();
                //imageProfileG.setImageURI(imageSelcted);

                String iduser = currentUser.getUid();
                storageRef = FirebaseStorage.getInstance().getReference();
                StorageReference imageprofile = storageRef.child("guidemedia/profile_img/" + iduser + ".jpg");
                imageprofile.putFile(imageSelcted).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Image uploaded successfully
                        // Dismiss dialog
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Image Uploaded!!", Toast.LENGTH_SHORT).show();

                        String pathImage = taskSnapshot.getMetadata().getPath();
                        Guide guide = new Guide(pathImage);
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("guide").child(currentUser.getUid());
                        myRef.setValue(guide).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                imageProfileG.setImageURI(imageSelcted);
                                Toast.makeText(getContext(), "The photo has been added successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}