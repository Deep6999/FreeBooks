package com.heloo.freebooks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.util.HashMap;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static com.heloo.freebooks.R.id.bookname;
import static com.heloo.freebooks.R.id.imageofthebook;
import static com.heloo.freebooks.R.id.name;

public class SpecificBook extends AppCompatActivity {
Intent intent;
TextView particularBook,titleofthebookspecific,genreinspecific,might,Introduction,author;
Button download,readnow;
FirebaseFirestore firebaseFirestore;
ImageView imageofthebook;
FirebaseAuth firebaseAuth;
FirestoreRecyclerAdapter<model,mViewHolder> Adapter;
RecyclerView mayberea;
ImageButton like;
String name,Author,nospace;
ImageButton back;
boolean oklike=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_book);
        getSupportActionBar().hide();
        like = findViewById(R.id.like);
        intent = getIntent();
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final String image = intent.getStringExtra("imageofthebook");
        name = intent.getStringExtra("nameofthebook");
        final String pdf = intent.getStringExtra("pdf");
        final String from = intent.getStringExtra("from");
        mayberea = findViewById(R.id.mayberea);
        imageofthebook = findViewById(R.id.imageofthebook);
        author = findViewById(R.id.author);
        download = findViewById(R.id.download);
         Author = intent.getStringExtra("Author");
        might = findViewById(R.id.might);
        Introduction = findViewById(R.id.Introduction);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        readnow = findViewById(R.id.readnow);

        particularBook = findViewById(R.id.particularBook);
        titleofthebookspecific = findViewById(R.id.titleofthebookspecific);
        genreinspecific = findViewById(R.id.genreinspecific);
        Picasso.get().load(image).resize(2000, 3000).placeholder(R.drawable.loading).into(imageofthebook);
        genreinspecific.setText("Genre-: " + from.toUpperCase());
        titleofthebookspecific.setText(name);
        nospace = name.replaceAll("\\s+", "");
        if (Author == null){}
        else {
            author.setText("Author-: "+Author);
        }
        final String Descr = intent.getStringExtra("Description");
        if (intent.getStringExtra("Description") == null){
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        }else {
        Introduction.setText(intent.getStringExtra("Description"));
        }
        particularBook.setText(name.toUpperCase());
        mayberea.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        Query query = firebaseFirestore.collection("Books").document("Categories").collection(from).whereNotEqualTo("nameofthebook",name).orderBy("nameofthebook",Query.Direction.DESCENDING).limit(10);

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
                        Toast.makeText(getApplicationContext(), ""+model.nameofthebook, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                holder.bookimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),SpecificBook.class);
                        intent.putExtra("from",from);
                        intent.putExtra("nameofthebook",model.nameofthebook);
                        intent.putExtra("imageofthebook",model.imageofthebook);
                        intent.putExtra("Author",model.Author);
                        intent.putExtra("Description",model.Description);
                        intent.putExtra("pdf",model.uriofthepdf);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        };
        Adapter.startListening();
        mayberea.setAdapter(Adapter);
        readnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),viewPdf.class);
                intent.putExtra("pdf",pdf);
                intent.putExtra("bookname",name);
                intent.putExtra("imageofthebook",image);
                intent.putExtra("from",from);
                intent.putExtra("Author",Author);
                intent.putExtra("Description",Descr);
                startActivity(intent);
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SpecificBook.this)
                        .setTitle("Sure")
                        .setMessage("Are you sure you want to download?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                downloading(pdf);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();


            }
        });

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    oklike = true;
                        firebaseFirestore.collection("Favorite").document(firebaseAuth.getCurrentUser().getUid()).collection("ok").document(nospace).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (oklike) {
                                    if (value.exists()) {
                                        oklike = false;
                                        firebaseFirestore.collection("Favorite").document(firebaseAuth.getCurrentUser().getUid()).collection("ok").document(nospace).delete();
                                        like.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                                    } else {
                                        oklike = false;
                                        model model = new model(name, image, pdf, from, Author, Descr);
                                       firebaseFirestore.collection("Favorite").document(firebaseAuth.getCurrentUser().getUid()).collection("ok").document(nospace).set(model);
                                        like.setImageResource(R.drawable.ic_baseline_favorite_24);
                                    }
                                    oklike = false;
                                }
                            }
                        });

//                    likedatabase = FirebaseDatabase.getInstance().getReference("Favorite").child(firebaseAuth.getCurrentUser().getUid());
//                    oklike = true;
//                    likedatabase.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if (oklike){
//                                if (snapshot.child(nospace).exists()){
//                                    oklike = false;
//                               //     firebaseFirestore.collection("Favorite").document(firebaseAuth.getCurrentUser().getUid()).collection("ok").document(nospace).delete();
//                                    likedatabase.child(nospace).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            Toast.makeText(SpecificBook.this, "Removed", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                    like.setImageResource(R.drawable.ic_baseline_favorite_border_24);
//                                }
//                                else {
//                                    oklike = false;
//                                    model model= new model(name,image,pdf,from,Author,Descr);
//                                   // firebaseFirestore.collection("Favorite").document(firebaseAuth.getCurrentUser().getUid()).collection("ok").document(nospace).set(model);
//                                    like.setImageResource(R.drawable.ic_baseline_favorite_24);
//                                    likedatabase.child(nospace).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//
//                                        }
//                                    });
//
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });

                }
            });
    }

    private void downloading(String pdf) {
        DownloadManager downloadManager = (DownloadManager) getApplicationContext().getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pdf));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(getApplicationContext(), DIRECTORY_DOWNLOADS, pdf + "pdf");
        downloadManager.enqueue(request);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseFirestore.collection("Favorite").document(firebaseAuth.getCurrentUser().getUid()).collection("ok").document(nospace).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()){
                    like.setImageResource(R.drawable.ic_baseline_favorite_24);
                }else {
                    like.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                }
            }
        });
//
//        likedatabase = FirebaseDatabase.getInstance().getReference("Favorite").child(firebaseAuth.getCurrentUser().getUid());
//        likedatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.child(nospace).exists()){
//                    like.setImageResource(R.drawable.ic_baseline_favorite_24);
//                }else {
//                    like.setImageResource(R.drawable.ic_baseline_favorite_border_24);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }
}