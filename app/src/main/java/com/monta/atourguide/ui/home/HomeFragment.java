package com.monta.atourguide.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.monta.atourguide.Adapters.GuideAdapter;
import com.monta.atourguide.Models.Guide;
import com.monta.atourguide.R;
import com.monta.atourguide.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    EditText editText;


    RecyclerView recyclerViewGuide;
    GuideAdapter guideAdapter;
    List<Guide> GuideListe;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);


        binding=FragmentHomeBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        recyclerViewGuide = root.findViewById(R.id.rc_item_guide_gd);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());

        recyclerViewGuide.setLayoutManager(linearLayoutManager);

        recyclerViewGuide.setHasFixedSize(true);

        GuideListe = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            GuideListe.add(new Guide("amira","mansouri","amiramansouri@gmail.com","tunise","Il peut être modifié par les constructeurs qui y ajoutent leurs surcouches, apportant ainsi des fonctionnalités supplémentaires mais au détriment du délai d'obtention des nouvelles mises à jour qui est parfois important","Il peut être modifié par les constructeurs qui y ajoutent leurs surcouches, apportant ainsi des fonctionnalités supplémentaires mais au détriment du délai d'obtention des nouvelles mises à jour qui est parfois importantrgl bgrehbhjb kvheb vheb vkguvybhveblvihbveh re vrkhvh rb evr ek'ihfvvvvvhf'","10:45","femme",55,55458961, R.drawable.profileuser,20f));

            GuideListe.add(new Guide("montassar","themri","montassarthemri@gmail.com","bazina","Il peut être modifié par les constructeurs qui y ajoutent leurs surcouches, apportant ainsi des fonctionnalités supplémentaires mais au détriment du délai d'obtention des nouvelles mises à jour qui est parfois important","Il peut être modifié par les constructeurs qui y ajoutent leurs surcouches, apportant ainsi des fonctionnalités supplémentaires mais au détriment du délai d'obtention des nouvelles mises à jour qui est parfois important rheubvbrvb rebvbehbvkjbevbkjk ebkbvrbjebvjrbvevjbvjebjbhkvrbhjevbejbvebhk","10:33","homme",15,55428961,R.drawable.userphoto,15f));
        }

        guideAdapter =new GuideAdapter(getContext(),GuideListe);
        recyclerViewGuide.setAdapter(guideAdapter);




       /* Button buttoncall=(Button) root.findViewById(R.id.call);
        buttoncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText= (EditText) root.findViewById(R.id.editTextTextPersonName);
                  String inputee=editText.getText().toString();
                if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){

                    Toast.makeText(getActivity(), "calle now", Toast.LENGTH_SHORT).show();
                   // String phone="+21626236286";
                    Intent intent =new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+inputee));
                    startActivity(intent);}
                else {
                    Toast.makeText(getActivity(), "sorry you can't call any one so go GRANTED PERMISSION ", Toast.LENGTH_SHORT).show();
                    final String[] PERMISSION_D ={Manifest.permission.CALL_PHONE};
                    ActivityCompat.requestPermissions(getActivity(),PERMISSION_D,9);

                }
            }
        });*/

        /*final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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