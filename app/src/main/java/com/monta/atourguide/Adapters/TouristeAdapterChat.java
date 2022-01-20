package com.monta.atourguide.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.monta.atourguide.MessageActivityChat;
import com.monta.atourguide.Models.Guide;
import com.monta.atourguide.Models.Tourist;
import com.monta.atourguide.R;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TouristeAdapterChat  extends RecyclerView.Adapter<TouristeAdapterChat.ViewHolder> {
    private Context mcontext;
    private List<Tourist> mtoursit;
    private StorageReference mstorageRef;

    public TouristeAdapterChat(Context mcontext, List<Tourist> mtoursit) {
        this.mcontext = mcontext;
        this.mtoursit = mtoursit;
        mstorageRef = FirebaseStorage.getInstance().getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);

        return new TouristeAdapterChat.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tourist tourist = mtoursit.get(position);
        holder.mUserName.setText(tourist.getName());

        File localfile = null;
        try {
            localfile = File.createTempFile("images", "jpg");

        } catch (IOException e) {
            e.printStackTrace();
        }
        final File finallocalfile = localfile;
        StorageReference reversRef = mstorageRef.child(tourist.getImage());
        reversRef.getFile(finallocalfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Bitmap bitmap = BitmapFactory.decodeFile(finallocalfile.getAbsolutePath());
                holder.mprofileimg.setImageBitmap(bitmap);
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext , MessageActivityChat.class);
                intent.putExtra("usertid" , tourist.getId());
                mcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mtoursit.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mUserName;
        private CircleImageView mprofileimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserName = itemView.findViewById(R.id.usernamee);
            mprofileimg = itemView.findViewById(R.id.profil_img);
        }}
}
