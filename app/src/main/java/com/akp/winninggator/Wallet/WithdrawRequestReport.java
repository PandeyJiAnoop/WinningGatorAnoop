package com.akp.winninggator.Wallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akp.winninggator.BackgroundSoundService;
import com.akp.winninggator.R;
import com.akp.winninggator.Util.Api_Urls;
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
import java.util.Map;

public class WithdrawRequestReport extends AppCompatActivity {
    RecyclerView rcvList;
    private final ArrayList<HashMap<String, String>> arrFriendsList = new ArrayList<>();
    private FriendsListAdapter pdfAdapTer;
    ImageView norecord_tv;
    String UserId;
    TextView title_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_request_report);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");

        findViewId();
        WalletHistory();

    }

    private void findViewId() {
        title_tv= findViewById(R.id.title_tv);
        rcvList = findViewById(R.id.rcvList);
        norecord_tv=findViewById(R.id.norecord_tv);
        ImageView menuImg= findViewById(R.id.menuImg);
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void WalletHistory() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.WithdrawlRequestReport, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("sadasdfa","sadsad"+response);
                progressDialog.dismiss();
                String jsonString = response;
                try {
                    JSONObject object = new JSONObject(jsonString);
                    String status = object.getString("Status");
                    if (status.equalsIgnoreCase("true")) {
                        norecord_tv.setVisibility(View.GONE);
                        JSONArray Response = object.getJSONArray("Response");
                        for (int i = 0; i < Response.length(); i++) {
                            title_tv.setText("Withdraw Request Report("+Response.length()+")");
                            JSONObject jsonObject = Response.getJSONObject(i);
                            HashMap<String, String> hashlist = new HashMap();
                            hashlist.put("MemberId", jsonObject.getString("MemberId"));
                            hashlist.put("Amount", jsonObject.getString("Amount"));
                            hashlist.put("Remark", jsonObject.getString("Remark"));
                            hashlist.put("EntryDate", jsonObject.getString("EntryDate"));
                            hashlist.put("AdminStatus", jsonObject.getString("AdminStatus"));
                            hashlist.put("ApproveBy", jsonObject.getString("ApproveBy"));
                            hashlist.put("ApproveDate", jsonObject.getString("ApproveDate"));
                            hashlist.put("PayAmount", jsonObject.getString("PayAmount"));
                            hashlist.put("TransFeeAmount", jsonObject.getString("TransFeeAmount"));
                            arrFriendsList.add(hashlist);
                        }
                        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
                        pdfAdapTer = new FriendsListAdapter(getApplicationContext(), arrFriendsList);
                        rcvList.setLayoutManager(layoutManager);
                        rcvList.setAdapter(pdfAdapTer);
                    } else {
                        norecord_tv.setVisibility(View.VISIBLE);
//                        Toast.makeText(WalletHistory.this, "No data found", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(WithdrawRequestReport.this, "Something went wrong:-" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("MemberId", UserId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(WithdrawRequestReport.this);
        requestQueue.add(stringRequest);
    }
    public class FriendsListAdapter extends RecyclerView.Adapter<WithdrawRequestReport.FriendsList> {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        public FriendsListAdapter(Context applicationContext, ArrayList<HashMap<String, String>> arrFriendsList) {
            data = arrFriendsList;
        }
        public WithdrawRequestReport.FriendsList onCreateViewHolder(ViewGroup parent, int viewType) {
            return new WithdrawRequestReport.FriendsList(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_withdrawreq, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final WithdrawRequestReport.FriendsList holder, final int position) {
            holder.tv1.setText("Request Amount : - \u20B9 "+data.get(position).get("Amount"));
            holder.tv2.setText("Pay Amount :- "+data.get(position).get("PayAmount")+"\n"+"Trans Fee Amount :- "+data.get(position).get("TransFeeAmount"));
            holder.tv3.setText(data.get(position).get("EntryDate"));
            holder.tv4.setText("Admin Status:- "+data.get(position).get("AdminStatus"));

            if (data.get(position).get("AdminStatus").equalsIgnoreCase("Pending")){
                holder.tv4.setTextColor(Color.RED);
            }
            else if (data.get(position).get("AdminStatus").equalsIgnoreCase("Reject")){
                holder.tv4.setTextColor(Color.RED);
            }
            else {
                holder.tv4.setTextColor(Color.GREEN);
            }
            holder.tv5.setText("Remark:- "+data.get(position).get("Remark"));
            holder.tv7.setText("Approve Date:- "+data.get(position).get("ApproveDate"));
        }

        public int getItemCount() {
            return data.size();
        }
    }
    public class FriendsList extends RecyclerView.ViewHolder {
        TextView tv1,tv2,tv3,tv4,tv5,tv7;

        public FriendsList(View itemView) {
            super(itemView);
            tv1=itemView.findViewById(R.id.tv1);
            tv2=itemView.findViewById(R.id.tv2);
            tv3=itemView.findViewById(R.id.tv3);
            tv4=itemView.findViewById(R.id.tv4);
            tv5=itemView.findViewById(R.id.tv5);
            tv7=itemView.findViewById(R.id.tv7);

        }
    }


}