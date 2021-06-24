package com.example.pgapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.Query;

public class MainRecycleview extends AppCompatActivity {

    private RecyclerView recyclerView;


    Roomadapter adapter; // Create Object of the Adapter class

    //DatabaseReference mbase; // Create object of the

    private FirebaseUser user;
    private DatabaseReference mbase;
    private String userid;
    private FirebaseAuth firebaseAuth;

    // Firebase Realtime Database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycleview);

        firebaseAuth = FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        mbase = FirebaseDatabase.getInstance().getReference("Room Details");
        userid =user.getUid();

        recyclerView = findViewById(R.id.recycler1);

        //----------------------
        recyclerView.setHasFixedSize(true);
        //------------------------

        // To display the Recycler view linearly

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // It is a class provide by the FirebaseUI to make a

        // query in the database to fetch appropriate data

        FirebaseRecyclerOptions<RoomProfile> options

                = new FirebaseRecyclerOptions.Builder<RoomProfile>()

                .setQuery(mbase.child(userid), RoomProfile.class)

                .build();

        // Connecting object of required Adapter class to

        // the Adapter class itself

        adapter = new Roomadapter(options);

        // Connecting Adapter class with the Recycler view*/

        recyclerView.setAdapter(adapter);

    }



    // Function to tell the app to start getting

    // data from database on starting of the activity

    @Override protected void onStart()

    {

        super.onStart();

        adapter.startListening();

    }



    // Function to tell the app to stop getting

    // data from database on stoping of the activity

    @Override protected void onStop()

    {

        super.onStop();

        adapter.stopListening();

    }
}