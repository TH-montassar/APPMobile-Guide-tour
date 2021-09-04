package com.monta.atourguide.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.monta.atourguide.Models.Guide;
import com.monta.atourguide.R;
import com.monta.atourguide.detaiilsporofileG;
import com.monta.atourguide.loginTourist;
import com.monta.atourguide.ui.home.HomeFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GuideAdapter extends RecyclerView.Adapter<GuideViewHolder> {

    Context mContext;
    List<Guide>GuideListe;
    HomeFragment homeFragment;



    public GuideAdapter(Context mContext,List<Guide> guideListe) {
        GuideListe = guideListe;
        this.mContext = mContext;
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
        holder.imageduide.setImageResource(guide.getImgGd());
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
                    intent.putExtra("descriptionDetailsGuide",guide.getDescriptionDetails());
                    intent.putExtra("sexGuide",guide.getSex());
                    intent.putExtra("ageGuide",guide.getAge());
                    intent.putExtra("numberGuide",guide.getNumber());
                    intent.putExtra("imageGuide",guide.getImgGd());
                    intent.putExtra("priceGuide",guide.getPrice());

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
