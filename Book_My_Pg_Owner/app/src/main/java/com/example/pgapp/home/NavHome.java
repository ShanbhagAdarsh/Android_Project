package com.example.pgapp.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pgapp.DeleteRoom;
import com.example.pgapp.PGprofile;
import com.example.pgapp.R;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.pgapp.R;
import com.example.pgapp.RoomActivity;
import com.example.pgapp.SliderAdapter;
import com.example.pgapp.SliderData;
import com.example.pgapp.UpdatePGdetails;
import com.example.pgapp.UploadImages;
import com.example.pgapp.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.smarteist.autoimageslider.DefaultSliderView;
//import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

//import com.smarteist.autoimageslider.SliderLayout;

public class NavHome extends Fragment implements View.OnClickListener {

    //private SliderLayout sliderLayout;
    private TextView fullname;
    ImageView gallary,pg,delete,rules;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userid;

    //String url1 = "https://lh3.googleusercontent.com/proxy/mN53dGMZEwD7YaXLLeMOHbHJtsvFJTR9jyH3LP4XauEkV-j5E2qMbemmD7aZDnwHsSrIhNDup9XJhQ4R7stXwDhGNhZGaNMtSasi6goZ3Va7h-5RuCw";

    String url1 = "https://www.roomsoom.com/blog/wp-content/uploads/2019/12/pg2.jpg";
    String url2 = "https://cdn.hotelmanagement.com.au/wp-content/uploads/2019/05/31112831/Quest-story.jpg";
    String url4 = "https://www.businesstravel.com/hubfs/Blog%20Art/BT_Blog_7HotelTipsForBusinessTravelers.jpg";
    String url3 = "https://cdn.travelpulse.com/images/99999999-9999-9999-9999-999999999999/753b205e-0cc1-57fe-ab59-5e3dddaaa318/630x355.jpg";

    //adds
    String url5 = "data:image/jpeg;base64,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";
    String url6 = "https://www.joile.in/upload/DominosBanner1.jpg";
    // String url3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRg3YotuIh3n2W9p-frTfR0T6v7v5c0OpaQSiCEZP-wUHz4FQgRJE9FrK-mO8kzNrKehQ&usqp=CAU";
    String url7 = "https://www.dominos.co.in/theme2/front/images/voucherimages/giftcardbanner.png";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_home, container, false);

        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
        ArrayList<SliderData> sliderDataArrayListadd = new ArrayList<>();

        SliderView sliderView = view.findViewById(R.id.slider);

        SliderView sliderViewadd = view.findViewById(R.id.add_slider);

        sliderDataArrayList.add(new SliderData(url1));

        sliderDataArrayList.add(new SliderData(url2));

        sliderDataArrayList.add(new SliderData(url4));

        sliderDataArrayList.add(new SliderData(url3));

        //add
        sliderDataArrayListadd.add(new SliderData(url5));

        sliderDataArrayListadd.add(new SliderData(url6));

        sliderDataArrayListadd.add(new SliderData(url7));

       // sliderDataArrayList.add(new SliderData(url3));



        //home
        SliderAdapter adapter = new SliderAdapter(getContext(), sliderDataArrayList);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(adapter);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        //add
        SliderAdapter adapteradd = new SliderAdapter(getContext(), sliderDataArrayListadd);
        sliderViewadd.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderViewadd.setSliderAdapter(adapteradd);
        sliderViewadd.setScrollTimeInSec(5);
        sliderViewadd.setAutoCycle(true);
        sliderViewadd.startAutoCycle();

        pg = (ImageView) view.findViewById(R.id.home_pg);
        pg.setOnClickListener((View.OnClickListener) this);
        gallary = (ImageView) view.findViewById(R.id.home_gallary);
        gallary.setOnClickListener((View.OnClickListener) this);

        delete = (ImageView) view.findViewById(R.id.home_room_delete);
        delete.setOnClickListener((View.OnClickListener) this);
        rules = (ImageView) view.findViewById(R.id.home_room_delete);
        rules.setOnClickListener((View.OnClickListener) this);

        fullname = (TextView) view.findViewById(R.id.Ownerfullname);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Owner Profile");
        userid =user.getUid();

        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //UserProfile userProfile = snapshot.getValue(UserProfile.class);

                UserProfile userProfile = snapshot.getValue(UserProfile.class);

                if(userProfile != null){
                    String fname = userProfile.userFName;
                    String lname = userProfile.userLName;
                    fullname.setText("Welcome "+fname+" "+lname);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return  view;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.home_pg:
                Intent intent = new Intent(getContext(), RoomActivity.class);
                startActivity(intent);
                break;

            case R.id.home_gallary:
                Intent intents = new Intent(getContext(), UploadImages.class);
                startActivity(intents);
                break;

            case R.id.home_room_delete:
                Intent intentas = new Intent(getContext(), DeleteRoom.class);
                startActivity(intentas);
                break;

        }
    }
}