package com.monta.atourguide.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.monta.atourguide.MessageActivityChat;
import com.monta.atourguide.Models.Guide;
import com.monta.atourguide.Models.Messageschats;
import com.monta.atourguide.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageschattingAdapter extends RecyclerView.Adapter<MessageschattingAdapter.ViewHolder> {
    public  static final  int MSG_TYPE_LEFT=0;
    public  static final  int MSG_TYPE_RIGHT=1;

    private String IMG_URL;
    private Context mcontext;
    private List<Messageschats> Messageschattinglist;
    private StorageReference mstorageRef;
    FirebaseUser fuser;


    public MessageschattingAdapter(Context mcontext, List<Messageschats> Messageschattinglist,String IMG_URL) {
        this.mcontext = mcontext;
        this.Messageschattinglist = Messageschattinglist;
        this.IMG_URL = IMG_URL;
        mstorageRef = FirebaseStorage.getInstance().getReference();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView showMsgText;
        private CircleImageView Pimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            showMsgText = itemView.findViewById(R.id.showMsgText);
            Pimage = itemView.findViewById(R.id.Pimage);
        }
    }


    @NonNull
    @Override
    public MessageschattingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        if(viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right, parent, false);

            return new MessageschattingAdapter.ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, parent, false);

            return new MessageschattingAdapter.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageschattingAdapter.ViewHolder holder, int position) {
      Messageschats messageschats = Messageschattinglist.get(position);
        holder.showMsgText.setText(messageschats.getMessge());
       // if (IMG_URL.equals("default")){
        holder.Pimage.setImageResource(R.mipmap.ic_launcher);
       // }else{

         //   Glide.with(mcontext).load(IMG_URL).into(holder.Pimage);
       // }

    }

    @Override
    public int getItemCount() {
        return  Messageschattinglist.size();
    }
    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if ( Messageschattinglist.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else {
            return MSG_TYPE_LEFT;
        }
    }


}
