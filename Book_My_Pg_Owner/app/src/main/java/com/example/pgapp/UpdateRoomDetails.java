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


import com.example.pgapp.home.Nav_room;
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

public class UpdateRoomDetails extends AppCompatActivity {

    private TextInputLayout PGroomno,PGroomdetails,PGdepositamt,PGfullamt,searchRoomNumber;
    private Button submit;
    private TextView ROOMTYPE;
    private FirebaseAuth firebaseAuth;
    String Sroomtype,Sroomno,Sroomdetails,Sroomdepositamt,Sroomfullamt;
    private FirebaseStorage firebaseStorage;

    int cnt = 0;
    private Spinner roomtypes;
    RoomProfile roomprofile;


    private FirebaseUser user;
    private DatabaseReference reference;
    private String userid;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_room_details);


        Intent intent = getIntent();

        final String ROOMNUMBER = intent.getStringExtra("message_key2");

        PGroomno = (TextInputLayout) findViewById(R.id.roomno);
        PGroomdetails = (TextInputLayout) findViewById(R.id.roomdetail);
        PGdepositamt = (TextInputLayout) findViewById(R.id.roomdeposit);
        PGfullamt = (TextInputLayout) findViewById(R.id.roomfullamt);

        roomtypes = (Spinner) findViewById(R.id.roomtype);
        ROOMTYPE = (TextView) findViewById(R.id.roomtypetext);


        submit = (Button)findViewById(R.id.submitroomdetails);

        searchRoomNumber = (TextInputLayout) findViewById(R.id.searchroomno);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        firebaseStorage =FirebaseStorage.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Room Details");
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


        reference.child(userid).child(ROOMNUMBER).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomprofile = snapshot.getValue(RoomProfile.class);


                PGroomno.getEditText().setText(roomprofile.getPgROOMNO());
                PGroomdetails.getEditText().setText(roomprofile.getPgROOMDETAILS());
                PGdepositamt.getEditText().setText(roomprofile.getPgROOMDEPOSIT());


                PGfullamt.getEditText().setText(roomprofile.getPgROOMAMOUNT());



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdateRoomDetails.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();

            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Sroomtype = roomtypes.getSelectedItem().toString();

                Sroomno = PGroomno.getEditText().getText().toString();
                Sroomdetails = PGroomdetails.getEditText().getText().toString();
                Sroomdepositamt = PGdepositamt.getEditText().getText().toString();

                Sroomfullamt = PGfullamt.getEditText().getText().toString();


                    if(Sroomtype == "Choose Room Type")
                    {
                        ROOMTYPE.setError("Select PG Type");
                        Toast.makeText(UpdateRoomDetails.this, "Select Room Type", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        // RoomProfile roomProfile = new RoomProfile();
                        // roomprofile = new RoomProfile(Sroomtype,Sroomno,Sroomdetails,Sroomdepositamt,Sroomfullamt,userid);
                        //reference.child(userid).child(Sroomno).setValue(roomprofile);
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
                                    String add = pgProfile.pgLOCALADDRESS;
                                    String mob = pgProfile.pgMOBILENO;

                                    if(Sroomtype == "Single")
                                        cnt = 1;
                                    if(Sroomtype == "Double")
                                        cnt = 2;
                                    if(Sroomtype == "Tripple")
                                        cnt = 3;

                                    roomprofile = new RoomProfile(Sroomtype,Sroomno,Sroomdetails,Sroomdepositamt,Sroomfullamt,userid,name,city,add,mob,cnt);
                                    reference.child(userid).child(Sroomno).setValue(roomprofile);

                                    DatabaseReference myRef3 = FirebaseDatabase.getInstance().getReference().child("For Users").child("PG Rooms").child(name).child(Sroomno);

                                    myRef3.setValue(roomprofile);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        //-------------------------------------------------------------------------

                        Intent intent = new Intent(getApplicationContext(),DisplayRoomActivity.class);



                        intent.putExtra("message_key", Sroomno);


                        startActivity(intent);
                        //startActivity(new Intent(UpdateRoomDetails.this, DisplayRoomActivity.class));
                    }




            }
        });


    }

}