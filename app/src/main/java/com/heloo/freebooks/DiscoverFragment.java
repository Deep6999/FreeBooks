package com.heloo.freebooks;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscoverFragment extends Fragment {
RecyclerView recadventure,recaction,mylist,continuereading;
FirebaseFirestore firebaseFirestore;
    ProgressBar progressBar;
    FirestoreRecyclerAdapter<modelmodel,mViewHolder> Adapter3;
    FirestoreRecyclerAdapter<model,mViewHolder> Adapter ,Adapter2,Adapter4,myf;
//    ImageSlider image_slider;
    TextView browseAdventure,browseAction,reading,readinglist,browsemylist;
    FirebaseAuth firebaseAuth;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_discover, container, false);
        recaction = v.findViewById(R.id.recaction);
//        image_slider = v.findViewById(R.id.image_slider);
        mylist = v.findViewById(R.id.mylist);
        reading = v.findViewById(R.id.reading);
//        final List<SlideModel> slideModels = new ArrayList<>();
        readinglist = v.findViewById(R.id.readinglist);
        browsemylist = v.findViewById(R.id.browsemylist);
        firebaseFirestore = FirebaseFirestore.getInstance();
        browseAction = v.findViewById(R.id.browseAction);
        browseAdventure = v.findViewById(R.id.browseAdventure);
        firebaseAuth = FirebaseAuth.getInstance();
        continuereading = v.findViewById(R.id.continuereading);


        browseAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ParticularGenre.class);
                intent.putExtra("from","Adventure&Action");
                startActivity(intent);
            }
        });
        ///Comedy Browse/////
        browseAdventure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ParticularGenre.class);
                intent.putExtra("from","Comedy");
                startActivity(intent);
            }
        });
        browsemylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),LikedBooks.class));
            }
        });


        /////Continue Reading//////

        continuereading.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        final Query query3 = firebaseFirestore.collection("Current").document(firebaseAuth.getCurrentUser().getUid()).collection( "pages").orderBy("page",Query.Direction.DESCENDING);

                    FirestoreRecyclerOptions<model> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<model>()
                            .setQuery(query3, model.class)
                            .build();
        Adapter4 = new FirestoreRecyclerAdapter<model, mViewHolder>(firestoreRecyclerOptions) {
                        @NonNull
                        @Override
                        public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_horizontal, parent, false);
                            return new mViewHolder(view);
                        }

                        @Override
                        protected void onBindViewHolder(@NonNull mViewHolder holder, int position, @NonNull final model model) {
                            holder.bookname.setText(model.nameofthebook);
                            if (!model.nameofthebook.isEmpty()){
                                reading.setVisibility(View.VISIBLE);
                            }
                            Picasso.get().load(model.imageofthebook).resize(500, 800).centerCrop().placeholder(R.drawable.loading).into(holder.bookimage);
                            holder.bookimage.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    Toast.makeText(getContext(), "" + model.nameofthebook, Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            });
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

                    Adapter4.startListening();
                    continuereading.setAdapter(Adapter4);


