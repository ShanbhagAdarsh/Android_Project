package com.example.pgapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firebaseAuth = FirebaseAuth.getInstance();

        bottomNavigationView = findViewById(R.id.bottomnavigationview);
        navController = Navigation.findNavController(this,R.id.frame_layout);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation_view);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.start,R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);

        NavigationUI.setupWithNavController(bottomNavigationView,navController);

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.owner_profile:
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                break;

            case R.id.pg_profile:
                startActivity(new Intent(MainActivity.this,PGProfileActivity.class));
                break;

            case R.id.room_profile:
                startActivity(new Intent(MainActivity.this,MainRecycleview.class));
                break;

            case R.id.account_setting:
                startActivity(new Intent(MainActivity.this,Setting.class));
                break;

            case R.id.app_contactus:
                startActivity(new Intent(MainActivity.this,ContactUsActivity.class));
                break;

            case R.id.developer_Aboutus:
                startActivity(new Intent(MainActivity.this,aboutus.class));
                break;

            case R.id.app_rateus:
                startActivity(new Intent(MainActivity.this,RateUs.class));
                break;

            case R.id.app_logout:
                Logout();
                break;
        }
        return true;
    }

    private void Logout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(MainActivity.this,Login.class));

    }

}