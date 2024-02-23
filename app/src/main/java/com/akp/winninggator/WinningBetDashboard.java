package com.akp.winninggator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akp.winninggator.Util.Api_Urls;
import com.akp.winninggator.Wallet.PaymentPage;
import com.akp.winninggator.Wallet.ViewWalletHistoryReport;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WinningBetDashboard extends AppCompatActivity {
TextView title_tv;
String UserId,UserName;

ImageView notification_img;
TextView balance_tv,deposite_tv,withdraw_tv;
RelativeLayout game_one_rl,game_two_rl,game_three_rl,game_four_rl,game_five_rl;
    TextView tvOnee, tvtwo, tvthree, tvfour, tvfive, tvthrees,tv_fundHistory,tvAvailbleBalance;
    EditText edt_amount;
    private AlertDialog alertDialog2; LinearLayout view_history_ll;
    private EditText w_rupee_et;
    private android.app.AlertDialog alertDialog1;
    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning_bet_dashboard);
        findViewId();
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        UserName= sharedPreferences.getString("U_name", "");
        title_tv.setText(UserName);
        GetWalletAPI();
    }

    private void findViewId() { bottomNav = findViewById(R.id.bottomNav);
        notification_img=findViewById(R.id.notification_img);
        title_tv=findViewById(R.id.user_tv);

        balance_tv=findViewById(R.id.balance_tv);
        deposite_tv=findViewById(R.id.deposite_tv);
        withdraw_tv=findViewById(R.id.withdraw_tv);
        game_one_rl=findViewById(R.id.game_one_rl);

        game_two_rl=findViewById(R.id.game_two_rl);
        game_three_rl=findViewById(R.id.game_three_rl);
        game_four_rl=findViewById(R.id.game_four_rl);
        game_five_rl=findViewById(R.id.came_five_rl);

        notification_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NotificationList.class);
                startActivity(intent);
            }
        });

        deposite_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWallet();
            }
        });
        withdraw_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserId.equalsIgnoreCase("")){
                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else {
                    withdrawpopupShow();
                }

            }
        });
        game_one_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),dashboard.class);
                startActivity(intent);
            }
        });
        game_two_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Coming Soon!",Toast.LENGTH_LONG).show();
            }
        });
        game_three_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Coming Soon!",Toast.LENGTH_LONG).show();
            }
        });

        game_four_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Coming Soon!",Toast.LENGTH_LONG).show();
            }
        });
