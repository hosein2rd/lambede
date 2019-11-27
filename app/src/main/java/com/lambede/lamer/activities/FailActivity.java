package com.lambede.lamer.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.analytics.Tracker;
//import com.google.firebase.analytics.FirebaseAnalytics;
import com.lambede.lamer.R;
import com.lambede.lamer.pop_up.PromptDialog;
import com.lambede.lamer.utilities.AnalyticsApplication;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FailActivity extends AppCompatActivity {

    TextView Support;
    Button GoHome;
    ImageView GIF;
    Typeface IranYekanBold;
    TextView Title, Content;
//    private FirebaseAnalytics mFirebaseAnalytics;
//    private Tracker mTracker;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail);

        // Obtain the FirebaseAnalytics instance.
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //Analytics Codes
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
//        mTracker = application.getDefaultTracker();
//        mTracker.setScreenName("App Installed");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        IranYekanBold = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");

        Support = (TextView)findViewById(R.id.fail_support);
        GoHome = (Button)findViewById(R.id.fail_home_button);
        Title = findViewById(R.id.fail_text_title);
        Content = findViewById(R.id.content);

        Title.setTypeface(IranYekanBold);
        GoHome.setTypeface(IranYekanBold);
        Support.setTypeface(IranYekanBold);

        SharedPreferences AddressSHP = getSharedPreferences("address", Context.MODE_PRIVATE);
        SharedPreferences.Editor addressEditor = AddressSHP.edit();
        addressEditor.clear().apply();

        GIF = (ImageView)findViewById(R.id.fail_image_gif);
        Glide.with(getApplicationContext()).
                load(R.drawable.failed_gif).into(GIF);

        GoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHomeClicked();
            }
        });

        Support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportClicked();
            }
        });
    }

    public void goHomeClicked(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void supportClicked(){
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:+982128429292"));
        startActivity(callIntent);
    }
}
