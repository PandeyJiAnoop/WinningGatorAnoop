package com.akp.winninggator.SlidingMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

public class TermsConditions extends AppCompatActivity {
    ImageView norecord_tv;
    TextView tv;
RelativeLayout top_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions); norecord_tv=findViewById(R.id.norecord_tv); tv=findViewById(R.id.tv);
        top_rl=findViewById(R.id.top_rl);
        top_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getHistory();
    }

    public void getHistory() {
        final ProgressDialog progressDialog = new ProgressDialog(TermsConditions.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api_Urls.PrivacyPolicyDetailsAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { Log.d("myTag", "message:"+response);
                progressDialog.dismiss();
                String jsonString = response;
                try {
                    JSONObject object = new JSONObject(jsonString);
                    String status = object.getString("Status");
                    if (status.equalsIgnoreCase("true")) {
                        norecord_tv.setVisibility(View.GONE);
                        JSONArray Response = object.getJSONArray("Response");
                        for (int i = 0; i < Response.length(); i++) {
                            JSONObject jsonObject = Response.getJSONObject(i);
                            tv.setText((Html.fromHtml(jsonObject.getString("PrivacyPolicy"))));
                        }
                    } else {
                        norecord_tv.setVisibility(View.VISIBLE);
                        Toast.makeText(TermsConditions.this, "No data found", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("myTag", "message:"+error);
                Toast.makeText(TermsConditions.this, "Something went wrong!"+error, Toast.LENGTH_SHORT).show();
            }
        }) ;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(TermsConditions.this);
        requestQueue.add(stringRequest);

    }

}