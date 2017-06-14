package com.example.eslamwael74.fireappbase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class signup extends AppCompatActivity {

    EditText EditTextName , pass , email , pNum;
    Button signup;
    ProgressDialog progressDialog;
    FirebaseAuth.AuthStateListener mAuthListener;
    List<customer> customers;
    DatabaseReference databaseCustomers;
    FirebaseAuth mAuth;
    public static final String TAG = "";
    private String userID;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressDialog = new ProgressDialog(this);
        EditTextName = (EditText) findViewById(R.id.name);
        pass = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        pNum = (EditText) findViewById(R.id.pNum);
        signup = (Button) findViewById(R.id.btn_signup);

        //getting the reference of Customers node
        databaseCustomers = FirebaseDatabase.getInstance().getReference("customers");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        //userID = user.getUid();


        mAuthListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    startActivity(new Intent(signup.this,MainActivity.class));

                }
                else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }

        };



        customers = new ArrayList<>();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signUp();
                //addCustomer();

            }
        });
    }


    private void addCustomer(){

        String name = EditTextName.getText().toString().trim();
        String num = pNum.getText().toString().trim();

        if(!TextUtils.isEmpty(name)){

            String unID = databaseCustomers.push().getKey();

            customer customer = new customer(name,unID,num);
            databaseCustomers.child(userID).setValue(customer);

            Toast.makeText(this,"Customer Added",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"Faild Added",Toast.LENGTH_LONG).show();
        }
    }

    public void signUp(){

        String emaiil = email.getText().toString().trim();
        String password = pass.getText().toString().trim();

        if (TextUtils.isEmpty(emaiil)) {
            email.setError("please enter email");

        }

        if (TextUtils.isEmpty(password)) {
            pass.setError("please enter password");

        }

        if (password.length() < 6) {
            pass.setError("Password too short, enter minimum 6 characters!");

        }
        mAuth.createUserWithEmailAndPassword(emaiil,password)
                .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(signup.this, "signUp Done",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(signup.this, RegisterNewUser.class));
                        }

                        else{
                            Toast.makeText(signup.this, "Failed signUP" + task.getException(),Toast.LENGTH_SHORT).show();

                        }


                    }
                });


    }

    /*public void buget(View view){
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

    }*/

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
