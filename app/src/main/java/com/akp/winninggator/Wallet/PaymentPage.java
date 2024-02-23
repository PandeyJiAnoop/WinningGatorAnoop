package com.akp.winninggator.Wallet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import java.util.HashMap;
import java.util.Map;

public class PaymentPage extends AppCompatActivity {
    private ClipboardManager myClipboard;
    private RelativeLayout referral_rl,header;
    TextView price_tv;

    AppCompatButton edtcode;
    private ClipData myClip;
    String getPrice;
    Button addButton;
    EditText utr_et;
    private String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        getPrice=getIntent().getStringExtra("send_price");


        ImageView menuImg= findViewById(R.id.menuImg);
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewId();
        onClickEvent();


    }

    private void onClickEvent() {
        price_tv.setText(getPrice+".00 INR");
        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        referral_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                String text = "https://easebuzz.in/pay/YTWTECH";
//                myClip = ClipData.newPlainText("text", text);
//                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(getApplicationContext(), "Link Copied", Toast.LENGTH_SHORT).show();
            }
        });
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edtcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String url = "https://easebuzz.in/pay/YTWTECH";
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (utr_et.getText().toString().equalsIgnoreCase("")){
                    utr_et.setError("Fields can't be blank!");
                    utr_et.requestFocus();
                }
                else {
                    RequestPaymentAPI();
                }
            }
        });


    }

    private void RequestPaymentAPI() {
        final ProgressDialog progressDialog1 = new ProgressDialog(this);
        progressDialog1.setMessage("Loading...");
        progressDialog1.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.RequestWalletAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                 Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                progressDialog1.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("Status");
                    if (status.equalsIgnoreCase("true")) {
                        JSONArray Response = object.getJSONArray("Response");
                        for (int i = 0; i < Response.length(); i++) {
                            JSONObject jsonObject = Response.getJSONObject(i);
                            Toast.makeText(PaymentPage.this, jsonObject.getString("Msg"), Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(PaymentPage.this).setTitle("Request Saved Successfully..")
                                    .setMessage("Request Sent to Admin Please Wait for Approved!!").setCancelable(false).setIcon(R.drawable.logo)
                                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            finish();
                                            //startActivity(i);
                                        }});
                            AlertDialog alert = builder.create();
                            alert.show();
                            Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                            //Set positive button background
                            pbutton.setBackgroundColor(Color.RED);
                            //Set positive button text color
                            pbutton.setTextColor(Color.WHITE);
                        }
                    } else {
                        Toast.makeText(PaymentPage.this, object.getString("Message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog1.dismiss();
                Toast.makeText(PaymentPage.this, "Something went wrong:-" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("MemberId", UserId);
                params.put("Amount", getPrice);
                params.put("TransactionNo", utr_et.getText().toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(PaymentPage.this);
        requestQueue.add(stringRequest);

    }

    private void findViewId() {
        edtcode=findViewById(R.id.edtcode);
        header=findViewById(R.id.header);
        price_tv=findViewById(R.id.price_tv);
        referral_rl=findViewById(R.id.referral_rl);

        utr_et=findViewById(R.id.utr_et);
        addButton=findViewById(R.id.addButton);

    }
}