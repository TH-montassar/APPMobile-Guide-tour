package com.monta.atourguide.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.monta.atourguide.Models.Guide;
import com.monta.atourguide.R;
import com.monta.atourguide.detaiilsporofileG;
import com.monta.atourguide.loginTourist;
import com.monta.atourguide.ui.home.HomeFragment;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GuideAdapter extends RecyclerView.Adapter<GuideViewHolder> {

    Context mContext;
    List<Guide>GuideListe;
    HomeFragment homeFragment;
    private StorageReference mstorageRef ;



    public GuideAdapter(Context mContext,List<Guide> guideListe) {
        GuideListe = guideListe;
        this.mContext = mContext;
        mstorageRef = FirebaseStorage.getInstance().getReference();
    }

    @NonNull
    @NotNull
    @Override
    public GuideViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View layaut;
        layaut = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guides,parent,false);
        return new GuideViewHolder(layaut);
    }

    @Override   
    public void onBindViewHolder(@NonNull @NotNull GuideViewHolder holder, int position) {
        Guide guide=GuideListe.get(position);
        holder.name_gd.setText(guide.getName());
        holder.desc_gd.setText(guide.getDescription());


        holder.gd_price.setText(guide.getPrice().toString());

        holder.gd_city.setText(guide.getCity());


        File localfile = null;
        try {
            localfile = File.createTempFile("images", "jpg");

        } catch (IOException e) {
            e.printStackTrace();
        }
        final File finallocalfile = localfile;
        StorageReference reversRef = mstorageRef.child(guide.getImgGd());
        reversRef.getFile(finallocalfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Bitmap bitmap = BitmapFactory.decodeFile(finallocalfile.getAbsolutePath());
                holder.imageduide.setImageBitmap(bitmap);
            }
        });



        holder.fullname.setText(guide.getFullname());


        holder.profileGD_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /*SharedPreferences Sharedfile = mContext.getSharedPreferences("personInformation", Context.MODE_PRIVATE);
                boolean is_connected= Sharedfile.getBoolean("is_conn",true);*/
                FirebaseAuth mAuth;
                // Initialize Firebase Auth
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();


                if (currentUser != null){

                    Intent intent=new Intent(mContext, detaiilsporofileG.class);
                    intent.putExtra("nameGuide",guide.getName());
                    intent.putExtra("fullnameGuide",guide.getFullname());
                    intent.putExtra("emailGuide",guide.getEmail());
                    intent.putExtra("cityGuide",guide.getCity());
                    intent.putExtra("descriptionDetailsGuide",guide.getDescription());
                    intent.putExtra("sexGuide",guide.getSex());
                    intent.putExtra("ageGuide",guide.getAge());
                    intent.putExtra("numberGuide",guide.getNumber());
                    intent.putExtra("imageGuide",guide.getImgGd());
                    intent.putExtra("priceGuide",guide.getPrice());
                    intent.putExtra("userid", guide.getId());

                    mContext.startActivity(intent);

                }


                else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                    builder1.setMessage("You are not logged in !");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Login now ?",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent=new Intent(mContext, loginTourist.class);
                                    mContext.startActivity(intent);
                                }
                            });

                    builder1.setNegativeButton(
                            "cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                }


            }
        });



    }

    @Override
    public int getItemCount() {
        return GuideListe.size();
    }
}
