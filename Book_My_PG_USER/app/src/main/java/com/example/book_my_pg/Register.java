package com.example.book_my_pg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private Button register;
    private EditText fname, email, pass1, pass2;
    private ProgressBar progressBar;
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myAuth = FirebaseAuth.getInstance();

        register = (Button) findViewById(R.id.button2);
        register.setOnClickListener(this);

        fname = (EditText) findViewById(R.id.etname);
        email = (EditText) findViewById(R.id.etmail1);
        pass1 = (EditText) findViewById(R.id.etpass1);
        pass2 = (EditText) findViewById(R.id.etpass2);

      progressBar=(ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }
        @Override
        public void onClick(View v){
            switch (v.getId()){

                case R.id.button2:
                    register();
                    break;
            }
        }

        private void register(){
            String getemail=email.getText().toString().trim();
            String getfname=fname.getText().toString().trim();
            String getpass1=pass1.getText().toString().trim();
            String getpass2=pass2.getText().toString().trim();

            if(getfname.isEmpty()){
                fname.setError("Full Name is required");
                fname.requestFocus();
                return;
            }
            if(getemail.isEmpty()){
                email.setError("E-mail is required");
                email.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(getemail).matches()){
                email.setError("Please enter valid E-mail");
                email.requestFocus();
                return;
            }
            if(getpass1.isEmpty()){
                pass1.setError("Password is required");
                pass1.requestFocus();
                return;
            }
            if(getpass1.length()<6){
                pass1.setError("Password length should be at least 6");
                pass1.requestFocus();
                return;
            }
            if(getpass2.isEmpty()){
                pass2.setError("Password is required");
                pass2.requestFocus();
                return;
            }
            if(getpass2.length()<6){
                pass2.setError("Password length should be at least 6");
                pass2.requestFocus();
                return;
            }
            if(!getpass1.equals(getpass2)){
                pass1.setError("Password dont match");
                pass1.requestFocus();
                return;
            }

          progressBar.setVisibility(View.VISIBLE);
            myAuth.createUserWithEmailAndPassword(getemail, getpass1)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                User user= new User(getfname,getemail);
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            Toast.makeText(Register.this, "User has been registered successfully ", Toast.LENGTH_LONG).show();
                                           progressBar.setVisibility(View.GONE);
                                           myAuth.signOut();
                                            startActivity(new Intent(Register.this, MainActivity.class));
                                        } else
                                        {
                                            Toast.makeText(Register.this, "Failed to register", Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                            }else
                            {
                                Toast.makeText(Register.this, "Failed to register", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }
    }
