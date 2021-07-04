package com.example.book_my_pg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

//import com.smarteist.autoimageslider.DefaultSliderView;
//import com.smarteist.autoimageslider.IndicatorAnimations;
//import com.smarteist.autoimageslider.SliderAnimations;
//import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements View.OnClickListener{

    SearchView searchView;
    ImageView hubli,blr,mys,bel;
    View v;
    String url1 = "https://bruneiclassified.com/wp-content/uploads/2020/07/HOUSE-RENT.jpg";
    String url2 = "https://abancommercials.com/uploadComercial/28569.jpg";
    String url3 = "https://th.bing.com/th/id/OIP.nkDEH42lyIY8BO8HG4jrKgHaEH?pid=ImgDet&rs=1";


   // private SliderLayout sliderLayout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    //variables
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        v=inflater.inflate(R.layout.fragment_main, container, false);

        hubli=v.findViewById(R.id.hubli_pic);
        hubli.setOnClickListener(this);
        blr=v.findViewById(R.id.blr_pic);
        blr.setOnClickListener(this);
        mys=v.findViewById(R.id.mysore_pic);
        mys.setOnClickListener(this);
        bel=v.findViewById(R.id.belgaum_pic);
        bel.setOnClickListener(this);


        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
        SliderView sliderView = v.findViewById(R.id.slider);

        sliderDataArrayList.add(new SliderData(url1));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));
        SliderAdapter adapter = new SliderAdapter(getContext(), sliderDataArrayList);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(adapter);
        sliderView.setScrollTimeInSec(2);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        searchView=(SearchView)v.findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                String str= searchView.getQuery().toString().trim().toUpperCase();
                Toast.makeText(getContext(),str,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MainRecycleview.class);
                intent.putExtra("message_key", str);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return v;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.hubli_pic:
                String str= "Hubli".toUpperCase();
                Toast.makeText(getContext(),str,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MainRecycleview.class);
                intent.putExtra("message_key", str);
                startActivity(intent);
                break;

            case R.id.blr_pic:
                String str1= "Banglore".toUpperCase();
                Toast.makeText(getContext(),"BANGLORE",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getContext(), MainRecycleview.class);
                intent1.putExtra("message_key", str1);
                startActivity(intent1);
                break;

            case R.id.mysore_pic:
                String str2= "Mysore".toUpperCase();
                Toast.makeText(getContext(),str2,Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(getContext(), MainRecycleview.class);
                intent2.putExtra("message_key", str2);
                startActivity(intent2);
                break;

            case R.id.belgaum_pic:
                String str3= "Belgaum".toUpperCase();
                Toast.makeText(getContext(),str3,Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(getContext(), MainRecycleview.class);
                intent3.putExtra("message_key", str3);
                startActivity(intent3);
                break;
        }
    }

}
