package com.lambede.lamer.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.analytics.Tracker;
//import com.google.firebase.analytics.FirebaseAnalytics;

import com.lambede.lamer.R;
import com.lambede.lamer.pop_up.PromptDialog;
import com.lambede.lamer.utilities.AnalyticsApplication;


public class FirstFragment extends Fragment {

    Button Plus, Minus;
    CardView SCard, MCard, LCard;
    TextView
            HourText, TextHint,
            S1Text, M1Text, L1Text,
            S2Text, M2Text, L2Text,
            S3Text, M3Text, L3Text,
            tvFirstSizeTitle, tvFirstHourTitle;
    int Size = 1, Hour = 4;
    TextView Cost, tvFirstTitle;
    SharedPreferences orderInfo;
    SharedPreferences.Editor orderInfoEdit;
    FrameLayout Next;
    Typeface IranYekanBold;
//    private FirebaseAnalytics mFirebaseAnalytics;
//    private Tracker mTracker;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        // Obtain the FirebaseAnalytics instance.
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
//
//        //Analytics Codes
//        // Obtain the shared Tracker instance.
//        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
//        mTracker = application.getDefaultTracker();
//        mTracker.setScreenName("Resident Order 1");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Next = view.findViewById(R.id.first_button);
        HourText = view.findViewById(R.id.first_hour);
        TextHint = view.findViewById(R.id.first_texthint);
        Plus = view.findViewById(R.id.first_plus);
        Minus = view.findViewById(R.id.first_minus);
        SCard = view.findViewById(R.id.first_scard);
        MCard = view.findViewById(R.id.first_mcard);
        LCard = view.findViewById(R.id.first_lcard);
        S1Text = view.findViewById(R.id.first_stitle);
        S2Text = view.findViewById(R.id.first_simage);
        S3Text = view.findViewById(R.id.first_stext);
        M1Text = view.findViewById(R.id.first_mtitle);
        M2Text = view.findViewById(R.id.first_mimage);
        M3Text = view.findViewById(R.id.first_mtext);
        L1Text = view.findViewById(R.id.first_ltitle);
        L2Text = view.findViewById(R.id.first_limage);
        L3Text = view.findViewById(R.id.first_ltext);
        Cost = view.findViewById(R.id.first_cost);
        tvFirstTitle = view.findViewById(R.id.first_title);
        tvFirstHourTitle = view.findViewById(R.id.first_hourtitle);
        tvFirstSizeTitle = view.findViewById(R.id.first_sizetitle);

        IranYekanBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");
        tvFirstTitle.setTypeface(IranYekanBold);
        Minus.setTypeface(IranYekanBold);
        Plus.setTypeface(IranYekanBold);
        S1Text.setTypeface(IranYekanBold);
        S2Text.setTypeface(IranYekanBold);
        L1Text.setTypeface(IranYekanBold);
        L2Text.setTypeface(IranYekanBold);
        M1Text.setTypeface(IranYekanBold);
        M2Text.setTypeface(IranYekanBold);
        HourText.setTypeface(IranYekanBold);
        Cost.setTypeface(IranYekanBold);
        tvFirstSizeTitle.setTypeface(IranYekanBold);
        tvFirstHourTitle.setTypeface(IranYekanBold);


        orderInfo = getActivity().getSharedPreferences("order info", Context.MODE_PRIVATE);
        orderInfoEdit = orderInfo.edit();

        SCard.setCardBackgroundColor(Color.parseColor("#329ba6"));
        MCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        LCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        S1Text.setTextColor(Color.parseColor("#FFFFFF"));
        S2Text.setTextColor(Color.parseColor("#FFFFFF"));
        S3Text.setTextColor(Color.parseColor("#FFFFFF"));
        M1Text.setTextColor(Color.parseColor("#329ba6"));
        M2Text.setTextColor(Color.parseColor("#329ba6"));
        M3Text.setTextColor(Color.parseColor("#329ba6"));
        L1Text.setTextColor(Color.parseColor("#329ba6"));
        L2Text.setTextColor(Color.parseColor("#329ba6"));
        L3Text.setTextColor(Color.parseColor("#329ba6"));
        orderInfoEdit.putString("size", "نقلی").apply();

        Size = 1;
        Hour = 4;

