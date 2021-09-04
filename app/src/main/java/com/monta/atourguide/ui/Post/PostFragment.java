package com.monta.atourguide.ui.Post;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.monta.atourguide.Adapters.ProfileGAdapter;
import com.monta.atourguide.Models.Post;
import com.monta.atourguide.R;
import com.monta.atourguide.databinding.FragmentPostBinding;

import java.util.ArrayList;
import java.util.List;

public class PostFragment extends Fragment {

    private PostViewModel notificationsViewModel;
    private FragmentPostBinding binding;
    RecyclerView recyclerView;
    ProfileGAdapter profileGAdapter;
    List<Post> posts;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(PostViewModel.class);

        binding = FragmentPostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.recypost);
       /* posts =new ArrayList<>();
        for (int i=0;i<10;i++){
            posts.add(new Post("title"," ergnjerhb ijreighe h:", R.drawable.lgog));
            posts.add(new Post("tunis","fkrb birb viu bvb ihebl iebvhbvijbf ibei h", R.drawable.avatargratuit));}

        profileGAdapter=new ProfileGAdapter(getContext(),posts);*/


        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        recyclerView.setHasFixedSize(true);


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
                    postList.add(post);

                }
                profileGAdapter = new ProfileGAdapter(getContext(), postList);
                recyclerView.setAdapter(profileGAdapter);

                Toast.makeText(getContext(), "size" + postList.size(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Value is: " + postList);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



        /*final TextView textView = binding.textPost;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}