package com.example.book_my_pg;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Button sub;
    private ImageView imageView;
    FirebaseStorage storage;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState){
            // Inflate the layout for this fragment
            View v= inflater.inflate(R.layout.fragment_profile, container, false);
        sub = (Button)v.findViewById(R.id.editbutton);
        sub.setOnClickListener((View.OnClickListener) this);

        DatabaseReference reference1;

        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("User Details");
        userID= user.getUid();

        final TextView fname=(TextView)v.findViewById(R.id.dFN);
        final TextView dob=(TextView)v.findViewById(R.id.dDOB);
        final TextView age=(TextView)v.findViewById(R.id.dAge);
        final TextView gen=(TextView)v.findViewById(R.id.dGen);
        final TextView phno=(TextView)v.findViewById(R.id.dPhno);
        final TextView occ=(TextView)v.findViewById(R.id.dOcc);
        final TextView add=(TextView)v.findViewById(R.id.dAdd);
        final TextView city=(TextView)v.findViewById(R.id.dCity);

        imageView = v.findViewById(R.id.profilepic);
        storage= FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        storageReference.child("User Pic").child(userID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(imageView);
            }
        });

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profile1 userProfile = snapshot.getValue(profile1.class);
                if (userProfile != null) {
                    String Name=userProfile.userFName;
                    String Phone_Number=userProfile.userMobileno;
                    String Native_address=userProfile.userLocalAddress;
                    String City=userProfile.userCITY;
                    String Age=userProfile.Age;
                    String Gender=userProfile.Gender;
                    String date=userProfile.DOB;
                    String occu=userProfile.Work;

                    fname.setText(Name);
                    age.setText(Age);
                    phno.setText(Phone_Number);
                    add.setText(Native_address);
                    gen.setText(Gender);
                    dob.setText(date);
                    occ.setText(occu);
                    city.setText(City);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error while loading", Toast.LENGTH_LONG).show();
            }
        });
            return v;
        }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.editbutton:
                Intent intent = new Intent(getContext(), userProfileEdit.class);
                startActivity(intent);
                break;
        }
    }
}

