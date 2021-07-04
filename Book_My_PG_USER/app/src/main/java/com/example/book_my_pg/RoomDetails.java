package com.example.book_my_pg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.SliderView;


import java.util.ArrayList;


public class RoomDetails extends AppCompatActivity {

    String url1 = "https://img.freepik.com/vrije-vector/cartoon-hostel-interieur-met-een-bed-vectorillustratie_264840-58.jpg?size=626&ext=jpg";
    String url2 = "https://www.penccil.com/files/table/U_36_409200089248_MBvisite.jpg";
    String url3 = "https://media-cdn.tripadvisor.com/media/photo-s/04/0a/0e/3a/bally-s-atlantic-city.jpg";

    private DatabaseReference reference;
    private Button booking;
    private FirebaseUser user;
    private String uid,city,pgadd,pgph;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);

        Intent i=getIntent();
        String str1=i.getStringExtra("str1");
        String str2=i.getStringExtra("str2");

        reference= FirebaseDatabase.getInstance().getReference("For Users").child("PG Rooms").child(str1).child(str2);
        final TextView Roomno=(TextView)findViewById(R.id.pgRoomNo);
        final TextView Roomtype=(TextView)findViewById(R.id.pgRoomType);
        final TextView Roomdet=(TextView)findViewById(R.id.pgRoomDet);
        final TextView Roomdepo=(TextView)findViewById(R.id.pgRoomDepo);
        final TextView Roomamt=(TextView)findViewById(R.id.pgRoomAmt);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RoomProfile profile = snapshot.getValue(RoomProfile.class);
                if (profile != null) {
                    String no=profile.pgROOMNO;
                    String type=profile.pgROOMTYPE;
                    String det=profile.pgROOMDETAILS;
                    String depo=profile.pgROOMDEPOSIT;
                    String amt=profile.pgROOMAMOUNT;
                    uid=profile.pgID;
                    city=profile.pgCity;
                    pgadd=profile.pgAddress;
                    pgph=profile.pgMobno;
                    count=profile.count;

                    Roomno.setText(no);
                    Roomtype.setText(type);
                    Roomdet.setText(det);
                    Roomdepo.setText(depo);
                    Roomamt.setText(amt);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RoomDetails.this, "Error while loading", Toast.LENGTH_LONG).show();
            }
        });

        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
        SliderView sliderView = findViewById(R.id.slider);

        sliderDataArrayList.add(new SliderData(url1));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));
        SliderAdapter adapter = new SliderAdapter(getApplicationContext(), sliderDataArrayList);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(adapter);
        sliderView.setScrollTimeInSec(2);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        booking = findViewById(R.id.book);
        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid;
                user = FirebaseAuth.getInstance().getCurrentUser();
                userid =user.getUid();
                DatabaseReference referencetemp = FirebaseDatabase.getInstance().getReference().child("User Details").child(userid);
                referencetemp.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        profile1 det = snapshot.getValue(profile1.class);
                        if (det != null) {
                            String uname = det.userFName;
                            String phno = det.userMobileno;
                            String age = det.Age;
                            String add = det.userLocalAddress;

                            DatabaseReference myref3,myref4;
                            myref3 = FirebaseDatabase.getInstance().getReference("Booked Users").child(uid).child(str2);
                            myref4 = FirebaseDatabase.getInstance().getReference("My Booking").child(userid).child(str2);
                            userDetails u = new userDetails(str2, uname, phno, age, add, userid.toString(), city,str1,pgadd,pgph);
                            myref3.setValue(u);
                            myref4.setValue(u);
                                reference = FirebaseDatabase.getInstance().getReference("Room Details");
                                reference.child(uid).child(str2).removeValue();
                                reference = FirebaseDatabase.getInstance().getReference("For Users");
                                reference.child("PG Rooms").child(str1).child(str2).removeValue();
                            Intent intent= new Intent(RoomDetails.this, MainPage.class);
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}