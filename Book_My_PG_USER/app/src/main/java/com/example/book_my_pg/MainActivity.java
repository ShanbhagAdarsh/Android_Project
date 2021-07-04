package com.example.book_my_pg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import javax.net.ssl.SSLSessionContext;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register,fpassword;
    private EditText email, password;
    private Button login;

    private FirebaseAuth myAuth;
    private ProgressBar progressBar;

    SharedPreferences sharedpreferences;
    int autoSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.etreg);
        register.setOnClickListener(this);

        login=(Button) findViewById(R.id.button1);
        login.setOnClickListener(this);

        email=(EditText) findViewById(R.id.etmail);
        password=(EditText) findViewById(R.id.etpassword);

        fpassword=(TextView) findViewById(R.id.etforgot);
        fpassword.setOnClickListener(this);

        myAuth=FirebaseAuth.getInstance();
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        FirebaseUser user = myAuth.getCurrentUser();
        if(user!=null)
        {
            finish();
            startActivity(new Intent(MainActivity.this,MainPage.class));
        }
    }

    @Override
    public void onBackPressed() {
        Intent a=new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
        public void onClick(View v){
        switch(v.getId()){
            case R.id.etreg:
                Intent intent= new Intent(MainActivity.this, phoneConfirm.class);
                startActivity(intent);
                break;

            case R.id.button1:
                userLogin();
                break;

            case R.id.etforgot:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }

    }

    private void userLogin(){

        String getemail=email.getText().toString().trim();
        String getpassword=password.getText().toString().trim();

        if(getemail.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(getemail).matches()){
            email.setError("Please enter valid E-mail");
            email.requestFocus();
            return;
        }

        if(getpassword.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if(getpassword.length()<6){
            password.setError("Password length should be at least 6");
            password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        myAuth.signInWithEmailAndPassword(getemail,getpassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                   FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                   String userid=user.getUid();

                    if (user.isEmailVerified()) {
                        //redirect to user profile
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        userid =user.getUid();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User Details");
                        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                profile1 userProfile = snapshot.getValue(profile1.class);
                                progressBar.setVisibility(View.GONE);
                                if(userProfile == null){
                                    startActivity(new Intent(MainActivity.this, Profile_Activity_new.class));
                                }
                                else{
                                    startActivity(new Intent(MainActivity.this, MainPage.class));
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Check your email to verify your account.", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Failed to register. Please check your credentials.", Toast.LENGTH_LONG).show();
                }
            }
        });
        }
}

