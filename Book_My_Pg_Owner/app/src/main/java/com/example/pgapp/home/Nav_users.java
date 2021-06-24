package com.example.pgapp.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import com.example.pgapp.DisplayRoomActivity;
import com.example.pgapp.LogoutActivity;
import com.example.pgapp.PGprofile;
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
import com.example.pgapp.user;
import com.example.pgapp.userAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;


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
import android.widget.Toast;

import static java.lang.String.valueOf;


public class Nav_users extends Fragment implements View.OnClickListener{


    private RecyclerView recyclerView;
    private FirebaseUser user;
    private DatabaseReference mbase;
    private String userid;
    private FirebaseAuth firebaseAuth;
    userAdapter adapter;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_nav_users, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        mbase = FirebaseDatabase.getInstance().getReference("Booked Users");

        userid =user.getUid();

        recyclerView = view.findViewById(R.id.recycler2);



        //----------------------
        recyclerView.setHasFixedSize(true);
        //------------------------

        // To display the Recycler view linearly

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        FirebaseRecyclerOptions<user> options
                = new FirebaseRecyclerOptions.Builder<com.example.pgapp.user>()

                .setQuery(mbase.child(userid), user.class)

                .build();



        adapter = new userAdapter(options);

        // Connecting Adapter class with the Recycler view*/
        //----------------------------------------------------------------------------

        DatabaseReference referencetemp = FirebaseDatabase.getInstance().getReference().child("Booked Users").child(userid);

        referencetemp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //UserProfile userProfile = snapshot.getValue(UserProfile.class);
                user pgProfile = snapshot.getValue(user.class);

                if(pgProfile == null){

                    Toast.makeText(getContext(),"Not Yet Booked",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //-------------------------------------------------------------------------------

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