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
import com.heloo.freebooks.MainActivity;
import com.heloo.freebooks.R;

public class LogIn extends AppCompatActivity {
Button login;
TextView gotoregister;
FirebaseAuth firebaseAuth;
EditText gmaillogin,passwordlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        login = findViewById(R.id.login);
        getSupportActionBar().hide();
        gmaillogin =findViewById(R.id.gmaillogin);
        gotoregister =findViewById(R.id.gotoregister);
        gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),Register.class));

            }
        });
        passwordlogin =findViewById(R.id.passwordlogin);
        firebaseAuth = FirebaseAuth.getInstance();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Sing In ...");
        progressDialog.setCancelable(false);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gmail = gmaillogin.getText().toString();
                String password = passwordlogin.getText().toString();
                if (gmail.isEmpty() || password.isEmpty()){
                    Toast.makeText(LogIn.this, "Fill All Field", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.show();
                    firebaseAuth.signInWithEmailAndPassword(gmail, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(LogIn.this, "Error !" + e, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }
}