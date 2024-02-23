package com.akp.winninggator.SlidingMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.akp.winninggator.BackgroundSoundService;
import com.akp.winninggator.R;

import java.net.URLEncoder;

public class SupportScreen extends AppCompatActivity {
RelativeLayout header1;
ImageView whatsap_img,telegram_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_screen);
        whatsap_img=findViewById(R.id.whatsap_img);
        telegram_img=findViewById(R.id.telegram_img);
        header1=findViewById(R.id.header1);
        header1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        telegram_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent telegramIntent = new Intent(Intent.ACTION_VIEW);
                    telegramIntent.setData(Uri.parse("https://t.me/"));
                    startActivity(telegramIntent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Telegram not Installed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        whatsap_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp("","Welcome To Winning Gator"); }});

    }


    //Whatsap intent akp
    private void openWhatsApp(String numero,String mensaje){
        try{
            PackageManager packageManager = getPackageManager();
            Intent i = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone="+ numero +"&text=" + URLEncoder.encode(mensaje, "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            }else {
                Toast.makeText(getApplicationContext(),"Whatsapp Not Installed!",Toast.LENGTH_LONG).show();
            }
        } catch(Exception e) {
            Log.e("ERROR WHATSAPP",e.toString());
            Toast.makeText(getApplicationContext(),"Whatsapp Not Installed!",Toast.LENGTH_LONG).show();
        } }
    public static boolean isAppAvailable(Context context, String appName)
    {
        PackageManager pm = context.getPackageManager();
        try
        {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
            return true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }
}