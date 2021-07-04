package com.example.book_my_pg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class roomRecycle extends AppCompatActivity {

    private RecyclerView recyclerView;

    roomAdapter adapter; // Create Object of the Adapter class
    private DatabaseReference mbase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_recycle);

        Intent intent = getIntent();
        final String PgName = intent.getStringExtra("message_key");

        mbase = FirebaseDatabase.getInstance().getReference("For Users").child("PG Rooms").child(PgName);

        recyclerView = findViewById(R.id.recycler1);
        recyclerView.setHasFixedSize(true);

        // To display the Recycler view linearly
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data

        FirebaseRecyclerOptions<RoomProfile> options
                = new FirebaseRecyclerOptions.Builder<RoomProfile>()
                .setQuery(mbase, RoomProfile .class)
                .build();

        // Connecting object of required Adapter class to the Adapter class itself\
        adapter = new roomAdapter(options);
        // Connecting Adapter class with the Recycler view*/

        DatabaseReference referencetemp = FirebaseDatabase.getInstance().getReference().child("For Users").child("PG Rooms").child(PgName);

        referencetemp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //UserProfile userProfile = snapshot.getValue(UserProfile.class);
                RoomProfile pgProfile = snapshot.getValue(RoomProfile.class);

                if(pgProfile == null){

                    Toast.makeText(roomRecycle.this,"Room unavailable",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView.setAdapter(adapter);
    }

    // Function to tell the app to start getting
    // data from database on starting of the activity

    @Override protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}