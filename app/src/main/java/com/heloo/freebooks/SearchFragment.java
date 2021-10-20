package com.heloo.freebooks;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.heloo.freebooks.R;
import com.heloo.freebooks.SpecificBook;
import com.heloo.freebooks.mViewHolder;
import com.heloo.freebooks.model;
import com.squareup.picasso.Picasso;

public class SearchFragment extends Fragment {
RecyclerView searchrac;
    FirestoreRecyclerAdapter<model, mViewHolder> Adapter;
FirebaseFirestore firebaseFirestore;
ImageView somethigs;
EditText searchtext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search, container, false);
        searchtext = v.findViewById(R.id.searchtext);
        searchrac = v.findViewById(R.id.searchrac);
        somethigs = v.findViewById(R.id.somethigs);
        firebaseFirestore = FirebaseFirestore.getInstance();

        searchtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    somethigs.setVisibility(View.VISIBLE);
                    searchrac.setVisibility(View.INVISIBLE);
                }
               else  {
                    somethigs.setVisibility(View.INVISIBLE);
                    searchrac.setVisibility(View.VISIBLE);
                    String search = s.toString().toLowerCase().replaceAll("\\s+", "");
                    Query query = firebaseFirestore.collection("Books").document("Categories").collection("all").orderBy("search").startAt(search).endAt(search+"\uf8ff").limit(10);
                    FirestoreRecyclerOptions<model> Option = new FirestoreRecyclerOptions.Builder<model>()
                            .setQuery(query, model.class)
                            .build();
                    Adapter = new FirestoreRecyclerAdapter<model, mViewHolder>(Option) {
                        @NonNull
                        @Override
                        public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_horizontal, parent, false);
                            return new mViewHolder(view);
                        }

                        @Override
                        protected void onBindViewHolder(@NonNull mViewHolder holder, int position, @NonNull final model model) {
                            holder.bookname.setText(model.getNameofthebook());
                            Picasso.get().load(model.imageofthebook).resize(500,800).centerCrop().placeholder(R.drawable.loading).into(holder.bookimage);
                            holder.bookimage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(v.getContext(), SpecificBook.class);
                                    intent.putExtra("from", model.from);
                                    intent.putExtra("nameofthebook", model.nameofthebook);
                                    intent.putExtra("imageofthebook", model.imageofthebook);
                                    intent.putExtra("pdf", model.uriofthepdf);
                                    intent.putExtra("Author",model.Author);
                                    intent.putExtra("Description",model.Description);
                                    startActivity(intent);
                                }
                            });
                        }
                    };
                    searchrac.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    Adapter.startListening();
                    searchrac.setAdapter(Adapter);
                }
            }
        });
        return v;
    }
}