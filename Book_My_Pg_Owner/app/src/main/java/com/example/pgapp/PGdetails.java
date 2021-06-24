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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PGdetails extends AppCompatActivity {

    private TextInputLayout PGname,PGownername,PGmail,PGmobileno,PGlocaladdress,PGpincode,PGcity,PGstate;
    private Button submitpg,selectpic;
    private TextView PGTYPE;
    private FirebaseAuth firebaseAuth;
    String SPGtype,SPGname,SPGemail,SPGownername,SPGMobileno,SPGLocalAddress,SPGPINCODE,SPGSTATE,SPGCITY;
    private FirebaseStorage firebaseStorage;
    private ImageView profilepic;
    private static final int PICK_IMAGE = 123;
    Uri imagePath;
    private StorageReference storageReference;
    private DatabaseReference reference;

    private Spinner pgtypes;
    PGprofile pgprofile;
    int maxid = 0;

    private FirebaseUser user;
    private DatabaseReference references;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_gdetails);
        setupUIVViews();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage =FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();
        //references = FirebaseDatabase.getInstance().getReference("PG Registration");
        userid =user.getUid();

        pgprofile = new PGprofile();

        List<String> Categories = new ArrayList<>();
        Categories.add(0, "Choose PG Type");
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




        selectpic.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent();
                intent.setType("image/*");  //application/* for documents
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE);

            }
        });

        submitpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    sendUserData();


                    startActivity(new Intent(PGdetails.this,RoomActivity.class));

                }
                else
                {
                    Toast.makeText(PGdetails.this,"Enter all the Details correctly",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

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




    private void setupUIVViews()
    {

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
    }

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private Boolean validate()
    {
        Boolean result = true;


        SPGtype = pgtypes.getSelectedItem().toString();

        SPGownername = PGownername.getEditText().getText().toString();
        SPGname = PGname.getEditText().getText().toString();
        SPGemail= PGmail.getEditText().getText().toString();
        SPGMobileno = PGmobileno.getEditText().getText().toString();
        SPGLocalAddress = PGlocaladdress.getEditText().getText().toString();
        SPGPINCODE = PGpincode.getEditText().getText().toString();
        SPGCITY = PGcity.getEditText().getText().toString();
        SPGSTATE = PGstate.getEditText().getText().toString();



        if (imagePath == null) {
            profilepic.findFocus();

        } else {
            result = true;
        }

        if (TextUtils.isEmpty(SPGownername)) {
            PGownername.setErrorEnabled(true);
            PGownername.setError("Enter Owner Name");
            return false;
        } else {
            result = true;
        }
        if (TextUtils.isEmpty(SPGname)) {
            PGname.setErrorEnabled(true);
            PGname.setError("Enter Last Name");
            return false;
        } else {
            result = true;
        }
        if (TextUtils.isEmpty(SPGemail)) {
            PGmail.setErrorEnabled(true);
            PGmail.setError("Email Is Required");
            return false;
        } else {
            if (SPGemail.matches(emailpattern)) {
                result = true;
            } else {
                PGmail.setErrorEnabled(true);
                PGmail.setError("Enter a Valid Email Id");
                return false;
            }
        }

        if (TextUtils.isEmpty(SPGMobileno)) {
            PGmobileno.setErrorEnabled(true);
            PGmobileno.setError("Mobile Number Is Required");
            return false;
        } else {
            if (SPGMobileno.length() < 10) {
                PGmobileno.setErrorEnabled(true);
                PGmobileno.setError("Invalid Mobile Number");
                return false;
            } else {
                result = true;
            }
        }
        if (TextUtils.isEmpty(SPGLocalAddress)) {
            PGlocaladdress.setErrorEnabled(true);
            PGlocaladdress.setError("Address Is Required");
            return false;
        } else {
            result = true;
        }
        if (TextUtils.isEmpty(SPGPINCODE)) {
            PGpincode.setErrorEnabled(true);
            PGpincode.setError("Please Enter Pincode");
            return false;
        } else {
            result = true;
        }


        if (TextUtils.isEmpty(SPGSTATE)) {
            PGstate.setErrorEnabled(true);
            PGstate.setError("Please Enter State");
            return  false;
        } else {
            result = true;
        }

        if (TextUtils.isEmpty(SPGCITY)) {
            PGcity.setErrorEnabled(true);
            PGcity.setError("Please Enter City");
            return false;
        } else {
            result = true;
        }

        return result;
    }



    private void sendUserData(){


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("PG Details").child(userid);

        DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference().child("For Users").child(SPGCITY).child(SPGname);

        StorageReference imageReference = storageReference.child("PG Profile Pic").child(userid);
        StorageReference imageReference2 = storageReference.child("For Users").child(SPGname).child("PG Profile").child("PG Profile Pic");

        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PGdetails.this,"Upload Failed",Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(PGdetails.this,"Upload Successful",Toast.LENGTH_SHORT).show();
            }
        });


        UploadTask uploadTask2 = imageReference2.putFile(imagePath);
        uploadTask2.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PGdetails.this,"Upload Failed",Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(PGdetails.this,"Upload Successful",Toast.LENGTH_SHORT).show();
            }
        });

        String pgid = userid.toString();
        pgprofile = new PGprofile(SPGtype,SPGname,SPGownername,SPGemail,SPGMobileno,SPGLocalAddress,SPGPINCODE,SPGCITY.toUpperCase(),SPGSTATE,pgid);

        myRef.setValue(pgprofile);

        myRef2.setValue(pgprofile);

    }
}
