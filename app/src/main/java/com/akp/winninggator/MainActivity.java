package com.akp.winninggator;
/**
 * Created by Anoop Pandey on 9696381023.
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.akp.winninggator.Util.Api_Urls;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    AppCompatButton login_btn;
    TextInputEditText edt_mobile, edt_pass;
    TextView reg_btn;
    private SharedPreferences login_preference;
    private SharedPreferences.Editor login_editor;
    String Ref_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);   Ref_Id=getIntent().getStringExtra("ref_id");
        initViews();
    }

    private void initViews() {
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_pass = findViewById(R.id.edt_pass);
        login_btn = findViewById(R.id.login_btn);
        reg_btn = findViewById(R.id.reg_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_mobile.getText().toString().trim().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter User ID", Toast.LENGTH_SHORT).show();
                } else if (edt_pass.getText().toString().trim().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter Password.", Toast.LENGTH_SHORT).show();
                } else {
                    LoginService();
//                    if (NetworkConnectionHelper.isOnline(MainActivity.this)) {
//                        Intent intent = new Intent(getApplicationContext(), dashboard.class);
//                        startActivity(intent);
//                    } else {
//                        DialogManager.openCheckInternetDialog(MainActivity.this);
//                    }
                }
            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                intent.putExtra("ref_id",Ref_Id);
                startActivity(intent);
            }
        });




    }
    public void LoginService() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.LoginAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Registration",""+response);
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("Status");
                    if (status.equalsIgnoreCase("true")) {
                        JSONArray Response = object.getJSONArray("Response");
                        for (int i = 0; i < Response.length(); i++) {
                            JSONObject jsonObject = Response.getJSONObject(i);
                            String userName = jsonObject.getString("UserName");
                            String name = jsonObject.getString("Name");

                            login_preference = getSharedPreferences("login_preference", MODE_PRIVATE);
                            login_editor = login_preference.edit();
                            login_editor.putString("U_id",userName);
                            login_editor.putString("U_name",name);
                            login_editor.commit();

                            Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(getApplicationContext(),WinningBetDashboard.class);
                            startActivity(intent);

                        }

                    } else {
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
                Toast.makeText(MainActivity.this, "Something went wrong:-" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("MobileNumber",edt_mobile.getText().toString());
                params.put("Action","");
                params.put("Password",edt_pass.getText().toString());
                params.put("Token","");
                return params;


            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }

}