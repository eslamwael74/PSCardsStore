package com.example.eslamwael74.fireappbase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signup extends AppCompatActivity {

    EditText name , pass , email , pNum;
    Button button;
    ProgressDialog progressDialog;
    FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if (firebaseUser != null) {
                    //Log.d("Siginin", firebaseUser.getUid());
                    startActivity(new Intent(signup.this,MainActivity.class));

                }
            }

        };

        progressDialog = new ProgressDialog(this);
        name = (EditText) findViewById(R.id.name);
        pass = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        pNum = (EditText) findViewById(R.id.pNum);
    }

    public void buget(View view){
        // Write a message to the database
        //progressDialog.setMessage("Wait");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("Users").child(name.getText().toString()).child("userName").setValue(name.getText().toString());
        myRef.child("Users").child(name.getText().toString()).child("pass").setValue(pass.getText().toString());
        myRef.child("Users").child(name.getText().toString()).child("email").setValue(email.getText().toString());
        myRef.child("Users").child(name.getText().toString()).child("pNum").setValue(pNum.getText().toString());
        progressDialog.setMessage("Waiting for signup");
        progressDialog.show();
        startActivity(new Intent(signup.this,MainActivity.class));


        //Intent intent = new Intent(signup.this,welcSignup.class);
        //startActivity(intent);

    }

}
