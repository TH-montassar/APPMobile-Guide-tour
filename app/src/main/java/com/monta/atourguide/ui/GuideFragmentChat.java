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
import com.monta.atourguide.Adapters.ProfileGAdapter;
import com.monta.atourguide.Models.Guide;
import com.monta.atourguide.R;

import java.util.ArrayList;
import java.util.List;

public class GuideFragmentChat extends Fragment {
    private RecyclerView recyclerView;
    private GuideAdapterChat guideAdapterChat;
    private List<Guide> mGuide;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_user_guide, container, false);
        recyclerView = view.findViewById(R.id.recyclerviewusers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mGuide = new ArrayList<>();
        readmGuide();

        return view;
    }

    private void readmGuide() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("guide");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mGuide.clear();
                for (DataSnapshot parentSnapshot : snapshot.getChildren()) {
                    Guide guide = parentSnapshot.getValue(Guide.class);


                        mGuide.add(guide);




                }
                guideAdapterChat = new GuideAdapterChat(getContext(), mGuide);
                recyclerView.setAdapter(guideAdapterChat);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
