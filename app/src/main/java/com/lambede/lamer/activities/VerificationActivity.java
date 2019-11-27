package com.lambede.lamer.activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.lambede.lamer.R;
import com.lambede.lamer.pop_up.PromptDialog;
import com.lambede.lamer.utilities.Singleton;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VerificationActivity extends AppCompatActivity {

    Button Enter;
    EditText ED1, ED2, ED3, ED4;
    String phone;
    String token;
    TextView Retry, tvVerificationTitle;
    AVLoadingIndicatorView AV;
    RelativeLayout relativeLayout;
    Typeface IranYekanBold;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_verification);

        Enter = findViewById(R.id.verification_enter);
        ED1 = findViewById(R.id.verification_number1);
        ED2 = findViewById(R.id.verification_number2);
        ED3 = findViewById(R.id.verification_number3);
        ED4 = findViewById(R.id.verification_number4);
        Retry = findViewById(R.id.verification_retry);
        AV = findViewById(R.id.avi);
        relativeLayout = findViewById(R.id.Relative);
        tvVerificationTitle = findViewById(R.id.verification_title);

        IranYekanBold = Typeface.createFromAsset(getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");
        tvVerificationTitle.setTypeface(IranYekanBold);
        Enter.setTypeface(IranYekanBold);

        SharedPreferences phoneSHP = getApplicationContext().getSharedPreferences("info", MODE_PRIVATE);
        phone = phoneSHP.getString("phone", "");

        ED1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ED1.length() == 1){
                    ED1.clearFocus();
                    ED2.requestFocus();
                }
            }
        });

        ED2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ED2.length() == 1){
                    ED2.clearFocus();
                    ED3.requestFocus();
                }
            }
        });

        ED3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ED3.length() == 1){
                    ED3.clearFocus();
                    ED4.requestFocus();
                }
            }
        });

        ED4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        ED4.getWindowToken(), 0);            }
        });

        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ED1.length() != 0 && ED2.length() != 0 && ED3.length() != 0 && ED4.length() != 0){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(getResources().getColor(R.color.loadingStatusBar));
                    }
                    Intent intent = new Intent(VerificationActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    popupForEmptyfield();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(getResources().getColor(R.color.white));
                    }
                    AV.setVisibility(View.GONE);
                    AV.hide();
                    relativeLayout.setVisibility(View.GONE);
                }
            }
        });

        Retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retryClicked();
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    public void Login(){
        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://www.lambede.com/api/v3/users/Login",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {


                        if (response.equals("{\"error\":\"Wrong Code\"}")){

                            popupForInvalidCode();

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                Window window = getWindow();
                                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                window.setStatusBarColor(getResources().getColor(R.color.white));
                            }

                            AV.setVisibility(View.GONE);
                            AV.hide();
                            relativeLayout.setVisibility(View.GONE);
                        }
                        else {
                            getMyOrders();
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                //refrence_code = jsonObject.getString("referenceCode");
//                                            if(user != null && pass != null){
                                token = jsonObject.getString("token");

                                SharedPreferences Token = getSharedPreferences( "token" , MODE_PRIVATE );
                                SharedPreferences.Editor editor = Token.edit();
                                editor.putString("token", token);
                                editor.apply();

                                SharedPreferences info = getSharedPreferences("info", MODE_PRIVATE);
                                SharedPreferences.Editor infoEditor = info.edit();
                                infoEditor.putString("code", ED1.getText().toString() + ED2.getText().toString() + ED3.getText().toString() + ED4.getText().toString());
                                infoEditor.apply();

                                getInfo();
                            }
                            catch (JSONException e) {
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(getResources().getColor(R.color.white));
                        }

                        AV.setVisibility(View.GONE);
                        AV.hide();
                        relativeLayout.setVisibility(View.GONE);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("code", ED1.getText().toString() + ED2.getText().toString() + ED3.getText().toString() + ED4.getText().toString());
                params.put("phone", phone);

                return params;
            }
        };
        Singleton.getInstance(this).addToRequestqueue(postRequest);
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

                        Intent intent = new Intent(VerificationActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                        AV.setVisibility(View.GONE);
                        AV.hide();
                        relativeLayout.setVisibility(View.GONE);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(getResources().getColor(R.color.white));
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Response", error.toString());

                        popupForOnError();

                        AV.setVisibility(View.GONE);
                        AV.hide();
                        relativeLayout.setVisibility(View.GONE);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(getResources().getColor(R.color.white));
                        }
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

    public void popupForEmptyfield(){
        new PromptDialog(this)
                .setDialogType(PromptDialog.DIALOG_TYPE_ERROR)
                .setAnimationEnable(true)
                .setTitleText(R.string.error)
                .setContentText(R.string.inset_correctly)
                .setDismissingListener(R.string.ok, new PromptDialog.OnDismissingListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void popupForInvalidCode(){
        new PromptDialog(this)
                .setDialogType(PromptDialog.DIALOG_TYPE_ERROR)
                .setAnimationEnable(true)
                .setTitleText(R.string.error)
                .setContentText(R.string.invalid_code)
                .setDismissingListener(R.string.ok, new PromptDialog.OnDismissingListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void getMyOrders(){
        final JsonArrayRequest jsonRequest = new JsonArrayRequest
                (Request.Method.GET, "https://www.lambede.com/api/v3/orders/myOrders", null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Response:", response.toString());

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

    public void retryClicked(){
        loginRequest();
    }

    public void loginRequest(){
        final SharedPreferences phoneNumber = getSharedPreferences("info", MODE_PRIVATE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://www.lambede.com/api/v3/users/Loginreq",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Response", response);
                        popupForRetry();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.e("Error.Response", "Error");
                        popupForOnError();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("phone", phoneNumber.getString("phone", ""));

                return params;
            }
        };
        Singleton.getInstance(this).addToRequestqueue(postRequest);
    }

    public void popupForRetry(){
        new PromptDialog(this)
                .setDialogType(PromptDialog.DIALOG_TYPE_ERROR)
                .setAnimationEnable(true)
                .setTitleText(R.string.message)
                .setContentText(R.string.get_code_again)
                .setDismissingListener(R.string.ok, new PromptDialog.OnDismissingListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
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
