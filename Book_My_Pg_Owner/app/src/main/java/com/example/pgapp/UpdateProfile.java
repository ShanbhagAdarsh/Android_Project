package com.example.pgapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class UpdateProfile extends AppCompatActivity {

    private TextInputLayout newuserfname, newuserEmail,newuserlname,newusermobileno,newuserlocaladdress,newuserarea,newuserpincode,newuserstate,newusercity;
    private Button savebtn,selectpic;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private ImageView updateProfilePic;
    private FirebaseStorage firebaseStorage;
    private static final int PICK_IMAGE = 123;
    Uri imagePath;
    private StorageReference storageReference;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userid;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE &&  resultCode == RESULT_OK ){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                updateProfilePic.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        savebtn =(Button) findViewById(R.id.updateprofilebutton);
        updateProfilePic =(ImageView) findViewById(R.id.changeProfilepic);
        selectpic =(Button) findViewById(R.id.changepicbtn);


        newuserfname = (TextInputLayout) findViewById(R.id.userEMAIL);
        newuserEmail = (TextInputLayout) findViewById(R.id.userEmail);
        newuserlname = (TextInputLayout) findViewById(R.id.userlname);
        newusermobileno = (TextInputLayout) findViewById(R.id.usermobileno);
        newuserlocaladdress = (TextInputLayout) findViewById(R.id.userLocaladdress);
        newuserarea = (TextInputLayout) findViewById(R.id.userArea);
        newuserpincode = (TextInputLayout) findViewById(R.id.userPincode);
        newuserstate = (TextInputLayout) findViewById(R.id.userState);
        newusercity = (TextInputLayout) findViewById(R.id.userCity);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Owner Profile");
        userid =user.getUid();


       // final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);

                newuserfname.getEditText().setText(userProfile.getUserFName());
                newuserlname.getEditText().setText(userProfile.getUserLName());
                newuserEmail.getEditText().setText(userProfile.getUserEmail());

                newusermobileno.getEditText().setText(userProfile.getUserMobileno());
                newuserlocaladdress.getEditText().setText(userProfile.getUserLocalAddress());
                newuserarea.getEditText().setText(userProfile.getUserAREA());

                newuserpincode.getEditText().setText(userProfile.getUserPINCODE());
                newuserstate.getEditText().setText(userProfile.getUserSTATE());
                newusercity.getEditText().setText(userProfile.getUserCITY());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdateProfile.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();

            }
        });

        final StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("Profile Pic").child(firebaseAuth.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(updateProfilePic);

            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fname = newuserfname.getEditText().getText().toString();
                String lname = newuserlname.getEditText().getText().toString();
                String emails = newuserEmail.getEditText().getText().toString();

                String mob = newusermobileno.getEditText().getText().toString();
                String loadd = newuserlocaladdress.getEditText().getText().toString();
                String area = newuserarea.getEditText().getText().toString();

                String pin = newuserpincode.getEditText().getText().toString();
                String state = newuserstate.getEditText().getText().toString();
                String city = newusercity.getEditText().getText().toString();




                if(imagePath != null) {

                    //upload details
                    UserProfile userProfile = new UserProfile(fname, lname, emails, mob, loadd, area, pin, city, state);
                    reference.child(userid).setValue(userProfile);

                    //upload image
                    StorageReference imageReference = storageReference.child("Profile Pic").child(firebaseAuth.getUid());  //user id/Images/Profile_Pic.png
                    UploadTask uploadTask = imageReference.putFile(imagePath);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateProfile.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(UpdateProfile.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UpdateProfile.this, ProfileActivity.class));
                        }
                    });

                }
                else{
                    UserProfile userProfile = new UserProfile(fname, lname, emails, mob, loadd, area, pin, city, state);
                    reference.child(userid).setValue(userProfile);
                    startActivity(new Intent(UpdateProfile.this, ProfileActivity.class));
                }

                //finish();



            }
        });
        selectpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");  //application/* for documents
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE);
            }
        });

    }
    //if menu add here

}