package com.akp.winninggator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class Register extends AppCompatActivity {
AppCompatButton reg_btn;
EditText etFullName,etEmail,etMobileNo,etNewPass,etRepPass,ref_et;
    private SharedPreferences login_preference;
    private SharedPreferences.Editor login_editor;
    private Dialog alertDialog;
Spinner spCountryList;
    ArrayList<String> StateName = new ArrayList<>();
    ArrayList<String> StateId = new ArrayList<>();
    String stateid;   String Ref_Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); Ref_Id=getIntent().getStringExtra("ref_id");
        findViewId();
        OnClickListner();
        getState();
        spCountryList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Change the selected item's text color
                ((TextView) view).setTextColor(Color.WHITE);
                if (position > 0) {

                    for (int j = 0; j <= StateId.size(); j++) {
                        if (spCountryList.getSelectedItem().toString().equalsIgnoreCase(StateName.get(j))) {
                            // position = i;
                            stateid = StateId.get(j - 1);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (Ref_Id == null){
        }
        else {
            ref_et.setText(Ref_Id);
            ref_et.setClickable(false);
            ref_et.setFocusable(false);
        }
    }

    private void OnClickListner() {
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etFullName.getText().toString().equalsIgnoreCase("")){
                    etFullName.setError("Fields can't be blank!");
                    etFullName.requestFocus();
                }
                else if (etEmail.getText().toString().equalsIgnoreCase("")){
                    etEmail.setError("Fields can't be blank!");
                    etEmail.requestFocus();
                }
                else if (etMobileNo.getText().toString().equalsIgnoreCase("")){
                    etMobileNo.setError("Fields can't be blank!");
                    etMobileNo.requestFocus();
                }
                else if (etNewPass.getText().toString().equalsIgnoreCase("")){
                    etNewPass.setError("Fields can't be blank!");
                    etNewPass.requestFocus();
                }
                else if (etRepPass.getText().toString().equalsIgnoreCase("")){
                    etRepPass.setError("Fields can't be blank!");
                    etRepPass.requestFocus();
                }
                else if(!etNewPass.getText().toString().equals(etRepPass.getText().toString())){
                    //Toast is the pop up message
                    Toast.makeText(getApplicationContext(), "Password Not matched!", Toast.LENGTH_LONG).show();
                }
                else {
                    if (Ref_Id == null){
                        RegistrationService("");
                    }
                    else {
                        RegistrationService(Ref_Id);
                    }
                }
            }
        });



    }

    private void findViewId() {
        etFullName=findViewById(R.id.etFullName);
        etEmail=findViewById(R.id.etEmail);
        etMobileNo=findViewById(R.id.etMobileNo);
        etNewPass=findViewById(R.id.etNewPass);
        etRepPass=findViewById(R.id.etRepPass);
        reg_btn=findViewById(R.id.reg_btn);
        spCountryList=findViewById(R.id.spCountryList);
        ref_et=findViewById(R.id.ref_et);

    }



    public void RegistrationService(String Refid) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.RegisterAPI, new Response.Listener<String>() {
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
                            String mobileNo = jsonObject.getString("MobileNo");
                            String emialId = jsonObject.getString("EmialId");
                            String password = jsonObject.getString("Password");
                            String reg = jsonObject.getString("RegDate");
                            login_preference = getSharedPreferences("login_preference", MODE_PRIVATE);
                            login_editor = login_preference.edit();
                            login_editor.putString("U_id",userName);
                            login_editor.putString("U_name",name);
                            login_editor.commit();
                            Toast.makeText(Register.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            //before inflating the custom alert dialog layout, we will get the current activity viewgroup
                            ViewGroup viewGroup = findViewById(android.R.id.content);
                            //then we will inflate the custom alert dialog xml that we created
                            View dialogView = LayoutInflater.from(Register.this).inflate(R.layout.successfullycreated_popup, viewGroup, false);
                            Button ok = (Button) dialogView.findViewById(R.id.btnDialog);
                            TextView txt_msg=dialogView.findViewById(R.id.txt_msg);
                            txt_msg.setText("Your User Id -("+userName+")"+"\nPassword Is - ("+password+")"+"\n\nRegistration Date Is - ("+reg+")");
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    alertDialog.dismiss();
                                }
                            });
                            //Now we need an AlertDialog.Builder object
                            AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                            //setting the view of the builder to our custom view that we already inflated
                            builder.setView(dialogView);
                            //finally creating the alert dialog and displaying it
                            alertDialog = builder.create();
                            alertDialog.show();

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
                Toast.makeText(Register.this, "Something went wrong:-" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("CountryID",stateid);
                params.put("MemberName",etFullName.getText().toString());
                params.put("MobileNo",etMobileNo.getText().toString());
                params.put("EmialId",etEmail.getText().toString());
                params.put("Password",etNewPass.getText().toString());
                params.put("ConfirmPassword",etRepPass.getText().toString());
                params.put("RefrerralId",Refid);

                return params;


            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
        requestQueue.add(stringRequest);
    }




    void getState() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api_Urls.CountryAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                  Toast.makeText(Register.this, response, Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    StateName.add("Select Country");
                        JSONArray jsonArray = jsonObject.getJSONArray("Response");
                        for (int i = 1; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            StateId.add(jsonObject1.getString("PKID"));
                            String statename = jsonObject1.getString("CountryName");
                            StateName.add(statename);
                        }


                    spCountryList.setAdapter(new ArrayAdapter<String>(Register.this, android.R.layout.simple_spinner_dropdown_item, StateName));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Register.this, "Internet connection is slow Or no internet connection", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}