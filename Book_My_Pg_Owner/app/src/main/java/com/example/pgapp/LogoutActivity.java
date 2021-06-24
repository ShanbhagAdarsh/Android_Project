package com.example.pgapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class LogoutActivity extends AppCompatActivity {

    private TextInputLayout email;
    private TextInputLayout password;
    private Button submit;
    private FirebaseAuth firebaseAuth;
    private String userid;
    private FirebaseUser user;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        //reference = FirebaseDatabase.getInstance().getReference("Room Details");
        userid =user.getUid();

        email = (TextInputLayout)findViewById(R.id.userEMAIL);
        password = (TextInputLayout)findViewById(R.id.userPassword);
        submit = (Button) findViewById(R.id.deletebutton);

        //final EditText email = findViewById(R.id.email);
        // final EditText password = findViewById(R.id.logpass);
        //Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //---------------------------------USER-----------------------------------------
                DatabaseReference referencetemp = FirebaseDatabase.getInstance().getReference().child("PG Details").child(userid);

                referencetemp.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //UserProfile userProfile = snapshot.getValue(UserProfile.class);
                        PGprofile pgProfile = snapshot.getValue(PGprofile.class);

                        if(pgProfile != null)
                        {
                            String name = pgProfile.pgNAME;
                            String city = pgProfile.pgCITY;

                            //pg details delete(for user)
                            DatabaseReference myRef4 = FirebaseDatabase.getInstance().getReference().child("For Users").child(city).child(name);
                            myRef4.removeValue();

                            //pg room delete(for user)
                            DatabaseReference myRef5 = FirebaseDatabase.getInstance().getReference().child("For Users").child("PG Rooms").child(name);
                            myRef5.removeValue();

                            //pg profile pic(for user)
                            StorageReference imageReference3 = storageReference.child("For Users").child(name);
                            imageReference3.delete();

                            //remove booked user
                            DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("Booked Users").child(userid);
                            userref.removeValue();

                            //-------------------------------------------OWNER------------------------------------------
                            //owner details delete
                            DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference().child("Owner Profile").child(userid);
                            myRef1.removeValue();

                            //proile pic
                            StorageReference imageReference = storageReference.child("Profile Pic").child(userid);
                            imageReference.delete();

                            //pg details delete(for Owner)
                            DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference().child("PG Details").child(userid);
                            myRef2.removeValue();

                            //pg profile delete pic
                            StorageReference imageReference1 = storageReference.child("PG Profile Pic").child(userid);
                            imageReference1.delete();

                            //multiple pics
                            StorageReference imageReference2 = storageReference.child("PG Pic").child(userid);
                            imageReference2.delete();

                            //room details delete(for owner)
                            DatabaseReference myRef3 = FirebaseDatabase.getInstance().getReference().child("Room Details").child(userid);
                            myRef3.removeValue();

                            //--------------------------------------Account delete---------------------------------------
                            deleteuser(email.getEditText().getText().toString(), password.getEditText().getText().toString());

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
    }

    private void deleteuser(String email, String password) {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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
                                            if (task.isSuccessful())
                                            {
                                                Log.d("TAG", "User account deleted.");
                                                startActivity(new Intent(LogoutActivity.this, Login.class));
                                                Toast.makeText(LogoutActivity.this, "Deleted User Successfully,", Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    });
                        }
                    });
        }
    }
}