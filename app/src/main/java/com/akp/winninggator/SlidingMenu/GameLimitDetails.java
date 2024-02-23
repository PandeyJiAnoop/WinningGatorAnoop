package com.akp.winninggator.SlidingMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akp.winninggator.BackgroundSoundService;
import com.akp.winninggator.R;
import com.akp.winninggator.Util.Api_Urls;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GameLimitDetails extends AppCompatActivity {
    TextView txtMarquee,txtMarquee1,txtMarquee2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_limit_details);

        RelativeLayout header=findViewById(R.id.header);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtMarquee=findViewById(R.id.txtMarquee);
        txtMarquee1=findViewById(R.id.txtMarquee1);
        txtMarquee2=findViewById(R.id.txtMarquee2);
        GetDetails();
    }


    public void GetDetails() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api_Urls.BaseURL+"GameLimitDetails", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("Status").equalsIgnoreCase("true")){
                        JSONArray Jarray = object.getJSONArray("Response");
                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject jsonobject = Jarray.getJSONObject(i);
                            String MinimumBit    = jsonobject.getString("MinimumBit");
                            String MaximumWinOneBit    = jsonobject.getString("MaximumWinOneBit");
                            String MaximumBit    = jsonobject.getString("MaximumBit");
                            txtMarquee.setText("Maximum Win One Bit :- "+MaximumWinOneBit);
                            txtMarquee1.setText("Minimum Bit :- "+MinimumBit);
                            txtMarquee2.setText("Maximum Bit :- "+MaximumBit);
                        } }
                    else {
                        Toast.makeText(getApplicationContext(),object.getString("Message"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(GameLimitDetails.this, "Something went wrong:-" + error, Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(GameLimitDetails.this);
        requestQueue.add(stringRequest);
    }

}