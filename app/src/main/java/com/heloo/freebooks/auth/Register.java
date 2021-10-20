package com.heloo.freebooks.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.heloo.freebooks.MainActivity;
import com.heloo.freebooks.R;

import java.util.HashMap;

public class Register extends AppCompatActivity {
        EditText name,gmail,password;
        TextView gotologin;
        FirebaseAuth firebaseAuth;
        Button register;
        FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        name = findViewById(R.id.name);
        gotologin = findViewById(R.id.gotologin);
        firebaseAuth = FirebaseAuth.getInstance();
        gmail = findViewById(R.id.gmail);
        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LogIn.class));
                finish();
            }
        });
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Signing Up :");
        progressDialog.setCancelable(false);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Gmail = gmail.getText().toString();
                final String Name = name.getText().toString();
                final String Password = password.getText().toString();
                if (password.length()<6){
                    password.setError("Password must be more than 6");
                }
                if (Gmail.isEmpty() || Name.isEmpty() || Password.isEmpty() || password.length()<6){
                    Toast.makeText(Register.this, "Fill All The Required Info", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(Gmail,Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            final HashMap<String,Object> map = new HashMap<>();
                            map.put("Name",Name);
                            map.put("gmail",Gmail);
                            map.put("password",Password);
                            final FirebaseAuth user = FirebaseAuth.getInstance();

                          FirebaseUser us = firebaseAuth.getCurrentUser();
                            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(Name).build();
                            us.updateProfile(userProfileChangeRequest);
                            firestore = FirebaseFirestore.getInstance();
                            DocumentReference docref =firestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid());
                            docref.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressDialog.dismiss();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Register.this, ""+e, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Register.this, "Error " +e, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
}