game_five_rl.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getApplicationContext(),CrickbuzzWebview.class);
        startActivity(intent);    }
});

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        return true;

                    case R.id.aviator:
                        Intent intent=new Intent(getApplicationContext(),dashboard.class);
                        startActivity(intent);
                        return true;

                    case R.id.account:
                        Intent intent1=new Intent(getApplicationContext(),user_profile.class);
                        startActivity(intent1);
                        return true;
                }
                return false;
            }
        });

    }



    public void GetWalletAPI() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.GetWalletAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { Log.d("GetWalletAPI","sadaf"+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        String member_id = jsonObject2.getString("Member_ID");
                        String mainWallet = jsonObject2.getString("MainWallet");
                        String memberName = jsonObject2.getString("MemberName");
                        balance_tv.setText("\u20B9 "+mainWallet);
//                        tvAvailbleBalance.setText("\u20B9 "+mainWallet);
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("MemberId",UserId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(WinningBetDashboard.this);
        requestQueue.add(stringRequest);
    }



    private void addWallet() {

        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.add_wallet, viewGroup, false);
        ImageView rlBack = (ImageView) dialogView.findViewById(R.id.ivback);
        tvAvailbleBalance= dialogView.findViewById(R.id.tvAvailbleBalance);
        view_history_ll= dialogView.findViewById(R.id.view_history_ll);
        tvOnee = dialogView.findViewById(R.id.tvOne);
        tvtwo = dialogView.findViewById(R.id.tvtwo);
        tvthree = dialogView.findViewById(R.id.tvthree);
        tvfour = dialogView.findViewById(R.id.tvfour);
        tvfive = dialogView.findViewById(R.id.tvfive);
        tvthrees = dialogView.findViewById(R.id.tvthrees);
        edt_amount = dialogView.findViewById(R.id.edt_amount);
        Button addButton = dialogView.findViewById(R.id.addButton);

        view_history_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WinningBetDashboard.this, ViewWalletHistoryReport.class));
                alertDialog2.dismiss();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double price= Double.parseDouble(edt_amount.getText().toString());
                Intent intent=new Intent(getApplicationContext(), PaymentPage.class);
                intent.putExtra("send_price",edt_amount.getText().toString());
                startActivity(intent);
                alertDialog2.dismiss();
            }
        });
        tvOnee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_amount.setText("100");
                tvOnee.setBackgroundResource(R.drawable.selectsizebackground);
                tvtwo.setBackgroundResource(R.drawable.backgroundd);
                tvthree.setBackgroundResource(R.drawable.backgroundd);
                tvfour.setBackgroundResource(R.drawable.backgroundd);
                tvfive.setBackgroundResource(R.drawable.backgroundd);
                tvthrees.setBackgroundResource(R.drawable.backgroundd);

            }
        });

        tvtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_amount.setText("150");
                tvOnee.setBackgroundResource(R.drawable.backgroundd);
                tvtwo.setBackgroundResource(R.drawable.selectsizebackground);
                tvthree.setBackgroundResource(R.drawable.backgroundd);
                tvfour.setBackgroundResource(R.drawable.backgroundd);
                tvfive.setBackgroundResource(R.drawable.backgroundd);
                tvthrees.setBackgroundResource(R.drawable.backgroundd);

            }
        });
        tvthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_amount.setText("200");
                tvOnee.setBackgroundResource(R.drawable.backgroundd);
                tvtwo.setBackgroundResource(R.drawable.backgroundd);
                tvthree.setBackgroundResource(R.drawable.selectsizebackground);
                tvfour.setBackgroundResource(R.drawable.backgroundd);
                tvfive.setBackgroundResource(R.drawable.backgroundd);
                tvthrees.setBackgroundResource(R.drawable.backgroundd);

            }
        });
        tvfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_amount.setText("500");
                tvOnee.setBackgroundResource(R.drawable.backgroundd);
                tvtwo.setBackgroundResource(R.drawable.backgroundd);
                tvthree.setBackgroundResource(R.drawable.backgroundd);
                tvfour.setBackgroundResource(R.drawable.selectsizebackground);
                tvfive.setBackgroundResource(R.drawable.backgroundd);
                tvthrees.setBackgroundResource(R.drawable.backgroundd);

            }
        });
        tvfive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_amount.setText("2000");
                tvOnee.setBackgroundResource(R.drawable.backgroundd);
                tvtwo.setBackgroundResource(R.drawable.backgroundd);
                tvthree.setBackgroundResource(R.drawable.backgroundd);
                tvfour.setBackgroundResource(R.drawable.backgroundd);
                tvthrees.setBackgroundResource(R.drawable.backgroundd);
                tvfive.setBackgroundResource(R.drawable.selectsizebackground);

            }
        });
        tvthrees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_amount.setText("3000");
                tvOnee.setBackgroundResource(R.drawable.backgroundd);
                tvtwo.setBackgroundResource(R.drawable.backgroundd);
                tvthree.setBackgroundResource(R.drawable.backgroundd);
                tvfour.setBackgroundResource(R.drawable.backgroundd);
                tvfive.setBackgroundResource(R.drawable.backgroundd);
                tvthrees.setBackgroundResource(R.drawable.selectsizebackground);

            }
        });
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.GetWalletAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { Log.d("GetWalletAPI","sadaf"+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        String member_id = jsonObject2.getString("Member_ID");
                        String mainWallet = jsonObject2.getString("MainWallet");
                        String memberName = jsonObject2.getString("MemberName");
                        tvAvailbleBalance.setText("\u20B9 "+mainWallet);
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("MemberId",UserId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog2.dismiss();
            }
        });

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        alertDialog2 = builder.create();
        alertDialog2.show();
    }


    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(WinningBetDashboard.this);
        builder.setMessage(Html.fromHtml("<font color='#000'>Are you sure want to Exit From App?</font>"));
        builder.setCancelable(false);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        android.app.AlertDialog alert = builder.create();
        alert.show();
        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setBackgroundColor(Color.BLACK);
        nbutton.setTextColor(Color.WHITE);
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setBackgroundColor(Color.RED);
        pbutton.setTextColor(Color.WHITE);
    }



    private void withdrawpopupShow() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.withdrawpopup, viewGroup, false);
        final AppCompatButton w_submit_btn = (AppCompatButton) dialogView.findViewById(R.id.w_submit_btn);
        w_rupee_et=dialogView.findViewById(R.id.w_rupee_et);

        w_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (w_rupee_et.getText().toString().equalsIgnoreCase("")){
                    w_rupee_et.setError("Fields can't be blank!");
                    w_rupee_et.requestFocus();
                }
                else {

                    WithdrawAPI();
                }
            }
        });
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setView(dialogView);
        alertDialog1 = builder.create();
        alertDialog1.show();
    }

    private void WithdrawAPI() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.WithdrawlRequest, new  Response.Listener<String>() {
            @Override
            public void onResponse(String response) { Log.d("WithdrawlRequest",""+response);
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("Status").equalsIgnoreCase("true")){
                        JSONArray Jarray = object.getJSONArray("Response");
                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject jsonobject = Jarray.getJSONObject(i);
                            alertDialog1.dismiss();
                            Toast.makeText(getApplicationContext(),jsonobject.getString("Msg"),Toast.LENGTH_LONG).show();
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),object.getString("Message"),Toast.LENGTH_LONG).show();
                        alertDialog1.dismiss();
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("myTag", "message:"+error);
                Toast.makeText(WinningBetDashboard.this, "Internet connection is slow Or no internet connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("MemberId",UserId);
                params.put("Amount",w_rupee_et.getText().toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(WinningBetDashboard.this);
        requestQueue.add(stringRequest);
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