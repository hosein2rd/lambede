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

public class SuccessActivity extends AppCompatActivity {

    TextView Support;
    Button GoHome;
    ImageView GIF;
    TextView ID;
    Typeface IranYekanBold;
    TextView Title;
//    private FirebaseAnalytics mFirebaseAnalytics;
//    private Tracker mTracker;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_success);

        // Obtain the FirebaseAnalytics instance.
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //Analytics Codes
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
//        mTracker = application.getDefaultTracker();
//        mTracker.setScreenName("App Installed");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        IranYekanBold = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");

        SharedPreferences idSHP = getSharedPreferences("id", Context.MODE_PRIVATE);
        String id = idSHP.getString("id", "");

        Support = (TextView)findViewById(R.id.success_home_button);
        GoHome = (Button)findViewById(R.id.success_home_button);
        ID = findViewById(R.id.textView2);
        Title = findViewById(R.id.success_text_title);

        Support.setTypeface(IranYekanBold);
        GoHome.setTypeface(IranYekanBold);
        Title.setTypeface(IranYekanBold);

        ID.setText(id);

        GIF = (ImageView)findViewById(R.id.success_image_gif);
        Glide.with(getApplicationContext()).
                load(R.drawable.loader_gif).into(GIF);

        Support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportClicked();
            }
        });

        GoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHomeClicked();
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
