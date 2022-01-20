package com.monta.atourguide.Adapters;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.monta.atourguide.Models.Post;
import com.monta.atourguide.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProfileGAdapter extends RecyclerView.Adapter<ProfileGAdapter.ViewHolder> {


    private List<Post> posts;
    private Context context;
    private StorageReference mstorageRef;
    private DatabaseReference dbref;
    private FirebaseDatabase database;



    public ProfileGAdapter(Context context, List<Post> Posts) {
        this.context = context;


        this.posts = Posts;
        mstorageRef = FirebaseStorage.getInstance().getReference();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title_pub, descrption_pub;
        ImageView images_pub, iddeletpost;

        public ViewHolder(@NonNull @NotNull View itemView) {

            super(itemView);
            title_pub = itemView.findViewById(R.id.textitle);
            descrption_pub = itemView.findViewById(R.id.textdesc);
            images_pub = itemView.findViewById(R.id.imagepub);
            iddeletpost = itemView.findViewById(R.id.iddeletpost);
        }
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profileg, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Post p = posts.get(position);
        holder.title_pub.setText(p.getTitle());
        holder.descrption_pub.setText(p.getDesc());
        File localfile = null;
        try {
            localfile = File.createTempFile("images", "jpg");

        } catch (IOException e) {
            e.printStackTrace();
        }
        final File finallocalfile = localfile;
        StorageReference reversRef = mstorageRef.child(p.getImagePub());
        reversRef.getFile(finallocalfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Bitmap bitmap = BitmapFactory.decodeFile(finallocalfile.getAbsolutePath());
                holder.images_pub.setImageBitmap(bitmap);
            }
        });

        FirebaseAuth mAuth;
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUserGuide = mAuth.getCurrentUser();

        String idcurrentuser = currentUserGuide.getUid();

        String id_post_user = p.getIduser();
        String ipost =p.getIdpost();


        if (!id_post_user.equals(idcurrentuser)) {
            holder.iddeletpost.setVisibility(View.INVISIBLE);
        }

        holder.iddeletpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                builder.setTitle("Delete");
                builder.setMessage("Are you sure to delete this  post?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                      database = FirebaseDatabase.getInstance();
                        dbref = database.getReference();

                        dbref.child("post").child(ipost).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "post delete", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                });
                builder.create().show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


}
