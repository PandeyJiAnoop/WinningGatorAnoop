package com.akp.winninggator.SlidingMenu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.akp.winninggator.BackgroundSoundService;
import com.akp.winninggator.Helper.AnimationHelper;
import com.akp.winninggator.Helper.NetworkConnectionHelper;
import com.akp.winninggator.R;
import com.akp.winninggator.Util.Api_Urls;
import com.akp.winninggator.dashboard;
import com.android.volley.AuthFailureError;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntBiFunction;

public class ReferralPersonList extends AppCompatActivity {
    RecyclerView cust_recyclerView;
    ArrayList<HashMap<String, String>> arrayList1 = new ArrayList<>();
    String UserId;
    SwipeRefreshLayout srl_refresh;
    ImageView sliding_menu;
    ImageView norecord_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral_person_list);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        srl_refresh=findViewById(R.id.srl_refresh);
        norecord_tv=findViewById(R.id.norecord_tv);

        sliding_menu=findViewById(R.id.back_img);
        cust_recyclerView=findViewById(R.id.cust_recyclerView);

        sliding_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), dashboard.class);
                startActivity(intent);
            }
        });

        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkConnectionHelper.isOnline(ReferralPersonList.this)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                            srl_refresh.setRefreshing(false);
                        }
                    }, 2000);
                } else {
                    Toast.makeText(ReferralPersonList.this, "Please check your internet connection! try again...", Toast.LENGTH_SHORT).show();
                }
            }
        });


        getHistory();



    }
    public void getHistory() {
        final ProgressDialog progressDialog = new ProgressDialog(ReferralPersonList.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.ReferralReportAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("responsenew",response);
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
                            HashMap<String, String> hashlist = new HashMap();
                            hashlist.put("MemberId", jsonObject.getString("MemberId"));
                            hashlist.put("MemberName", jsonObject.getString("MemberName"));
                            hashlist.put("MobileNo", jsonObject.getString("MobileNo"));
                            hashlist.put("EmailID", jsonObject.getString("EmailID"));




                            arrayList1.add(hashlist);
                        }
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(ReferralPersonList.this, 1);
                        ReferralPersonListAdapter customerListAdapter = new ReferralPersonListAdapter(ReferralPersonList.this, arrayList1);
                        cust_recyclerView.setLayoutManager(gridLayoutManager);
                        cust_recyclerView.setAdapter(customerListAdapter);
                    } else {
                        norecord_tv.setVisibility(View.VISIBLE);
                        Toast.makeText(ReferralPersonList.this, "No data found", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ReferralPersonList.this, "Something went wrong!"+error, Toast.LENGTH_SHORT).show();
            }
        }) {  @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            HashMap<String, String> params = new HashMap<>();
            params.put("MemberId","WNB000003");
            return params; }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(ReferralPersonList.this);
        requestQueue.add(stringRequest);

    }




    public class ReferralPersonListAdapter extends RecyclerView.Adapter<ReferralPersonListAdapter.VH> {
        Context context;
        List<HashMap<String,String>> arrayList;
        public ReferralPersonListAdapter(Context context, List<HashMap<String,String>> arrayList) {
            this.arrayList=arrayList;
            this.context=context;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.dynamic_referaalperson, viewGroup, false);
            VH viewHolder = new VH(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull VH vh, int i) {
            AnimationHelper.animatate(context,vh.itemView, R.anim.alfa_animation);
            vh.sr_no.setText(String.valueOf(" "+(i+1)));
            vh.bbid_tv.setText(arrayList.get(i).get("MemberId"));
            vh.name_tv.setText(arrayList.get(i).get("MemberName"));
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"Member Id:- "+arrayList.get(i).get("MemberId")+"\n\nMember Name:-"+arrayList.get(i).get("MemberName"),Toast.LENGTH_LONG).show();
                }
            });



        }



        @Override
        public int getItemCount() {
            return arrayList.size();
        }
        public class VH extends RecyclerView.ViewHolder {
            TextView name_tv,sr_no,bbid_tv;
            public VH(@NonNull View itemView) {
                super(itemView);
                name_tv = itemView.findViewById(R.id.name_tv);
                sr_no = itemView.findViewById(R.id.sr_no);
                bbid_tv = itemView.findViewById(R.id.bbid_tv);


            }
        }
    }

}