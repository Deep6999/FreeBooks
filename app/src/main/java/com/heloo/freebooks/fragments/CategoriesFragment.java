package com.heloo.freebooks.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.heloo.freebooks.ParticularGenre;
import com.heloo.freebooks.R;


public class CategoriesFragment extends Fragment {
 ImageView adventureandaction,romantic,comedy,Scienceandtechnology,Businessandtechnology,Programming,selfhelp,AutoBiography,Fiction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View v =  inflater.inflate(R.layout.fragment_categories, container, false);
        adventureandaction = v.findViewById(R.id.adventureandaction);
        romantic = v.findViewById(R.id.romantic);
        comedy = v.findViewById(R.id.comedy);
        Scienceandtechnology = v.findViewById(R.id.Scienceandtechnology);
        Businessandtechnology = v.findViewById(R.id.Businessandtechnology);
        Fiction = v.findViewById(R.id.Fiction);
        Programming = v.findViewById(R.id.Programming);
        selfhelp = v.findViewById(R.id.selfhelp);
        AutoBiography = v.findViewById(R.id.AutoBiography);

        adventureandaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ParticularGenre.class);
                intent.putExtra("from","Adventure&Action");
                startActivity(intent);
            }
        });
        Fiction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ParticularGenre.class);
                intent.putExtra("from","Fiction");
                startActivity(intent);
            }
        });
        romantic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ParticularGenre.class);
                intent.putExtra("from","Romantic");
                startActivity(intent);
            }
        });
        comedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ParticularGenre.class);
                intent.putExtra("from","Comedy");
                startActivity(intent);
            }
        });
        Scienceandtechnology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ParticularGenre.class);
                intent.putExtra("from","Science&Technology");
                startActivity(intent);
            }
        });
        Businessandtechnology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ParticularGenre.class);
                intent.putExtra("from","Business&Economics");
                startActivity(intent);
            }
        });
        Programming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ParticularGenre.class);
                intent.putExtra("from","Programming");
                startActivity(intent);
            }
        });
        selfhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ParticularGenre.class);
                intent.putExtra("from","SelfHelp");
                startActivity(intent);
            }
        });
        AutoBiography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ParticularGenre.class);
                intent.putExtra("from","Biography");
                startActivity(intent);
            }
        });
       return v;
    }
}