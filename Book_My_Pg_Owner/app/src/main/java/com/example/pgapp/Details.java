package com.example.pgapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.IOException;

public class Details extends AppCompatActivity {

    private TextInputLayout userfname, userPassword,ConfirmPassword, userEmail,userlname,usermobileno,userlocaladdress,userarea,userpincode,userstate,usercity;
    private Button regButton,selectpic;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
    String userFname,userpassword,userconfirmpassword,useremail,userLname,userMobileno,userLocalAddress,userAREA,userPINCODE,userSTATE,userCITY;
    private FirebaseStorage firebaseStorage;
    private ImageView profilepic;
    private static final int PICK_IMAGE = 123;
    Uri imagePath;
    private StorageReference storageReference;
   // private DatabaseReference reference;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setupUIVViews();

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Owner Profile");
        userid =user.getUid();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage =FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();

        selectpic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent();
                intent.setType("image/*");  //application
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE);
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    sendUserData();
                    //startActivity(new Intent(Details.this,SendOTPActivity.class));
                    startActivity(new Intent(Details.this,PGdetails.class));
                }
                else
                {
                    Toast.makeText(Details.this,"Enter all the Details correctly",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    //end of oncreate
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE &&  resultCode == RESULT_OK){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                profilepic.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setupUIVViews()
    {
        userfname = (TextInputLayout) findViewById(R.id.userEMAIL);
        userlname = (TextInputLayout) findViewById(R.id.userlname);
        userEmail = (TextInputLayout) findViewById(R.id.userEmail);

        regButton = (Button) findViewById(R.id.registerbutton);

        usermobileno = (TextInputLayout) findViewById(R.id.usermobileno);
        userlocaladdress = (TextInputLayout) findViewById(R.id.userLocaladdress);
        userarea = (TextInputLayout) findViewById(R.id.userArea);
        userpincode = (TextInputLayout) findViewById(R.id.userPincode);
        userstate = (TextInputLayout) findViewById(R.id.userState);
        usercity = (TextInputLayout) findViewById(R.id.userCity);

        profilepic =(ImageView)findViewById(R.id.picSelect);
        selectpic = (Button)findViewById(R.id.selectimgbtn);
    }

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private Boolean validate()
    {
        Boolean result = true;
        userFname = userfname.getEditText().getText().toString();
        userLname = userlname.getEditText().getText().toString();
        useremail = userEmail.getEditText().getText().toString();
        userMobileno = usermobileno.getEditText().getText().toString();
        userLocalAddress = userlocaladdress.getEditText().getText().toString();
        userAREA = userarea.getEditText().getText().toString();
        userPINCODE = userpincode.getEditText().getText().toString();
        userCITY = usercity.getEditText().getText().toString();
        userSTATE = userstate.getEditText().getText().toString();

            if (imagePath == null) {
                profilepic.findFocus();
            } else {
                result = true;
            }

            if (TextUtils.isEmpty(userFname)) {
                userfname.setErrorEnabled(true);
                userfname.setError("Enter First Name");
                return false;
            } else {
                result = true;
            }
            if (TextUtils.isEmpty(userLname)) {
                userlname.setErrorEnabled(true);
                userlname.setError("Enter Last Name");
                return false;
            } else {
                result = true;
            }
            if (TextUtils.isEmpty(useremail)) {
                userEmail.setErrorEnabled(true);
                userEmail.setError("Email Is Required");
                return false;
            } else {
                if (useremail.matches(emailpattern)) {
                    result = true;
                } else {
                    userEmail.setErrorEnabled(true);
                    userEmail.setError("Enter a Valid Email Id");
                    return false;
                }
            }

            if (TextUtils.isEmpty(userMobileno)) {
                usermobileno.setErrorEnabled(true);
                usermobileno.setError("Mobile Number Is Required");
                return false;
            } else {
                if (userMobileno.length() < 10) {
                    usermobileno.setErrorEnabled(true);
                    usermobileno.setError("Invalid Mobile Number");
                    return false;
                } else {
                    result = true;
                }
            }
            if (TextUtils.isEmpty(userLocalAddress)) {
                userlocaladdress.setErrorEnabled(true);
                userlocaladdress.setError("Address Is Required");
                return false;
            } else {
                result = true;
            }
            if (TextUtils.isEmpty(userPINCODE)) {
                userpincode.setErrorEnabled(true);
                userpincode.setError("Please Enter Pincode");
                return false;
            } else {
                result = true;
            }
            if (TextUtils.isEmpty(userAREA)) {
                userarea.setErrorEnabled(true);
                userarea.setError("Please Enter Area");
                return false;
            } else {
                result = true;
            }

            if (TextUtils.isEmpty(userSTATE)) {
                userstate.setErrorEnabled(true);
                userstate.setError("Please Enter State");
                return  false;
            } else {
                result = true;
            }

            if (TextUtils.isEmpty(userCITY)) {
                usercity.setErrorEnabled(true);
                usercity.setError("Please Enter City");
                return false;
            } else {
                result = true;
            }
        return result;
    }

    private void sendUserData(){

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Owner Profile").child(userid);

        StorageReference imageReference = storageReference.child("Profile Pic").child(userid);

        UploadTask uploadTask = imageReference.putFile(imagePath);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Details.this,"Upload Failed",Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Details.this,"Upload Successful",Toast.LENGTH_SHORT).show();
            }
        });
        UserProfile userProfile = new UserProfile(userFname,userLname,useremail,userMobileno,userLocalAddress,userAREA,userPINCODE,userCITY,userSTATE);
        myRef.setValue(userProfile);

    }
}
