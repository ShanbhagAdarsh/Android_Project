package com.example.pgapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchRoom extends AppCompatActivity {

    private TextInputLayout PGROOMNUMBER;
    private Button searchbtn;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference reference;
    private String userid;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_room);




        searchbtn = (Button)findViewById(R.id.search);
        PGROOMNUMBER = (TextInputLayout)findViewById(R.id.searchroomno);
        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Room Details");
        userid =user.getUid();

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = PGROOMNUMBER.getEditText().getText().toString();


                Intent intent = new Intent(getApplicationContext(), DisplayRoomActivity.class);

                intent.putExtra("message_key", str);


                startActivity(intent);
            }
        });
    }
}
