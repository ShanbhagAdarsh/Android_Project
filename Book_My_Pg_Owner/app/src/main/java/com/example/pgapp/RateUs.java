package com.example.pgapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static java.lang.String.valueOf;

public class RateUs extends AppCompatActivity {

    RatingBar rt;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_us);
        setContentView(R.layout.activity_rate_us);

        user = FirebaseAuth.getInstance().getCurrentUser();

        userid =user.getUid();

        firebaseAuth = FirebaseAuth.getInstance();

        rt = (RatingBar) findViewById(R.id.ratingBar);

        //finding the specific RatingBar with its unique ID
        LayerDrawable stars=(LayerDrawable)rt.getProgressDrawable();

        //Use for changing the color of RatingBar
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
    }

    public void Call(View v)
    {
        TextView t = (TextView)findViewById(R.id.textView2);
        String temp = valueOf(rt.getRating());
        t.setText("You have Ratted "+temp+" Thank You!");
        sendUserData(temp);
    }

   private void sendUserData(String temp){

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("App Ratting").child(userid);
        UserProfile userProfile = new UserProfile(temp);
        myRef.setValue(userProfile);

    }
}
