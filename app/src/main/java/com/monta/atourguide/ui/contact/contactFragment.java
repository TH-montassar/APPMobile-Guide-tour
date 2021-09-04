package com.monta.atourguide.ui.contact;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.monta.atourguide.R;
import com.monta.atourguide.databinding.FragmentContactBinding;


public class contactFragment extends Fragment {
    private ContactViewModel contactViewModel;
    private FragmentContactBinding binding;

     Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

/*
        View rootview=inflater.inflate(R.layout.fragment_contact, container , false);
        context =rootview.getContext();*/
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        binding = FragmentContactBinding.inflate(inflater, container, false);
        View rootview = binding.getRoot();

        Button btncall=(Button) rootview.findViewById(R.id.buttoncall);
        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){

                Toast.makeText(getActivity(), "calle now", Toast.LENGTH_SHORT).show();
                String phone="+21626236286";
                Intent intent =new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phone));
                startActivity(intent);}
               else {
                   Toast.makeText(getActivity(), "sorry you can't call any one so go GRANTED PERMISSION ", Toast.LENGTH_SHORT).show();
                   final String[] PERMISSION_D ={Manifest.permission.CALL_PHONE};
                   ActivityCompat.requestPermissions(getActivity(),PERMISSION_D,9);

               }
            }
        });

                Button   mailcontact=(Button) rootview.findViewById(R.id. mail_contact);
                  mailcontact.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){

                              Toast.makeText(getActivity(), "text now", Toast.LENGTH_SHORT).show();
                              String mail="admingiude@gmail.com";
                              Intent intent =new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+mail));
                              startActivity(intent);}
                          else {
                              Toast.makeText(getActivity(), "sorry you can't text  any one so go GRANTED PERMISSION ", Toast.LENGTH_SHORT).show();
                              final String[] PERMISSION_D ={Manifest.permission.SEND_SMS};
                              ActivityCompat.requestPermissions(getActivity(),PERMISSION_D,9);

                          }

                      }
                  });










        final TextView textView = binding.textContact;
        contactViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return rootview;
    }

   @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}