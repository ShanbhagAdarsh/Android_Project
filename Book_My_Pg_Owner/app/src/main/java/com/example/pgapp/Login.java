package com.example.pgapp;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Login extends AppCompatActivity
{
    private TextInputLayout Name;
    private TextInputLayout Password;
    private TextView userRegistration;
    private Button Loging;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView Forgotpassword;
    private FirebaseUser user;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Name = (TextInputLayout)findViewById(R.id.userEMAIL);
        Password = (TextInputLayout)findViewById(R.id.userPassword);
        userRegistration = (TextView) findViewById(R.id.registertxtbtn);
        Loging = (Button) findViewById(R.id.loginbutton);
        Forgotpassword = (TextView)findViewById(R.id.forgotpassword);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null)
        {
            finish();
            startActivity(new Intent(Login.this,MainActivity.class));
        }

        Loging.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                validate(Name.getEditText().getText().toString(),Password.getEditText().getText().toString());
            }
        });

        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,SendOTPActivity.class));
                //startActivity(new Intent(Login.this,Registration.class));
            }
        });

        Forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(Login.this,UpdatePasswordActivity.class));
            }
        });

    }

    private void validate(String username,String userPassword)
    {
        progressDialog.setMessage("Verifying Please wait for a movement.");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(username,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    //Toast.makeText(MainActivity.this,"Login Sucessful",Toast.LENGTH_SHORT).show();
                    checkEmailVerification();
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this,"Login Failed!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();
        if(emailflag){
            //-------------------------------------------------------------
            user = FirebaseAuth.getInstance().getCurrentUser();
            userid =user.getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Owner Profile");
            reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserProfile userProfile = snapshot.getValue(UserProfile.class);
                    if(userProfile == null)
                        startActivity(new Intent(Login.this,Details.class));
                    else
                        startActivity(new Intent(Login.this,MainActivity.class));
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            //-----------------------------------------------------------------
        }
        else {
            Toast.makeText(Login.this,"Please verify your Email",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}