//////WishList//////////

        mylist.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        final Query query4 = firebaseFirestore.collection("Favorite").document(firebaseAuth.getCurrentUser().getUid()).collection( "ok").orderBy("page",Query.Direction.DESCENDING).limit(5);

        FirestoreRecyclerOptions<model> RecyclerOptions = new FirestoreRecyclerOptions.Builder<model>()
                .setQuery(query4, model.class)
                .build();
        myf = new FirestoreRecyclerAdapter<model, mViewHolder>(RecyclerOptions) {
            @NonNull
            @Override
            public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_horizontal, parent, false);
                return new mViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull mViewHolder holder, int position, @NonNull final model model) {
                holder.bookname.setText(model.nameofthebook);
                if (!model.nameofthebook.isEmpty()){
                    readinglist.setVisibility(View.VISIBLE);
                    browsemylist.setVisibility(View.VISIBLE);
                }
                Picasso.get().load(model.imageofthebook).resize(500, 800).centerCrop().placeholder(R.drawable.loading).into(holder.bookimage);
                holder.bookimage.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(getContext(), "" + model.nameofthebook, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
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

        myf.startListening();
        mylist.setAdapter(myf);


     /////Slider/////

//        firebaseFirestore.collection("Books").document("Categories").collection("Slider").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()){
//                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
//                        slideModels.add(new SlideModel(documentSnapshot.get("imageofthebook").toString(),documentSnapshot.get("nameofthebook").toString(), ScaleTypes.FIT));
//                        image_slider.setImageList(slideModels);
//
//                        final List<model> models = new ArrayList<>();
//                        models.add(new model(documentSnapshot.get("nameofthebook").toString(),documentSnapshot.get("imageofthebook").toString(),documentSnapshot.get("uriofthepdf").toString()));
//
//                        image_slider.animate().setStartDelay(2000);
//                        image_slider.setItemClickListener(new ItemClickListener() {
//                            @Override
//                            public void onItemSelected(int i) {
//                                Toast.makeText(getContext(), slideModels.get(i).getTitle(), Toast.LENGTH_SHORT).show();
////                                Intent intent = new Intent(getContext(),ParticularGenre.class);
////                                intent.putExtra("from",slideModels.get(i).getTitle());
////                                startActivity(intent);
//                            }
//                        });
//                    }
//                }
//            }
//        });

        recadventure = v.findViewById(R.id.recadventure);
        recadventure.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));

////Comedy/////
        Query query = firebaseFirestore.collection("Books").document("Categories").collection( "Comedy").limit(6);

        FirestoreRecyclerOptions<model> Option = new FirestoreRecyclerOptions.Builder<model>()
                .setQuery(query,model.class)
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
            holder.bookname.setText(model.nameofthebook);
            Picasso.get().load(model.imageofthebook).resize(500,800).centerCrop().placeholder(R.drawable.loading).into(holder.bookimage);
                holder.bookimage.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(getContext(), ""+model.nameofthebook, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                holder.bookimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),SpecificBook.class);
                        intent.putExtra("from","Comedy");
                        intent.putExtra("nameofthebook",model.nameofthebook);
                        intent.putExtra("imageofthebook",model.imageofthebook);
                        intent.putExtra("pdf",model.uriofthepdf);
                        intent.putExtra("Author",model.Author);
                        intent.putExtra("Description",model.Description);


                        startActivity(intent);
                    }
                });
            }
        };
        Adapter.startListening();
        recadventure.setAdapter(Adapter);

        //////Adventure And Action////

        recaction.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));

        Query query2 = firebaseFirestore.collection("Books").document("Categories").collection( "Adventure&Action").limit(6).orderBy("nameofthebook",Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<model> Option2 = new FirestoreRecyclerOptions.Builder<model>()
                .setQuery(query2,model.class)
                .build();
        Adapter2 = new FirestoreRecyclerAdapter<model, mViewHolder>(Option2) {
            @NonNull
            @Override
            public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_horizontal, parent, false);
                return new mViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull mViewHolder holder, int position, @NonNull final model model) {
                holder.bookname.setText(model.nameofthebook);
                Picasso.get().load(model.imageofthebook).resize(500,800).centerCrop().placeholder(R.drawable.loading).into(holder.bookimage);
                holder.bookimage.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(getContext(), ""+model.nameofthebook, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                holder.bookimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),SpecificBook.class);
                        intent.putExtra("from","Adventure&Action");
                        intent.putExtra("nameofthebook",model.nameofthebook);
                        intent.putExtra("imageofthebook",model.imageofthebook);
                        intent.putExtra("pdf",model.uriofthepdf);
                        intent.putExtra("Author",model.Author);
                        intent.putExtra("Description",model.Description);
                        startActivity(intent);
                    }
                });
            }
        };
        Adapter2.startListening();
        recaction.setAdapter(Adapter2);

        return  v;
    }

    @Override
    public void onStop() {
        super.onStop();
        Adapter.stopListening();
        Adapter2.stopListening();
        if (Adapter3 != null) {
            Adapter3.stopListening();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Adapter.startListening();
        Adapter2.startListening();
        if (Adapter3 !=null){
        Adapter3.startListening();
       }
    }
}