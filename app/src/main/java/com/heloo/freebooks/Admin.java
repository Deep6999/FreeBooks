    package com.heloo.freebooks;

    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;

    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.CheckBox;
    import android.widget.CompoundButton;
    import android.widget.EditText;
    import android.widget.ImageView;

    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.firestore.FirebaseFirestore;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.StorageReference;
    import com.google.firebase.storage.UploadTask;
    import com.squareup.picasso.Picasso;

    import java.util.HashMap;

    public class Admin extends AppCompatActivity {
    ImageView imageselector;
    CheckBox Adventure,Businessandeconomics,Romantic,Comedy,Scienceandtechnology,Slider,Programming,SelfHelp,AutoBiography,Fiction;
    EditText nameofbooksadmin,Description,Author;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    Button pdf,Upload;
    FirebaseFirestore firebaseFirestore;
    Uri filepath,bookimage,Final;
    StorageReference storageReference;
    boolean adventure,businessandtechnology,romantic,comedy,scienceandtechnology,slider,programming,selfhelp,autobiography,fiction;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin);
            getSupportActionBar().hide();
            Upload = findViewById(R.id.Upload);
            Description = findViewById(R.id.Description);
            Author = findViewById(R.id.Author);
            pdf = findViewById(R.id.pdf);
            nameofbooksadmin = findViewById(R.id.nameofbooksadmin);
            Fiction = findViewById(R.id.Fiction);
            Programming= findViewById(R.id.Programming);
            SelfHelp= findViewById(R.id.SelfHelp);
            Adventure = findViewById(R.id.Adventure);
            AutoBiography=findViewById(R.id.AutoBiography);
            Businessandeconomics = findViewById(R.id.Businessandeconomics);
            Slider = findViewById(R.id.Slider);
            firebaseAuth = FirebaseAuth.getInstance();
            user = firebaseAuth.getCurrentUser();
            Romantic = findViewById(R.id.Romantic);
            Comedy = findViewById(R.id.Comedy);
            Scienceandtechnology = findViewById(R.id.Scienceandtechnology);
            imageselector = findViewById(R.id.imageselector);

            Adventure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    adventure = isChecked;
                }
            });
            Fiction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    fiction = isChecked;
                }
            });
            AutoBiography.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    autobiography = isChecked;
                }
            });
            Businessandeconomics.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    businessandtechnology = isChecked;
                }
            });
            Romantic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    romantic = isChecked;
                }
            });
            SelfHelp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    selfhelp = isChecked;
                }
            });
            Comedy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    comedy = isChecked;
                }
            });
            Scienceandtechnology.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    scienceandtechnology = isChecked;
                }
            });
            Slider.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    slider = isChecked;
                }
            });
            Programming.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    programming = isChecked;
                }
            });
            imageselector.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent,100);
                }
            });
            pdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType("*/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent,101);
                }
            });
            Upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uploading();
                }
            });
     }

        private void Uploading() {
//            final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
//            progressDialog.setTitle("Uploading Image :");
//            progressDialog.setMessage("Please Wait");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
            storageReference = FirebaseStorage.getInstance().getReference();
            final StorageReference uploader = storageReference.child("pdf/"+"pdf"+System.currentTimeMillis());
            uploader.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Final = uri;
                            final StorageReference uploaderimage = storageReference.child("image/"+"img"+System.currentTimeMillis());
                            uploaderimage.putFile(bookimage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    uploaderimage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String nameofbook = nameofbooksadmin.getText().toString();
                                            HashMap<String,Object> map = new HashMap<>();
                                            map.put("imageofthebook",uri.toString());
                                            map.put("nameofthebooks",nameofbook);
                                            map.put("pdf",Final.toString());
                                            firebaseFirestore = FirebaseFirestore.getInstance();
                                            String child = "";
                                            if(adventure){
                                                child = "Adventure&Action";
                                            }
                                            if(slider){
                                                child = "Slider";
                                            }
                                            if(fiction){
                                                child = "Fiction";
                                            }
                                            if (businessandtechnology){
                                                child = "Business&Economics";
                                            }
                                            if (romantic){
                                                child = "Romantic";
                                            }
                                            if (comedy){
                                                child = "Comedy";
                                            }
                                            if (scienceandtechnology){
                                                child ="Science&Technology";
                                            }
                                            if (selfhelp){
                                                child ="SelfHelp";
                                            }
                                            if (programming){
                                                child ="Programming";
                                            }
                                            if (autobiography){
                                                child ="Biography";
                                            }
                                            // progressDialog.dismiss();
                                            model model= new model(nameofbook,uri.toString(),Final.toString(),nameofbook.toLowerCase().replaceAll("\\s+", ""),child,Author.getText().toString(),Description.getText().toString());
                                     //       modelmodel modelmodel = new modelmodel(Author.getText().toString(),Description.getText().toString());
                                            if (child!="Slider") {
                                                firebaseFirestore.collection("Books").document("Categories").collection("all").add(model);
                                            }
                                            firebaseFirestore.collection("Books").document("Categories").collection(child).add(model);

                                        }
                                    });
                                }
                            });

                        }
                    });
                }
            });
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 100 && resultCode == RESULT_OK){
                bookimage = data.getData();
                Picasso.get().load(bookimage).into(imageselector);
            }
            if (requestCode == 101 && resultCode == RESULT_OK){
                filepath = data.getData();
            }
        }
    }