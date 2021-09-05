package com.monta.atourguide.ui.Messages;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.monta.atourguide.Models.Guide;
import com.monta.atourguide.Models.Tourist;
import com.monta.atourguide.R;
import com.monta.atourguide.databinding.FragmentMessagesBinding;
import com.monta.atourguide.ui.TouristFragmentChat;
import com.monta.atourguide.ui.chatFragment;
import com.monta.atourguide.ui.GuideFragmentChat;

import java.util.ArrayList;

public class MessagesFragment extends Fragment {
    ImageView imagesuser;
    TextView nameuser;


    FirebaseUser currentUserGuide, currentUsertoursit;


    /* */
    private FragmentMessagesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMessagesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        FirebaseAuth mAuth;
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUserGuide = mAuth.getCurrentUser();
        currentUsertoursit = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("guide").child(currentUserGuide.getUid());


        Toolbar toolbar = root.findViewById(R.id.toolbarr);


        imagesuser = root.findViewById(R.id.profileimg);
        nameuser = root.findViewById(R.id.username);
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Guide guide = dataSnapshot.getValue(Guide.class);

                if (guide != null){
                    nameuser.setText(guide.getName() + " " + guide.getFullname());
                }else {

                    DatabaseReference myReff = database.getReference("tourist").child(currentUsertoursit.getUid());

                    // Read from the database
                    myReff.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            Tourist tourist = dataSnapshot.getValue(Tourist.class);
                            nameuser.setText(tourist.getName());


                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });



                }

              /* if (guide.getImgGd().equals("default")){
                    imagesuser.setImageResource(R.drawable.bbb);

                }else{
                    Glide.with(MessagesFragment.this).load(guide.getImgGd()).into( imagesuser);
                }*/


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

        TabLayout tabLayout = root.findViewById(R.id.tab_layout);

        ViewPager viewpage = root.findViewById(R.id.viewpage);
        // viewpage.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPagerAdapter.addFragment(new chatFragment(), "chats");
        viewPagerAdapter.addFragment(new GuideFragmentChat(), "Guide");
        viewPagerAdapter.addFragment(new TouristFragmentChat(), "users");

        viewpage.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewpage);


        return root;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}