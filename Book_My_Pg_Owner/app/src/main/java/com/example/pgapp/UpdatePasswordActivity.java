package com.example.pgapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class UpdatePasswordActivity extends AppCompatActivity {

    private TextInputLayout passwordmail;
    private Button resetpassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        passwordmail = (TextInputLayout)findViewById(R.id.useremail);
        resetpassword = (Button)findViewById(R.id.resetpasswordbutton);
        firebaseAuth = FirebaseAuth.getInstance();

        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usermail = passwordmail.getEditText().getText().toString().trim();

                if(usermail.equals("")){
                    Toast.makeText(UpdatePasswordActivity.this,"Please enter your registered Email Id",Toast.LENGTH_SHORT).show();
                }
                else {
                    firebaseAuth.sendPasswordResetEmail(usermail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(UpdatePasswordActivity.this,"Password change Request Sent,Please check Your Mail",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(UpdatePasswordActivity.this,Login.class));
                            }
                            else {
                                Toast.makeText(UpdatePasswordActivity.this,"Error in sending!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });





    }
}