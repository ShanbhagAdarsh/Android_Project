package com.example.book_my_pg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class rateUs extends AppCompatActivity {

    TextView phone, mail;
    RatingBar rate;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);

        rate=findViewById(R.id.rate);
        submit=findViewById(R.id.sub);
        LayerDrawable stars = (LayerDrawable) rate.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=String.valueOf(rate.getRating());
                Toast.makeText(getApplicationContext(),"Thank You! You have rated "+s+" star",Toast.LENGTH_SHORT).show();
            }
        });

        phone=(TextView) findViewById(R.id.ph);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard=(ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip= ClipData.newPlainText("Text", phone.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(rateUs.this,"Copied", Toast.LENGTH_SHORT).show();
            }
        });
        mail=findViewById(R.id.mail);
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard=(ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip= ClipData.newPlainText("Text", mail.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(rateUs.this,"Copied", Toast.LENGTH_SHORT).show();
            }
        });
    }
}