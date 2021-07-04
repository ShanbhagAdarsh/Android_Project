package com.example.book_my_pg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class delAccActivity extends AppCompatActivity {

    private EditText email,password;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delacc_activity);

        email = (EditText)findViewById(R.id.userEMAIL);
        password = (EditText)findViewById(R.id.userPassword);
        submit = (Button) findViewById(R.id.deletebutton);

        //final EditText email = findViewById(R.id.email);
        // final EditText password = findViewById(R.id.logpass);
        //Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteuser(email.getText().toString(), password.getText().toString());
            }
        });
    }

    private void deleteuser(String email, String password) {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);

        // Prompt the user to re-provide their sign-in credentials
        if (user != null) {
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                DatabaseReference reference1,ref2,ref3;
                                                String userid;
                                                userid =user.getUid();
                                                reference1 = FirebaseDatabase.getInstance().getReference("profile1").child(userid);
                                                reference1.removeValue();
                                                ref2=FirebaseDatabase.getInstance().getReference("profile2").child(userid);
                                                ref2.removeValue();
                                                ref3=FirebaseDatabase.getInstance().getReference("bdate").child(userid);
                                                ref3.removeValue();

                                                Log.d("TAG", "User account deleted.");
                                                startActivity(new Intent(delAccActivity.this, MainActivity.class));
                                                Toast.makeText(delAccActivity.this, "Deleted User Successfully,", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                    });
        }
    }
}