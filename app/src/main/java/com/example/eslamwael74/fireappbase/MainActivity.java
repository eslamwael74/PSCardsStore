package com.example.eslamwael74.fireappbase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity  {

    private EditText email,newAEmail,newPass;
    private Button btnChangeEmail , btnChangePass , btnSendReset
            , changeEmail,changePass,resetPass,removeUser,signOut;
    private TextView tEmail;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    login m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user == null) {
                    // user auth state is change
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, login.class));
                    finish();
                }
            }
        };

        tEmail = (TextView) findViewById(R.id.Temail);
        tEmail.setText("Your Email : " + mUser.getEmail());

        email = (EditText) findViewById(R.id.pass_reset_email);
        newAEmail = (EditText) findViewById(R.id.new_email);
        newPass = (EditText) findViewById(R.id.new_password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnChangeEmail = (Button) findViewById(R.id.btn_change_email);
        btnChangePass = (Button) findViewById(R.id.btn_change_pass);
        btnSendReset = (Button) findViewById(R.id.btn_send_pass);
        changeEmail = (Button) findViewById(R.id.change_email_button);
        changePass = (Button) findViewById(R.id.change_password_button);
        resetPass = (Button) findViewById(R.id.pass_reset_button);
        removeUser = (Button) findViewById(R.id.remove_user_button);
        signOut = (Button) findViewById(R.id.sign_out);

        email.setVisibility(View.GONE);
        newAEmail.setVisibility(View.GONE);
        newPass.setVisibility(View.GONE);
        btnChangeEmail.setVisibility(View.GONE);
        btnChangePass.setVisibility(View.GONE);
        btnSendReset.setVisibility(View.GONE);


        //if (progressBar != null){
            progressBar.setVisibility(View.GONE);
        //}



        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setVisibility(View.GONE);
                newAEmail.setVisibility(View.VISIBLE);
                newPass.setVisibility(View.GONE);
                btnChangeEmail.setVisibility(View.VISIBLE);
                btnChangePass.setVisibility(View.GONE);
                btnSendReset.setVisibility(View.GONE);

            }
                      });
        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { updateEmail();}
        });



        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email.setVisibility(View.GONE);
                newAEmail.setVisibility(View.GONE);
                newPass.setVisibility(View.VISIBLE);
                btnChangeEmail.setVisibility(View.GONE);
                btnChangePass.setVisibility(View.VISIBLE);
                btnSendReset.setVisibility(View.GONE);

            }
        });
       btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });



        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setVisibility(View.VISIBLE);
                newAEmail.setVisibility(View.GONE);
                newPass.setVisibility(View.GONE);
                btnChangeEmail.setVisibility(View.GONE);
                btnChangePass.setVisibility(View.GONE);
                btnSendReset.setVisibility(View.VISIBLE);

            }
        });
        btnSendReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { resetPassword();
            }
        });



        removeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeUser();
            }
        });



        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOUT();
            }
        });


    }


    public void updateEmail(){
        String newEmail = newAEmail.getText().toString().trim();


                progressBar.setVisibility(View.VISIBLE);
                if(mUser !=null && !newEmail.equals("")){

                    mUser.updateEmail(newEmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(MainActivity.this ,
                                                "Email Updated!! PLEASE SIGNIN NEW EMAIL EMAIL",Toast.LENGTH_SHORT).show();
                                        signOUT();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else {
                                        Toast.makeText(MainActivity.this ,
                                                "FAILED UPDATE EMAIL.please try again",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                }
                else if (newEmail.equals("")){
                    newAEmail.setError("Oops!! Enter Your Email");
                    progressBar.setVisibility(View.GONE);
                }
            }

    public void updatePassword(){

        final String newPassword = newPass.getText().toString().trim();

        progressBar.setVisibility(View.GONE);
        if(mUser != null && !newPassword.equals("")){
            if (newPassword.length() < 6){
                newPass.setError("Password Short!! please enter minimum 6 Characters");
                progressBar.setVisibility(View.GONE);
            }
            else{
                mUser.updatePassword(newPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(MainActivity.this ,
                                            "Password Updated!! PLEASE SIGNIN With NEW PASWORD",Toast.LENGTH_SHORT).show();
                                    signOUT();
                                    progressBar.setVisibility(View.GONE);
                                }
                                else {
                                    Toast.makeText(MainActivity.this ,
                                            "FAILED UPDATE PASSWORD.please try again!!",Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }

                            }
                        });
            }
        }



    }

    public void resetPassword(){

        final String resetEmailPassword = email.getText().toString().trim();
        mAuth = FirebaseAuth.getInstance();

        progressBar.setVisibility(View.VISIBLE);
        if(!resetEmailPassword.equals("")){
            mAuth.sendPasswordResetEmail(resetEmailPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this ,
                                        "Password Sent!! PLEASE Check your Email",Toast.LENGTH_SHORT).show();
                                signOUT();
                                progressBar.setVisibility(View.GONE);
                            }
                            else {
                                Toast.makeText(MainActivity.this ,
                                        "FAILED send reset link.please try again!!",Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });
        }
        else{
            email.setError("Enter Email");
            progressBar.setVisibility(View.GONE);
        }

    }

    public void removeUser(){

        progressBar.setVisibility(View.VISIBLE);
        if (mUser !=null){
            mUser.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "DONE!! profile DELETED", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, signup.class));
                                finish();
                                progressBar.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(MainActivity.this, "Failed to delete Account", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });
        }

    }

    public void signOUT(){

        mAuth.signOut();
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);

    }
    @Override
    public void onStop(){
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
