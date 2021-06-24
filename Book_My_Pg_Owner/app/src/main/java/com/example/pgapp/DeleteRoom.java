package com.example.pgapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteRoom extends AppCompatActivity {

    private TextInputLayout Roomno;
    private Button delete;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference reference;
    private String userid;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_room);

        delete = (Button)findViewById(R.id.search);
        Roomno = (TextInputLayout)findViewById(R.id.searchroomno);

        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Room Details");
        userid =user.getUid();


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str = Roomno.getEditText().getText().toString();

                reference.child(userid).child(str).removeValue();
                startActivity(new Intent(DeleteRoom.this,MainRecycleview.class));
            }
        });
    }
}