package com.akp.winninggator;
/**
 * Created by Anoop Pandey on 9696381023.
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akp.winninggator.Helper.DialogManager;
import com.akp.winninggator.Util.Api_Urls;
import com.akp.winninggator.storage.SharedPref;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.akp.winninggator.Helper.NetworkConnectionHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import static android.content.ContentValues.TAG;

public class splashscrn extends AppCompatActivity {
    ImageView iv;
    long Delay = 5000;
    // Splash screen timer
    private static final int SPLASH_TIME_OUT = 1500;
    LinearLayout ll_1, ll_2;
    NumberProgressBar npb_progress;
    int progresscount = 0;
    ImageView img_icon;
    String general = "general-setting";
    ImageView staic;
    private String UserId;
    private String prodid;


    String userid,usertype;
    String versionName = "", versionCode = "";
    String TAG ="splash";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscrn);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        SharedPref.init(getApplicationContext());

        Animation uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        Animation downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        npb_progress = findViewById(R.id.npb_progress);
        img_icon = findViewById(R.id.img_icon);
        staic = findViewById(R.id.staic);
        staic.setAnimation(downtoup);
//        img_icon.setAnimation(uptodown);
        npb_progress.setProgress(progresscount);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progresscount == 100) {
                    /**/
                } else {
                    handler.postDelayed(this, 30);
                    progresscount++;
                    npb_progress.setProgress(progresscount);
                }
            }
        }, 200);










        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
//                            Toast.makeText(getApplicationContext(),deepLink.toString(),Toast.LENGTH_LONG).show();
                            Log.e(TAG, " my referlink "+deepLink.toString());
                            //   "http://www.blueappsoftware.com/myrefer.php?custid=cust123-prod456"
                            String referlink = deepLink.toString();
                            try {
                                referlink = referlink.substring(referlink.lastIndexOf("=")+1);
                                Log.e(TAG, " substring "+referlink); //cust123-prod456
                                String custid = referlink.substring(0, referlink.indexOf("-"));
                                prodid = referlink.substring(referlink.indexOf("-")+1);
                                Log.e(TAG, "custid "+custid +"----prpdiid "+ prodid);
                                // shareprefernce (prodid, custid);
                                //sharepreference  (refercustid, custid)
                            }catch (Exception e){
                                Log.e(TAG, " error "+e.toString());
                            }
                        }
                        // Handle the deep link. For example, open the linked
                        // content, or apply promotional credit to the user's
                        // account.
                        // ...
                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "getDynamicLink:onFailure", e);
                    }
                });







        UpdateVersion();




        iv = (ImageView) findViewById(R.id.iv);
        Glide.with(splashscrn.this)
                .load(R.drawable.loadingimg)
                .into(iv);


    }

    private void checkLogin() {
        Timer RunSplash = new Timer();
        // Task to do when the timer ends
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent1 = new Intent(splashscrn.this, BackgroundSoundService.class);
                startService(intent1);
                if (NetworkConnectionHelper.isOnline(splashscrn.this)) {
                    if (UserId.equalsIgnoreCase("")) {
                        Intent intent = new Intent(getApplicationContext(), Welcome.class);
                        intent.putExtra("ref_id",prodid);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent myIntent = new Intent(splashscrn.this, WinningBetDashboard.class);
                        startActivity(myIntent);
                        finish();
                    }

                } else {
                    DialogManager.openCheckInternetDialog(splashscrn.this);
                }

            }
        }, SPLASH_TIME_OUT);
    }



    public void AlertVersion() {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_ok);
        TextView tvMessage = dialog.findViewById(R.id.tvMessage);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        tvMessage.setText(getString(R.string.newVersion));
        btnSubmit.setText(getString(R.string.update));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity(); // or finish();
              /*  if (code.equalsIgnoreCase("")){
                    Intent myIntent = new Intent(SplashScreen.this,WelcomeSlider.class);
                    startActivity(myIntent);
                }
                else {
                    Intent myIntent = new Intent(SplashScreen.this, DashboardScreen.class);
                    startActivity(myIntent);
                }*/
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                dialog.dismiss();
            }
        });
    }

    private void getVersionInfo() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = String.valueOf(packageInfo.versionCode);
            Log.v("vname", versionName + " ," + String.valueOf(versionCode));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void UpdateVersion() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.VersionAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray Jarray = object.getJSONArray("Response");
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject jsonobject = Jarray.getJSONObject(i);
                        String UpdateVersion = jsonobject.getString("AppVersion");
                        if (object.getString("Status").equalsIgnoreCase("true"))
                            getVersionInfo();
                        {
                            if (versionName.equalsIgnoreCase(UpdateVersion)) {
                                checkLogin();
                            } else {
                                AlertVersion();
                                //checkLogin();
                            }
                        }
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something went wrong:-" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}


