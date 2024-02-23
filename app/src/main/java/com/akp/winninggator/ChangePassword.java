package com.akp.winninggator;
/**
 * Created by Anoop Pandey on 9696381023.
 */
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.akp.winninggator.storage.SharedPref;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.akp.winninggator.Util.Api_Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {
    EditText edt_new_pass;
    private EditText edt_old_pass,edt_conf_pass;
    private Button btn_sendotp;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        SharedPref.init(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        edt_new_pass=(EditText)findViewById(R.id.edt_new_pass);
        edt_old_pass=(EditText)findViewById(R.id.edt_old_pass);
        edt_conf_pass=(EditText)findViewById(R.id.edt_conf_pass);
        btn_sendotp=(Button)findViewById(R.id.btn_sendotp);
        findViewById(R.id.back_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_old_pass.getText().toString().equalsIgnoreCase("")){
                    edt_old_pass.setError("Fields can't be blank!");
                    edt_old_pass.requestFocus();
                }
                else if (edt_new_pass.getText().toString().equalsIgnoreCase("")){
                    edt_new_pass.setError("Fields can't be blank!");
                    edt_new_pass.requestFocus();
                }
                else if (edt_conf_pass.getText().toString().equalsIgnoreCase("")){
                    edt_conf_pass.setError("Fields can't be blank!");
                    edt_conf_pass.requestFocus();
                }
//               else if(!edt_new_pass.getText().toString().equals(edt_conf_pass.getText().toString())){
//                    //Toast is the pop up message
//                    Toast.makeText(getApplicationContext(), "Password Not matched!", Toast.LENGTH_LONG).show();
//                }
                else {
                    changePassword();
                    // Toast.makeText(getApplicationContext(),"Password Changed Successfully!",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void changePassword() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.ChnagePasswordAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { Log.d("ChnagePasswordAPI","sadaf"+response);
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
//                    Toast.makeText(ChangePassword.this, object.getString("Message"), Toast.LENGTH_SHORT).show();
                    if (object.getString("Message").equalsIgnoreCase("Password Changed Successfully. ")){
                        Intent intent=new Intent(getApplicationContext(), dashboard.class);
                        startActivity(intent);
                    }

                    else {
                        Toast.makeText(ChangePassword.this,object.getString("Message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //  Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ChangePassword.this, "Internet connection is slow Or no internet connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("UserId",UserId);
                params.put("UserId",UserId);
                params.put("UserId",UserId);
                params.put("UserId",UserId);

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(ChangePassword.this);
        requestQueue.add(stringRequest);

    }
//    @Override
//    protected void onPause() {
//        Toast.makeText(getApplicationContext(),"Anoop Music Stop",Toast.LENGTH_LONG).show();
//        super.onPause();
//
//        // Check if the app is going into the background
//        if (!isChangingConfigurations()) {
//            // Stop the music service
//            Intent intent = new Intent(this, BackgroundSoundService.class);
//            stopService(intent);
//        }
//    }
}