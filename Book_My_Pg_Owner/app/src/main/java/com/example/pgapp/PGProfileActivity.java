package com.example.pgapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PlayGamesAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class PGProfileActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_g_profile);

        FULLNAME = (TextView)findViewById(R.id.pgownername);
        PGTYPE = (TextView)findViewById(R.id.pgtype);
        PGNAME = (TextView)findViewById(R.id.pgname);
        MAIL = (TextView)findViewById(R.id.pgmailid);
        MOBILE = (TextView)findViewById(R.id.pgmobilenum);
        LOCALADDRESS = (TextView)findViewById(R.id.pgaddresss);
        PINCODE = (TextView)findViewById(R.id.pgpincodes);
        CITY = (TextView)findViewById(R.id.pgcities);
        STATE = (TextView)findViewById(R.id.pgstatess);

        profileedit = (Button)findViewById(R.id.Editprofilebutton);



        profilepic = (ImageView)findViewById(R.id.ProfilePic);


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

        profileedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PGProfileActivity.this,UpdatePGdetails.class));

            }
        });




    }
    private void Logout()
    {
        firebaseAuth.signOut();
        // finish();
        startActivity(new Intent(PGProfileActivity.this,MainActivity.class));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.log:{
                Logout();
                break;
            }

            case R.id.acc:{
                startActivity(new Intent(PGProfileActivity.this,ProfileActivity.class));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}