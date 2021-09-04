package com.monta.atourguide.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
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
    private StorageReference mstorageRef ;


    public ProfileGAdapter(Context context, List<Post> Posts) {
        this.context = context;


        this.posts = Posts;
        mstorageRef = FirebaseStorage.getInstance().getReference();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title_pub, descrption_pub;
        ImageView images_pub;

        public ViewHolder(@NonNull @NotNull View itemView) {

            super(itemView);
            title_pub = itemView.findViewById(R.id.textitle);
            descrption_pub = itemView.findViewById(R.id.textdesc);
            images_pub = itemView.findViewById(R.id.imagepub);
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

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


}
