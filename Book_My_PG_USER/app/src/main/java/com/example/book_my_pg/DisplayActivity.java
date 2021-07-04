package com.example.book_my_pg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class DisplayActivity extends AppCompatActivity {

    private Button pics,avail;
    private DatabaseReference reference;
    private ImageView imageView;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Intent i= getIntent();

            String pgCity=i.getStringExtra("str1");
            String pgName=i.getStringExtra("str2");

            reference= FirebaseDatabase.getInstance().getReference("For Users").child(pgCity).child(pgName);

            final TextView Pname=(TextView)findViewById(R.id.pgName);
            final TextView Oname=(TextView)findViewById(R.id.pgOname);
            final TextView Phno=(TextView)findViewById(R.id.pgPhno);
            final TextView Ptype=(TextView)findViewById(R.id.pgType);
            final TextView Padd=(TextView)findViewById(R.id.pgAdd);
            final TextView Pcity=(TextView)findViewById(R.id.pgCity);
            imageView =findViewById(R.id.imageView5);

        storage= FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        storageReference.child("For Users").child(pgName).child("PG Profile").child("PG Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(imageView);
            }
        });

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    PGprofile profile = snapshot.getValue(PGprofile.class);
                    if (profile != null) {
                        String pname=profile.pgNAME;
                        String oname=profile.pgOWNERNAME;
                        String ph=profile.pgMOBILENO;
                        String type=profile.pgTYPE;
                        String add=profile.pgLOCALADDRESS;
                        String city=profile.pgCITY;

                        Pname.setText(pname);
                        Oname.setText(oname);
                        Phno.setText(ph);
                        Ptype.setText(type);
                        Padd.setText(add);
                        Pcity.setText(city);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(DisplayActivity.this, "Error while loading", Toast.LENGTH_LONG).show();
                }
            });

            pics=(Button)findViewById(R.id.pgPic);
            pics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DisplayActivity.this, ShowAllImagesFromStorage.class);
                    intent.putExtra("message_key", pgName);
                    startActivity(intent);
                }
            });

            avail=(Button)findViewById(R.id.pgAvail);
            avail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DisplayActivity.this, roomRecycle.class);
                    intent.putExtra("message_key", pgName);
                    startActivity(intent);
                }
            });
    }
}