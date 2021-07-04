package com.example.book_my_pg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.Calendar;

public class Profile_Activity_new extends AppCompatActivity {

    private TextInputLayout userfname,userEmail,usermobileno,userlocaladdress,userarea,userpincode,userstate,usercity,usercadd,usercarea,usercpincode,usercstate,userccity,occ,age;
    private Button regButton,selectpic;
    private TextView userLogin,dob;
    private FirebaseAuth firebaseAuth;
    String userFname,useremail,userMobileno,userLocalAddress,userAREA,userPINCODE,userSTATE,userCITY,userCurrentAddress,userCAREA,userCPINCODE,userCSTATE,userCCITY,work,AGE,DOB;
    private FirebaseStorage firebaseStorage;
    private ImageView profilepic;
    private static final int PICK_IMAGE = 123;
    Uri imagePath;
    private StorageReference storageReference;
    // private DatabaseReference reference;
    DatePickerDialog.OnDateSetListener setListener;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userid,date,gen;
    private RadioGroup rg;
    private RadioButton gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__new);
        setupUIVViews();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage =FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        user=FirebaseAuth.getInstance().getCurrentUser();
        userid=user.getUid();

        selectpic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent();
                intent.setType("image/*");  //application/ for documents
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE);
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    sendUserData();
                    startActivity(new Intent(Profile_Activity_new.this,MainPage.class));
                }
                else
                {
                    Toast.makeText(Profile_Activity_new.this,"Enter all the Details correctly",Toast.LENGTH_SHORT).show();
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
        userEmail = (TextInputLayout) findViewById(R.id.userEmail);

        regButton = (Button) findViewById(R.id.registerbutton);

        usermobileno = (TextInputLayout) findViewById(R.id.usermobileno);
        userlocaladdress = (TextInputLayout) findViewById(R.id.userLocaladdress);
        userarea = (TextInputLayout) findViewById(R.id.userArea);
        userpincode = (TextInputLayout) findViewById(R.id.userPincode);
        userstate = (TextInputLayout) findViewById(R.id.userState);
        usercity = (TextInputLayout) findViewById(R.id.userCity);
        usercadd = (TextInputLayout) findViewById(R.id.userCurrentaddress);
        usercarea = (TextInputLayout) findViewById(R.id.userCurrentArea);
        usercpincode = (TextInputLayout) findViewById(R.id.userCurrentPincode);
        usercstate = (TextInputLayout) findViewById(R.id.userCurrentState);
        userccity = (TextInputLayout) findViewById(R.id.userCurrentCity);
        occ=(TextInputLayout) findViewById(R.id.userWork);
        age=(TextInputLayout) findViewById(R.id.userAge);
        dob= (TextView)findViewById(R.id.DOB);
        rg = (RadioGroup) findViewById(R.id.mfgen);

        profilepic =(ImageView)findViewById(R.id.picSelect);
        selectpic = (Button)findViewById(R.id.selectimgbtn);

        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(
                        Profile_Activity_new.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , setListener, year, month, day);
                dpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dpd.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                date = day + "/" + month + "/" + year;
                dob.setText(date);
                if(date.isEmpty()){
                    dob.setError("Select date");
                    dob.requestFocus();
                }
            }
        };
    }

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private Boolean validate()
    {
        Boolean result = true;
        userFname = userfname.getEditText().getText().toString();
        useremail = userEmail.getEditText().getText().toString();
        userMobileno = usermobileno.getEditText().getText().toString();
        userLocalAddress = userlocaladdress.getEditText().getText().toString();
        userAREA = userarea.getEditText().getText().toString();
        userPINCODE = userpincode.getEditText().getText().toString();
        userCITY = usercity.getEditText().getText().toString();
        userSTATE = userstate.getEditText().getText().toString();

        userCurrentAddress = usercadd.getEditText().getText().toString();
        userCAREA = usercarea.getEditText().getText().toString();
        userCPINCODE = usercpincode.getEditText().getText().toString();
        userCCITY = userccity.getEditText().getText().toString();
        userCSTATE = usercstate.getEditText().getText().toString();
        work=occ.getEditText().getText().toString();

        AGE=age.getEditText().getText().toString();
        DOB=dob.getText().toString();

        int radio =rg.getCheckedRadioButtonId();
        gender=findViewById(radio);
        gen=gender.getText().toString().trim();

       /* if (imagePath == null) {
            profilepic.findFocus();

        } else {
            result = true;
        }*/

        if (TextUtils.isEmpty(userFname)) {
            userfname.setErrorEnabled(true);
            userfname.setError("Enter First Name");
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

        if (TextUtils.isEmpty(userCurrentAddress)) {
            usercadd.setErrorEnabled(true);
            usercadd.setError("Address Is Required");
            return false;
        } else {
            result = true;
        }
        if (TextUtils.isEmpty(userCPINCODE)) {
            usercpincode.setErrorEnabled(true);
            usercpincode.setError("Please Enter Pincode");
            return false;
        } else {
            result = true;
        }
        if (TextUtils.isEmpty(userCAREA)) {
            usercarea.setErrorEnabled(true);
            usercarea.setError("Please Enter Area");
            return false;
        } else {
            result = true;
        }

        if (TextUtils.isEmpty(userCSTATE)) {
            usercstate.setErrorEnabled(true);
            usercstate.setError("Please Enter State");
            return  false;
        } else {
            result = true;
        }

        if (TextUtils.isEmpty(userCCITY)) {
            userccity.setErrorEnabled(true);
            userccity.setError("Please Enter City");
            return false;
        } else {
            result = true;
        }

        if (TextUtils.isEmpty(work)) {
            occ.setErrorEnabled(true);
            occ.setError("Please Enter City");
            return false;
        } else {
            result = true;
        }

        if (TextUtils.isEmpty(AGE)) {
            age.setErrorEnabled(true);
            age.setError("Please Enter Age");
            return false;
        } else {
            result = true;
        }

        return result;
    }

    private void sendUserData(){

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("User Details").child(userid);

        StorageReference imageReference = storageReference.child("User Pic").child(userid);
        UploadTask uploadTask = imageReference.putFile(imagePath);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile_Activity_new.this,"Upload Failed",Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Profile_Activity_new.this,"Upload Successful",Toast.LENGTH_SHORT).show();
            }
        });
        profile1 userProfile = new profile1(userFname,useremail,userMobileno,DOB,AGE,gen,userLocalAddress,userAREA,userPINCODE,userCITY,userSTATE,userCurrentAddress,userCAREA,userCPINCODE,userCCITY,userCSTATE,work);
        myRef.setValue(userProfile);
    }

    @Override
    public void onBackPressed() {

    }
}