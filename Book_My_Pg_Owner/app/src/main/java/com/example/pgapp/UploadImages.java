package com.example.pgapp;

import android.app.ProgressDialog;
import android.content.ClipData;

import android.content.Intent;

import android.net.Uri;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;

import android.widget.Button;

import android.widget.ImageSwitcher;

import android.widget.ImageView;

import android.widget.TextView;

import android.widget.Toast;

import android.widget.ViewSwitcher;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

import java.util.ArrayList;

import java.util.List;
import java.util.Date;



public class UploadImages extends AppCompatActivity {



    Button select, previous, next,upld;

    ImageSwitcher imageView;

    int PICK_IMAGE_MULTIPLE = 1;

    String imageEncoded;

    TextView total;

    ArrayList<Uri> mArrayUri;
    ArrayList<Uri> mArrayUrip;

    private ProgressDialog progressDialog;
    private DatabaseReference referencetemp;


    int position = 0;
    int i = 0;

    List<String> imagesEncodedList;
    private FirebaseStorage firebaseStorage;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    Uri imageurlp,imageurl;

    private FirebaseUser user;
    private DatabaseReference references;
    private String userid;

    String[] idlist;


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_upload_images);



        user = FirebaseAuth.getInstance().getCurrentUser();
        userid =user.getUid();

        select = findViewById(R.id.select);

        total = findViewById(R.id.text);

        imageView = findViewById(R.id.image);

        previous = findViewById(R.id.previous);

        upld = findViewById(R.id.upload);

        mArrayUri = new ArrayList<Uri>();
        mArrayUrip = new ArrayList<Uri>();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading ..........");


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage =FirebaseStorage.getInstance();

        references = FirebaseDatabase.getInstance().getReference().child("PG All Image ID").child(userid);

        storageReference = firebaseStorage.getReference();



        // showing all images in imageswitcher

        imageView.setFactory(new ViewSwitcher.ViewFactory() {

            @Override

            public View makeView() {

                ImageView imageView1 = new ImageView(getApplicationContext());

                return imageView1;

            }

        });

        next = findViewById(R.id.next);



        upld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                upldpics();

                //DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("PG All Image ID").child(userid);
                // UserProfile userProfile = new UserProfile(imgID);
                //myRef.setValue(userProfile);



            }
        });



        // click here to select next image

        next.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                if (position < mArrayUri.size() - 1) {

                    // increase the position by 1

                    position++;

                    imageView.setImageURI(mArrayUri.get(position));

                } else {

                    Toast.makeText(UploadImages.this, "Last Image Already Shown", Toast.LENGTH_SHORT).show();

                }

            }

        });



        // click here to view previous image

        previous.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                if (position > 0) {

                    // decrease the position by 1

                    position--;

                    imageView.setImageURI(mArrayUri.get(position));

                }

            }

        });



        imageView = findViewById(R.id.image);



        // click here to select image

        select.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {



                // initialising intent

                Intent intent = new Intent();



                // setting type to select to be image

                intent.setType("image/*");



                // allowing multiple image to be selected

                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);

            }

        });

    }



    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // When an Image is picked

        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {

            // Get the Image from data

            if (data.getClipData() != null) {

                ClipData mClipData = data.getClipData();

                int cout = data.getClipData().getItemCount();

                for (int i = 0; i < cout; i++) {

                    // adding imageuri in array

                    imageurl = data.getClipData().getItemAt(i).getUri();

                    imageurlp = data.getData();

                    mArrayUri.add(imageurl);
                    mArrayUrip.add(imageurlp);

                }

                // setting 1st selected image into image switcher

                imageView.setImageURI(mArrayUri.get(0));

                position = 0;

            } else {

                Uri imageurl = data.getData();

                mArrayUri.add(imageurl);

                imageView.setImageURI(mArrayUri.get(0));

                position = 0;

            }

        } else {



            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();

        }

    }

    private void upldpics()
    {
        for (int uploads=0; uploads < mArrayUrip.size(); uploads++) {
            Date date = new Date();
            String imgID = "pic id - "+uploads+" "+date.toString();

            StorageReference imageReference = storageReference.child("PG Pic").child(userid).child(imgID);
            Uri Image  = mArrayUri.get(uploads);


            UploadTask uploadTask = imageReference.putFile(Image);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadImages.this,"Upload Failed",Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //i++;
                    //if(i==mArrayUrip.size()) {

                        //progressDialog.dismiss();
                        Toast.makeText(UploadImages.this, "please wait a moment....", Toast.LENGTH_SHORT).show();
                        //imageView.setImageURI(null);

                        //startActivity(new Intent(UploadImages.this, ShowAllImagesFromStorage.class));

                  //  }

                }
            });

        }

        //-----------------for user-----------------
        referencetemp = FirebaseDatabase.getInstance().getReference().child("PG Details").child(userid);

        referencetemp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //UserProfile userProfile = snapshot.getValue(UserProfile.class);
                PGprofile pgProfile = snapshot.getValue(PGprofile.class);

                if(pgProfile != null){
                    String name = pgProfile.pgNAME;
                    String city = pgProfile.pgCITY;
                    for (int uploads=0; uploads < mArrayUrip.size(); uploads++) {
                        Date date = new Date();
                        String imgID = "pic id - "+uploads+" "+date.toString();

                    StorageReference imageReference2 = storageReference.child("For Users").child(name).child("PG Pics").child(imgID);
                        Uri Image  = mArrayUri.get(uploads);

                        UploadTask uploadTask = imageReference2.putFile(Image);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UploadImages.this,"Upload Failed",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                i++;
                                if(i==mArrayUrip.size()) {

                                    progressDialog.dismiss();
                                    Toast.makeText(UploadImages.this, "Upload Successful....", Toast.LENGTH_SHORT).show();
                                    imageView.setImageURI(null);

                                    startActivity(new Intent(UploadImages.this, MainActivity.class));

                                }

                            }
                        });

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}