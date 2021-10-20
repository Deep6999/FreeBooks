package com.heloo.freebooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class ParticularGenre extends AppCompatActivity {
TextView particularGenre;
RecyclerView particularGenrerec;
Intent intent;
FirebaseFirestore firebaseFirestore;
ImageView background;
ImageButton imageButton;
FirestoreRecyclerAdapter<model,myViewHolder> Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_genre);
        particularGenrerec = findViewById(R.id.particularGenrerec);
        particularGenre = findViewById(R.id.particularGenre);
        background = findViewById(R.id.background);
        imageButton = findViewById(R.id.imageButton);
        intent = getIntent();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//       getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final String from = intent.getStringExtra("from");
        particularGenre.setText(intent.getStringExtra("from").toUpperCase());
        particularGenrerec.setLayoutManager(new LinearLayoutManager(this));
        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Books").document("Categories").collection(intent.getStringExtra("from"));

        FirestoreRecyclerOptions<model> Option = new FirestoreRecyclerOptions.Builder<model>()
                .setQuery(query,model.class)
                .build();
        Adapter = new FirestoreRecyclerAdapter<model, myViewHolder>(Option) {
            @NonNull
            @Override
            public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow_particlegenre, parent, false);
                return new myViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull final model model) {
                holder.bookname.setText(model.nameofthebook);
                holder.author.setText("Author-: "+model.Author);
                Picasso.get().load(model.imageofthebook).resize(500,800).centerCrop().placeholder(R.drawable.loading).into(holder.bookimage);
                holder.bookimage.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(getApplicationContext(), ""+model.nameofthebook, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                holder.linearLayout2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),SpecificBook.class);
                        intent.putExtra("from",from);
                        intent.putExtra("nameofthebook",model.nameofthebook);
                        intent.putExtra("imageofthebook",model.imageofthebook);
                        intent.putExtra("pdf",model.uriofthepdf);
                        intent.putExtra("Author",model.Author);
                        intent.putExtra("Description",model.Description);
                        startActivity(intent);
                    }
                });
                holder.bookimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),SpecificBook.class);
                        intent.putExtra("from",from);
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
        particularGenrerec.setAdapter(Adapter);

    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        ImageView bookimage;
        TextView bookname,author;
        View view;
        LinearLayout linearLayout2;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            bookname = itemView.findViewById(R.id.bookname);
            view = itemView;
            linearLayout2 = itemView.findViewById(R.id.linearLayout2);
            bookimage = itemView.findViewById(R.id.bookimage);
            author = itemView.findViewById(R.id.author);
        }
    }
}