        final SharedPreferences prices = getActivity().getSharedPreferences("prices", Context.MODE_PRIVATE);

        Cost.setText("هزینه سفارش: " + prices.getString("male 4 hours", "") + " تومان");

        SharedPreferences hoursSHP = getActivity().getSharedPreferences("hours", Context.MODE_PRIVATE);
        final SharedPreferences.Editor hoursEditor = hoursSHP.edit();

        SharedPreferences CostSHP = getActivity().getSharedPreferences("cost", Context.MODE_PRIVATE);
        final SharedPreferences.Editor CostEditor = CostSHP.edit();

        SCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Size == 2 || Size == 3)
                {
                    Size = 1;
                    Hour = 4;
                    HourText.setText("4 ساعت");
                    TextHint.setText("حداقل زمان تمیزکاری بر اساس متراژ واحد شما 4 ساعت می باشد.");
                    SCard.setCardBackgroundColor(Color.parseColor("#329ba6"));
                    MCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    LCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    S1Text.setTextColor(Color.parseColor("#FFFFFF"));
                    S2Text.setTextColor(Color.parseColor("#FFFFFF"));
                    S3Text.setTextColor(Color.parseColor("#FFFFFF"));
                    M1Text.setTextColor(Color.parseColor("#329ba6"));
                    M2Text.setTextColor(Color.parseColor("#329ba6"));
                    M3Text.setTextColor(Color.parseColor("#329ba6"));
                    L1Text.setTextColor(Color.parseColor("#329ba6"));
                    L2Text.setTextColor(Color.parseColor("#329ba6"));
                    L3Text.setTextColor(Color.parseColor("#329ba6"));
                    orderInfoEdit.putString("size", "نقلی").apply();
                }

                hoursEditor.putString("hours", String.valueOf(Hour)).apply();
                if (Hour == 4){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 4 hours", "") + " تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }
                else if (Hour == 6){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 6 hours", "") + " تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }
                else if (Hour == 8){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 8 hours", "") + " تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }
                else if (Hour == 10){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 10 hours", "") + " تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }
            }
        });

        MCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Size == 1 || Size == 3)
                {
                    Size = 2;
                    Hour = 6;
                    HourText.setText("6 ساعت");
                    TextHint.setText("حداقل زمان تمیزکاری بر اساس متراژ واحد شما 6 ساعت می باشد.");
                    SCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    MCard.setCardBackgroundColor(Color.parseColor("#329ba6"));
                    LCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    S1Text.setTextColor(Color.parseColor("#329ba6"));
                    S2Text.setTextColor(Color.parseColor("#329ba6"));
                    S3Text.setTextColor(Color.parseColor("#329ba6"));
                    M1Text.setTextColor(Color.parseColor("#FFFFFF"));
                    M2Text.setTextColor(Color.parseColor("#FFFFFF"));
                    M3Text.setTextColor(Color.parseColor("#FFFFFF"));
                    L1Text.setTextColor(Color.parseColor("#329ba6"));
                    L2Text.setTextColor(Color.parseColor("#329ba6"));
                    L3Text.setTextColor(Color.parseColor("#329ba6"));
                    orderInfoEdit.putString("size", "متوسط").apply();
                }

                SharedPreferences prices = getActivity().getSharedPreferences("prices", Context.MODE_PRIVATE);

                hoursEditor.putString("hours", String.valueOf(Hour)).apply();
                if (Hour == 4){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 4 hours", "") + " تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }
                else if (Hour == 6){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 6 hours", "") + " تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }
                else if (Hour == 8){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 8 hours", "") + " تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }
                else if (Hour == 10){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 10 hours", "") + " تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }
            }
        });

        LCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Size == 1 || Size == 2)
                {
                    Size = 3;
                    Hour = 8;
                    HourText.setText("8 ساعت");
                    TextHint.setText("حداقل زمان تمیزکاری بر اساس متراژ واحد شما 8 ساعت می باشد.");
                    SCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    MCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    LCard.setCardBackgroundColor(Color.parseColor("#329ba6"));
                    S1Text.setTextColor(Color.parseColor("#329ba6"));
                    S2Text.setTextColor(Color.parseColor("#329ba6"));
                    S3Text.setTextColor(Color.parseColor("#329ba6"));
                    M1Text.setTextColor(Color.parseColor("#329ba6"));
                    M2Text.setTextColor(Color.parseColor("#329ba6"));
                    M3Text.setTextColor(Color.parseColor("#329ba6"));
                    L1Text.setTextColor(Color.parseColor("#FFFFFF"));
                    L2Text.setTextColor(Color.parseColor("#FFFFFF"));
                    L3Text.setTextColor(Color.parseColor("#FFFFFF"));
                    orderInfoEdit.putString("size", "بزرگ").apply();
                }

                SharedPreferences prices = getActivity().getSharedPreferences("prices", Context.MODE_PRIVATE);

                hoursEditor.putString("hours", String.valueOf(Hour)).apply();
                if (Hour == 4){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 4 hours", "") + "تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }
                else if (Hour == 6){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 6 hours", "") + " تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }
                else if (Hour == 8){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 8 hours", "") + " تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }
                else if (Hour == 10){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 10 hours", "") + " تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }
            }
        });

        Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Size == 1)
                {
                    if (Hour == 8) {
                        HourText.setText("10 ساعت");
                        Hour = 10;
                    }
                    if (Hour == 6) {
                        HourText.setText("8 ساعت");
                        Hour = 8;
                    }
                    if (Hour == 4) {
                        HourText.setText("6 ساعت");
                        Hour = 6;
                    }
                }
                if (Size == 2)
                {
                    if (Hour == 8) {
                        HourText.setText("10 ساعت");
                        Hour = 10;
                    }
                    if (Hour == 6) {
                        HourText.setText("8 ساعت");
                        Hour = 8;
                    }
                }
                if (Size == 3)
                {
                    if (Hour == 8) {
                        HourText.setText("10 ساعت");
                        Hour = 10;
                    }
                }
                SharedPreferences prices = getActivity().getSharedPreferences("prices", Context.MODE_PRIVATE);

                hoursEditor.putString("hours", String.valueOf(Hour)).apply();
                if (Hour == 4){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 4 hours", "") + " تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }
                else if (Hour == 6){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 6 hours", "") + " تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }
                else if (Hour == 8){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 8 hours", "") + " تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }
                else if (Hour == 10){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 10 hours", "") + " تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }
            }
        });

        Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Size == 1)
                {
                    if (Hour == 6) {
                        HourText.setText("4 ساعت");
                        Hour = 4;
                    }
                    if (Hour == 8) {
                        HourText.setText("6 ساعت");
                        Hour = 6;
                    }
                    if (Hour == 10) {
                        HourText.setText("8 ساعت");
                        Hour = 8;
                    }
                }
                if (Size == 2)
                {
                    if (Hour == 8) {
                        HourText.setText("6 ساعت");
                        Hour = 6;
                    }
                    if (Hour == 10) {
                        HourText.setText("8 ساعت");
                        Hour = 8;
                    }
                }
                if (Size == 3)
                {
                    if (Hour == 10) {
                        HourText.setText("8 ساعت");
                        Hour = 8;
                    }
                }

                SharedPreferences prices = getActivity().getSharedPreferences("prices", Context.MODE_PRIVATE);

                hoursEditor.putString("hours", String.valueOf(Hour)).apply();
                if (Hour == 4){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 4 hours", "") + " تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }
                else if (Hour == 6){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 6 hours", "") + " تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }
                else if (Hour == 8){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 8 hours", "") + " تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }
                else if (Hour == 10){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 10 hours", "") + " تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }
            }
        });


        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hoursEditor.putString("hours", String.valueOf(Hour)).apply();
                if (Hour == 4){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 4 hours", "") + " تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }
                else if (Hour == 6){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 6 hours", "") + " تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }
                else if (Hour == 8){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 8 hours", "") + " تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }
                else if (Hour == 10){
                    Cost.setText("هزینه سفارش: " + prices.getString("male 10 hours", "") + " تومان");
                    CostEditor.putString("cost", Cost.getText().toString()).apply();
                }

                SecondFragment secondfragment = new SecondFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.container1, secondfragment)
                        .addToBackStack("secondFrag")
                        .commit();
            }
        });

        return view;
    }

}
