package com.example.pgapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pgapp.home.Nav_Pg;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class UpdatePGdetails extends AppCompatActivity {

    private TextInputLayout PGname,PGownername,PGmail,PGmobileno,PGlocaladdress,PGpincode,PGcity,PGstate;
    private Button submitpg,selectpic;
    private TextView PGTYPE;
    private FirebaseAuth firebaseAuth;
    private String SPGtype;
    private FirebaseStorage firebaseStorage;
    private ImageView profilepic;
    private static final int PICK_IMAGE = 123;
    private Uri imagePath;
    private StorageReference storageReference;
    // private DatabaseReference reference;
    DatabaseReference myRef2;

    private Spinner pgtypes;
    PGprofile pgprofile,pgprofile2;
    private DatabaseReference referencetemp;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userid;
    private FirebaseDatabase firebaseDatabase;
    PGprofile temp;
    String tem;
    String name,city;
    //PGprofile pgprofile;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE &&  resultCode == RESULT_OK){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                profilepic.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_p_gdetails);

        PGname = (TextInputLayout) findViewById(R.id.pgname);
        PGownername = (TextInputLayout) findViewById(R.id.ownername);
        PGmail = (TextInputLayout) findViewById(R.id.pgemail);

        pgtypes = (Spinner) findViewById(R.id.pgtype);
        PGTYPE = (TextView) findViewById(R.id.pgtypetext);

        submitpg = (Button) findViewById(R.id.submitpgdetails);

        PGmobileno = (TextInputLayout) findViewById(R.id.pgmobno);
        PGlocaladdress = (TextInputLayout) findViewById(R.id.pgLocaladdress);
        PGpincode = (TextInputLayout) findViewById(R.id.pgpincode);
        PGstate = (TextInputLayout) findViewById(R.id.pgstate);
        PGcity = (TextInputLayout) findViewById(R.id.pgcity);

        profilepic =(ImageView)findViewById(R.id.pgProfilepic);
        selectpic = (Button)findViewById(R.id.selectimgbtn);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseStorage =FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("PG Details");
        userid =user.getUid();

        temp = new PGprofile();
        tem = temp.pgTYPE;



        List<String> Categories = new ArrayList<>();
        Categories.add(0,"Choose PG Type");
        Categories.add("Boys PG");
        Categories.add("Girls PG");
        Categories.add("Both");




        ArrayAdapter<String> dataAdapter;



        dataAdapter = new ArrayAdapter( this,android.R.layout.simple_spinner_item,Categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        pgtypes.setAdapter(dataAdapter);


        pgtypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemsSelected(AdapterView<?> adapterView, View view, int i , long l) {
                if (adapterView.getItemAtPosition(i).equals("Choose PG Type")) {

                }else
                {
                    PGTYPE.setText(adapterView.getSelectedItem().toString());
                }
            }

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            public void onNothingSelected(AdapterView<?> adapterView){

            }
        });


        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PGprofile pgprofile = snapshot.getValue(PGprofile.class);


                // PGTYPE.setText(pgprofile.getPgTYPE());

                //SPGtype = pgprofile.pgTYPE;

                PGname.getEditText().setText(pgprofile.getPgNAME());
                PGownername.getEditText().setText(pgprofile.getPgOWNERNAME());
                PGmail.getEditText().setText(pgprofile.getPgMAIL());


                PGmobileno.getEditText().setText(pgprofile.getPgMOBILENO());
                PGlocaladdress.getEditText().setText(pgprofile.getPgLOCALADDRESS());


                PGpincode.getEditText().setText(pgprofile.getPgPINCODE());
                PGcity.getEditText().setText(pgprofile.getPgCITY());
                PGstate.getEditText().setText(pgprofile.getPgSTATE());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdatePGdetails.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();

            }
        });

        final StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("PG Profile Pic").child(firebaseAuth.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(profilepic);

            }
        });

        submitpg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                String pgtyp = pgtypes.getSelectedItem().toString();
                String pgnam = PGname.getEditText().getText().toString();
                String pgown = PGownername.getEditText().getText().toString();
                String pgmail = PGmail.getEditText().getText().toString();
                String pgmob = PGmobileno.getEditText().getText().toString();
                String pgloadd = PGlocaladdress.getEditText().getText().toString();
                String pgpin = PGpincode.getEditText().getText().toString();
                String pgstate = PGstate.getEditText().getText().toString();
                String pgcity = PGcity.getEditText().getText().toString();

                if (imagePath != null)
                {

                    if (pgtyp == "Choose PG Type") {
                        PGTYPE.setError("Select PG Type");
                        Toast.makeText(UpdatePGdetails.this, "Select PG Type", Toast.LENGTH_SHORT).show();
                    }

                    StorageReference imageReference = storageReference.child("PG Profile Pic").child(firebaseAuth.getUid());

                    UploadTask uploadTask = imageReference.putFile(imagePath);


                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdatePGdetails.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(UpdatePGdetails.this, "please wait a momentt...", Toast.LENGTH_SHORT).show();

                        }
                    });

                    pgprofile2 = new PGprofile(pgtyp, pgnam, pgown, pgmail, pgmob, pgloadd, pgpin, pgcity, pgstate, userid.toString());
                    reference.child(userid).setValue(pgprofile2);
                   // pgprofile = new PGprofile(pgtyp, pgnam, pgown, pgmail, pgmob, pgloadd, pgpin, pgcity, pgstate, userid.toString());
                    //reference.child(userid).setValue(pgprofile);

                    //---------------------------for user---------------------------------------------
                    DatabaseReference referencetemp = FirebaseDatabase.getInstance().getReference().child("PG Details").child(userid);

                    referencetemp.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //UserProfile userProfile = snapshot.getValue(UserProfile.class);
                            PGprofile pgProfile = snapshot.getValue(PGprofile.class);

                            if(pgProfile != null){
                                String name = pgProfile.pgNAME;
                                String city = pgProfile.pgCITY;

                                DatabaseReference myRef3 = FirebaseDatabase.getInstance().getReference().child("For Users").child(city).child(name);

                                StorageReference imageReference2 = storageReference.child("For Users").child(name).child("PG Profile").child("PG Profile Pic");

                                UploadTask uploadTask2 = imageReference2.putFile(imagePath);
                                uploadTask2.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(UpdatePGdetails.this,"Upload Failed",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Toast.makeText(UpdatePGdetails.this,"Upload Successful",Toast.LENGTH_SHORT).show();
                                    }
                                });

                                myRef3.setValue(pgprofile2);
                                startActivity(new Intent(UpdatePGdetails.this, PGProfileActivity.class));

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    //-------------------------------------------------------------------------



                } else {
                    if (pgtyp == "Choose PG Type") {
                        PGTYPE.setError("Select PG Type");
                        Toast.makeText(UpdatePGdetails.this, "Select PG Type", Toast.LENGTH_SHORT).show();
                    } else {

                        pgprofile = new PGprofile(pgtyp, pgnam, pgown, pgmail, pgmob, pgloadd, pgpin, pgcity.toUpperCase(), pgstate, userid.toString());
                        reference.child(userid).setValue(pgprofile);

                        //------------------------------------------------------------------------
                        DatabaseReference referencetemp = FirebaseDatabase.getInstance().getReference().child("PG Details").child(userid);

                        referencetemp.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //UserProfile userProfile = snapshot.getValue(UserProfile.class);
                                PGprofile pgProfile = snapshot.getValue(PGprofile.class);

                                if(pgProfile != null){
                                    String name = pgProfile.pgNAME;
                                    String city = pgProfile.pgCITY;

                                    DatabaseReference myRef3 = FirebaseDatabase.getInstance().getReference().child("For Users").child(city).child(name);

                                    myRef3.setValue(pgprofile);
                                    startActivity(new Intent(UpdatePGdetails.this, PGProfileActivity.class));

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        //-------------------------------------------------------------------------


                    }


                }
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

}