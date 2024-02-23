//  package com.akp.winninggator.fragments;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.media.Image;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.text.Editable;
//import android.text.InputFilter;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.AlphaAnimation;
//import android.view.animation.Animation;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//import androidx.viewpager.widget.ViewPager;
//
//import com.akp.winninggator.Helper.AnimationHelper;
//import com.akp.winninggator.NotificationList;
//import com.akp.winninggator.Util.MyVolleySingleton;
//import com.akp.winninggator.storage.SharedPref;
//import com.android.volley.AuthFailureError;
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.facebook.drawee.backends.pipeline.Fresco;
//import com.akp.winninggator.Helper.NetworkConnectionHelper;
//import com.akp.winninggator.R;
//import com.akp.winninggator.Util.Api_Urls;
//import com.akp.winninggator.Util.Preferences;
//import com.smarteist.autoimageslider.SliderLayout;
//import com.smarteist.autoimageslider.SliderView;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.w3c.dom.Text;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import pl.droidsonroids.gif.annotations.Beta;
//
//import static android.content.Context.MODE_PRIVATE;
//
///**
// * Created by Anoop Pandey on 9696381023.
// */
//
//
//public class HomeFragment extends Fragment implements View.OnClickListener {
//    SliderLayout sliderLayout;
//    TextView game_number_tv;
//    Activity activity;
//    int n1 = 1;
//    String value = String.valueOf(1);
//    double amount = 0;
//    TextView tvOne,tvtwo,tvthree,tvfour,tvOnee1,tvtwo1,tvthree1,tvfour1,x_value;
//
//    int n11 = 1;
//    String value1 = String.valueOf(1);
//    double amount1 = 0;
//    private static final int SPLASH_TIME_OUT = 3500;
//    private static final int SPLASH_TIME_OUT1 = 17200;
//
//    int progresscount = 0;
//
//    int progresscount1 = 0;
//
//
//    private static final int SPLASH_TIME_OUT2 = 1000;
//    int progresscount2 = 0;
//    ArrayList<HashMap<String, String>> arrayList1 = new ArrayList<>();
//
////    SwipeRefreshLayout srl_refresh;
//    TextView tvTotal1,tvTotal,withdraw_tv,withdraw_tv1;
//    EditText tv_qty,tv_qty1;
//    LinearLayout bet_ll,cancel_bet_ll,cancel_bet_ll1,bet_ll1,withdraw_bet_ll,withdraw_bet_ll1,main;
//    private RequestQueue requestQueue;// Declare
//    private String UserId;
//    String gameId;
//    private String final_amount;
//    //initialisations
//    Runnable mTicker,mTicker5,mTicker7;
//    private Handler mHandler;
//
//
//    private RequestQueue requestQueue1;// Declare
//    private RequestQueue requestQueue2;// Declare
//    private RequestQueue requestQueue3;// Declare
//    private String GetPKID;
//    private int speed =0;
//
//
//    RecyclerView gunahistory_rv,all_bets_rv,my_bets_rv,top_rv;
//    ArrayList<HashMap<String, String>> my_betsarrayList1 = new ArrayList<>();
//    ArrayList<HashMap<String, String>> all_bets_arrayList1 = new ArrayList<>();
//    ArrayList<HashMap<String, String>> top_bets_arrayList1 = new ArrayList<>();
//
//TextView userTv,userTv1,userTv2;
//RelativeLayout gif_rl;
//    final float total = 7.00F;
//    float counter = 1;
//    Thread myThread;
//    public HomeFragment() {
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        this.activity = activity;
//    }
//
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
////        Fresco.initialize(getActivity());
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//
//        withdraw_bet_ll=view.findViewById(R.id.withdraw_bet_ll);
//        withdraw_tv=view.findViewById(R.id.withdraw_tv);
//        gif_rl=view.findViewById(R.id.gif_rl);
//        withdraw_bet_ll1=view.findViewById(R.id.withdraw_bet_ll1);
//        withdraw_tv1=view.findViewById(R.id.withdraw_tv1);
//        main=view.findViewById(R.id.main);
//        sliderLayout = view.findViewById(R.id.imageSlider);
//        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL);
//        sliderLayout.setScrollTimeInSec(3); //set scroll delay in seconds :
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login_preference",MODE_PRIVATE);
//        UserId= sharedPreferences.getString("U_id", "");
//
//
////        GetGameId API implemented here
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api_Urls.Game_SlotDetailsAPI, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) { Log.d("Game_SlotDetailsAPI","sadaf"+response);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray jsonArray = jsonObject.getJSONArray("Response");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
//                        gameId = jsonObject2.getString("GameId");
//                        Toast.makeText(getActivity(),"GameId:- "+gameId,Toast.LENGTH_LONG).show();
//
//                        RealTimeGuna_ShowAPI(gameId);
//
//
//                        MyBetReportOnGameAPI(gameId);
//                        TopBetHistoryAPI(gameId);
//                        AllBetHistoryAPI(gameId);
////        auto refersh layout for gameId
//                        final Handler mHandler5 = new Handler();
//                        mTicker5 = new Runnable() {
//                            @Override
//                            public void run() {
////                GetRealTime_GameStatusAPI();
//                                // user interface interactions and updates on screen
//                                mHandler5.postDelayed(mTicker5, 5000);
//                                all_bets_arrayList1.clear();
//                                my_betsarrayList1.clear();
//                                top_bets_arrayList1.clear();
//                                AllBetHistoryAPI(gameId);
//
//                                MyBetReportOnGameAPI(gameId);
//                                TopBetHistoryAPI(gameId);
//
//
//                            }
//                        };
//                        mHandler5.postDelayed(mTicker5, 5000); }
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        });
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        if (requestQueue == null) {
//            requestQueue = Volley.newRequestQueue(getActivity());
//            requestQueue.add(stringRequest);
//        } else {
//            requestQueue.add(stringRequest);
//        }
////End game Id Get API
//
//
//
//
//        //forBanner();
//        setSliderViews();
//        findViewByIds(view);
//        GetGunnaHistoryAPI();
//
//
////        auto refersh layout for gameId
//        final Handler mHandler = new Handler();
//        mTicker = new Runnable() {
//            @Override
//            public void run() {
//                SpeedAPI();
//                RealTimeGuna_InsertAPI(gameId,game_number_tv.getText().toString());
//                GetRealTime_GameStatusAPI();
//                // user interface interactions and updates on screen
//                mHandler.postDelayed(mTicker, 1000);
//            }
//        };
//        mHandler.postDelayed(mTicker, 1000);
//
//
//
//
////        auto refersh layout for gameId
//        final Handler mHandler7 = new Handler();
//        mTicker7 = new Runnable() {
//            @Override
//            public void run() {
//                SpeedAPI();
//                // user interface interactions and updates on screen
//                mHandler7.postDelayed(mTicker7, 2000);
//            }
//        };
//        mHandler.postDelayed(mTicker7, 2000);
//
//
//
//
////        auto refersh layout for gameId
//        final Handler mHandler5 = new Handler();
//        mTicker5 = new Runnable() {
//            @Override
//            public void run() { RealTimeGuna_InsertAPI(gameId,game_number_tv.getText().toString());
////                GetRealTime_GameStatusAPI();
//                // user interface interactions and updates on screen
//                mHandler5.postDelayed(mTicker5, 50000);
//
//            }
//        };
//        mHandler5.postDelayed(mTicker5, 50000);
//
//
//
//
//        x_value.setVisibility(View.GONE);
//        //   unseencount();
//
////        for start x value
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (progresscount == 100) {
//
//                    /**/
//                } else {
//                    handler.postDelayed(this, 10);
//                    progresscount++;
//                }
//            }
//        }, 4200);
//
//        Timer RunSplash = new Timer();
//        // Task to do when the timer ends
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                bet_ll.setClickable(false);
//                bet_ll1.setClickable(false);
//                cancel_bet_ll.setVisibility(View.VISIBLE);
//                cancel_bet_ll1.setVisibility(View.VISIBLE);
//                bet_ll.setVisibility(View.GONE);
//                bet_ll1.setVisibility(View.GONE);
//               CounterStart();
//            }
//        }, SPLASH_TIME_OUT);
////     end   for start x value
//
//
//
//
//
//
//
//
//
//
//
////
////for refersh layout
//        final Handler handler1 = new Handler();
//        handler1.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (progresscount1 == 100) {
//                    InsertBetGunaHistroyAPI(game_number_tv.getText().toString());
//
//                } else {
//                    handler1.postDelayed(this, 500);
//                    progresscount1++;
//                }
//            }
//        }, 17200);
//
//        Timer RunSplash1 = new Timer();
//        // Task to do when the timer ends
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                bet_ll.setClickable(true);
//                bet_ll1.setClickable(true);
//                cancel_bet_ll.setVisibility(View.GONE);
//                cancel_bet_ll1.setVisibility(View.GONE);
//                bet_ll.setVisibility(View.VISIBLE);
//                bet_ll1.setVisibility(View.VISIBLE);
//                EndGameAPI();
//            }
//        }, SPLASH_TIME_OUT1);
//
//
//
//
//
//
//
//        return view;
//    }
//
//
//    ///
//
//    private void setSliderViews() {
//        for (int i = 0; i <= 3; i++) {
//            SliderView sliderView = new SliderView(getActivity());
//            switch (i) {
//                case 0:
//                    sliderView.setImageDrawable(R.drawable.ban_1);
//                    sliderView.setDescription("Welcome To\n" +
//                            "WinningBet Company");
//                    break;
//                case 1:
//                    sliderView.setImageDrawable(R.drawable.ban_2);
////                    sliderView.setDescription("सच होगा सपना");
//                    break;
//                case 2:
//                    sliderView.setImageDrawable(R.drawable.ban_3);
//                    ;
////                    sliderView.setDescription("सोचो  एक  नयी  दुनिया ");
//                    break;
//                case 3:
//                    sliderView.setImageDrawable(R.drawable.ban_4);
//                    ;
////                    sliderView.setDescription("खुशियां  हो  जहाँ  ");
//                    break;
//                case 4:
//                    sliderView.setImageDrawable(R.drawable.a_pop1);
//                    ;
////                    sliderView.setDescription("खुशियां  हो  जहाँ  ");
//                    break;
//            }
//            sliderView.setImageScaleType(ImageView.ScaleType.FIT_XY);
//            final int finalI = i;
//            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
//                @Override
//                public void onSliderClick(SliderView sliderView) {
//                    Toast.makeText(getActivity(), "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();
//                }
//            });
//            //at last add this view in your layout :
//            sliderLayout.addSliderView(sliderView);
//        }
//    }
//
//    private void findViewByIds(View view) {
//        userTv=view.findViewById(R.id.userTv);
//        userTv1=view.findViewById(R.id.userTv1);
//        userTv2=view.findViewById(R.id.userTv2);
//        all_bets_rv=view.findViewById(R.id.all_bets_rv);
//        my_bets_rv=view.findViewById(R.id.my_bets_rv);
//        top_rv=view.findViewById(R.id.top_rv);
//        gunahistory_rv=view.findViewById(R.id.gunahistory_rv);
//        x_value=view.findViewById(R.id.x_value);
//        ImageView iv_add = (ImageView) view.findViewById(R.id.iv_add);
//        ImageView iv_minus = (ImageView) view.findViewById(R.id.iv_minus);
//         tv_qty = (EditText) view.findViewById(R.id.tv_qty);
//         tvTotal = (TextView) view.findViewById(R.id.tvTotalll);
////        srl_refresh= view.findViewById(R.id.srl_refresh);
//        tvOne = view.findViewById(R.id.tvOne);
//        tvtwo = view.findViewById(R.id.tvtwo);
//        tvthree = view.findViewById(R.id.tvthree);
//        tvfour = view.findViewById(R.id.tvfour);
//        game_number_tv=view.findViewById(R.id.game_number_tv);
//        iv_minus.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                n1 = Integer.parseInt(tv_qty.getText().toString());
//                if (n1 > 1) {
//                    n1 = n1 - 1;
//                    value = String.valueOf(n1);
//                    tv_qty.setText(value);
//                    int ProductPrice = Integer.parseInt(value);
//                    Log.v("asggsa", String.valueOf(ProductPrice * amount));
//                    tvTotal.setText(tv_qty.getText().toString()+".00 INR");
//                }
//            }
//        });
//        iv_add.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                n1 = Integer.parseInt(tv_qty.getText().toString());
//                n1 = n1 + 1;
//                value = String.valueOf(n1);
//                tv_qty.setText(value);
//                int ProductPrice = Integer.parseInt(value);
//                Log.v("asggsa", String.valueOf(ProductPrice * amount));
//                tvTotal.setText(tv_qty.getText().toString()+".00 INR");
//            }
//        });
//
//        tvOne.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv_qty.setText("100");
//                tvOne.setTextColor(Color.RED);
//                tvtwo.setTextColor(Color.WHITE);
//                tvthree.setTextColor(Color.WHITE);
//                tvfour.setTextColor(Color.WHITE);
//                tvTotal.setText(tv_qty.getText().toString()+".00 INR");
//            }
//        });
//
//        tvtwo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv_qty.setText("200");
//                tvOne.setTextColor(Color.WHITE);
//                tvtwo.setTextColor(Color.RED);
//                tvthree.setTextColor(Color.WHITE);
//                tvfour.setTextColor(Color.WHITE);
//                tvTotal.setText(tv_qty.getText().toString()+".00 INR");
//            }
//        });
//        tvthree.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv_qty.setText("500");
//                tvOne.setTextColor(Color.WHITE);
//                tvtwo.setTextColor(Color.WHITE);
//                tvthree.setTextColor(Color.RED);
//                tvfour.setTextColor(Color.WHITE);
//                tvTotal.setText(tv_qty.getText().toString()+".00 INR");
//            }
//        });
//        tvfour.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv_qty.setText("1000");
//                tvOne.setTextColor(Color.WHITE);
//                tvtwo.setTextColor(Color.WHITE);
//                tvthree.setTextColor(Color.WHITE);
//                tvfour.setTextColor(Color.RED);
//                tvTotal.setText(tv_qty.getText().toString()+".00 INR");
//            }
//        });
//
//
//
////        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
////            @Override
////            public void onRefresh() {
////                if (NetworkConnectionHelper.isOnline(getActivity())) {
////                    new Handler().postDelayed(new Runnable() {
////                        @Override
////                        public void run() {
////                            getActivity().finish();
////                            getActivity().overridePendingTransition(0, 0);
////                            getActivity().overridePendingTransition(0, 0);
////                            startActivity(getActivity().getIntent());
////                            srl_refresh.setRefreshing(false);
////                        }
////                    }, 2000);
////                } else {
////                    Toast.makeText(getActivity(), "Please check your internet connection! try again...", Toast.LENGTH_SHORT).show();
////                }
////            }
////        });
//
//        TextView all_bets_tv = view.findViewById(R.id.all_bets_tv);
//        TextView my_bets_tv = view.findViewById(R.id.my_bets_tv);
//        TextView top_tv = view.findViewById(R.id.top_tv);
//        LinearLayout all_bets_ll = view.findViewById(R.id.all_bets_ll);
//        LinearLayout my_bets_ll = view.findViewById(R.id.my_bets_ll);
//        LinearLayout top_ll = view.findViewById(R.id.top_ll);
//
//        all_bets_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                all_bets_ll.setVisibility(View.VISIBLE);
//                my_bets_ll.setVisibility(View.GONE);
//                top_ll.setVisibility(View.GONE);
//
//                all_bets_tv.setBackgroundResource(R.drawable.back_red);
//                my_bets_tv.setBackgroundResource(R.drawable.back_grey);
//                top_tv.setBackgroundResource(R.drawable.back_grey);
//
//            }
//        });
//
//        my_bets_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                all_bets_ll.setVisibility(View.GONE);
//                my_bets_ll.setVisibility(View.VISIBLE);
//                top_ll.setVisibility(View.GONE);
//                all_bets_tv.setBackgroundResource(R.drawable.back_grey);
//                my_bets_tv.setBackgroundResource(R.drawable.back_red);
//                top_tv.setBackgroundResource(R.drawable.back_grey);
//            }
//        });
//
//        top_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                all_bets_ll.setVisibility(View.GONE);
//                my_bets_ll.setVisibility(View.GONE);
//                top_ll.setVisibility(View.VISIBLE);
//                all_bets_tv.setBackgroundResource(R.drawable.back_grey);
//                my_bets_tv.setBackgroundResource(R.drawable.back_grey);
//                top_tv.setBackgroundResource(R.drawable.back_red);
//            }
//        });
//
//
//        ImageView add_layout_img = view.findViewById(R.id.add_layout_img);
//        ImageView minus_layout_img = view.findViewById(R.id.minus_layout_img);
//        LinearLayout two_ll = view.findViewById(R.id.two_ll);
//        add_layout_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                two_ll.setVisibility(View.VISIBLE);
//                add_layout_img.setVisibility(View.INVISIBLE);
//            }
//        });
//        minus_layout_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                two_ll.setVisibility(View.GONE);
//                add_layout_img.setVisibility(View.VISIBLE);
//            }
//        });
//
//
//
//
//
//        ImageView iv_add1 = (ImageView) view.findViewById(R.id.iv_add1);
//        ImageView iv_minus1 = (ImageView) view.findViewById(R.id.iv_minus1);
//         tv_qty1 = (EditText) view.findViewById(R.id.tv_qty1);
//         tvTotal1 = (TextView) view.findViewById(R.id.tvTotalll1);
//        tvOnee1 = view.findViewById(R.id.tvOne1);
//        tvtwo1 = view.findViewById(R.id.tvtwo1);
//        tvthree1 = view.findViewById(R.id.tvthree1);
//        tvfour1 = view.findViewById(R.id.tvfour1);
//        iv_minus1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                n11 = Integer.parseInt(tv_qty1.getText().toString());
//                if (n11 > 1) {
//                    n11 = n11 - 1;
//                    value1 = String.valueOf(n11);
//                    tv_qty1.setText(value1);
//                    int ProductPrice1 = Integer.parseInt(value1);
//                    Log.v("asggsa", String.valueOf(ProductPrice1 * amount1));
//                    tvTotal1.setText(tv_qty1.getText().toString()+".00 INR");
//
//                }
//
//            }
//        });
//        iv_add1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                n11 = Integer.parseInt(tv_qty1.getText().toString());
//                n11 = n11 + 1;
//                value1 = String.valueOf(n11);
//                tv_qty1.setText(value1);
//                int ProductPrice1 = Integer.parseInt(value1);
//                Log.v("asggsa", String.valueOf(ProductPrice1 * amount1));
//                tvTotal1.setText(tv_qty1.getText().toString()+".00 INR");
//
//
//            }
//        });
//
//        tvOnee1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv_qty1.setText("100");
//                tvOnee1.setTextColor(Color.RED);
//                tvtwo1.setTextColor(Color.WHITE);
//                tvthree1.setTextColor(Color.WHITE);
//                tvfour1.setTextColor(Color.WHITE);
//                tvTotal1.setText(tv_qty1.getText().toString()+".00 INR");
//
//            }
//        });
//
//        tvtwo1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv_qty1.setText("200");
//                tvOnee1.setTextColor(Color.WHITE);
//                tvtwo1.setTextColor(Color.RED);
//                tvthree1.setTextColor(Color.WHITE);
//                tvfour1.setTextColor(Color.WHITE);
//                tvTotal1.setText(tv_qty1.getText().toString()+".00 INR");
//            }
//        });
//        tvthree1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv_qty1.setText("500");
//                tvOnee1.setTextColor(Color.WHITE);
//                tvtwo1.setTextColor(Color.WHITE);
//                tvthree1.setTextColor(Color.RED);
//                tvfour1.setTextColor(Color.WHITE);
//                tvTotal1.setText(tv_qty1.getText().toString()+".00 INR");
//            }
//        });
//        tvfour1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv_qty1.setText("1000");
//                tvOnee1.setTextColor(Color.WHITE);
//                tvtwo1.setTextColor(Color.WHITE);
//                tvthree1.setTextColor(Color.WHITE);
//                tvfour1.setTextColor(Color.RED);
//                tvTotal1.setText(tv_qty1.getText().toString()+".00 INR");
//            }
//        });
//
//
//
//
//        tv_qty.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                // TODO Auto-generated method stub
//            }
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                // TODO Auto-generated method stub
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//                tvTotal.setText(s+".0 INR");
//                // Place the logic here for your output edittext
//            }
//        });
//
//
//        tv_qty1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // TODO Auto-generated method stub
//            }
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // TODO Auto-generated method stub
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//                tvTotal1.setText(s+".0 INR");
//                // Place the logic here for your output edittext
//            }
//        });
//
//
//         bet_ll = view.findViewById(R.id.bet_ll);
//         cancel_bet_ll = view.findViewById(R.id.cancel_bet_ll);
//         bet_ll1 = view.findViewById(R.id.bet_ll1);
//         cancel_bet_ll1 = view.findViewById(R.id.cancel_bet_ll1);
//         bet_ll.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 AddBetAPI(gameId,"1",tv_qty.getText().toString());
//                 Log.d("param",""+gameId+tvTotal.getText().toString());
//             }
//         });
//
//        bet_ll1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AddBetAPI(gameId,"2",tv_qty.getText().toString());
//                Log.d("param",""+gameId+tvTotal1.getText().toString());
//            }
//        });
//
//
////        bet_ll.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                cancel_bet_ll.setVisibility(View.VISIBLE);
////                bet_ll.setVisibility(View.GONE);
////            }
////        });
////        cancel_bet_ll.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                bet_ll.setVisibility(View.VISIBLE);
////                cancel_bet_ll.setVisibility(View.GONE);
////            }
////        });
////        bet_ll1.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                cancel_bet_ll1.setVisibility(View.VISIBLE);
////                bet_ll1.setVisibility(View.GONE);
////            }
////        });
////        cancel_bet_ll1.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                bet_ll1.setVisibility(View.VISIBLE);
////                cancel_bet_ll1.setVisibility(View.GONE);
////            }
////        });
//
//    }
//
//
//    private void CounterStart() {
//
//        myThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // Code to execute in the thread
//                while (!Thread.currentThread().isInterrupted()) {
//                    while (counter < total) {
//                        try {
//                            Thread.sleep(speed);    Thread.sleep(300); /*yha se counter ki speed badhegi ghategi--By anoop*/
//                        } catch (InterruptedException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                        game_number_tv.post(new Runnable() {
//                            public void run() {
//                                x_value.setVisibility(View.VISIBLE);
//                                game_number_tv.setText(counter+"");
//                                Double gameno= Double.valueOf(game_number_tv.getText().toString());
//                                Double tvtotal= Double.valueOf(tv_qty.getText().toString());
//                                Log.d("win_amount",""+gameno+tvtotal);
//                                Double win_amount=(gameno*tvtotal);
//                                final_amount= String.valueOf(win_amount);
//                                withdraw_tv.setText(final_amount);
//                                Log.d("win_amount1",""+win_amount);
//                            }
//                        });
//                        counter += 0.014;
////                    counter += 0.012;
//                    }
//                }
//            }
//        });
//        myThread.start(); // Start the thread
//
//
//
//
//    /*    final float total = 7.00F; // the total number
//        //...
//        //when you want to start the counting start the thread bellow.
//        new Thread(new Runnable() {
//            float counter = 1;
//            public void run() {
//                while (counter < total) {
//                    try {
//                        Thread.sleep(speed);  *//*  Thread.sleep(300); yha se counter ki speed badhegi ghategi--By anoop*//*
//                    } catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                    game_number_tv.post(new Runnable() {
//                        public void run() {
//                            x_value.setVisibility(View.VISIBLE);
//                            game_number_tv.setText(counter+"");
//                            Double gameno= Double.valueOf(game_number_tv.getText().toString());
//                            Double tvtotal= Double.valueOf(tv_qty.getText().toString());
//                            Log.d("win_amount",""+gameno+tvtotal);
//                            Double win_amount=(gameno*tvtotal);
//                            final_amount= String.valueOf(win_amount);
//                            withdraw_tv.setText(final_amount);
//                            Log.d("win_amount1",""+win_amount);
//
//                        }
//
//                    });
//                    counter += 0.014;
////                    counter += 0.012;
//                }
//
//            }
//
//        }).start();
//*/
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//
//        }
//    }
//
//
//
//    public void AddBetAPI(String s_game_id,String s_betid,String s_bet_amount) {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.Game_AddBetOnGameAPI, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) { Log.d("Game_AddBetOnGameAPI","sadaf"+response);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    if (jsonObject.getString("Status").equalsIgnoreCase("true")){
//                        JSONArray jsonArray = jsonObject.getJSONArray("Response");
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
//                            String msg = jsonObject2.getString("Msg");
//                            GetPKID=jsonObject2.getString("PKID");
//
//                            tvOne.setClickable(false);
//                            tvtwo.setClickable(false);
//                            tvthree.setClickable(false);
//                            tvfour.setClickable(false);
//
//
//                            withdraw_bet_ll.setVisibility(View.VISIBLE);
////                            withdraw_bet_ll1.setVisibility(View.VISIBLE);
//
//                            withdraw_bet_ll.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    GetGameWithdrawalAPI(GetPKID,final_amount,gameId,"1",game_number_tv.getText().toString());
//                                }
//                            });
//
//
//                            LayoutInflater inflater = getLayoutInflater();
//                            View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)getActivity().findViewById(R.id.custom_toast_layout));
//                            TextView tv = (TextView) layout.findViewById(R.id.txtvw);
//                            tv.setText(msg);
//                            Toast toast = new Toast(getActivity());
//                            toast.setGravity(Gravity.TOP, 0, 100);
//                            toast.setDuration(Toast.LENGTH_LONG);
//                            toast.setView(layout);
//                            toast.show();
//
//
////                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
////                            builder.setTitle(msg)
////                                    .setCancelable(false)
////                                    .setIcon(R.drawable.logo)
////                                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
////                                        public void onClick(DialogInterface dialog, int id) {
////                                            dialog.cancel();
////                                        }
////                                    });
////                            AlertDialog dialog = builder.create();
////                            dialog.show();
////                            Button buttonNegative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
////                            buttonNegative.setTextColor(ContextCompat.getColor(getActivity(), R.color.red));
////                            final Handler handler = new Handler(Looper.getMainLooper());
////                            handler.postDelayed(new Runnable() {
////                                @Override
////                                public void run() {
////                                    dialog.cancel();
////                                }
////                            }, 2000);
//
//
//                        }
//                    }
//                    else {
//                        LayoutInflater inflater = getLayoutInflater();
//                        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)getActivity().findViewById(R.id.custom_toast_layout));
//                        TextView tv = (TextView) layout.findViewById(R.id.txtvw);
//                        tv.setText(jsonObject.getString("Message"));
//                        Toast toast = new Toast(getActivity());
//                        toast.setGravity(Gravity.TOP, 0, 100);
//                        toast.setDuration(Toast.LENGTH_LONG);
//                        toast.setView(layout);
//                        toast.show();
//                        bet_ll.setVisibility(View.VISIBLE);
//                        cancel_bet_ll.setVisibility(View.GONE);
//                        withdraw_bet_ll.setVisibility(View.GONE);
//
//                    }
//
//
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> params = new HashMap<>();
//                params.put("MemberId",UserId);
//                params.put("GameId",s_game_id);
//                params.put("BetId",s_betid);
//                params.put("BetAmount",s_bet_amount);
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        if (requestQueue2 == null) {
//            requestQueue2 = Volley.newRequestQueue(getActivity());
//            requestQueue2.add(stringRequest);
//        } else {
//            requestQueue2.add(stringRequest);
//        }
//    }
//
//
//    public void GetGameWithdrawalAPI(String pid,String wamount,String gameid,String betid,String betgunna) {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.GameWithdrawalAPI, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) { Log.d("GameWithdrawalAPI","sadaf"+response);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    if(jsonObject.getString("Status").equalsIgnoreCase("true")){
//                        JSONArray jsonArray = jsonObject.getJSONArray("Response");
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
//                            LayoutInflater inflater = getLayoutInflater();
//
//
//                            View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)getActivity().findViewById(R.id.custom_toast_layout));
//                            TextView tv = (TextView) layout.findViewById(R.id.txtvw);
//                            tv.setText(jsonObject2.getString("Msg"));
//                            Toast toast = new Toast(getActivity());
//                            toast.setGravity(Gravity.TOP, 0, 100);
//                            toast.setDuration(Toast.LENGTH_LONG);
//                            toast.setView(layout);
//                            toast.show();
//
//
////                            Toast.makeText(getActivity(),jsonObject2.getString("Msg"),Toast.LENGTH_LONG).show();
//                            if (jsonObject2.getString("Msg").equalsIgnoreCase("Withdrawal Successfully.")){
//                                cancel_bet_ll.setVisibility(View.VISIBLE);
//                                withdraw_bet_ll.setVisibility(View.GONE);
//                                bet_ll.setVisibility(View.GONE);
//
//                            }
//                            cancel_bet_ll.setVisibility(View.VISIBLE);
//                            withdraw_bet_ll.setVisibility(View.GONE);
//                            bet_ll.setVisibility(View.GONE);
//                        }
//                    }else {
//                        LayoutInflater inflater = getLayoutInflater();
//                        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)getActivity().findViewById(R.id.custom_toast_layout));
//                        TextView tv = (TextView) layout.findViewById(R.id.txtvw);
//                        tv.setText(jsonObject.getString("Message"));
//                        Toast toast = new Toast(getActivity());
//                        toast.setGravity(Gravity.TOP, 0, 100);
//                        toast.setDuration(Toast.LENGTH_LONG);
//                        toast.setView(layout);
//                        toast.show();
////                        Toast.makeText(getActivity(),jsonObject.getString("Message"),Toast.LENGTH_LONG).show();
//                        getActivity().overridePendingTransition(0, 0);
//                        getActivity().overridePendingTransition(0, 0);
//                        startActivity(getActivity().getIntent());
//                    }
//
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> params = new HashMap<>();
//                params.put("PkId",pid);
//                params.put("MemberId",UserId);
//                params.put("WithdrawalAmount",wamount);
//                params.put("GameId",gameid);
//                params.put("BetId",betid);
//                params.put("BetGuna",betgunna);
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        if (requestQueue3 == null) {
//            requestQueue3 = Volley.newRequestQueue(getActivity());
//            requestQueue3.add(stringRequest);
//        } else {
//            requestQueue3.add(stringRequest);
//        }
//    }
//
//
//    public void GetRealTime_GameStatusAPI() {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.RealTime_GameStatusAPI, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) { Log.d("RealTime_GameStatusAPI","sadaf"+response);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    if (jsonObject.getString("Status").equalsIgnoreCase("true")){
//                    }
//                    else {
//                        EndGameAPI();
//                    }
//                    JSONArray jsonArray = jsonObject.getJSONArray("Response");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
////                        Toast.makeText(getActivity(),gameId,Toast.LENGTH_LONG).show();
//                    }
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> params = new HashMap<>();
//                params.put("GameId",gameId);
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        if (requestQueue1 == null) {
//            requestQueue1 = Volley.newRequestQueue(getActivity());
//            requestQueue1.add(stringRequest);
//        } else {
//            requestQueue1.add(stringRequest);
//        }
//    }
//
//
//
//    public void SpeedAPI() {
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api_Urls.GameSpeedAPI, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) { Log.d("GameSpeedAPI","sadaf"+response);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    if (jsonObject.getString("Status").equalsIgnoreCase("true")){
//                        JSONArray jsonArray = jsonObject.getJSONArray("Response");
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
//                            speed = Integer.parseInt(jsonObject2.getString("GameSpeed"));
//                        }
//                    }
//                    else {
//
//                    }
//
//
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        });
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        if (requestQueue2 == null) {
//            requestQueue2 = Volley.newRequestQueue(getActivity());
//            requestQueue2.add(stringRequest);
//        } else {
//            requestQueue2.add(stringRequest);
//        }
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////    gunna API
//public void GetGunnaHistoryAPI() {
//    final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//    progressDialog.setMessage("Loading");
//    progressDialog.show();
//    StringRequest stringRequest = new StringRequest(Request.Method.GET, Api_Urls.GetBetGunaHistroy, new Response.Listener<String>() {
//        @Override
//        public void onResponse(String response) {
//            progressDialog.dismiss();
//            String jsonString = response;
//            try {
//                JSONObject object = new JSONObject(jsonString);
//                String status = object.getString("Status");
//                if (status.equalsIgnoreCase("true")) {
//                    JSONArray Response = object.getJSONArray("Response");
//                    for (int i = 0; i < Response.length(); i++) {
//                        JSONObject jsonObject = Response.getJSONObject(i);
//                        HashMap<String, String> hashlist = new HashMap();
//                        hashlist.put("PKID", jsonObject.getString("PKID"));
//                        hashlist.put("GameId", jsonObject.getString("GameId"));
//                        hashlist.put("BetGuna", jsonObject.getString("BetGuna"));
//                        hashlist.put("IsActive", jsonObject.getString("IsActive"));
//                        hashlist.put("EntryDate", jsonObject.getString("EntryDate"));
//                        arrayList1.add(hashlist);
//                    }  GridLayoutManager gridLayoutManager =new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
////                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
//                    GunnalListAdapter customerListAdapter = new GunnalListAdapter(getActivity(), arrayList1);
//                    gunahistory_rv.setLayoutManager(gridLayoutManager);
//                    gunahistory_rv.setAdapter(customerListAdapter);
//                } else {
//                    Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }, new Response.ErrorListener() {
//        @Override
//        public void onErrorResponse(VolleyError error) {
//            progressDialog.dismiss();
//            Log.d("myTag", "message:"+error);
//            Toast.makeText(getActivity(), "Something went wrong!"+error, Toast.LENGTH_SHORT).show();
//        }
//    });
//    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//            30000,
//            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//    requestQueue.add(stringRequest);
//
//}
//    public class GunnalListAdapter extends RecyclerView.Adapter<GunnalListAdapter.VH> {
//        Context context;
//        List<HashMap<String,String>> arrayList;
//        public GunnalListAdapter(Context context, List<HashMap<String,String>> arrayList) {
//            this.arrayList=arrayList;
//            this.context=context;
//        }
//
//        @NonNull
//        @Override
//        public GunnalListAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//            View view = LayoutInflater.from(context).inflate(R.layout.dashbord_top, viewGroup, false);
//            GunnalListAdapter.VH viewHolder = new GunnalListAdapter.VH(view);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull GunnalListAdapter.VH vh, int i) {
//            AnimationHelper.animatate(context,vh.itemView, R.anim.alfa_animation);
//            vh.tv1.setText(arrayList.get(i).get("BetGuna")+"x");
//
//
//            if (arrayList.get(i).get("BetGuna").equalsIgnoreCase("null")){
//
//            }
//            else {
//                if (Double.parseDouble(arrayList.get(i).get("BetGuna")) < 2){
//                    vh.tv1.setTextColor(ContextCompat.getColor(context, R.color.purple));
//                }
//
//                else if (Double.parseDouble(arrayList.get(i).get("BetGuna")) > 2){
//                    vh.tv1.setTextColor(ContextCompat.getColor(context, R.color.skyblue));
//                }
//                else if (Double.parseDouble(arrayList.get(i).get("BetGuna")) > 3){
//                    vh.tv1.setTextColor(ContextCompat.getColor(context, R.color.pink));
//                }
//            }
//
//
//        }
//
//
//
//        @Override
//        public int getItemCount() {
//            return arrayList.size();
//        }
//        public class VH extends RecyclerView.ViewHolder {
//            TextView tv1;
//            public VH(@NonNull View itemView) {
//                super(itemView);
//                tv1 = itemView.findViewById(R.id.tv1);
//            }
//        }
//    }
//
//
//
//
////Insert gunna in top view
//    public void InsertBetGunaHistroyAPI(String betgunna) {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.InsertBetGunaHistroy, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) { Log.d("InsertBetGunaHistroy","sadaf"+response);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray jsonArray = jsonObject.getJSONArray("Response");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
////                        Toast.makeText(getActivity(),gameId,Toast.LENGTH_LONG).show();
//                    }
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> params = new HashMap<>();
//                params.put("GameId",gameId);
//                params.put("BetGuna",betgunna);
//                Log.d("pargfhfg","sadaf"+params);
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        if (requestQueue1 == null) {
//            requestQueue1 = Volley.newRequestQueue(getActivity());
//            requestQueue1.add(stringRequest);
//        } else {
//            requestQueue1.add(stringRequest);
//        }
//    }
//
//
////End Game API
////Insert gunna in top view
//public void EndGameAPI() {
//    StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.GameEnd, new Response.Listener<String>() {
//        @Override
//        public void onResponse(String response) { Log.d("GameEnd","sadaf"+response);
//            try {
//                JSONObject jsonObject = new JSONObject(response);
//                if (jsonObject.getString("Status").equalsIgnoreCase("true")){
//                    JSONArray jsonArray = jsonObject.getJSONArray("Response");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
//// Call interrupt() to stop the thread
//                        myThread.interrupt();
////                        getFragmentManager().beginTransaction().detach(HomeFragment.this).attach(HomeFragment.this).commit();
//
//                        getActivity().finish();
//                        getActivity().overridePendingTransition(0, 0);
//                        getActivity().startActivity(getActivity().getIntent());
//                        getActivity().overridePendingTransition(0, 0);
//                    }
//                }
//
//            }catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }, new Response.ErrorListener() {
//        @Override
//        public void onErrorResponse(VolleyError error) {
//        }
//    }) {
//        @Override
//        protected Map<String, String> getParams() throws AuthFailureError {
//            HashMap<String, String> params = new HashMap<>();
//            params.put("GameId",gameId);
//            Log.d("pargfhfg","sadaf"+params);
//            return params;
//        }
//    };
//    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//            30000,
//            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//    if (requestQueue1 == null) {
//        requestQueue1 = Volley.newRequestQueue(getActivity());
//        requestQueue1.add(stringRequest);
//    } else {
//        requestQueue1.add(stringRequest);
//    }
//}
//
//
//
//    //    allbets list on dashboard
//    public void MyBetReportOnGameAPI(String gid) {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.MyBetReportOnGame, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) { Log.d("MyBetReportOnGame","sadaf"+response);
//                String jsonString = response;
//                try {
//                    JSONObject object = new JSONObject(jsonString);
//                    String status = object.getString("Status");
//                    if (status.equalsIgnoreCase("true")) {
//                        JSONArray Response = object.getJSONArray("Response");
//                        for (int i = 0; i < Response.length(); i++) {  userTv1.setText(Response.length()+" bets");
//                            JSONObject jsonObject = Response.getJSONObject(i);
//                            HashMap<String, String> hashlist = new HashMap();
//                            hashlist.put("PKID", jsonObject.getString("PKID"));
//                            hashlist.put("MemberID", jsonObject.getString("MemberID"));
//                            hashlist.put("GameId", jsonObject.getString("GameId"));
//                            hashlist.put("BetId", jsonObject.getString("BetId"));
//                            hashlist.put("BetAmount", jsonObject.getString("BetAmount"));
//
//                            hashlist.put("BetGuna", jsonObject.getString("BetGuna"));
//                            hashlist.put("WithDrawalAmount", jsonObject.getString("WithDrawalAmount"));
//                            hashlist.put("Result", jsonObject.getString("Result"));
//                            hashlist.put("Entrydate", jsonObject.getString("Entrydate"));
//
//
//                            hashlist.put("MemberName", jsonObject.getString("MemberName"));
//                            hashlist.put("MobileNo", jsonObject.getString("MobileNo"));
//                            hashlist.put("NewMobileNo", jsonObject.getString("NewMobileNo"));
//
//
//                            my_betsarrayList1.add(hashlist);
//                        }
//                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
//                        MyBetsListAdapter customerListAdapter = new MyBetsListAdapter(getActivity(), my_betsarrayList1);
//                        my_bets_rv.setLayoutManager(gridLayoutManager);
//                        my_bets_rv.setAdapter(customerListAdapter);
//                    } else {
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> params = new HashMap<>();
//                params.put("MemberId",UserId);
//                params.put("GameId",gid);
//                Log.d("MyBetReportOnGame","sadaf"+params);
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        if (requestQueue1 == null) {
//            requestQueue1 = Volley.newRequestQueue(getActivity());
//            requestQueue1.add(stringRequest);
//        } else {
//            requestQueue1.add(stringRequest);
//        }
//    }
//    public class MyBetsListAdapter extends RecyclerView.Adapter<MyBetsListAdapter.VH> {
//        Context context;
//        List<HashMap<String,String>> arrayList1;
//        public MyBetsListAdapter(Context context, List<HashMap<String,String>> arrayList) {
//            this.arrayList1=arrayList;
//            this.context=context;
//        }
//
//        @NonNull
//        @Override
//        public MyBetsListAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//            View view = LayoutInflater.from(context).inflate(R.layout.das_live_data_mybat_list, viewGroup, false);
//            MyBetsListAdapter.VH viewHolder = new MyBetsListAdapter.VH(view);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull MyBetsListAdapter.VH vh, int i) {
//            AnimationHelper.animatate(context,vh.itemView, R.anim.alfa_animation);
//            vh.tv1.setText(arrayList1.get(i).get("NewMobileNo"));
//            vh.tv2.setText(arrayList1.get(i).get("BetAmount")+arrayList1.get(i).get("BetGuna")+"x");
//            vh.tv3.setText(arrayList1.get(i).get("WithDrawalAmount")+"x");
//
//
//        }
//
//
//
//        @Override
//        public int getItemCount() {
//            return arrayList1.size();
//        }
//        public class VH extends RecyclerView.ViewHolder {
//            TextView tv1,tv2,tv3;
//            public VH(@NonNull View itemView) {
//                super(itemView);
//                tv1 = itemView.findViewById(R.id.tv1);
//                tv2=itemView.findViewById(R.id.tv2);
//                tv3=itemView.findViewById(R.id.tv3);
//
//            }
//        }
//    }
//
//
//    //    mybets list on dashboard
//    public void AllBetHistoryAPI(String gid) {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.AllBetReportOnGame, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) { Log.d("AllBetReportOnGame","sadaf"+response);
//                String jsonString = response;
//                try {
//                    JSONObject object = new JSONObject(jsonString);
//                    String status = object.getString("Status");
//                    if (status.equalsIgnoreCase("true")) {
//                        JSONArray Response = object.getJSONArray("Response");
//                        for (int i = 0; i < Response.length(); i++) {
//                            userTv.setText(Response.length()+" users");
//                            JSONObject jsonObject = Response.getJSONObject(i);
//                            HashMap<String, String> hashlist = new HashMap();
//                            hashlist.put("PKID", jsonObject.getString("PKID"));
//                            hashlist.put("MemberID", jsonObject.getString("MemberID"));
//                            hashlist.put("GameId", jsonObject.getString("GameId"));
//                            hashlist.put("BetId", jsonObject.getString("BetId"));
//                            hashlist.put("BetAmount", jsonObject.getString("BetAmount"));
//
//                            hashlist.put("BetGuna", jsonObject.getString("BetGuna"));
//                            hashlist.put("WithDrawalAmount", jsonObject.getString("WithDrawalAmount"));
//                            hashlist.put("Result", jsonObject.getString("Result"));
//                            hashlist.put("Entrydate", jsonObject.getString("Entrydate"));
//
//
//                            hashlist.put("MemberName", jsonObject.getString("MemberName"));
//                            hashlist.put("MobileNo", jsonObject.getString("MobileNo"));
//                            hashlist.put("NewMobileNo", jsonObject.getString("NewMobileNo"));
//                            all_bets_arrayList1.add(hashlist);
//                        }
//                         GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
//                        AllbetsListAdapter customerListAdapter = new AllbetsListAdapter(getActivity(), all_bets_arrayList1);
//                        all_bets_rv.setLayoutManager(gridLayoutManager);
//                        all_bets_rv.setAdapter(customerListAdapter);
//                    } else {
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> params = new HashMap<>();
//                params.put("GameId",gid);
//                Log.d("MyBetReportOnGame","sadaf"+params);
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        if (requestQueue1 == null) {
//            requestQueue1 = Volley.newRequestQueue(getActivity());
//            requestQueue1.add(stringRequest);
//        } else {
//            requestQueue1.add(stringRequest);
//        }
//    }
//    public class AllbetsListAdapter extends RecyclerView.Adapter<AllbetsListAdapter.VH> {
//        Context context;
//        List<HashMap<String,String>> allarrayList;
//        public AllbetsListAdapter(Context context, List<HashMap<String,String>> arrayList) {
//            this.allarrayList=arrayList;
//            this.context=context;
//        }
//
//        @NonNull
//        @Override
//        public AllbetsListAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//            View view = LayoutInflater.from(context).inflate(R.layout.das_live_data_list, viewGroup, false);
//            AllbetsListAdapter.VH viewHolder = new AllbetsListAdapter.VH(view);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull AllbetsListAdapter.VH vh, int i) {
//            AnimationHelper.animatate(context,vh.itemView, R.anim.alfa_animation);
//            vh.tv1.setText(allarrayList.get(i).get("NewMobileNo"));
//            vh.tv2.setText(allarrayList.get(i).get("BetAmount"));
//            vh.tv3.setText(allarrayList.get(i).get("BetGuna")+"x");
//            vh.tv4.setText(allarrayList.get(i).get("WithDrawalAmount")+" INR");
//        }
//
//
//
//        @Override
//        public int getItemCount() {
//            return allarrayList.size();
//        }
//        public class VH extends RecyclerView.ViewHolder {
//            TextView tv1,tv2,tv3,tv4;
//            public VH(@NonNull View itemView) {
//                super(itemView);
//                tv1 = itemView.findViewById(R.id.tv1);
//                tv2 = itemView.findViewById(R.id.tv2);
//                tv3 = itemView.findViewById(R.id.tv3);
//                tv4 = itemView.findViewById(R.id.tv4);
//            }
//        }
//    }
//
//
//    //    topbets list on dashboard
//    public void TopBetHistoryAPI(String gid) {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.TopBetReportOnGame, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) { Log.d("TopBetReportOnGame","sadaf"+response);
//                String jsonString = response;
//                try {
//                    JSONObject object = new JSONObject(jsonString);
//                    String status = object.getString("Status");
//                    if (status.equalsIgnoreCase("true")) {
//                        JSONArray Response = object.getJSONArray("Response");
//                        for (int i = 0; i < Response.length(); i++) {  userTv2.setText(Response.length()+" top bets");
//                            JSONObject jsonObject = Response.getJSONObject(i);
//                            HashMap<String, String> hashlist = new HashMap();
//                            hashlist.put("PKID", jsonObject.getString("PKID"));
//                            hashlist.put("MemberID", jsonObject.getString("MemberID"));
//                            hashlist.put("GameId", jsonObject.getString("GameId"));
//                            hashlist.put("BetId", jsonObject.getString("BetId"));
//                            hashlist.put("BetAmount", jsonObject.getString("BetAmount"));
//
//                            hashlist.put("BetGuna", jsonObject.getString("BetGuna"));
//                            hashlist.put("WithDrawalAmount", jsonObject.getString("WithDrawalAmount"));
//                            hashlist.put("Result", jsonObject.getString("Result"));
//                            hashlist.put("Entrydate", jsonObject.getString("Entrydate"));
//
//
//                            hashlist.put("MemberName", jsonObject.getString("MemberName"));
//                            hashlist.put("MobileNo", jsonObject.getString("MobileNo"));
//                            hashlist.put("NewMobileNo", jsonObject.getString("NewMobileNo"));
//
//
//                            top_bets_arrayList1.add(hashlist);
//                        }
//                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
//                        TopBetsListAdapter customerListAdapter = new TopBetsListAdapter(getActivity(), top_bets_arrayList1);
//                        top_rv.setLayoutManager(gridLayoutManager);
//                        top_rv.setAdapter(customerListAdapter);
//                    } else {
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> params = new HashMap<>();
//                params.put("GameId",gid);
//                Log.d("MyBetReportOnGame","sadaf"+params);
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        if (requestQueue1 == null) {
//            requestQueue1 = Volley.newRequestQueue(getActivity());
//            requestQueue1.add(stringRequest);
//        } else {
//            requestQueue1.add(stringRequest);
//        }
//    }
//    public class TopBetsListAdapter extends RecyclerView.Adapter<TopBetsListAdapter.VH> {
//        Context context;
//        List<HashMap<String,String>> toparrayList;
//        public TopBetsListAdapter(Context context, List<HashMap<String,String>> arrayList) {
//            this.toparrayList=arrayList;
//            this.context=context;
//        }
//
//        @NonNull
//        @Override
//        public TopBetsListAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//            View view = LayoutInflater.from(context).inflate(R.layout.das_tobets_list, viewGroup, false);
//            TopBetsListAdapter.VH viewHolder = new TopBetsListAdapter.VH(view);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull TopBetsListAdapter.VH vh, int i) {
//            AnimationHelper.animatate(context,vh.itemView, R.anim.alfa_animation);
//            vh.tv1.setText(toparrayList.get(i).get("NewMobileNo"));
//            vh.tv2.setText(toparrayList.get(i).get("BetAmount"));
//            vh.tv3.setText(toparrayList.get(i).get("BetGuna")+"x");
//            vh.tv4.setText(toparrayList.get(i).get("WithDrawalAmount")+" INR");
//        }
//
//
//
//        @Override
//        public int getItemCount() {
//            return toparrayList.size();
//        }
//        public class VH extends RecyclerView.ViewHolder {
//            TextView tv1,tv2,tv3,tv4;
//            public VH(@NonNull View itemView) {
//                super(itemView);
//                tv1 = itemView.findViewById(R.id.tv1);
//                tv2 = itemView.findViewById(R.id.tv2);
//                tv3 = itemView.findViewById(R.id.tv3);
//                tv4 = itemView.findViewById(R.id.tv4);
//            }
//        }
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//    //End Game API
////Insert gunna in top view
//    public void RealTimeGuna_InsertAPI(String id,String betgunna) {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.RealTimeGuna_Insert, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) { Log.d("RealTimeGuna_Insert","sadaf"+response);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONArray jsonArray = jsonObject.getJSONArray("Response");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
//
//                    }
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> params = new HashMap<>();
//                params.put("GameId",id);
//                params.put("BetGuna",betgunna);
//                Log.d("pargfhfg","sadaf"+params);
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        if (requestQueue1 == null) {
//            requestQueue1 = Volley.newRequestQueue(getActivity());
//            requestQueue1.add(stringRequest);
//        } else {
//            requestQueue1.add(stringRequest);
//        }
//    }
//
//
//
//    //End Game API
////Insert gunna in top view
//    public void RealTimeGuna_ShowAPI(String g_id) {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.RealTimeGuna_Show, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) { Log.d("RealTimeGuna_Showfddsfs","sadaf"+response);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    if (jsonObject.getString("Status").equalsIgnoreCase("true")){
//                    JSONArray jsonArray = jsonObject.getJSONArray("Response");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
//                        if (jsonObject2.getString("GameId").equalsIgnoreCase(gameId)){
//                            game_number_tv.setText(jsonObject2.getString("BetGuna"));
//                            Log.d("RealTimeGuna_Showfname","sadaf"+game_number_tv.getText().toString());
//                        }
//
//                    }
//                    }
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> params = new HashMap<>();
//                params.put("GameId",g_id);
//                Log.d("efsfsdfsdf","sadaf"+params);
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        if (requestQueue1 == null) {
//            requestQueue1 = Volley.newRequestQueue(getActivity());
//            requestQueue1.add(stringRequest);
//        } else {
//            requestQueue1.add(stringRequest);
//        }
//    }
//
//
//}









/*
package com.akp.winninggator;
*//**
 * Created by Anoop Pandey on 9696381023.
 *//*

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
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

import com.akp.winninggator.Helper.AnimationHelper;
import com.akp.winninggator.SlidingMenu.GameLimitDetails;
import com.akp.winninggator.SlidingMenu.GameRuleDetails;
import com.akp.winninggator.SlidingMenu.PrivacyPolicy;
import com.akp.winninggator.SlidingMenu.SupportScreen;
import com.akp.winninggator.Util.Api_Urls;
import com.akp.winninggator.Util.Constants;
import com.akp.winninggator.Wallet.FundHistoryReport;
import com.akp.winninggator.Wallet.PaymentPage;
import com.akp.winninggator.Wallet.ViewWalletHistoryReport;
import com.akp.winninggator.Wallet.WithdrawRequestReport;
import com.akp.winninggator.storage.SharedPref;
import com.akp.winninggator.Util.Preferences;
import com.akp.winninggator.storage.SharedPrefereceUserData;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.akp.winninggator.Util.Constants.LOGINKEY;


public class dashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Context context;
    Preferences pref;
    int i = 0;
    TextView tv_Dashboard,tv_logout;
    RelativeLayout rlWallet;

    TextView tvOnee, tvtwo, tvthree, tvfour, tvfive, tvthrees,tv_fundHistory;
    EditText edt_amount;
    private AlertDialog alertDialog2;

    TextView txt_username,txt_mobile,tv_change_password,tv_Profile,tvWallet,tvAvailbleBalance,game_limit_tv,game_rule_tv,tv_privacy_policy,tv_support,tv_withdrawlHistory;
    CircleImageView civ_profile_image;
    LinearLayout ll1;
    private String UserId;

    LinearLayout view_history_ll;
    BottomNavigationView bottomNav;
    Runnable mTicker;








    SliderLayout sliderLayout;
    TextView game_number_tv;
    Activity activity;
    int n1 = 1;
    String value = String.valueOf(1);
    double amount = 0;
    TextView tvOne,tvOnee1,tvtwo1,tvthree1,tvfour1,x_value;

    int n11 = 1;
    String value1 = String.valueOf(1);
    double amount1 = 0;
    private static final int SPLASH_TIME_OUT = 3500;
    private static final int SPLASH_TIME_OUT1 = 17200;

    int progresscount = 0;

    int progresscount1 = 0;


    private static final int SPLASH_TIME_OUT2 = 1000;
    int progresscount2 = 0;
    ArrayList<HashMap<String, String>> arrayList1 = new ArrayList<>();

    //    SwipeRefreshLayout srl_refresh;
    TextView tvTotal1,tvTotal,withdraw_tv,withdraw_tv1;
    EditText tv_qty,tv_qty1;
    LinearLayout bet_ll,cancel_bet_ll,cancel_bet_ll1,bet_ll1,withdraw_bet_ll,withdraw_bet_ll1,main;
    private RequestQueue requestQueue;// Declare
    String gameId;
    private String final_amount;
    //initialisations
    Runnable mTicker5,mTicker7;
    private Handler mHandler;


    private RequestQueue requestQueue1;// Declare
    private RequestQueue requestQueue2;// Declare
    private RequestQueue requestQueue3;// Declare
    private String GetPKID;
    private int APIspeed =0;


    RecyclerView gunahistory_rv,all_bets_rv,my_bets_rv,top_rv;
    ArrayList<HashMap<String, String>> my_betsarrayList1 = new ArrayList<>();
    ArrayList<HashMap<String, String>> all_bets_arrayList1 = new ArrayList<>();
    ArrayList<HashMap<String, String>> top_bets_arrayList1 = new ArrayList<>();

    TextView userTv,userTv1,userTv2;
    RelativeLayout gif_rl;



    final float startValue = 1.0f;
    final float endValue = 50f;
    //    final float increment =  0.135f; // adjust this value to change the speed of the animation
    Handler handler;
    FloatIncrementRunnable runnable;

    TextView flew_tv;










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        SharedPref.init(getApplicationContext());
        findViewByIds();
        onClickListner();
        toolbar = findViewById(R.id.toolbar);
        context = this.getApplicationContext();
        pref = new Preferences(context);
        setSupportActionBar(toolbar);
        //toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawatv_MyOfferble.ic_menu));
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
//        HomeFragment homeFragment = new HomeFragment();
//        loadFragment(homeFragment);


//        contactUs();
        navigationItemClicks();
        //  isStoragePermissionGranted();
        checkAndRequestPermissions();
        GetWalletAPI();
        ProfileView();

        x_value.setVisibility(View.GONE);
        flew_tv.setVisibility(View.GONE);
    }

    private void onClickListner() {
        rlWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWallet();
            }
        });



        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.account:
                        Intent intent=new Intent(getApplicationContext(),user_profile.class);
                        startActivity(intent);
                        return true;
                    case R.id.back:
                        Intent intent1=new Intent(getApplicationContext(),WinningBetDashboard.class);
                        startActivity(intent1);
                        return true;
                }
                return false;
            }
        });
    }

    private void findViewByIds() {
        flew_tv=findViewById(R.id.flew_tv);

        tv_Dashboard = findViewById(R.id.tv_Dashboard);
        tv_logout = findViewById(R.id.tv_logout);
        rlWallet= findViewById(R.id.rlWallet);
        tv_fundHistory= findViewById(R.id.tv_fundHistory);
        tvWallet= findViewById(R.id.tvWallet);
        txt_username= findViewById(R.id.txt_username);
        txt_mobile= findViewById(R.id.txt_mobile);
        tv_change_password= findViewById(R.id.tv_change_password);
        tv_Profile= findViewById(R.id.tv_Profile);
        ll1= findViewById(R.id.ll1);
        civ_profile_image= findViewById(R.id.civ_profile_image);

        game_limit_tv= findViewById(R.id.game_limit_tv);
        game_rule_tv= findViewById(R.id.game_rule_tv);

        tv_privacy_policy= findViewById(R.id.tv_privacy_policy);
        tv_support= findViewById(R.id.tv_support);

        bottomNav = findViewById(R.id.bottomNav);

        tv_withdrawlHistory= findViewById(R.id.tv_withdrawlHistory);






        userTv=findViewById(R.id.userTv);
        userTv1=findViewById(R.id.userTv1);
        userTv2=findViewById(R.id.userTv2);
        all_bets_rv=findViewById(R.id.all_bets_rv);
        my_bets_rv=findViewById(R.id.my_bets_rv);
        top_rv=findViewById(R.id.top_rv);
        gunahistory_rv=findViewById(R.id.gunahistory_rv);
        x_value=findViewById(R.id.x_value);
        ImageView iv_add = (ImageView) findViewById(R.id.iv_add);
        ImageView iv_minus = (ImageView) findViewById(R.id.iv_minus);
        tv_qty = (EditText) findViewById(R.id.tv_qty);
        tvTotal = (TextView) findViewById(R.id.tvTotalll);
//        srl_refresh= findViewById(R.id.srl_refresh);
        tvOne = findViewById(R.id.tvOne);
        tvtwo = findViewById(R.id.tvtwo);
        tvthree = findViewById(R.id.tvthree);
        tvfour = findViewById(R.id.tvfour);
        game_number_tv=findViewById(R.id.game_number_tv);
        iv_minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                n1 = Integer.parseInt(tv_qty.getText().toString());
                if (n1 > 1) {
                    n1 = n1 - 1;
                    value = String.valueOf(n1);
                    tv_qty.setText(value);
                    int ProductPrice = Integer.parseInt(value);
                    Log.v("asggsa", String.valueOf(ProductPrice * amount));
                    tvTotal.setText(tv_qty.getText().toString()+".00 INR");
                }
            }
        });
        iv_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                n1 = Integer.parseInt(tv_qty.getText().toString());
                n1 = n1 + 1;
                value = String.valueOf(n1);
                tv_qty.setText(value);
                int ProductPrice = Integer.parseInt(value);
                Log.v("asggsa", String.valueOf(ProductPrice * amount));
                tvTotal.setText(tv_qty.getText().toString()+".00 INR");
            }
        });

        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_qty.setText("100");
                tvOne.setTextColor(Color.RED);
                tvtwo.setTextColor(Color.WHITE);
                tvthree.setTextColor(Color.WHITE);
                tvfour.setTextColor(Color.WHITE);
                tvTotal.setText(tv_qty.getText().toString()+".00 INR");
            }
        });

        tvtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_qty.setText("200");
                tvOne.setTextColor(Color.WHITE);
                tvtwo.setTextColor(Color.RED);
                tvthree.setTextColor(Color.WHITE);
                tvfour.setTextColor(Color.WHITE);
                tvTotal.setText(tv_qty.getText().toString()+".00 INR");
            }
        });
        tvthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_qty.setText("500");
                tvOne.setTextColor(Color.WHITE);
                tvtwo.setTextColor(Color.WHITE);
                tvthree.setTextColor(Color.RED);
                tvfour.setTextColor(Color.WHITE);
                tvTotal.setText(tv_qty.getText().toString()+".00 INR");
            }
        });
        tvfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_qty.setText("1000");
                tvOne.setTextColor(Color.WHITE);
                tvtwo.setTextColor(Color.WHITE);
                tvthree.setTextColor(Color.WHITE);
                tvfour.setTextColor(Color.RED);
                tvTotal.setText(tv_qty.getText().toString()+".00 INR");
            }
        });



//        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if (NetworkConnectionHelper.isOnline(dashboard.this)) {
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            dashboard.this.finish();
//                            dashboard.this.overridePendingTransition(0, 0);
//                            dashboard.this.overridePendingTransition(0, 0);
//                            startActivity(dashboard.this.getIntent());
//                            srl_refresh.setRefreshing(false);
//                        }
//                    }, 2000);
//                } else {
//                    Toast.makeText(dashboard.this, "Please check your internet connection! try again...", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        TextView all_bets_tv = findViewById(R.id.all_bets_tv);
        TextView my_bets_tv = findViewById(R.id.my_bets_tv);
        TextView top_tv = findViewById(R.id.top_tv);
        LinearLayout all_bets_ll = findViewById(R.id.all_bets_ll);
        LinearLayout my_bets_ll = findViewById(R.id.my_bets_ll);
        LinearLayout top_ll = findViewById(R.id.top_ll);

        all_bets_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all_bets_ll.setVisibility(View.VISIBLE);
                my_bets_ll.setVisibility(View.GONE);
                top_ll.setVisibility(View.GONE);

                all_bets_tv.setBackgroundResource(R.drawable.back_red);
                my_bets_tv.setBackgroundResource(R.drawable.back_grey);
                top_tv.setBackgroundResource(R.drawable.back_grey);

            }
        });

        my_bets_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all_bets_ll.setVisibility(View.GONE);
                my_bets_ll.setVisibility(View.VISIBLE);
                top_ll.setVisibility(View.GONE);
                all_bets_tv.setBackgroundResource(R.drawable.back_grey);
                my_bets_tv.setBackgroundResource(R.drawable.back_red);
                top_tv.setBackgroundResource(R.drawable.back_grey);
            }
        });

        top_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all_bets_ll.setVisibility(View.GONE);
                my_bets_ll.setVisibility(View.GONE);
                top_ll.setVisibility(View.VISIBLE);
                all_bets_tv.setBackgroundResource(R.drawable.back_grey);
                my_bets_tv.setBackgroundResource(R.drawable.back_grey);
                top_tv.setBackgroundResource(R.drawable.back_red);
            }
        });


        ImageView add_layout_img = findViewById(R.id.add_layout_img);
        ImageView minus_layout_img = findViewById(R.id.minus_layout_img);
        LinearLayout two_ll = findViewById(R.id.two_ll);
        add_layout_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                two_ll.setVisibility(View.VISIBLE);
                add_layout_img.setVisibility(View.INVISIBLE);
            }
        });
        minus_layout_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                two_ll.setVisibility(View.GONE);
                add_layout_img.setVisibility(View.VISIBLE);
            }
        });





        ImageView iv_add1 = (ImageView) findViewById(R.id.iv_add1);
        ImageView iv_minus1 = (ImageView) findViewById(R.id.iv_minus1);
        tv_qty1 = (EditText) findViewById(R.id.tv_qty1);
        tvTotal1 = (TextView) findViewById(R.id.tvTotalll1);
        tvOnee1 = findViewById(R.id.tvOne1);
        tvtwo1 = findViewById(R.id.tvtwo1);
        tvthree1 = findViewById(R.id.tvthree1);
        tvfour1 = findViewById(R.id.tvfour1);
        iv_minus1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                n11 = Integer.parseInt(tv_qty1.getText().toString());
                if (n11 > 1) {
                    n11 = n11 - 1;
                    value1 = String.valueOf(n11);
                    tv_qty1.setText(value1);
                    int ProductPrice1 = Integer.parseInt(value1);
                    Log.v("asggsa", String.valueOf(ProductPrice1 * amount1));
                    tvTotal1.setText(tv_qty1.getText().toString()+".00 INR");

                }

            }
        });
        iv_add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n11 = Integer.parseInt(tv_qty1.getText().toString());
                n11 = n11 + 1;
                value1 = String.valueOf(n11);
                tv_qty1.setText(value1);
                int ProductPrice1 = Integer.parseInt(value1);
                Log.v("asggsa", String.valueOf(ProductPrice1 * amount1));
                tvTotal1.setText(tv_qty1.getText().toString()+".00 INR");


            }
        });

        tvOnee1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_qty1.setText("100");
                tvOnee1.setTextColor(Color.RED);
                tvtwo1.setTextColor(Color.WHITE);
                tvthree1.setTextColor(Color.WHITE);
                tvfour1.setTextColor(Color.WHITE);
                tvTotal1.setText(tv_qty1.getText().toString()+".00 INR");

            }
        });

        tvtwo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_qty1.setText("200");
                tvOnee1.setTextColor(Color.WHITE);
                tvtwo1.setTextColor(Color.RED);
                tvthree1.setTextColor(Color.WHITE);
                tvfour1.setTextColor(Color.WHITE);
                tvTotal1.setText(tv_qty1.getText().toString()+".00 INR");
            }
        });
        tvthree1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_qty1.setText("500");
                tvOnee1.setTextColor(Color.WHITE);
                tvtwo1.setTextColor(Color.WHITE);
                tvthree1.setTextColor(Color.RED);
                tvfour1.setTextColor(Color.WHITE);
                tvTotal1.setText(tv_qty1.getText().toString()+".00 INR");
            }
        });
        tvfour1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_qty1.setText("1000");
                tvOnee1.setTextColor(Color.WHITE);
                tvtwo1.setTextColor(Color.WHITE);
                tvthree1.setTextColor(Color.WHITE);
                tvfour1.setTextColor(Color.RED);
                tvTotal1.setText(tv_qty1.getText().toString()+".00 INR");
            }
        });




        tv_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {
                tvTotal.setText(s+".0 INR");
                // Place the logic here for your output edittext
            }
        });


        tv_qty1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {
                tvTotal1.setText(s+".0 INR");
                // Place the logic here for your output edittext
            }
        });


        bet_ll = findViewById(R.id.bet_ll);
        cancel_bet_ll = findViewById(R.id.cancel_bet_ll);
        bet_ll1 = findViewById(R.id.bet_ll1);
        cancel_bet_ll1 = findViewById(R.id.cancel_bet_ll1);
        bet_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBetAPI(gameId,"1",tv_qty.getText().toString());
                Log.d("param",""+gameId+tvTotal.getText().toString());
            }
        });

        bet_ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBetAPI(gameId,"2",tv_qty.getText().toString());
                Log.d("param",""+gameId+tvTotal1.getText().toString());
            }
        });


//        bet_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cancel_bet_ll.setVisibility(View.VISIBLE);
//                bet_ll.setVisibility(View.GONE);
//            }
//        });
//        cancel_bet_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bet_ll.setVisibility(View.VISIBLE);
//                cancel_bet_ll.setVisibility(View.GONE);
//            }
//        });
//        bet_ll1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cancel_bet_ll1.setVisibility(View.VISIBLE);
//                bet_ll1.setVisibility(View.GONE);
//            }
//        });
//        cancel_bet_ll1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bet_ll1.setVisibility(View.VISIBLE);
//                cancel_bet_ll1.setVisibility(View.GONE);
//            }
//        });


        withdraw_bet_ll=findViewById(R.id.withdraw_bet_ll);
        withdraw_tv=findViewById(R.id.withdraw_tv);
        gif_rl=findViewById(R.id.gif_rl);
        withdraw_bet_ll1=findViewById(R.id.withdraw_bet_ll1);
        withdraw_tv1=findViewById(R.id.withdraw_tv1);
        main=findViewById(R.id.main);
        sliderLayout = findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL);
        sliderLayout.setScrollTimeInSec(3); //set scroll delay in seconds :


//        GetGameId API implemented here
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api_Urls.Game_SlotDetailsAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { Log.d("Game_SlotDetailsAPI","sadaf"+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        gameId = jsonObject2.getString("GameId");
                        Toast.makeText(dashboard.this,"GameId:- "+gameId,Toast.LENGTH_LONG).show();



                        MyBetReportOnGameAPI(gameId);
                        TopBetHistoryAPI(gameId);
                        AllBetHistoryAPI(gameId);
//        auto refersh layout for gameId
                        final Handler mHandler5 = new Handler();
                        mTicker5 = new Runnable() {
                            @Override
                            public void run() {
//                GetRealTime_GameStatusAPI();
                                // user interface interactions and updates on screen
                                mHandler5.postDelayed(mTicker5, 5000);
                                all_bets_arrayList1.clear();
                                my_betsarrayList1.clear();
                                top_bets_arrayList1.clear();
                                AllBetHistoryAPI(gameId);

                                MyBetReportOnGameAPI(gameId);
                                TopBetHistoryAPI(gameId);


                            }
                        };
                        mHandler5.postDelayed(mTicker5, 5000); }

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(dashboard.this);
            requestQueue.add(stringRequest);
        } else {
            requestQueue.add(stringRequest);
        }
//End game Id Get API




        //forBanner();
        setSliderViews();
        GetGunnaHistoryAPI();
        GetRealTime_GameStatusAPI(gameId);

//        auto refersh layout for gameId
        final Handler mHandler = new Handler();
        mTicker = new Runnable() {
            @Override
            public void run() {
                RealTimeGuna_InsertAPI(gameId,game_number_tv.getText().toString());
                // user interface interactions and updates on screen
                mHandler.postDelayed(mTicker, 1000);
            }
        };
        mHandler.postDelayed(mTicker, 1000);




//        auto refersh layout for gameId
        final Handler mHandler7 = new Handler();
        mTicker7 = new Runnable() {
            @Override
            public void run() {
                SpeedAPI();
                // user interface interactions and updates on screen
                mHandler7.postDelayed(mTicker7, 2000);
            }
        };
        mHandler.postDelayed(mTicker7, 2000);




//        auto refersh layout for gameId
        final Handler mHandler5 = new Handler();
        mTicker5 = new Runnable() {
            @Override
            public void run() {
                RealTimeGuna_InsertAPI(gameId,game_number_tv.getText().toString());
//                GetRealTime_GameStatusAPI();
                // user interface interactions and updates on screen
                mHandler5.postDelayed(mTicker5, 50000);

            }
        };
        mHandler5.postDelayed(mTicker5, 50000);





        //   unseencount();

//        for start x value
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progresscount == 100) {

                    *//**//*
                } else {
                    handler.postDelayed(this, 5000);
                    progresscount++;
                }
            }
        }, 5000);

        Timer RunSplash = new Timer();
        // Task to do when the timer ends
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RealTimeGuna_ShowAPI(gameId);
                bet_ll.setClickable(false);
                bet_ll1.setClickable(false);
                cancel_bet_ll.setVisibility(View.VISIBLE);
                cancel_bet_ll1.setVisibility(View.VISIBLE);
                bet_ll.setVisibility(View.GONE);
                bet_ll1.setVisibility(View.GONE);
                CounterStart();
            }
        }, SPLASH_TIME_OUT);
//     end   for start x value











//
//for refersh layout
        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progresscount1 == 100) {
                    InsertBetGunaHistroyAPI(game_number_tv.getText().toString());

                } else {
                    handler1.postDelayed(this, 17200);
                    progresscount1++;
                }
            }
        }, 17200);

        Timer RunSplash1 = new Timer();
        // Task to do when the timer ends
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bet_ll.setClickable(true);
                bet_ll1.setClickable(true);
                cancel_bet_ll.setVisibility(View.GONE);
                cancel_bet_ll1.setVisibility(View.GONE);
                bet_ll.setVisibility(View.VISIBLE);
                bet_ll1.setVisibility(View.VISIBLE);
                EndGameAPI();
            }
        }, SPLASH_TIME_OUT1);




    }




//    private void loadFragment(Fragment fragment) {
//        // load fragment
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.container, fragment);
//        //transaction.addToBackStack(null);
//        fragmentManager.popBackStack();
//        transaction.commit();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opt_menu_owner, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;
        switch (menuItem.getItemId()) {
            case R.id.bnav_Home:
//                HomeFragment homeFragment = new HomeFragment();
//                loadFragment(homeFragment);
                break;
            case R.id.bnav_History:
//                startActivity(new Intent(dashboard.this, dashboard.class));
                break;
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    void navigationItemClicks() {
        tv_Dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(dashboard.this, user_profile.class));
                drawer.closeDrawers();
            }
        });
        tv_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, user_profile.class));
                drawer.closeDrawers();
            }
        });
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, user_profile.class));
                drawer.closeDrawers();
            }
        });
        tv_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, ChangePassword.class));
                drawer.closeDrawers();
            }
        });
        tv_fundHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, FundHistoryReport.class));
                drawer.closeDrawers();
            }
        });

        game_limit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, GameLimitDetails.class));
                drawer.closeDrawers();
            }
        });

        game_rule_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, GameRuleDetails.class));
                drawer.closeDrawers();
            }
        });



        tv_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, PrivacyPolicy.class));
                drawer.closeDrawers();
            }
        });

        tv_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, SupportScreen.class));
                drawer.closeDrawers();
            }
        });



        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(dashboard.this);
                builder.setMessage(Html.fromHtml("<font color='#FF7F27'>Are you sure want to logout?</font>"));
                builder.setCancelable(false);
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Constants.clearSavedPreferences(dashboard.this, LOGINKEY);
                        final SharedPreferences.Editor sharedPreferences = new SharedPrefereceUserData(dashboard.this).getRemove();
                        sharedPreferences.putString("User", null);
                        sharedPreferences.clear();
                        sharedPreferences.commit();
                        startActivity(new Intent(dashboard.this,MainActivity.class));
                    }
                });
                android.app.AlertDialog alert = builder.create();
                alert.show();
                Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                //Set negative button background
                nbutton.setBackgroundColor(Color.RED);
                //Set negative button text color
                nbutton.setTextColor(Color.WHITE);
                Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                //Set positive button background
                pbutton.setBackgroundColor(Color.BLUE);
                //Set positive button text color
                pbutton.setTextColor(Color.WHITE);
            }
        });

        tv_withdrawlHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), WithdrawRequestReport.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkAndRequestPermissions() {
        int phone = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int storage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int loc = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int loc2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (phone != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {
                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("TAG", "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }



    private void addWallet() {

        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.add_wallet, viewGroup, false);
        ImageView rlBack = (ImageView)  dialogView.findViewById(R.id.ivback);
        tvAvailbleBalance= dialogView.findViewById(R.id.tvAvailbleBalance);
        view_history_ll=  dialogView.findViewById(R.id.view_history_ll);
        tvOnee =  dialogView.findViewById(R.id.tvOne);
        tvtwo =  dialogView.findViewById(R.id.tvtwo);
        tvthree =  dialogView.findViewById(R.id.tvthree);
        tvfour =  dialogView.findViewById(R.id.tvfour);
        tvfive =  dialogView.findViewById(R.id.tvfive);
        tvthrees =  dialogView.findViewById(R.id.tvthrees);
        edt_amount =  dialogView.findViewById(R.id.edt_amount);
        Button addButton =  dialogView.findViewById(R.id.addButton);

        view_history_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, ViewWalletHistoryReport.class));
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
                        tvWallet.setText("\u20B9 "+mainWallet);
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
        RequestQueue requestQueue = Volley.newRequestQueue(dashboard.this);
        requestQueue.add(stringRequest);
    }



    public void  ProfileView(){
        final ProgressDialog progressDialog = new ProgressDialog(dashboard.this);
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
                            String memberName = jsonobject.getString("MemberName");
                            String MobileNo = jsonobject.getString("MobileNo");
                            String EmaiLID = jsonobject.getString("EmaiLID");

                            String RegDate = jsonobject.getString("RegDate");
                            String ProfilePic = jsonobject.getString("ProfilePic");

                            if (jsonobject.getString("ProfilePic").equalsIgnoreCase("")){
                            }
                            else {
                                Glide.with(getApplicationContext()).load(jsonobject.getString("ProfilePic")).error(R.drawable.logo).into(civ_profile_image);
                            }
                            txt_username.setText(memberName);
                            txt_mobile.setText(MobileNo);

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
                Toast.makeText(dashboard.this, "Something went wrong:-" + error, Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(dashboard.this);
        requestQueue.add(stringRequest);


    }


















    private void CounterStart() {
        if (game_number_tv.getText().toString().equalsIgnoreCase("")){
            GetRealTime_GameStatusAPI(gameId);
            handler = new Handler();
            runnable = new FloatIncrementRunnable(handler, game_number_tv, startValue, endValue,APIspeed);
            runnable.start();
        }






     *//*   myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                GetRealTime_GameStatusAPI(gameId);
                // Code to execute in the thread
                while (!Thread.currentThread().isInterrupted()) {
                    while (counter < total) {
                        try {
                            Thread.sleep(speed);    Thread.sleep(300); *//**//*yha se counter ki speed badhegi ghategi--By anoop*//**//*
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        game_number_tv.post(new Runnable() {
                            public void run() {
                                x_value.setVisibility(View.VISIBLE);
                                game_number_tv.setText(counter+"");
                                Double gameno= Double.valueOf(game_number_tv.getText().toString());
                                Double tvtotal= Double.valueOf(tv_qty.getText().toString());
                                Log.d("win_amount",""+gameno+tvtotal);
                                Double win_amount=(gameno*tvtotal);
                                final_amount= String.valueOf(win_amount);
                                withdraw_tv.setText(final_amount);
                                Log.d("win_amount1",""+win_amount);
                            }
                        });
                        counter += 0.014;
//                    counter += 0.012;
                    }
                }
            }
        });
        myThread.start(); // Start the thread
*//*

    }





    public void AddBetAPI(String s_game_id,String s_betid,String s_bet_amount) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.Game_AddBetOnGameAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { Log.d("Game_AddBetOnGameAPI","sadaf"+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("Status").equalsIgnoreCase("true")){
                        JSONArray jsonArray = jsonObject.getJSONArray("Response");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            String msg = jsonObject2.getString("Msg");
                            GetPKID=jsonObject2.getString("PKID");

                            tvOne.setClickable(false);
                            tvtwo.setClickable(false);
                            tvthree.setClickable(false);
                            tvfour.setClickable(false);


                            withdraw_bet_ll.setVisibility(View.VISIBLE);
//                            withdraw_bet_ll1.setVisibility(View.VISIBLE);

                            withdraw_bet_ll.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    GetGameWithdrawalAPI(GetPKID,final_amount,gameId,"1",game_number_tv.getText().toString());
                                }
                            });


                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)dashboard.this.findViewById(R.id.custom_toast_layout));
                            TextView tv = (TextView) layout.findViewById(R.id.txtvw);
                            tv.setText(msg);
                            Toast toast = new Toast(dashboard.this);
                            toast.setGravity(Gravity.TOP, 0, 100);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();


//                            AlertDialog.Builder builder = new AlertDialog.Builder(dashboard.this);
//                            builder.setTitle(msg)
//                                    .setCancelable(false)
//                                    .setIcon(R.drawable.logo)
//                                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            dialog.cancel();
//                                        }
//                                    });
//                            AlertDialog dialog = builder.create();
//                            dialog.show();
//                            Button buttonNegative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
//                            buttonNegative.setTextColor(ContextCompat.getColor(dashboard.this, R.color.red));
//                            final Handler handler = new Handler(Looper.getMainLooper());
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    dialog.cancel();
//                                }
//                            }, 2000);


                        }
                    }
                    else {
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)dashboard.this.findViewById(R.id.custom_toast_layout));
                        TextView tv = (TextView) layout.findViewById(R.id.txtvw);
                        tv.setText(jsonObject.getString("Message"));
                        Toast toast = new Toast(dashboard.this);
                        toast.setGravity(Gravity.TOP, 0, 100);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();
                        bet_ll.setVisibility(View.VISIBLE);
                        cancel_bet_ll.setVisibility(View.GONE);
                        withdraw_bet_ll.setVisibility(View.GONE);

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
                params.put("GameId",s_game_id);
                params.put("BetId",s_betid);
                params.put("BetAmount",s_bet_amount);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        if (requestQueue2 == null) {
            requestQueue2 = Volley.newRequestQueue(dashboard.this);
            requestQueue2.add(stringRequest);
        } else {
            requestQueue2.add(stringRequest);
        }
    }


    public void GetGameWithdrawalAPI(String pid,String wamount,String gameid,String betid,String betgunna) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.GameWithdrawalAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { Log.d("GameWithdrawalAPI","sadaf"+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("Status").equalsIgnoreCase("true")){
                        JSONArray jsonArray = jsonObject.getJSONArray("Response");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            LayoutInflater inflater = getLayoutInflater();


                            View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)dashboard.this.findViewById(R.id.custom_toast_layout));
                            TextView tv = (TextView) layout.findViewById(R.id.txtvw);
                            tv.setText(jsonObject2.getString("Msg"));
                            Toast toast = new Toast(dashboard.this);
                            toast.setGravity(Gravity.TOP, 0, 100);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();


//                            Toast.makeText(dashboard.this,jsonObject2.getString("Msg"),Toast.LENGTH_LONG).show();
                            if (jsonObject2.getString("Msg").equalsIgnoreCase("Withdrawal Successfully.")){
                                cancel_bet_ll.setVisibility(View.VISIBLE);
                                withdraw_bet_ll.setVisibility(View.GONE);
                                bet_ll.setVisibility(View.GONE);

                            }
                            cancel_bet_ll.setVisibility(View.VISIBLE);
                            withdraw_bet_ll.setVisibility(View.GONE);
                            bet_ll.setVisibility(View.GONE);
                        }
                    }else {
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup)dashboard.this.findViewById(R.id.custom_toast_layout));
                        TextView tv = (TextView) layout.findViewById(R.id.txtvw);
                        tv.setText(jsonObject.getString("Message"));
                        Toast toast = new Toast(dashboard.this);
                        toast.setGravity(Gravity.TOP, 0, 100);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();
//                        Toast.makeText(dashboard.this,jsonObject.getString("Message"),Toast.LENGTH_LONG).show();
                        dashboard.this.overridePendingTransition(0, 0);
                        dashboard.this.overridePendingTransition(0, 0);
                        startActivity(dashboard.this.getIntent());
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
                params.put("PkId",pid);
                params.put("MemberId",UserId);
                params.put("WithdrawalAmount",wamount);
                params.put("GameId",gameid);
                params.put("BetId",betid);
                params.put("BetGuna",betgunna);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        if (requestQueue3 == null) {
            requestQueue3 = Volley.newRequestQueue(dashboard.this);
            requestQueue3.add(stringRequest);
        } else {
            requestQueue3.add(stringRequest);
        }
    }


    public void GetRealTime_GameStatusAPI(String g_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.RealTime_GameStatusAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { Log.d("RealTime_GameStatusAPI","sadaf"+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("Status").equalsIgnoreCase("true")){
                    }
                    else {
                        EndGameAPI();
                    }
                    JSONArray jsonArray = jsonObject.getJSONArray("Response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
//                        Toast.makeText(dashboard.this,gameId,Toast.LENGTH_LONG).show();
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
                params.put("GameId",g_id);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        if (requestQueue1 == null) {
            requestQueue1 = Volley.newRequestQueue(dashboard.this);
            requestQueue1.add(stringRequest);
        } else {
            requestQueue1.add(stringRequest);
        }
    }



    public void SpeedAPI() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api_Urls.GameSpeedAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { Log.d("GameSpeedAPI","sadaf"+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("Status").equalsIgnoreCase("true")){
                        JSONArray jsonArray = jsonObject.getJSONArray("Response");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            APIspeed = Integer.parseInt(jsonObject2.getString("GameSpeed"));
                        }
                    }
                    else {

                    }



                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        if (requestQueue2 == null) {
            requestQueue2 = Volley.newRequestQueue(dashboard.this);
            requestQueue2.add(stringRequest);
        } else {
            requestQueue2.add(stringRequest);
        }
    }












    //    gunna API
    public void GetGunnaHistoryAPI() {
        final ProgressDialog progressDialog = new ProgressDialog(dashboard.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api_Urls.GetBetGunaHistroy, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { Log.d("GetBetGunaHistroy", "message:"+response);
                progressDialog.dismiss();
                String jsonString = response;
                try {
                    JSONObject object = new JSONObject(jsonString);
                    String status = object.getString("Status");
                    if (status.equalsIgnoreCase("true")) {
                        JSONArray Response = object.getJSONArray("Response");
                        for (int i = 0; i < Response.length(); i++) {
                            JSONObject jsonObject = Response.getJSONObject(i);
                            HashMap<String, String> hashlist = new HashMap();
                            hashlist.put("PKID", jsonObject.getString("PKID"));
                            hashlist.put("GameId", jsonObject.getString("GameId"));
                            hashlist.put("BetGuna", jsonObject.getString("BetGuna"));
                            hashlist.put("IsActive", jsonObject.getString("IsActive"));
                            hashlist.put("EntryDate", jsonObject.getString("EntryDate"));
                            arrayList1.add(hashlist);
                        }  GridLayoutManager gridLayoutManager =new GridLayoutManager(dashboard.this, 1, GridLayoutManager.HORIZONTAL, false);
//                    GridLayoutManager gridLayoutManager = new GridLayoutManager(dashboard.this, 1);
                        GunnalListAdapter customerListAdapter = new GunnalListAdapter(dashboard.this, arrayList1);
                        gunahistory_rv.setLayoutManager(gridLayoutManager);
                        gunahistory_rv.setAdapter(customerListAdapter);
                    } else {
                        Toast.makeText(dashboard.this, "No data found", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(dashboard.this, "Something went wrong!"+error, Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(dashboard.this);
        requestQueue.add(stringRequest);

    }
    public class GunnalListAdapter extends RecyclerView.Adapter<GunnalListAdapter.VH> {
        Context context;
        List<HashMap<String,String>> arrayList;
        public GunnalListAdapter(Context context, List<HashMap<String,String>> arrayList) {
            this.arrayList=arrayList;
            this.context=context;
        }

        @NonNull
        @Override
        public GunnalListAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.dashbord_top, viewGroup, false);
            GunnalListAdapter.VH viewHolder = new GunnalListAdapter.VH(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull GunnalListAdapter.VH vh, int i) {
            AnimationHelper.animatate(context,vh.itemView, R.anim.alfa_animation);
            vh.tv1.setText(arrayList.get(i).get("BetGuna")+"x");


            if (arrayList.get(i).get("BetGuna").equalsIgnoreCase("null")){

            }
            else {
                if (Double.parseDouble(arrayList.get(i).get("BetGuna")) < 2){
                    vh.tv1.setTextColor(ContextCompat.getColor(context, R.color.purple));
                }

                else if (Double.parseDouble(arrayList.get(i).get("BetGuna")) > 2){
                    vh.tv1.setTextColor(ContextCompat.getColor(context, R.color.skyblue));
                }
                else if (Double.parseDouble(arrayList.get(i).get("BetGuna")) > 3){
                    vh.tv1.setTextColor(ContextCompat.getColor(context, R.color.pink));
                }
                else {
                    vh.tv1.setTextColor(ContextCompat.getColor(context, R.color.purple));
                }
            }
            if (arrayList.get(i).get("BetGuna") == null){

            }
            else {
                if (Double.parseDouble(arrayList.get(i).get("BetGuna")) < 2){
                    vh.tv1.setTextColor(ContextCompat.getColor(context, R.color.purple));
                }

                else if (Double.parseDouble(arrayList.get(i).get("BetGuna")) > 2){
                    vh.tv1.setTextColor(ContextCompat.getColor(context, R.color.skyblue));
                }
                else if (Double.parseDouble(arrayList.get(i).get("BetGuna")) > 3){
                    vh.tv1.setTextColor(ContextCompat.getColor(context, R.color.pink));
                }
                else {
                    vh.tv1.setTextColor(ContextCompat.getColor(context, R.color.purple));
                }
            }

        }



        @Override
        public int getItemCount() {
            return arrayList.size();
        }
        public class VH extends RecyclerView.ViewHolder {
            TextView tv1;
            public VH(@NonNull View itemView) {
                super(itemView);
                tv1 = itemView.findViewById(R.id.tv1);
            }
        }
    }




    //Insert gunna in top view
    public void InsertBetGunaHistroyAPI(String betgunna) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.InsertBetGunaHistroy, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { Log.d("InsertBetGunaHistroy","sadaf"+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
//                        Toast.makeText(dashboard.this,gameId,Toast.LENGTH_LONG).show();
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
                params.put("GameId",gameId);
                params.put("BetGuna",betgunna);
                Log.d("pargfhfg","sadaf"+params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        if (requestQueue1 == null) {
            requestQueue1 = Volley.newRequestQueue(dashboard.this);
            requestQueue1.add(stringRequest);
        } else {
            requestQueue1.add(stringRequest);
        }
    }


    //End Game API
//Insert gunna in top view
    public void EndGameAPI() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.GameEnd, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { Log.d("GameEnd","sadaf"+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("Status").equalsIgnoreCase("true")){
                        JSONArray jsonArray = jsonObject.getJSONArray("Response");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
// Call interrupt() to stop the thread
                            runnable.stop();
                            flew_tv.setVisibility(View.VISIBLE);
                            game_number_tv.setVisibility(View.GONE);
                            x_value.setVisibility(View.GONE);
//                        getFragmentManager().beginTransaction().detach(HomeFragment.this).attach(HomeFragment.this).commit();

                            recreate();

//                            finish();
//                            overridePendingTransition(0, 0);
//                            startActivity(getIntent());
//                            overridePendingTransition(0, 0);
                        }
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
                params.put("GameId",gameId);
                Log.d("pargfhfg","sadaf"+params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        if (requestQueue1 == null) {
            requestQueue1 = Volley.newRequestQueue(dashboard.this);
            requestQueue1.add(stringRequest);
        } else {
            requestQueue1.add(stringRequest);
        }
    }



    //    allbets list on dashboard
    public void MyBetReportOnGameAPI(String gid) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.MyBetReportOnGame, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { Log.d("MyBetReportOnGame","sadaf"+response);
                String jsonString = response;
                try {
                    JSONObject object = new JSONObject(jsonString);
                    String status = object.getString("Status");
                    if (status.equalsIgnoreCase("true")) {
                        JSONArray Response = object.getJSONArray("Response");
                        for (int i = 0; i < Response.length(); i++) {  userTv1.setText(Response.length()+" bets");
                            JSONObject jsonObject = Response.getJSONObject(i);
                            HashMap<String, String> hashlist = new HashMap();
                            hashlist.put("PKID", jsonObject.getString("PKID"));
                            hashlist.put("MemberID", jsonObject.getString("MemberID"));
                            hashlist.put("GameId", jsonObject.getString("GameId"));
                            hashlist.put("BetId", jsonObject.getString("BetId"));
                            hashlist.put("BetAmount", jsonObject.getString("BetAmount"));

                            hashlist.put("BetGuna", jsonObject.getString("BetGuna"));
                            hashlist.put("WithDrawalAmount", jsonObject.getString("WithDrawalAmount"));
                            hashlist.put("Result", jsonObject.getString("Result"));
                            hashlist.put("Entrydate", jsonObject.getString("Entrydate"));


                            hashlist.put("MemberName", jsonObject.getString("MemberName"));
                            hashlist.put("MobileNo", jsonObject.getString("MobileNo"));
                            hashlist.put("NewMobileNo", jsonObject.getString("NewMobileNo"));


                            my_betsarrayList1.add(hashlist);
                        }
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(dashboard.this, 1);
                        MyBetsListAdapter customerListAdapter = new MyBetsListAdapter(dashboard.this, my_betsarrayList1);
                        my_bets_rv.setLayoutManager(gridLayoutManager);
                        my_bets_rv.setAdapter(customerListAdapter);
                    } else {
                    }
                } catch (JSONException e) {
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
                params.put("GameId",gid);
                Log.d("MyBetReportOnGame","sadaf"+params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        if (requestQueue1 == null) {
            requestQueue1 = Volley.newRequestQueue(dashboard.this);
            requestQueue1.add(stringRequest);
        } else {
            requestQueue1.add(stringRequest);
        }
    }
    public class MyBetsListAdapter extends RecyclerView.Adapter<MyBetsListAdapter.VH> {
        Context context;
        List<HashMap<String,String>> arrayList1;
        public MyBetsListAdapter(Context context, List<HashMap<String,String>> arrayList) {
            this.arrayList1=arrayList;
            this.context=context;
        }

        @NonNull
        @Override
        public MyBetsListAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.das_live_data_mybat_list, viewGroup, false);
            MyBetsListAdapter.VH viewHolder = new MyBetsListAdapter.VH(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyBetsListAdapter.VH vh, int i) {
            AnimationHelper.animatate(context,vh.itemView, R.anim.alfa_animation);
            vh.tv1.setText(arrayList1.get(i).get("NewMobileNo"));
            vh.tv2.setText(arrayList1.get(i).get("BetAmount")+arrayList1.get(i).get("BetGuna")+"x");
            vh.tv3.setText(arrayList1.get(i).get("WithDrawalAmount")+"x");


        }



        @Override
        public int getItemCount() {
            return arrayList1.size();
        }
        public class VH extends RecyclerView.ViewHolder {
            TextView tv1,tv2,tv3;
            public VH(@NonNull View itemView) {
                super(itemView);
                tv1 = itemView.findViewById(R.id.tv1);
                tv2=itemView.findViewById(R.id.tv2);
                tv3=itemView.findViewById(R.id.tv3);

            }
        }
    }


    //    mybets list on dashboard
    public void AllBetHistoryAPI(String gid) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.AllBetReportOnGame, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { Log.d("AllBetReportOnGame","sadaf"+response);
                String jsonString = response;
                try {
                    JSONObject object = new JSONObject(jsonString);
                    String status = object.getString("Status");
                    if (status.equalsIgnoreCase("true")) {
                        JSONArray Response = object.getJSONArray("Response");
                        for (int i = 0; i < Response.length(); i++) {
                            userTv.setText(Response.length()+" users");
                            JSONObject jsonObject = Response.getJSONObject(i);
                            HashMap<String, String> hashlist = new HashMap();
                            hashlist.put("PKID", jsonObject.getString("PKID"));
                            hashlist.put("MemberID", jsonObject.getString("MemberID"));
                            hashlist.put("GameId", jsonObject.getString("GameId"));
                            hashlist.put("BetId", jsonObject.getString("BetId"));
                            hashlist.put("BetAmount", jsonObject.getString("BetAmount"));

                            hashlist.put("BetGuna", jsonObject.getString("BetGuna"));
                            hashlist.put("WithDrawalAmount", jsonObject.getString("WithDrawalAmount"));
                            hashlist.put("Result", jsonObject.getString("Result"));
                            hashlist.put("Entrydate", jsonObject.getString("Entrydate"));


                            hashlist.put("MemberName", jsonObject.getString("MemberName"));
                            hashlist.put("MobileNo", jsonObject.getString("MobileNo"));
                            hashlist.put("NewMobileNo", jsonObject.getString("NewMobileNo"));
                            all_bets_arrayList1.add(hashlist);
                        }
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(dashboard.this, 1);
                        AllbetsListAdapter customerListAdapter = new AllbetsListAdapter(dashboard.this, all_bets_arrayList1);
                        all_bets_rv.setLayoutManager(gridLayoutManager);
                        all_bets_rv.setAdapter(customerListAdapter);
                    } else {
                    }
                } catch (JSONException e) {
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
                params.put("GameId",gid);
                Log.d("MyBetReportOnGame","sadaf"+params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        if (requestQueue1 == null) {
            requestQueue1 = Volley.newRequestQueue(dashboard.this);
            requestQueue1.add(stringRequest);
        } else {
            requestQueue1.add(stringRequest);
        }
    }
    public class AllbetsListAdapter extends RecyclerView.Adapter<AllbetsListAdapter.VH> {
        Context context;
        List<HashMap<String,String>> allarrayList;
        public AllbetsListAdapter(Context context, List<HashMap<String,String>> arrayList) {
            this.allarrayList=arrayList;
            this.context=context;
        }

        @NonNull
        @Override
        public AllbetsListAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.das_live_data_list, viewGroup, false);
            AllbetsListAdapter.VH viewHolder = new AllbetsListAdapter.VH(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull AllbetsListAdapter.VH vh, int i) {
            AnimationHelper.animatate(context,vh.itemView, R.anim.alfa_animation);
            vh.tv1.setText(allarrayList.get(i).get("NewMobileNo"));
            vh.tv2.setText(allarrayList.get(i).get("BetAmount"));
            vh.tv3.setText(allarrayList.get(i).get("BetGuna")+"x");
            vh.tv4.setText(allarrayList.get(i).get("WithDrawalAmount")+" INR");
        }



        @Override
        public int getItemCount() {
            return allarrayList.size();
        }
        public class VH extends RecyclerView.ViewHolder {
            TextView tv1,tv2,tv3,tv4;
            public VH(@NonNull View itemView) {
                super(itemView);
                tv1 = itemView.findViewById(R.id.tv1);
                tv2 = itemView.findViewById(R.id.tv2);
                tv3 = itemView.findViewById(R.id.tv3);
                tv4 = itemView.findViewById(R.id.tv4);
            }
        }
    }


    //    topbets list on dashboard
    public void TopBetHistoryAPI(String gid) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.TopBetReportOnGame, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { Log.d("TopBetReportOnGame","sadaf"+response);
                String jsonString = response;
                try {
                    JSONObject object = new JSONObject(jsonString);
                    String status = object.getString("Status");
                    if (status.equalsIgnoreCase("true")) {
                        JSONArray Response = object.getJSONArray("Response");
                        for (int i = 0; i < Response.length(); i++) {  userTv2.setText(Response.length()+" top bets");
                            JSONObject jsonObject = Response.getJSONObject(i);
                            HashMap<String, String> hashlist = new HashMap();
                            hashlist.put("PKID", jsonObject.getString("PKID"));
                            hashlist.put("MemberID", jsonObject.getString("MemberID"));
                            hashlist.put("GameId", jsonObject.getString("GameId"));
                            hashlist.put("BetId", jsonObject.getString("BetId"));
                            hashlist.put("BetAmount", jsonObject.getString("BetAmount"));

                            hashlist.put("BetGuna", jsonObject.getString("BetGuna"));
                            hashlist.put("WithDrawalAmount", jsonObject.getString("WithDrawalAmount"));
                            hashlist.put("Result", jsonObject.getString("Result"));
                            hashlist.put("Entrydate", jsonObject.getString("Entrydate"));


                            hashlist.put("MemberName", jsonObject.getString("MemberName"));
                            hashlist.put("MobileNo", jsonObject.getString("MobileNo"));
                            hashlist.put("NewMobileNo", jsonObject.getString("NewMobileNo"));


                            top_bets_arrayList1.add(hashlist);
                        }
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(dashboard.this, 1);
                        TopBetsListAdapter customerListAdapter = new TopBetsListAdapter(dashboard.this, top_bets_arrayList1);
                        top_rv.setLayoutManager(gridLayoutManager);
                        top_rv.setAdapter(customerListAdapter);
                    } else {
                    }
                } catch (JSONException e) {
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
                params.put("GameId",gid);
                Log.d("MyBetReportOnGame","sadaf"+params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        if (requestQueue1 == null) {
            requestQueue1 = Volley.newRequestQueue(dashboard.this);
            requestQueue1.add(stringRequest);
        } else {
            requestQueue1.add(stringRequest);
        }
    }
    public class TopBetsListAdapter extends RecyclerView.Adapter<TopBetsListAdapter.VH> {
        Context context;
        List<HashMap<String,String>> toparrayList;
        public TopBetsListAdapter(Context context, List<HashMap<String,String>> arrayList) {
            this.toparrayList=arrayList;
            this.context=context;
        }

        @NonNull
        @Override
        public TopBetsListAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.das_tobets_list, viewGroup, false);
            TopBetsListAdapter.VH viewHolder = new TopBetsListAdapter.VH(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TopBetsListAdapter.VH vh, int i) {
            AnimationHelper.animatate(context,vh.itemView, R.anim.alfa_animation);
            vh.tv1.setText(toparrayList.get(i).get("NewMobileNo"));
            vh.tv2.setText(toparrayList.get(i).get("BetAmount"));
            vh.tv3.setText(toparrayList.get(i).get("BetGuna")+"x");
            vh.tv4.setText(toparrayList.get(i).get("WithDrawalAmount")+" INR");
        }



        @Override
        public int getItemCount() {
            return toparrayList.size();
        }
        public class VH extends RecyclerView.ViewHolder {
            TextView tv1,tv2,tv3,tv4;
            public VH(@NonNull View itemView) {
                super(itemView);
                tv1 = itemView.findViewById(R.id.tv1);
                tv2 = itemView.findViewById(R.id.tv2);
                tv3 = itemView.findViewById(R.id.tv3);
                tv4 = itemView.findViewById(R.id.tv4);
            }
        }
    }













    //End Game API
//Insert gunna in top view
    public void RealTimeGuna_InsertAPI(String id,String betgunna) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.RealTimeGuna_Insert, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { Log.d("RealTimeGuna_Insert","sadaf"+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);

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
                params.put("GameId",id);
                params.put("BetGuna",betgunna);
                Log.d("pargfhfg","sadaf"+params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        if (requestQueue1 == null) {
            requestQueue1 = Volley.newRequestQueue(dashboard.this);
            requestQueue1.add(stringRequest);
        } else {
            requestQueue1.add(stringRequest);
        }
    }



    //End Game API
//Insert gunna in top view
    public void RealTimeGuna_ShowAPI(String g_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.RealTimeGuna_Show, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { Log.d("RealTimeGuna_Showfddsfs","sadaf"+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("Status").equalsIgnoreCase("true")){
                        JSONArray jsonArray = jsonObject.getJSONArray("Response");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            if (jsonObject2.getString("GameId").equalsIgnoreCase(gameId)){
                                game_number_tv.setText(jsonObject2.getString("BetGuna"));
                                Log.d("RealTimeGuna_Showfname","sadaf"+game_number_tv.getText().toString());
                            }

                        }
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
                params.put("GameId",g_id);
                Log.d("efsfsdfsdf","sadaf"+params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        if (requestQueue1 == null) {
            requestQueue1 = Volley.newRequestQueue(dashboard.this);
            requestQueue1.add(stringRequest);
        } else {
            requestQueue1.add(stringRequest);
        }
    }
    private void setSliderViews() {
        for (int i = 0; i <= 3; i++) {
            SliderView sliderView = new SliderView(dashboard.this);
            switch (i) {
                case 0:
                    sliderView.setImageDrawable(R.drawable.ban_1);
                    sliderView.setDescription("Welcome To\n" +
                            "WinningGator Company");
                    break;
                case 1:
                    sliderView.setImageDrawable(R.drawable.ban_2);
//                    sliderView.setDescription("सच होगा सपना");
                    break;
                case 2:
                    sliderView.setImageDrawable(R.drawable.ban_3);
                    ;
//                    sliderView.setDescription("सोचो  एक  नयी  दुनिया ");
                    break;
                case 3:
                    sliderView.setImageDrawable(R.drawable.ban_4);
                    ;
//                    sliderView.setDescription("खुशियां  हो  जहाँ  ");
                    break;
                case 4:
                    sliderView.setImageDrawable(R.drawable.a_pop1);
                    ;
//                    sliderView.setDescription("खुशियां  हो  जहाँ  ");
                    break;
            }
            sliderView.setImageScaleType(ImageView.ScaleType.FIT_XY);
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Toast.makeText(dashboard.this, "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();
                }
            });
            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }





    public class FloatIncrementRunnable implements Runnable {
        private final Handler handler;
        private final TextView textView;
        private final float startValue;
        private final float endValue;
        //        private final float increment;
        private double d_value;
        private boolean started;
        private int d_speed;

        public FloatIncrementRunnable(Handler handler, TextView textView, float startValue, float endValue,int d_speed) {
            this.handler = handler;
            this.textView = textView;
            this.startValue = startValue;
            this.endValue = endValue;
//            this.increment = increment;
            this.d_value = startValue;
            this.d_speed = d_speed;
        }

        @Override
        public void run() {
            if (started) {
                textView.setText(String.valueOf(d_value));

                x_value.setVisibility(View.VISIBLE);
                Double gameno= Double.valueOf(game_number_tv.getText().toString());
                Double tvtotal= Double.valueOf(tv_qty.getText().toString());
                Log.d("win_amount",""+gameno+tvtotal);
                Double win_amount=(gameno*tvtotal);
                final_amount= String.valueOf(win_amount);
                withdraw_tv.setText(final_amount);
                Log.d("win_amount1",""+win_amount);
                if (d_value < endValue) {
                    d_value += 0.044;
//                    d_value += increment;
                    handler.postDelayed(this, APIspeed);
                }
            }
        }

        public void start() {
            if (!started) {
                started = true;
                handler.post(this);
            }
        }

        public void stop() {
            started = false;
            handler.removeCallbacks(this);
        }
    }


    @Override
    public void onBackPressed() {

    }
}*/
