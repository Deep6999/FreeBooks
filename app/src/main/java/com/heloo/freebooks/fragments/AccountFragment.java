package com.heloo.freebooks.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.heloo.freebooks.LikedBooks;
import com.heloo.freebooks.MainActivity;
import com.heloo.freebooks.R;
import com.heloo.freebooks.auth.LogIn;
import com.heloo.freebooks.auth.Register;

import java.util.HashMap;

public class AccountFragment extends Fragment {
    Button logout,updateprofile;
    FirebaseAuth firebaseAuth;
    ConstraintLayout oki;
    TextView gmailoftheuser,seefavorite,nameoftheuser;
    FirebaseFirestore firebaseFirestore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);
         logout = v.findViewById(R.id.logout);
        nameoftheuser = v.findViewById(R.id.nameoftheuser);
        updateprofile = v.findViewById(R.id.updateprofile);
        oki = v.findViewById(R.id.oki);
        gmailoftheuser = v.findViewById(R.id.gmailoftheuser);
        seefavorite = v.findViewById(R.id.seefavorite);
        firebaseFirestore = FirebaseFirestore.getInstance();
         firebaseAuth = FirebaseAuth.getInstance();
         logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 firebaseAuth.signOut();
                 startActivity(new Intent(getContext(), LogIn.class));
                 getActivity().finish();
             }
         });
        oki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LikedBooks.class));
            }
        });
         nameoftheuser.setText("Hello "+firebaseAuth.getCurrentUser().getDisplayName());
        gmailoftheuser.setText(firebaseAuth.getCurrentUser().getEmail());
        updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap<String,Object> map = new HashMap<>();
                map.put("Name",firebaseAuth.getCurrentUser().getDisplayName());

                FirebaseUser us = firebaseAuth.getCurrentUser();
                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(nameoftheuser.getText().toString()).build();
                us.updateProfile(userProfileChangeRequest);
                firebaseFirestore = FirebaseFirestore.getInstance();

                DocumentReference docref =firebaseFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid());
                docref.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Successful ", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), ""+e, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    return v;
    }
}