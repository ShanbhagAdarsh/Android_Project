package com.example.pgapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Registration extends AppCompatActivity {

    private TextInputLayout  userPassword,ConfirmPassword, userEmail;
    private Button userLogin;
    private FirebaseAuth firebaseAuth;
    String userpassword,userconfirmpassword,useremail;
    private FirebaseStorage firebaseStorage;
    private Button regButton;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIVViews();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage =FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    //upload data to data base
                    String user_email = userEmail.getEditText().getText().toString().trim();
                    String user_password = userPassword.getEditText().getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendEmailVerification();
                            } else {
                                Toast.makeText(Registration.this, "error!", Toast.LENGTH_SHORT).show();
                            }

                        }

                    });

                }else{
                    Toast.makeText(Registration.this,"Enter all the Details correctly",Toast.LENGTH_SHORT).show();
                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registration.this, Login.class));
            }
        });

    }

    private void setupUIVViews()
    {

        userEmail = (TextInputLayout) findViewById(R.id.userEmail);

        userPassword = (TextInputLayout) findViewById(R.id.Password);
        ConfirmPassword = (TextInputLayout) findViewById(R.id.confirmpassword);

        regButton = (Button) findViewById(R.id.registerbutton);
        userLogin = (Button) findViewById(R.id.loginbtntxt);

    }

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private Boolean validate()
    {
        Boolean result = true;
        useremail = userEmail.getEditText().getText().toString();
        userpassword = userPassword.getEditText().getText().toString();
        userconfirmpassword = ConfirmPassword.getEditText().getText().toString();

            if (TextUtils.isEmpty(useremail)) {
                userEmail.setErrorEnabled(true);
                userEmail.setError("Email Is Required");
                return false;
            }
            else {
                if (useremail.matches(emailpattern)) {
                    result = true;
                } else {
                    userEmail.setErrorEnabled(true);
                    userEmail.setError("Enter a Valid Email Id");
                    return false;
                }
            }

            if (TextUtils.isEmpty(userpassword)) {
                userPassword.setErrorEnabled(true);
                userPassword.setError("Enter Password");
                return false;
            } else {
                if (userpassword.length() < 8) {
                    userPassword.setErrorEnabled(true);
                    userPassword.setError("Password is Weak");
                    return false;
                } else {
                    result = true;
                }
            }

            if(TextUtils.isEmpty(userconfirmpassword)){
                ConfirmPassword.setErrorEnabled(true);
                ConfirmPassword.setError("Enter Password Again");
                return false;
            }else {
                result = true;
            }
            if(!userpassword.equals(userconfirmpassword)){
                    ConfirmPassword.setErrorEnabled(true);
                    ConfirmPassword.setError("Password Dosen't Match");
                return false;
                }else{
                    result = true;
                }

        return result;
    }

    private void sendEmailVerification()
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        // startActivity(new Intent(Registration.this,Loginback.class));
                        Toast.makeText(Registration.this,"Sucessfully Registered,Verification mailsent",Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        startActivity(new Intent(Registration.this,Login.class));
                        //finish();
                        //startActivity(new Intent(Registration.this,SendOTPActivity.class));
                    }
                    else {
                        Toast.makeText(Registration.this,"Mail Verification has'nt been sent",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
