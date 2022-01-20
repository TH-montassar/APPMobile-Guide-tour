package com.monta.atourguide.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.monta.atourguide.Adapters.GuideAdapterChat;
import com.monta.atourguide.Adapters.TouristeAdapterChat;
import com.monta.atourguide.Models.Guide;
import com.monta.atourguide.Models.Tourist;
import com.monta.atourguide.R;

import java.util.ArrayList;
import java.util.List;

public class TouristFragmentChat extends Fragment {

    private RecyclerView recyclerView;
    private TouristeAdapterChat touristeAdapterChat;
    private List<Tourist> mtourist;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_tourist_chat, container, false);
        recyclerView = view.findViewById(R.id.recyclerviewusers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mtourist = new ArrayList<>();

        readmtourist();

        return view;
    }
    private void readmtourist() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("tourist");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mtourist.clear();
                for (DataSnapshot touristSnapshot : snapshot.getChildren()) {
                    Tourist tourist = touristSnapshot.getValue(Tourist.class);
                    assert tourist != null;
                    assert firebaseUser != null;
                    if(!tourist.getId().equals(firebaseUser.getUid())){
                        mtourist.add(tourist);
                    }


                }
                touristeAdapterChat = new  TouristeAdapterChat(getContext(), mtourist);
                recyclerView.setAdapter(touristeAdapterChat);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}