package com.example.pgapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        getSupportActionBar().hide();
        LogoLauncher logoLauncher = new LogoLauncher();
        logoLauncher.start();
    }

    private class LogoLauncher extends Thread{
        public void run(){
            try {
                sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            startActivity(new Intent(SplashscreenActivity.this,Login.class));
            SplashscreenActivity.this.finish();
        }
    }
}