package com.akp.winninggator;
/**
 * Created by Anoop Pandey on 9696381023.
 */
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.akp.winninggator.Util.Api_Urls;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.akp.winninggator.Util.Api_Urls.UpdateProfileAPI;

public class user_profile extends AppCompatActivity {

     String UserId,U_name;
    private CircleImageView img_showProfile;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    String temp;
    ImageView btnSetting;
    private AlertDialog alertDialog6;
    BottomNavigationView bottomNav;
    EditText name_et,mobile_et,email_et,reg_et,upiid_et;
    EditText AccountNumbertv,BankNametv,AccountHolderNametv,IfscCodetv,BranchNametv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        U_name= sharedPreferences.getString("U_name", "");


        findViewId();
        RelativeLayout ok1 = (RelativeLayout) findViewById(R.id.update_rl);
        bottomNav = findViewById(R.id.bottomNav);
        img_showProfile=findViewById(R.id.imageView);
        RelativeLayout rlHeader=findViewById(R.id.rlHeader);
        btnSetting=findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music();
            }
        });
        getProfile();

        
        rlHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),dashboard.class);
                startActivity(intent);    overridePendingTransition(0, 0);
            }
        });
        img_showProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        return true;

                    case R.id.account:
                        Intent intent=new Intent(getApplicationContext(),dashboard.class);
                        startActivity(intent);
                        return true;

                    case R.id.back:
                        finish();
                        return true;
                }
                return false;
            }
        });



        ok1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (temp == null){
                    UpdateAPI("");
                }
                else {
                    UpdateAPI(temp);

                }

            }
        });
        
    
    }

    private void UpdateAPI(String img) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UpdateProfileAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("UpdateProfileAPI","sadaf"+response);
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("Status").equalsIgnoreCase("true")){
                        JSONArray Jarray = object.getJSONArray("Response");
                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject jsonobject = Jarray.getJSONObject(i);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                            Toast.makeText(getApplicationContext(),jsonobject.getString("Msg"),Toast.LENGTH_LONG).show();
                        }
                    }
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

                Toast.makeText(user_profile.this, "Something went wrong:-" + error, Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("UserId",UserId);
                params.put("MemberName",name_et.getText().toString());
                params.put("MobileNo",mobile_et.getText().toString());
                params.put("EmialId",email_et.getText().toString());
                params.put("ProfilePic",img);
                params.put("CountryID","6");
                params.put("UPIID",upiid_et.getText().toString());
                params.put("AccountNumber",AccountNumbertv.getText().toString());
                params.put("BankName",BankNametv.getText().toString());
                params.put("AccountHolderName",AccountHolderNametv.getText().toString());
                params.put("IfscCode",IfscCodetv.getText().toString());
                params.put("BranchName",BranchNametv.getText().toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(user_profile.this);
        requestQueue.add(stringRequest);

    }

    private void findViewId() {
        name_et=findViewById(R.id.edt_name);
        mobile_et=findViewById(R.id.mobile_et);
        email_et=findViewById(R.id.email_et);
        reg_et=findViewById(R.id.reg_et);
        upiid_et=findViewById(R.id.upiid_et);

        AccountNumbertv=findViewById(R.id.AccountNumbertv);
        BankNametv=findViewById(R.id.BankNametv);
        AccountHolderNametv=findViewById(R.id.AccountHolderNametv);
        IfscCodetv=findViewById(R.id.IfscCodetv);
        BranchNametv=findViewById(R.id.BranchNametv);



    }

    private void getProfile() {
        final ProgressDialog progressDialog = new ProgressDialog(user_profile.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.ViewProfileAPI, new Response.Listener<String>() {
            //        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.Signature_BASE_URL + url, new  Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ViewProfileAPI","sadaf"+response);
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("Status").equalsIgnoreCase("true")){
                        JSONArray Jarray = object.getJSONArray("Response");
                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject jsonobject = Jarray.getJSONObject(i);
                            String UserID       = jsonobject.getString("UserID");
                            String MemberName    = jsonobject.getString("MemberName");
                            String MobileNo      = jsonobject.getString("MobileNo");
                            String EmaiLID       = jsonobject.getString("EmaiLID");
                            String RegDate    = jsonobject.getString("RegDate");


                            String AccountNumber       = jsonobject.getString("AccountNumber");
                            String BankName    = jsonobject.getString("BankName");
                            String AccountHolderName       = jsonobject.getString("AccountHolderName");
                            String IfscCode       = jsonobject.getString("IfscCode");
                            String BranchName    = jsonobject.getString("BranchName");
                            String UPIID    = jsonobject.getString("UPIID");

                            String ProfilePic = jsonobject.getString("ProfilePic");

                            if (jsonobject.getString("ProfilePic").equalsIgnoreCase("")){
                            }
                            else {
                                Glide.with(getApplicationContext()).load(jsonobject.getString("ProfilePic")).error(R.drawable.logo).into(img_showProfile);
                            }
                            AccountNumbertv.setText(AccountNumber);
                            BankNametv.setText(BankName);
                            AccountHolderNametv.setText(AccountHolderName);
                            IfscCodetv.setText(IfscCode);
                            BranchNametv.setText(BranchName);

                            name_et.setText(MemberName);
                            mobile_et.setText(MobileNo);
                            email_et.setText(EmaiLID);
                            reg_et.setText(RegDate);
                            upiid_et.setText(UPIID);

                            Toast.makeText(getApplicationContext(),object.getString("Msg"),Toast.LENGTH_LONG).show();
                        }
                    }
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
                Toast.makeText(user_profile.this, "Something went wrong:-" + error, Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("UserId",UserId);
                params.put("MemberType","");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(user_profile.this);
        requestQueue.add(stringRequest);
    }


    private void selectImage() {
        final CharSequence[] items = {"Choose from Library", "Cancel" };
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(user_profile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(user_profile.this);
                if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        Toast.makeText(getApplicationContext(),""+bm,Toast.LENGTH_LONG).show();
        img_showProfile.setImageBitmap(bm);
        Bitmap immagex=bm;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] b = baos.toByteArray();
        temp = Base64.encodeToString(b,Base64.DEFAULT);
        Toast.makeText(getApplicationContext(),""+temp,Toast.LENGTH_LONG).show();
        Log.d("temp","sadaf"+temp);
    }


    private void music() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.music, viewGroup, false);
        RelativeLayout rlBack = (RelativeLayout) dialogView.findViewById(R.id.rlHeader);
        Switch swtich = (Switch) dialogView.findViewById(R.id.swtich);
        swtich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMyServiceRunning(BackgroundSoundService.class)) {
                    stopService(new Intent(user_profile.this, BackgroundSoundService.class));
                } else {
                    Toast.makeText(user_profile.this, "Music Service started by user.", Toast.LENGTH_LONG).show();

                    startService(new Intent(user_profile.this, BackgroundSoundService.class));
                }
            }
        });


        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog6.dismiss();
            }
        });

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        alertDialog6 = builder.create();
        alertDialog6.show();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        CheckIFSCAPI();
    }
    public void CheckIFSCAPI() {
        final ProgressDialog progressDialog = new ProgressDialog(user_profile.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://www.postalpincode.in/api/pincode/", new Response.Listener<String>() {
            //        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.Signature_BASE_URL + url, new  Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray Jarray = object.getJSONArray("PostOffice");
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject jsonobject = Jarray.getJSONObject(i);
//                        city_et.setText(jsonobject.getString("District"));
//                        state_et.setText(jsonobject.getString("State"));
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
//                Toast.makeText(user_profile.this, "IFSC Code Invalid", Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(user_profile.this);
        requestQueue.add(stringRequest);


    }

}