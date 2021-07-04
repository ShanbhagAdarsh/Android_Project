package com.example.book_my_pg;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Button log;
    private TextView contact,about;
    private DatabaseReference reference1;
    private FirebaseAuth myAuth;
    private BottomNavigationView bottomNavigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    View v;
    private ImageView imageView;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;
    FirebaseStorage storage;
    StorageReference storageReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_settings, container, false);

        AppCompatActivity act=(AppCompatActivity) getActivity();

        drawerLayout = v.findViewById(R.id.drawerlayout);
        navigationView = v.findViewById(R.id.navigation_view);
        toggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,R.string.start,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        contact = (TextView) v.findViewById(R.id.Contact);
        contact.setOnClickListener(this);
        about = (TextView) v.findViewById(R.id.abt);
        about.setOnClickListener(this);
        log = (Button) v.findViewById(R.id.logoutbut);
        log.setOnClickListener(this);
        imageView = v.findViewById(R.id.profilepic);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference1 = FirebaseDatabase.getInstance().getReference("User Details");
        userID = user.getUid();

        storage=FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        storageReference.child("User Pic").child(userID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(imageView);
            }
        });

                final TextView fname = (TextView) v.findViewById(R.id.sFN);
                final TextView phno = (TextView) v.findViewById(R.id.sPhno);
                final TextView occ = (TextView) v.findViewById(R.id.sOcc);
                final TextView add = (TextView) v.findViewById(R.id.sAdd);

                reference1.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        profile1 userProfile = snapshot.getValue(profile1.class);
                        if (userProfile != null) {
                            String Name = userProfile.userFName;
                            String Phone_Number = userProfile.userMobileno;
                            String Native_address = userProfile.userLocalAddress;
                            String occupation = userProfile.Work;

                            fname.setText(Name);
                            phno.setText(Phone_Number);
                            add.setText(Native_address);
                            occ.setText(occupation);
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

            case R.id.logoutbut:
                myAuth=FirebaseAuth.getInstance();
                if (FirebaseAuth.getInstance().getCurrentUser() != null)
                    myAuth.signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                break;

            case R.id.abt:
                startActivity(new Intent(getContext(), aboutUs.class));
                break;
            case R.id.Contact:
                startActivity(new Intent(getContext(), ContactUsActivity.class));
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return true;
        //return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.home:

                startActivity(new Intent(getContext(),MainPage.class));
                break;

            case R.id.edit_profile:

                startActivity(new Intent(getContext(),userProfileEdit.class));
                break;

            case R.id.rateus:

                startActivity(new Intent(getActivity(),rateUs.class));
                break;

            case R.id.logout:

                startActivity(new Intent(getActivity(),MainActivity.class));
                break;
            case R.id.delacc:

                startActivity(new Intent(getActivity(),delAccActivity.class));
                break;
            case R.id.update:

                startActivity(new Intent(getActivity(),ForgotPassword.class));
                break;
        }
        return true;
    }
}
