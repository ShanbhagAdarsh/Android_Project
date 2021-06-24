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

public class RoomActivity extends AppCompatActivity {

    private TextInputLayout PGroomno,PGroomdetails,PGdepositamt,PGfullamt;
    private Button submit;
    private TextView ROOMTYPE;
    private FirebaseAuth firebaseAuth;
    String Sroomtype,Sroomno,Sroomdetails,Sroomdepositamt,Sroomfullamt;
    private FirebaseStorage firebaseStorage;

    private StorageReference storageReference;
    private DatabaseReference reference;

    private Spinner roomtypes;
    RoomProfile roomProfile;

    int cnt=0;

    private FirebaseUser user;
    private DatabaseReference references;
    private String userid,pgcity;

    private DatabaseReference referencetemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);



        setupUIVViews();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage =FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();
      // references = FirebaseDatabase.getInstance().getReference("Room Details");
        userid =user.getUid();


        List<String> Categories = new ArrayList<>();
        Categories.add(0, "Choose Room Type");
        Categories.add("Single");
        Categories.add("Double");
        Categories.add("Tripple");


        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter( this,android.R.layout.simple_spinner_item,Categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        roomtypes.setAdapter(dataAdapter);

        roomtypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemsSelected(AdapterView<?> adapterView, View view, int i , long l) {
                if (adapterView.getItemAtPosition(i).equals("Choose Room Type")) {

                }else
                {
                    ROOMTYPE.setText(adapterView.getSelectedItem().toString());
                }
            }

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            public void onNothingSelected(AdapterView<?> adapterView){

            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    sendUserData();
                    startActivity(new Intent(RoomActivity.this,UploadImages.class));

                }
                else
                {
                    Toast.makeText(RoomActivity.this,"Enter all the Details correctly",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private void setupUIVViews()
    {

        PGroomno = (TextInputLayout) findViewById(R.id.roomno);
        PGroomdetails = (TextInputLayout) findViewById(R.id.roomdetail);
        PGdepositamt = (TextInputLayout) findViewById(R.id.roomdeposit);
        PGfullamt = (TextInputLayout) findViewById(R.id.roomfullamt);

        roomtypes = (Spinner) findViewById(R.id.roomtype);
        ROOMTYPE = (TextView) findViewById(R.id.roomtypetext);


        submit = (Button)findViewById(R.id.submitroomdetails);
    }


    private Boolean validate()
    {
        Boolean result = true;


        Sroomtype = roomtypes.getSelectedItem().toString();

        Sroomno = PGroomno.getEditText().getText().toString();
        Sroomdetails = PGroomdetails.getEditText().getText().toString();
        Sroomdepositamt= PGdepositamt.getEditText().getText().toString();
        Sroomfullamt = PGfullamt.getEditText().getText().toString();



        if (TextUtils.isEmpty(Sroomtype)) {
            ROOMTYPE.setError("Enter Owner Name");
            return false;
        } else {
            result = true;
        }
        if (TextUtils.isEmpty(Sroomno))
        {
            PGroomno.setErrorEnabled(true);
            PGroomno.setError("Enter Room Number");
            return false;
        } else
            {
            result = true;
        }


        if (TextUtils.isEmpty(Sroomdetails)) {
            PGroomdetails.setErrorEnabled(true);
            PGroomdetails.setError("Mobile Number Is Required");
            return false;
        }

        if (TextUtils.isEmpty(Sroomdepositamt)) {
            PGdepositamt.setErrorEnabled(true);
            PGdepositamt.setError("Enter Amount");
            return false;
        } else {
            result = true;
        }
        if (TextUtils.isEmpty(Sroomfullamt)) {
            PGfullamt.setErrorEnabled(true);
            PGfullamt.setError("Enter amount");
            return false;
        } else {
            result = true;
        }


        return result;
    }



    private void sendUserData(){


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        //for user

        //----------------------------------------------------------------------------------------

        referencetemp = FirebaseDatabase.getInstance().getReference().child("PG Details").child(userid);

        referencetemp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //UserProfile userProfile = snapshot.getValue(UserProfile.class);
                PGprofile pgProfile = snapshot.getValue(PGprofile.class);

                if(pgProfile != null){
                    String name = pgProfile.pgNAME;
                    String city = pgProfile.pgCITY;
                    String add = pgProfile.pgLOCALADDRESS;
                    String mob = pgProfile.pgMOBILENO;

                    if(Sroomtype == "Single")
                        cnt = 1;
                    if(Sroomtype == "Double")
                        cnt = 2;
                    if(Sroomtype == "Tripple")
                        cnt = 3;

                    roomProfile = new RoomProfile(Sroomtype,Sroomno,Sroomdetails,Sroomdepositamt,Sroomfullamt,userid,name,city,add,mob,cnt);

                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Room Details").child(userid).child(Sroomno);

                    myRef.setValue(roomProfile);

                    DatabaseReference myRef3 = FirebaseDatabase.getInstance().getReference().child("For Users").child("PG Rooms").child(name).child(Sroomno);

                    myRef3.setValue(roomProfile);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
