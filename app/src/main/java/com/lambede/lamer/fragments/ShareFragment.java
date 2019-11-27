package com.lambede.lamer.fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.analytics.Tracker;
//import com.google.firebase.analytics.FirebaseAnalytics;

import com.lambede.lamer.R;
import com.lambede.lamer.pop_up.PromptDialog;
import com.lambede.lamer.utilities.AnalyticsApplication;


public class ShareFragment extends Fragment {


    public ShareFragment() {
        // Required empty public constructor
    }

    Button Share;
    TextView tvShareTitle, tvVersion;
    Typeface IranYekanBold;
    Typeface IranYekanLight;
//    private FirebaseAnalytics mFirebaseAnalytics;
//    private Tracker mTracker;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);

        // Obtain the FirebaseAnalytics instance.
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
//
//        //Analytics Codes
//        // Obtain the shared Tracker instance.
//        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
//        mTracker = application.getDefaultTracker();
//        mTracker.setScreenName("Invite Friends");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Share = view.findViewById(R.id.share_invite);
        tvShareTitle = view.findViewById(R.id.share_title);
        tvVersion = view.findViewById(R.id.share_version);

        IranYekanBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");
        IranYekanLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANYekanLightMobile(FaNum).ttf");
        tvShareTitle.setTypeface(IranYekanBold);
        Share.setTypeface(IranYekanBold);
        tvVersion.setTypeface(IranYekanLight);

        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        return view;
    }

    public void share(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "برای دانلود اپلیکشن لم بده، سرویس سفارش آنلاین خدمات خانگی رو لینک زیر کلیک کنید:\n" +
                        "https://lambede.com/app");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

}
