package com.akp.winninggator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Welcome extends AppCompatActivity {

    LinearLayout start_ll;
    String Ref_Id;
    AppCompatButton login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Ref_Id=getIntent().getStringExtra("ref_id");
        login_btn=findViewById(R.id.login_btn);
        start_ll=findViewById(R.id.start_ll);
        start_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("ref_id",Ref_Id);
                startActivity(intent);
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("ref_id",Ref_Id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Pause or stop the music service when the app goes into the background
        Intent intent = new Intent(this, BackgroundSoundService.class);
        intent.setAction(BackgroundSoundService.ACTION_PAUSE);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Resume or start the music service when the app comes to the foreground
        Intent intent = new Intent(this, BackgroundSoundService.class);
        intent.setAction(BackgroundSoundService.ACTION_RESUME);
        startService(intent);
    }
}