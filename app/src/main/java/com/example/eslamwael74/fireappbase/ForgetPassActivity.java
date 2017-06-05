package com.example.eslamwael74.fireappbase;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassActivity extends AppCompatActivity {
    private EditText email;
    private Button reset , back;
    private FirebaseAuth mAuth;
    private TextInputLayout emailWrapper;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        email = (EditText) findViewById(R.id.email);
        reset = (Button) findViewById(R.id.reset);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        back = (Button) findViewById(R.id.back);
        emailWrapper = (TextInputLayout) findViewById(R.id.email_wrapper);
        emailWrapper.setHint("Email");
        mAuth = FirebaseAuth.getInstance();
        progressBar.setVisibility(View.GONE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v != null) {
                    resetPassword();
                }

            }
        });

    }

    private void resetPassword() {

        String tEmail = email.getText().toString().trim();

        if (TextUtils.isEmpty(tEmail)) {
            email.setError("please enter email");
            //Toast.makeText(getApplicationContext(), "Enter Your Email To Reset Password!!", Toast.LENGTH_SHORT).show();
            return;
        }
        //progressDialog.setMessage("Please Wait!!");
        //progressDialog.show();

        progressBar.setVisibility(View.VISIBLE);

        mAuth.sendPasswordResetEmail(tEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ForgetPassActivity.this, "DONE!! please check you email", Toast.LENGTH_SHORT).show();
                            //return;
                        } else {
                            Toast.makeText(ForgetPassActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }


}
