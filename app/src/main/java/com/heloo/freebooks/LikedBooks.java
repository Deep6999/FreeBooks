package com.heloo.freebooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class LikedBooks extends AppCompatActivity {

RecyclerView recyclerView;
FirebaseAuth firebaseAuth;
FirebaseFirestore firebaseFirestore;
FirestoreRecyclerAdapter<model,mViewHolder> myf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_books);
        recyclerView = findViewById(R.id.recyclerView);
        getSupportActionBar().hide();
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        firebaseAuth = FirebaseAuth.getInstance();
        final Query query4 = firebaseFirestore.collection("Favorite").document(firebaseAuth.getCurrentUser().getUid()).collection( "ok").orderBy("page",Query.Direction.DESCENDING);

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
                Picasso.get().load(model.imageofthebook).resize(500,800).centerCrop().placeholder(R.drawable.loading).into(holder.bookimage);
                holder.bookname.setText(model.nameofthebook);
                holder.bookimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),SpecificBook.class);
                        intent.putExtra("imageofthebook",model.imageofthebook);
                        intent.putExtra("nameofthebook",model.nameofthebook);
                        intent.putExtra("pdf",model.uriofthepdf);
                        intent.putExtra("Author",model.Author);
                        intent.putExtra("Description",model.Description);
                        intent.putExtra("from",model.from);
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(myf);
        myf.startListening();

        }
}