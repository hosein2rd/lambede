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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.analytics.Tracker;
//import com.google.firebase.analytics.FirebaseAnalytics;
import com.lambede.lamer.R;
import com.lambede.lamer.pop_up.PromptDialog;
import com.lambede.lamer.utilities.AnalyticsApplication;
import com.lambede.lamer.utilities.Singleton;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.Map;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class PhoneNumberActivity extends AppCompatActivity {

    EditText Phonenumber;
    ImageView Tick;
    Button Enter;
//    private FirebaseAnalytics mFirebaseAnalytics;
//    private Tracker mTracker;
    AVLoadingIndicatorView AV;
    RelativeLayout relativeLayout;
    Typeface IranYekanBold;
    TextView tvLoginTitle;

    //------------------------------------------------------------------
    //Overriding Fonts

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    //------------------------------------------------------------------
    //onCreate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonenumber);

        //------------------------------------------------------------------
        //Analytics
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//        AnalyticsApplication application = (AnalyticsApplication) getApplication();
//        mTracker = application.getDefaultTracker();
//        mTracker.setScreenName( Build.MANUFACTURER + " " + Build.PRODUCT + " PhoneNumberActivity");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
//
//        mTracker.send(new HitBuilders.EventBuilder()
//                .setCategory("Open")
//                .setAction("PhoneNumberActivity")
//                .build());


        //------------------------------------------------------------------
        //Declaration
        Phonenumber = findViewById(R.id.phonenumber_phonnumber);
        Tick = findViewById(R.id.phonenumber_tick);
        Enter = findViewById(R.id.phonenumber_enter);
        AV = findViewById(R.id.avi);
        relativeLayout = findViewById(R.id.Relative);
        tvLoginTitle = findViewById(R.id.phonenumber_title);

        //------------------------------------------------------------------
        //Fonts
        IranYekanBold = Typeface.createFromAsset(getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");

        Enter.setTypeface(IranYekanBold);
        tvLoginTitle.setTypeface(IranYekanBold);

        //------------------------------------------------------------------
        //Phone Change Listener
        Phonenumber.addTextChangedListener(phonenumberWatcher);

        Phonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSoftKeyboard(v);
            }
        });

        //------------------------------------------------------------------
        //Enter Change Listener
        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Phonenumber.length() == 11){

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(getResources().getColor(R.color.loadingStatusBar));
                    }
//                    AV.setVisibility(View.VISIBLE);
//                    AV.show();
//                    relativeLayout.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(PhoneNumberActivity.this, VerificationActivity.class);
                    startActivity(intent);
                }
                else if (Phonenumber.length() == 0){
                    popupForEmptyField();
                }
                else {
                    popupForInCorrectField();
                }
            }
        });
    }

    private final TextWatcher phonenumberWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Tick.setVisibility(View.GONE);
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            textView.setVisibility(View.VISIBLE);
        }

        public void afterTextChanged(Editable s) {
            if (s.length() == 11) {
//                textView.setVisibility(View.GONE);
                Tick.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        Phonenumber.getWindowToken(), 0);
            } else{
//                textView.setText("You have entered : " + passwordEditText.getText());
                Tick.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void loginRequest(){
        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://www.lambede.com/api/v3/users/Loginreq",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Response", response);
                        SharedPreferences info = getApplicationContext().getSharedPreferences("info", MODE_PRIVATE);
                        SharedPreferences.Editor infoEditor = info.edit();
                        infoEditor.putString("phone", Phonenumber.getText().toString()).apply();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(getResources().getColor(R.color.white));
                        }

                        Intent intent = new Intent(PhoneNumberActivity.this, VerificationActivity.class);
                        startActivity(intent);

                        AV.setVisibility(View.GONE);
                        AV.hide();
                        relativeLayout.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.e("Error.Response", "Error");

                        int code = error.networkResponse.statusCode;

                        switch (code){
                            case 404:
                                popupForInvalidNumber();
                                break;

                            default:
                                popupForOnError();
                                break;
                        }

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
                params.put("phone", Phonenumber.getText().toString());

                return params;
            }
        };
        Singleton.getInstance(this).addToRequestqueue(postRequest);
    }

    public void popupForEmptyField(){
        new PromptDialog(this)
                .setDialogType(PromptDialog.DIALOG_TYPE_ERROR)
                .setAnimationEnable(true)
                .setTitleText(R.string.error)
                .setContentText(R.string.input_data)
                .setDismissingListener(R.string.ok, new PromptDialog.OnDismissingListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void popupForInCorrectField(){
        new PromptDialog(this)
                .setDialogType(PromptDialog.DIALOG_TYPE_ERROR)
                .setAnimationEnable(true)
                .setTitleText(R.string.error)
                .setContentText(R.string.valid_inset)
                .setDismissingListener(R.string.ok, new PromptDialog.OnDismissingListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void popupForInvalidNumber(){
        new PromptDialog(this)
                .setDialogType(PromptDialog.DIALOG_TYPE_ERROR)
                .setAnimationEnable(true)
                .setTitleText(R.string.error)
                .setContentText(R.string.is_not_registered)
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
