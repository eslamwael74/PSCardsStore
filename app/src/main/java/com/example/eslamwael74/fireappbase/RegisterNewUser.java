package com.example.eslamwael74.fireappbase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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

import java.util.List;


public class RegisterNewUser extends AppCompatActivity {
    EditText EditTextName , pNum;
    Button btnSubmit;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference databaseCustomers;
    FirebaseAuth mAuth;
    public static final String TAG = "";
    private String userID;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);

        EditTextName = (EditText) findViewById(R.id.name);
        pNum = (EditText) findViewById(R.id.pNum);
        btnSubmit = (Button) findViewById(R.id.btn_submit);

        //getting the reference of Customers node
        databaseCustomers = FirebaseDatabase.getInstance().getReference("customers");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                }
                else {
                    // User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }

        };

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCustomer();
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
            startActivity(new Intent(RegisterNewUser.this,MainActivity.class));
        }
        else{
            Toast.makeText(this,"Faild Added",Toast.LENGTH_LONG).show();
        }
    }

}
