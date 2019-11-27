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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class RegisterActivity extends AppCompatActivity {

    Button Enter;
    TextView LogIn, tvRegisterTitle;
    EditText Name, Phone, Email;
//    private FirebaseAnalytics mFirebaseAnalytics;
//    private Tracker mTracker;
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
        setContentView(R.layout.activity_register);

        // Obtain the FirebaseAnalytics instance.
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//
//        //Analytics Codes
//        // Obtain the shared Tracker instance.
//        AnalyticsApplication application = (AnalyticsApplication) getApplication();
//        mTracker = application.getDefaultTracker();
//        mTracker.setScreenName("Register View");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Enter = findViewById(R.id.register_enter);
        LogIn = findViewById(R.id.register_login);

        Name = findViewById(R.id.register_name);
        Phone = findViewById(R.id.register_phonenumber);
        Email = findViewById(R.id.register_mail_text);
        tvRegisterTitle = findViewById(R.id.register_title);
        IranYekanBold = Typeface.createFromAsset(getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");

        tvRegisterTitle.setTypeface(IranYekanBold);
        Enter.setTypeface(IranYekanBold);

        AV = findViewById(R.id.avi);
        relativeLayout = findViewById(R.id.Relative);

        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Name.getText().toString().trim().length() == 0 ||
                        Phone.getText().toString().trim().length() == 0 ||
                        Email.getText().toString().trim().length() == 0){
                    popupForEmptyField();
                }
                else {

                    if (Phone.length() != 11){
                        popupForInvalidMobile();
                    }
                    else {
                        String MailValid, MobileValid;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(getResources().getColor(R.color.loadingStatusBar));
                        }

//                        relativeLayout.setVisibility(View.VISIBLE);
//                        AV.setVisibility(View.VISIBLE);
//                        AV.show();

                        MailValid = Email.getText().toString();
                        MobileValid = Phone.getText().toString();

                        int MailV, MobileV;

                        if (isValidMail(MailValid))
                        {
                            MailV = 1;
                        } else {
                            MailV = 0;
                        }

                        if (isValidMobile(MobileValid))
                        {
                            MobileV = 1;
                        } else {
                            MobileV = 0;
                        }

                        if (MailV == 0 && MobileV == 0)
                        {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                Window window = getWindow();
                                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                window.setStatusBarColor(getResources().getColor(R.color.white));
                            }

                            Log.e("error", "mobile and email are invalid");
                            popupForInvalidEmailAndPhone();
                            relativeLayout.setVisibility(View.GONE);
                            AV.setVisibility(View.GONE);
                            AV.hide();
                        }

                        if (MailV == 0 && MobileV == 1)
                        {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                Window window = getWindow();
                                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                window.setStatusBarColor(getResources().getColor(R.color.white));
                            }

                            Log.e("error", "email is invalid");
                            popupForInvalidEmail();
                            relativeLayout.setVisibility(View.GONE);
                            AV.setVisibility(View.GONE);
                            AV.hide();
                        }

                        if (MailV == 1 && MobileV == 0)
                        {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                Window window = getWindow();
                                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                window.setStatusBarColor(getResources().getColor(R.color.white));
                            }
                            Log.e("error", "mobile is invalid");
                            popupForInvalidMobile();
                            relativeLayout.setVisibility(View.GONE);
                            AV.setVisibility(View.GONE);
                            AV.hide();
                        }

                        if (MailV == 1 && MobileV == 1)
                        {
                            Intent intent = new Intent(RegisterActivity.this, VerificationActivity.class);
                            startActivity(intent);
//                            register();
                        }
                    }
                }
            }
        });

        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, PhoneNumberActivity.class);
                startActivity(intent);
            }
        });
    }

    public void register(){
        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://www.lambede.com/api/v3/users/register",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Response", response);

                        SharedPreferences InfoSHP = getApplicationContext().getSharedPreferences("info", MODE_PRIVATE);
                        SharedPreferences.Editor InfoEditor = InfoSHP.edit();
                        InfoEditor.putString("phone", Phone.getText().toString());
                        InfoEditor.putString("email", Email.getText().toString());
                        InfoEditor.putString("name", Name.getText().toString());
                        InfoEditor.apply();

                        loginRequest();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.e("Error.Response", "Error");

                        int code = error.networkResponse.statusCode;

                        switch (code){
                            case 409:
                                popupForFailure();
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
                params.put("name", Name.getText().toString());
                params.put("phone", Phone.getText().toString());
                params.put("email", Email.getText().toString());

                return params;
            }
        };
        Singleton.getInstance(this).addToRequestqueue(postRequest);
    }

    private boolean isValidMail(String email) {
        boolean check;
        Pattern p;
        Matcher m;

        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        p = Pattern.compile(EMAIL_STRING);

        m = p.matcher(email);
        check = m.matches();

        return check;
    }

    private boolean isValidMobile(String phone) {
        boolean check;
        if(Pattern.matches("(\\+98|0)?9\\d{9}", phone)) {
            if(phone.length() < 6 || phone.length() > 13) {
                // if(phone.length() != 10) {
                check = false;
            } else {
                check = true;
            }
        } else {
            check=false;
        }
        return check;
    }

    public void loginRequest(){
        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://www.lambede.com/api/v3/users/Loginreq",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Response", response);

                        SharedPreferences infoSHP = getSharedPreferences("info", MODE_PRIVATE);
                        SharedPreferences.Editor infoEditor = infoSHP.edit();
                        infoEditor.putString("name", Name.getText().toString());
                        infoEditor.putString("email", Email.getText().toString());
                        infoEditor.apply();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(getResources().getColor(R.color.white));
                        }

                        relativeLayout.setVisibility(View.GONE);
                        AV.setVisibility(View.GONE);
                        AV.hide();

                        Intent intent = new Intent(RegisterActivity.this, VerificationActivity.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.e("Error.Response", "Error");
                        popupForOnError();
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
                params.put("name", Name.getText().toString());
                params.put("phone", Phone.getText().toString());
                params.put("email", Email.getText().toString());

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
                .setContentText(R.string.compelete_field)
                .setDismissingListener(R.string.ok, new PromptDialog.OnDismissingListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void popupForInvalidEmail(){
        new PromptDialog(this)
                .setDialogType(PromptDialog.DIALOG_TYPE_ERROR)
                .setAnimationEnable(true)
                .setTitleText(R.string.error)
                .setContentText(R.string.invalid_mail)
                .setDismissingListener(R.string.ok, new PromptDialog.OnDismissingListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void popupForInvalidMobile(){
        new PromptDialog(this)
                .setDialogType(PromptDialog.DIALOG_TYPE_ERROR)
                .setAnimationEnable(true)
                .setTitleText(R.string.error)
                .setContentText(R.string.invalid_number)
                .setDismissingListener(R.string.ok, new PromptDialog.OnDismissingListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    public void popupForFailure(){
        new PromptDialog(this)
                .setDialogType(PromptDialog.DIALOG_TYPE_ERROR)
                .setAnimationEnable(true)
                .setTitleText(R.string.error)
                .setContentText(R.string.exist_number)
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

    public void popupForInvalidEmailAndPhone(){
        new PromptDialog(this)
                .setDialogType(PromptDialog.DIALOG_TYPE_ERROR)
                .setAnimationEnable(true)
                .setTitleText(R.string.error)
                .setContentText(R.string.invalid_email_mobile)
                .setDismissingListener(R.string.ok, new PromptDialog.OnDismissingListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }
}
