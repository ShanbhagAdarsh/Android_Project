package com.example.book_my_pg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        BottomNavigationView btn= findViewById(R.id.bottomNavigationView);
        btn.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Fragment()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener= new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment=null;

                    switch (item.getItemId()){
                        case R.id.fragment1:
                            selectedFragment=new MainFragment();
                            break;

                        case R.id.fragment2:
                            selectedFragment=new ProfileFragment();
                            break;

                        case R.id.fragment3:
                            selectedFragment=new SettingsFragment();
                            break;

                        case R.id.fragment4:
                            selectedFragment=new NotifyFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment, selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public void onBackPressed() {
       Intent a=new Intent(Intent.ACTION_MAIN);
       a.addCategory(Intent.CATEGORY_HOME);
       a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(a);
    }
}