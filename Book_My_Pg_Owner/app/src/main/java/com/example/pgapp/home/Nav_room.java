package com.example.pgapp.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import com.example.pgapp.DisplayRoomActivity;
import com.example.pgapp.ProfileActivity;
import com.example.pgapp.R;

import android.os.Bundle;

import com.example.pgapp.R;
import com.example.pgapp.RateUs;
import com.example.pgapp.RoomProfile;
import com.example.pgapp.SearchRoom;
import com.example.pgapp.UpdatePGdetails;
import com.example.pgapp.UpdateProfile;
import com.example.pgapp.UserProfile;
import com.example.pgapp.personAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import static java.lang.String.valueOf;


public class Nav_room extends Fragment implements View.OnClickListener{


    private RecyclerView recyclerView;
    private FirebaseUser user;
    private DatabaseReference mbase;
    private String userid;
    private FirebaseAuth firebaseAuth;
    personAdapter adapter;

   View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_nav_room, container, false);
        view = inflater.inflate(R.layout.fragment_nav_room, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        mbase = FirebaseDatabase.getInstance().getReference("Room Details");
        userid =user.getUid();

        recyclerView = view.findViewById(R.id.recycler1);

        //----------------------
        recyclerView.setHasFixedSize(true);
        //------------------------

        // To display the Recycler view linearly

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        // It is a class provide by the FirebaseUI to make a

        // query in the database to fetch appropriate data

        FirebaseRecyclerOptions<RoomProfile> options

                = new FirebaseRecyclerOptions.Builder<RoomProfile>()

                .setQuery(mbase.child(userid), RoomProfile.class)

                .build();

        // Connecting object of required Adapter class to

        // the Adapter class itself

        adapter = new personAdapter(options);

        // Connecting Adapter class with the Recycler view*/

        recyclerView.setAdapter(adapter);


        return view;

    }

    @Override
    public void onStart()

    {

        super.onStart();

        adapter.startListening();

    }


    // Function to tell the app to stop getting
    // data from database on stoping of the activity

    @Override
    public void onStop()

    {

        super.onStop();

        adapter.stopListening();

    }


    @Override
    public void onClick(View v) {



    }
}