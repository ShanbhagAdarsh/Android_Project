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

public class ProfileActivity extends AppCompatActivity {
    private ImageView profilepic;
  //  private TextInputLayout userfname, userEmail,userlname,usermobileno,userlocaladdress,userarea,userpincode,userstate,usercity;
    private Button profileedit,passwordedit;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private TextView FULLNAME,MAIL,MOBILE,AREA,STATE,CITY,PINCODE,LOCALADDRESS;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FULLNAME = (TextView)findViewById(R.id.name);
        MAIL = (TextView)findViewById(R.id.mailid);
        MOBILE = (TextView)findViewById(R.id.mobilenum);
        LOCALADDRESS = (TextView)findViewById(R.id.addresss);
        AREA = (TextView)findViewById(R.id.area);
        PINCODE = (TextView)findViewById(R.id.pincodes);
        CITY = (TextView)findViewById(R.id.cities);
        STATE = (TextView)findViewById(R.id.statess);
        profileedit = (Button)findViewById(R.id.Editprofilebutton);
        profilepic = (ImageView)findViewById(R.id.ProfilePic);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Owner Profile");
        userid =user.getUid();


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("Profile Pic").child(firebaseAuth.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(profilepic);
            }
        });

        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);

                if(userProfile != null)
                {
                    String Fname = userProfile.userFName;
                    String Lname = userProfile.userLName;
                    String mail = userProfile.userEmail;
                    String mob = userProfile.userMobileno;
                    String add = userProfile.userLocalAddress;
                    String pin = userProfile.userPINCODE;
                    String are = userProfile.userAREA;
                    String sta = userProfile.userSTATE;
                    String cit = userProfile.userCITY;

                    FULLNAME.setText(Fname+" "+Lname);
                    MAIL.setText(mail);
                    MOBILE.setText(mob);
                    LOCALADDRESS.setText(add);
                    PINCODE.setText(pin);
                    AREA.setText(are);
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
                startActivity(new Intent(ProfileActivity.this,UpdateProfile.class));

            }
        });

    }
    private void Logout()
    {
        firebaseAuth.signOut();
        // finish();
        startActivity(new Intent(ProfileActivity.this,MainActivity.class));

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
            case R.id.log: {
                Logout();
                break;
            }

            case R.id.acc:{
                startActivity(new Intent(ProfileActivity.this,ProfileActivity.class));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}