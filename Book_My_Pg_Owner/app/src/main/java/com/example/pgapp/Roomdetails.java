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


public class Roomdetails extends AppCompatActivity {

    private TextInputLayout ownerroomtype,ownerroomno,ownerroomdetails,ownerrommdeposit,ownerroomamount;
    private Button roomedit;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private TextView ROOMTYPE,ROOMNO,ROOMDETAILS,ROOMDEPOSIT,ROOMAMOUNT;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomdetails);


        Intent intent = getIntent();

        final String ROOMNUMBER = intent.getStringExtra("message_key");

        ROOMTYPE = (TextView)findViewById(R.id.pgroomtype);
        ROOMNO = (TextView)findViewById(R.id.pgroomno);
        ROOMDEPOSIT = (TextView)findViewById(R.id.pgdepositamount);
        ROOMDETAILS = (TextView)findViewById(R.id.pgroomdetails);
        ROOMAMOUNT = (TextView)findViewById(R.id.pgtotalamount);


       // roomedit = (Button)findViewById(R.id.Editroombutton);






        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Room Details");
        userid =user.getUid();


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());


        reference.child(userid).child(ROOMNUMBER).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //UserProfile userProfile = snapshot.getValue(UserProfile.class);
                RoomProfile roomProfile = snapshot.getValue(RoomProfile.class);

                if(roomProfile != null){
                    String RTYPE = roomProfile.pgROOMTYPE;
                    String RNO = roomProfile.pgROOMNO;
                    String RDETAILS = roomProfile.pgROOMDETAILS;
                    String RD = roomProfile.pgROOMDEPOSIT;
                    String RA = roomProfile.pgROOMAMOUNT;


                    ROOMTYPE.setText(RTYPE);
                    ROOMNO.setText(RNO);
                    ROOMDETAILS.setText(RDETAILS);
                    ROOMDEPOSIT.setText(RD);
                    ROOMAMOUNT.setText(RA);

                }
                else
                    startActivity(new Intent(Roomdetails.this,SearchRoom.class));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    private void Logout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(Roomdetails.this,MainActivity.class));

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
                startActivity(new Intent(Roomdetails.this,ProfileActivity.class));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}