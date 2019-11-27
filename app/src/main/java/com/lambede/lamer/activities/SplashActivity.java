package com.lambede.lamer.activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.analytics.Tracker;
//import com.google.firebase.analytics.FirebaseAnalytics;
import com.lambede.lamer.R;
import com.lambede.lamer.pop_up.PromptDialog;
import com.lambede.lamer.utilities.AnalyticsApplication;
import com.lambede.lamer.utilities.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class SplashActivity extends AppCompatActivity {

    CircularProgressView progressView;
    String phone, code;
    String token;
    ArrayList<String> Times = new ArrayList<>();
//    private FirebaseAnalytics mFirebaseAnalytics;
//    private Tracker mTracker;
    Button Retry;
    TextView Hint;
    TextView tvSplashTitle;
    Typeface IranYekanBold;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Retry = findViewById(R.id.splash_button);
        Hint = findViewById(R.id.splash_retry);
        tvSplashTitle = findViewById(R.id.splash_text);

        IranYekanBold = Typeface.createFromAsset(getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");
        tvSplashTitle.setTypeface(IranYekanBold);

        Retry.setVisibility(View.GONE);
        Hint.setVisibility(View.GONE);

        // Obtain the FirebaseAnalytics instance.
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //Analytics Codes
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
//        mTracker = application.getDefaultTracker();
//        mTracker.setScreenName((String) Build.MANUFACTURER + " " + Build.PRODUCT + " SplashActivity");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
//
//        mTracker.send(new HitBuilders.EventBuilder()
//                .setCategory("Open")
//                .setAction("SplashActivity")
//                .build());

        progressView = findViewById(R.id.splash_loading);

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


        if ((wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected())){
//            progressView.startAnimation();
//            getDays();
            SharedPreferences getInfo = getSharedPreferences("info", MODE_PRIVATE);
            code = getInfo.getString("code", "");
            phone = getInfo.getString("phone","");

//            if (code.equals("")){
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 3000);
//            }
//            else {
//                Login();
//            }

        }else{
            progressView.setVisibility(View.GONE);
            Retry.setVisibility(View.VISIBLE);
            Hint.setVisibility(View.VISIBLE);
        }
        Retry.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                progressView.setVisibility(View.VISIBLE);
                Retry.setVisibility(View.GONE);
                Hint.setVisibility(View.GONE);
                getDays();
                SharedPreferences getInfo = getSharedPreferences("info", MODE_PRIVATE);
                code = getInfo.getString("code", "");
                phone = getInfo.getString("phone","");

                if ((wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected())){
//                    progressView.startAnimation();

                }else{
                    progressView.setVisibility(View.GONE);
                    Retry.setVisibility(View.VISIBLE);
                    Hint.setVisibility(View.VISIBLE);
                }
            }});
    }

    public void getDays(){
        final JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, "https://www.lambede.com/api/v3/orders/getDays", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response:", response.toString());

                        getPrices();
                        try {
                            JSONArray times = response.getJSONArray("times");
                            for (int i = 0; i < times.length(); i++){
                                JSONObject items = times.getJSONObject(i);
                                String day = items.getString("day");
                                String date = items.getString("date");
                                date = day + " " +date;

                                SharedPreferences TimesSHP = getSharedPreferences("times", MODE_PRIVATE);
                                SharedPreferences.Editor timeEditor = TimesSHP.edit();
                                timeEditor.putString(i + "", date);
                                timeEditor.apply();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", error.toString());
                        popupForOnError();
                        progressView.setVisibility(View.GONE);
                        Retry.setVisibility(View.VISIBLE);
                        Hint.setVisibility(View.VISIBLE);
                    }
                });
        Singleton.getInstance(getApplicationContext()).addToRequestqueue(jsonRequest);
    }

    public void getPrices(){
        final JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, "https://www.lambede.com/api/v3/orders/prices", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response:", response.toString());

                        SharedPreferences prices = getSharedPreferences("prices", MODE_PRIVATE);
                        SharedPreferences.Editor priceEdit = prices.edit();
                        try {
                            Log.e("male 4 hours", response.getString("m4"));
                            priceEdit.putString("male 4 hours", response.getString("m4"));
                            priceEdit.putString("male 6 hours", response.getString("m6"));
                            priceEdit.putString("male 8 hours", response.getString("m8"));
                            priceEdit.putString("male 10 hours", response.getString("m10"));
                            priceEdit.putString("female 4 hours", response.getString("f4"));
                            priceEdit.putString("female 6 hours", response.getString("f6"));
                            priceEdit.putString("female 8 hours", response.getString("f8"));
                            priceEdit.putString("female 10 hours", response.getString("f10"));
                            priceEdit.putString("stairs 1", response.getString("stair1"));
                            priceEdit.putString("stairs 2", response.getString("stair2"));
                            priceEdit.putString("stairs 3", response.getString("stair3"));
                            priceEdit.putString("car 1", response.getString("car1"));
                            priceEdit.putString("car 2", response.getString("car2"));
                            priceEdit.putString("car 3", response.getString("car3"));
                            priceEdit.putString("extra 1", response.getString("extra1"));
                            priceEdit.putString("extra 2", response.getString("extra2"));
                            priceEdit.putString("extra 3", response.getString("extra3"));
                            priceEdit.putBoolean("carwash", response.getBoolean("carwash"));
                            priceEdit.apply();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (code.equals("")){
                            Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Login();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Response", error.toString());
                        popupForOnError();
                    }
                });
        Singleton.getInstance(this).addToRequestqueue(jsonRequest);
    }

    public void getInfo(){
        final JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, "https://www.lambede.com/api/v3/users/info", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response:", response.toString());
                        SharedPreferences info = getSharedPreferences("info", MODE_PRIVATE);
                        SharedPreferences.Editor infoEditor = info.edit();
                        try {
                            infoEditor.putString("name", response.getString("name"));
                            infoEditor.putString("email", response.getString("email"));
                            infoEditor.putString("phone", response.getString("phone"));
                            infoEditor.apply();
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Response", error.toString());
                        popupForOnError();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("token", token);
                return headers;
            }
        };
        Singleton.getInstance(this).addToRequestqueue(jsonRequest);
    }

    public void Login(){
        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://www.lambede.com/api/v3/users/Login",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Response", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            token = jsonObject.getString("token");
                            Log.d("Token : ", token);

                            SharedPreferences Token = getSharedPreferences( "token" , MODE_PRIVATE );
                            SharedPreferences.Editor editor = Token.edit();
                            editor.putString("token", token);
                            editor.apply();

                            getInfo();
                            getMyOrders();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        catch (JSONException e) {
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.e("Error.Response", "Error");
                        popupForOnError();
                        progressView.setVisibility(View.GONE);
                        Retry.setVisibility(View.VISIBLE);
                        Hint.setVisibility(View.VISIBLE);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("code", code);
                params.put("phone", phone);

                return params;
            }
        };
        Singleton.getInstance(this).addToRequestqueue(postRequest);
    }

    public void getMyOrders(){
        final JsonArrayRequest jsonRequest = new JsonArrayRequest
                (Request.Method.GET, "https://www.lambede.com/api/v3/orders/myOrders", null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        SharedPreferences prices = getSharedPreferences("prices", MODE_PRIVATE);
                        SharedPreferences.Editor priceEdit = prices.edit();
                        try {
                            SharedPreferences preferences = getSharedPreferences("length of my orders", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("length", response.length()).apply();

                            for (int i = response.length() - 1 ; i >= 0 ; i--){
                                JSONObject order = response.getJSONObject(i);
                                SharedPreferences myOrderSHP = getSharedPreferences("order" + i, MODE_PRIVATE);
                                SharedPreferences.Editor myOrderEdit = myOrderSHP.edit();
                                String type = order.getString("type");
                                myOrderEdit.putString("type", type);
                                if (!type.equals("کارواش")){
                                    myOrderEdit.putString("size", order.getString("size"));
                                    myOrderEdit.putString("gender", order.getString("gender"));
                                }
                                myOrderEdit.putString("dueDate", order.getString("dueDate"));
                                myOrderEdit.putString("price", order.getString("price"));
                                myOrderEdit.putString("address", order.getString("address"));
                                if (type.equals("خانه") || type.equals("محل کار")){
                                    myOrderEdit.putString("hour", order.getString("hour"));
                                    myOrderEdit.putString("lamers", order.getString("lamers"));
                                }
                                myOrderEdit.apply();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Response", error.toString());
                        popupForOnError();
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("token", token);
                return headers;}
        };
        Singleton.getInstance(this).addToRequestqueue(jsonRequest);
    }

    public void popupForOnError(){
        new PromptDialog(this)
                .setDialogType(PromptDialog.DIALOG_TYPE_ERROR)
                .setAnimationEnable(true)
                .setTitleText(R.string.error)
                .setContentText(R.string.connect_error)
                .setDismissingListener(R.string.ok, new PromptDialog.OnDismissingListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }
}
