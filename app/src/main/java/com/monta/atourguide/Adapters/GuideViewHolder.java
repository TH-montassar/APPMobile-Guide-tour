package com.monta.atourguide.Adapters;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.monta.atourguide.R;

import org.jetbrains.annotations.NotNull;

public class GuideViewHolder extends RecyclerView.ViewHolder {
    TextView name_gd, desc_gd, gd_price, gd_date,fullname,gd_city;
    ImageView imageduide;
    Button profileGD_btn;
    ConstraintLayout  itemguide_btn;


    public GuideViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        profileGD_btn=itemView.findViewById(R.id.profileGD_btn);
        name_gd = itemView.findViewById(R.id.name_gd);
        desc_gd = itemView.findViewById(R.id.desc_gd);
        gd_price = itemView.findViewById(R.id.gd_price);
        gd_city = itemView.findViewById(R.id.gd_city);
        imageduide = itemView.findViewById(R.id.imageduide);
        fullname=itemView.findViewById(R.id.fullname);
        itemguide_btn=itemView.findViewById(R.id.itemguide_btn);



    }
}
