package com.example.pgapp.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.pgapp.PGprofile;
import com.example.pgapp.R;
import com.example.pgapp.UpdatePGdetails;
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
import com.squareup.picasso.Picasso;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Nav_Pg extends Fragment implements View.OnClickListener {

    private ImageView profilepic;
    private TextInputLayout userfname, userEmail,usermobileno,userlocaladdress,userpgname,userpgtype,userpincode,userstate,usercity;
    private Button profileedit;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private TextView FULLNAME,MAIL,MOBILE,AREA,STATE,CITY,PINCODE,LOCALADDRESS,PGTYPE,PGNAME;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userid;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_nav_pg, container, false);

       // profileedit = (Button)view.findViewById(R.id.Editprofilebutton);
        // profileedit.setOnClickListener((View.OnClickListener) this);

        FULLNAME = (TextView)view.findViewById(R.id.pgownername);
        PGTYPE = (TextView)view.findViewById(R.id.pgtype);
        PGNAME = (TextView)view.findViewById(R.id.pgname);
        MAIL = (TextView)view.findViewById(R.id.pgmailid);
        MOBILE = (TextView)view.findViewById(R.id.pgmobilenum);
        LOCALADDRESS = (TextView)view.findViewById(R.id.pgaddresss);
        PINCODE = (TextView)view.findViewById(R.id.pgpincodes);
        CITY = (TextView)view.findViewById(R.id.pgcities);
        STATE = (TextView)view.findViewById(R.id.pgstatess);

        profileedit = (Button)view.findViewById(R.id.Editprofilebutton);



        profilepic = (ImageView)view.findViewById(R.id.ProfilePic);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("PG Details");
        userid =user.getUid();


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());


        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("PG Profile Pic").child(firebaseAuth.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(profilepic);

            }
        });

        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //UserProfile userProfile = snapshot.getValue(UserProfile.class);
                PGprofile pgProfile = snapshot.getValue(PGprofile.class);

                if(pgProfile != null){
                    String Oname = pgProfile.pgOWNERNAME;
                    String pname = pgProfile.pgNAME;
                    String ptype = pgProfile.pgTYPE;
                    String mail = pgProfile.pgMAIL;
                    String mob = pgProfile.pgMOBILENO;
                    String add = pgProfile.pgLOCALADDRESS;
                    String pin = pgProfile.pgPINCODE;
                    String sta = pgProfile.pgSTATE;
                    String cit = pgProfile.pgCITY;

                    FULLNAME.setText(Oname);
                    PGNAME.setText(pname);
                    PGTYPE.setText(ptype);
                    MAIL.setText(mail);
                    MOBILE.setText(mob);
                    LOCALADDRESS.setText(add);
                    PINCODE.setText(pin);
                    STATE.setText(sta);
                    CITY.setText(cit);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }

    @Override
    public void onClick(View v) {



    }
}