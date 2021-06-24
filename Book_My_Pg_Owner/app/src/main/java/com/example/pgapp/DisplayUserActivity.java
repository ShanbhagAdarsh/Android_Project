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


public class DisplayUserActivity extends AppCompatActivity {

    private TextInputLayout ownerroomtype,ownerroomno,ownerroomdetails,ownerrommdeposit,ownerroomamount;
    private Button roomedit;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private TextView name,mobno,roomno,age,address;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user);

        Intent intent = getIntent();

        final String ROOMNUMBER = intent.getStringExtra("message_key");

        name = (TextView)findViewById(R.id.displaybookedusername);
        mobno = (TextView)findViewById(R.id.displaybookedusermobno);
        roomno = (TextView)findViewById(R.id.displaybookeduserroomno);
        age = (TextView)findViewById(R.id.displaybookeduserage);
        address = (TextView)findViewById(R.id.displaybookeduseraddress);

        roomedit = (Button)findViewById(R.id.Editroombutton);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Booked Users");
        userid =user.getUid();


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        reference.child(userid).child(ROOMNUMBER).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //UserProfile userProfile = snapshot.getValue(UserProfile.class);
                user roomProfile = snapshot.getValue(user.class);

                if(roomProfile != null){
                    String sname = roomProfile.uname;
                    String smobno = roomProfile.phoneno;
                    String sroomno = roomProfile.roomno;
                    String sage = roomProfile.age;
                    String sadd = roomProfile.addresss;

                    name.setText(sname);
                    mobno.setText(smobno);
                    roomno.setText(sroomno);
                    age.setText(sage);
                    address.setText(sadd);

                }
                else
                    startActivity(new Intent(DisplayUserActivity.this,SearchRoom.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void Logout()
    {
        firebaseAuth.signOut();
        // finish();
        startActivity(new Intent(DisplayUserActivity.this,MainActivity.class));
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
                startActivity(new Intent(DisplayUserActivity.this,ProfileActivity.class));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}