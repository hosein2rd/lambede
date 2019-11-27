package com.lambede.lamer.fragments;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.analytics.Tracker;
//import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

import com.lambede.lamer.R;
import com.lambede.lamer.pop_up.PromptDialog;
import com.lambede.lamer.utilities.AnalyticsApplication;
import com.lambede.lamer.utilities.AnimatedColor;


public class FirstStairsFragment extends Fragment {


    public FirstStairsFragment() {
        // Required empty public constructor
    }

    FrameLayout Next;
    Button Plus, Minus;
    public RelativeLayout wheelContainer;
    CardView SCard, MCard, LCard;
    TextView
            FloorText,
            S1Text, M1Text, L1Text,
            S2Text, M2Text, L2Text,
            S3Text, M3Text, L3Text,
            DateText, WheelTitle;
    ImageView AcceptTime, RejectTime, DarkBack, BottomDarkBack;
    WheelPicker TimePicker;
    ArrayList<String> TimeArray = new ArrayList<>();
    String Time_String;
    SharedPreferences TimeSHP;
    SharedPreferences InfoSHP;
    SharedPreferences pricesSHP;
    int Size = 1, Floor = 3;
    TextView Cost, tvFirstStairsTitle, tvFirstStairsSizesTitle, tvFirstStairsFloorsTitle, tvFirstStairsDatesTitle;
    String stair1, stair2, stair3;
    int stair1Int, stair2Int, stair3Int;
    int cost;
    Typeface IranYekanBold;
//    private FirebaseAnalytics mFirebaseAnalytics;
//    private Tracker mTracker;
    Animation Scale_out, Scale_in;
    AnimatedColor blueToBlack;
    AnimatedColor blackToBlue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_stairs, container, false);

        // Obtain the FirebaseAnalytics instance.
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
//
//        //Analytics Codes
//        // Obtain the shared Tracker instance.
//        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
//        mTracker = application.getDefaultTracker();
//        mTracker.setScreenName("Stair Order 1");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Next = view.findViewById(R.id.first_stairs_button);
        FloorText = view.findViewById(R.id.first_stairs_floor);
        Plus = view.findViewById(R.id.first_stairs_plus);
        Minus = view.findViewById(R.id.first_stairs_minus);
        SCard = view.findViewById(R.id.first_stairs_scard);
        MCard = view.findViewById(R.id.first_stairs_mcard);
        LCard = view.findViewById(R.id.first_stairs_lcard);
        S1Text = view.findViewById(R.id.first_stairs_stitle);
        S2Text = view.findViewById(R.id.first_stairs_simage);
        S3Text = view.findViewById(R.id.first_stairs_stext);
        M1Text = view.findViewById(R.id.first_stairs_mtitle);
        M2Text = view.findViewById(R.id.first_stairs_mimage);
        M3Text = view.findViewById(R.id.first_stairs_mtext);
        L1Text = view.findViewById(R.id.first_stairs_ltitle);
        L2Text = view.findViewById(R.id.first_stairs_limage);
        L3Text = view.findViewById(R.id.first_stairs_ltext);
        DateText = view.findViewById(R.id.first_stairs_datetext);
        AcceptTime = view.findViewById(R.id.first_stairs_timeaccept);
        RejectTime = view.findViewById(R.id.first_stairs_timereject);
        TimePicker = view.findViewById(R.id.first_stairs_maintimewheel);
        WheelTitle = view.findViewById(R.id.first_stairs_wheeltitle);
        wheelContainer = view.findViewById(R.id.first_stairs_wheel_container);
        DarkBack = view.findViewById(R.id.first_stairs_darkback);
        tvFirstStairsTitle = view.findViewById(R.id.first_stairs_title);
        tvFirstStairsDatesTitle = view.findViewById(R.id.first_stairs_datetitle);
        tvFirstStairsSizesTitle = view.findViewById(R.id.first_stairs_sizetitle);
        tvFirstStairsFloorsTitle = view.findViewById(R.id.first_stairs_floortitle);


        BottomDarkBack = getActivity().findViewById(R.id.darkback);
        DateText = view.findViewById(R.id.first_stairs_datetext);
        Cost = view.findViewById(R.id.first_stairs_cost);

        Scale_in = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_in);
        Scale_out = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_out);

        blueToBlack = new AnimatedColor(ContextCompat.getColor(getActivity(), R.color.colorStatusBarNormal), ContextCompat.getColor(getActivity(), R.color.colorStatusBarDate));
        blackToBlue = new AnimatedColor(ContextCompat.getColor(getActivity(), R.color.colorStatusBarDate), ContextCompat.getColor(getActivity(), R.color.colorStatusBarNormal));

        IranYekanBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");
        S1Text.setTypeface(IranYekanBold);
        S2Text.setTypeface(IranYekanBold);
        M1Text.setTypeface(IranYekanBold);
        M2Text.setTypeface(IranYekanBold);
        L1Text.setTypeface(IranYekanBold);
        L2Text.setTypeface(IranYekanBold);
        FloorText.setTypeface(IranYekanBold);
        DateText.setTypeface(IranYekanBold);
        Cost.setTypeface(IranYekanBold);
        tvFirstStairsTitle.setTypeface(IranYekanBold);
        tvFirstStairsSizesTitle.setTypeface(IranYekanBold);
        tvFirstStairsDatesTitle.setTypeface(IranYekanBold);
        tvFirstStairsFloorsTitle.setTypeface(IranYekanBold);


        TimeSHP = getActivity().getSharedPreferences("times", Context.MODE_PRIVATE);

        pricesSHP = getActivity().getSharedPreferences("prices", Context.MODE_PRIVATE);
        stair1 = pricesSHP.getString("stairs 1", "10");
        stair2 = pricesSHP.getString("stairs 2", "20");
        stair3 = pricesSHP.getString("stairs 3", "30");
        stair1Int = Integer.parseInt(stair1);
        stair2Int = Integer.parseInt(stair2);
        stair3Int = Integer.parseInt(stair3);


        InfoSHP = getActivity().getSharedPreferences("order info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = InfoSHP.edit();
        editor.putString("size", "نقلی").apply();
        Floor = 3;
        FloorText.setText("3 طبقه");

        TimeArray = new ArrayList<>();
        for (int i = 0; i < 18; i++){
            TimeArray.add(TimeSHP.getString(i + "", "دوشنبه 17 اسفند"));
        }

        DateText.setText(TimeArray.get(0));

        FloorText.setText("3 طبقه");
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
        cost = stair1Int*3;
        Cost.setText("هزینه سفارش: " + cost + " تومان");

        SCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Size == 2 || Size == 3)
                {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    Size = 1;
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

                    if (Floor == 3){
                        cost = stair1Int*3;
                    }
                    else if (Floor == 4){
                        cost = stair1Int*4;
                    }
                    else if (Floor == 5){
                        cost = stair1Int*5;
                    }
                    else if (Floor == 6){
                        cost = stair1Int*6;
                    }
                    else if (Floor == 7){
                        cost = stair1Int*7;
                    }
                    else if (Floor == 8){
                        cost = stair1Int*8;
                    }
                    Cost.setText("هزینه سفارش: " + cost + " تومان");

                    SharedPreferences.Editor editor = InfoSHP.edit();
                    editor.putString("size", "نقلی").apply();
                }
            }
        });

        MCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Size == 1 || Size == 3)
                {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    Size = 2;
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

                    if (Floor == 3){
                        cost = stair2Int*3;
                    }
                    else if (Floor == 4){
                        cost = stair2Int*4;
                    }
                    else if (Floor == 5){
                        cost = stair2Int*5;
                    }
                    else if (Floor == 6){
                        cost = stair2Int*6;
                    }
                    else if (Floor == 7){
                        cost = stair2Int*7;
                    }
                    else if (Floor == 8){
                        cost = stair2Int*8;
                    }
                    Cost.setText("هزینه سفارش: " + cost + " تومان");

                    SharedPreferences.Editor editor = InfoSHP.edit();
                    editor.putString("size", "متوسظ").apply();
                }
            }
        });

        LCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Size == 1 || Size == 2)
                {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    Size = 3;
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

                    if (Floor == 3){
                        cost = stair3Int*3;
                    }
                    else if (Floor == 4){
                        cost = stair3Int*4;
                    }
                    else if (Floor == 5){
                        cost = stair3Int*5;
                    }
                    else if (Floor == 6){
                        cost = stair3Int*6;
                    }
                    else if (Floor == 7){
                        cost = stair3Int*7;
                    }
                    else if (Floor == 8){
                        cost = stair3Int*8;
                    }
                    Cost.setText("هزینه سفارش: " + cost + " تومان");

                    SharedPreferences.Editor editor = InfoSHP.edit();
                    editor.putString("size", "بزرگ").apply();
                }
            }
        });

        Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlusClicked();
            }
        });

        Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MinusClicked();
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextClicked();
            }
        });

        TimeArray = new ArrayList<>();
        for (int i = 0; i < 18; i++){
            TimeArray.add(TimeSHP.getString(i + "", "دوشنبه 17 اسفند"));
        }

        DateText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                animateStatusBarBlueToBlack();
                BottomDarkBack.setVisibility(View.VISIBLE);
                DarkBack.setVisibility(View.VISIBLE);
                wheelContainer.setVisibility(View.VISIBLE);
                wheelContainer.startAnimation(Scale_in);
                animateBackgroundShow();

                if (TimePicker.getCurrentItemPosition() == 0 ) {
                    Time_String = TimeArray.get(0);
                }else if (TimePicker.getCurrentItemPosition() == 1) {
                    Time_String = TimeArray.get(1);
                }else if (TimePicker.getCurrentItemPosition() == 2) {
                    Time_String = TimeArray.get(2);
                }else if (TimePicker.getCurrentItemPosition() == 3) {
                    Time_String = TimeArray.get(3);
                }else if (TimePicker.getCurrentItemPosition() == 4) {
                    Time_String = TimeArray.get(4);
                }else if (TimePicker.getCurrentItemPosition() == 5) {
                    Time_String = TimeArray.get(5);
                }else if (TimePicker.getCurrentItemPosition() == 6) {
                    Time_String = TimeArray.get(6);
                }else if (TimePicker.getCurrentItemPosition() == 7) {
                    Time_String = TimeArray.get(7);
                }else if (TimePicker.getCurrentItemPosition() == 8) {
                    Time_String = TimeArray.get(8);
                }else if (TimePicker.getCurrentItemPosition() == 9) {
                    Time_String = TimeArray.get(9);
                }else if (TimePicker.getCurrentItemPosition() == 10) {
                    Time_String = TimeArray.get(10);
                }else if (TimePicker.getCurrentItemPosition() == 11) {
                    Time_String = TimeArray.get(11);
                }else if (TimePicker.getCurrentItemPosition() == 12){
                    Time_String = TimeArray.get(12);
                }else if (TimePicker.getCurrentItemPosition() == 13){
                    Time_String = TimeArray.get(13);
                }else if (TimePicker.getCurrentItemPosition() == 14){
                    Time_String = TimeArray.get(14);
                }else if (TimePicker.getCurrentItemPosition() == 15){
                    Time_String = TimeArray.get(15);
                }else if (TimePicker.getCurrentItemPosition() == 16){
                    Time_String = TimeArray.get(16);
                }else if (TimePicker.getCurrentItemPosition() == 17){
                    Time_String = TimeArray.get(17);
                }

                Typeface IRANYekanFaNum = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");

                TimePicker.setData(TimeArray);
                TimePicker.setTypeface(IRANYekanFaNum);

            }
        });

        AcceptTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animateStatusBarBlackToBlue();
                wheelContainer.startAnimation(Scale_out);
                animateBackgroundHide();
                wheelContainer.setVisibility(View.GONE);


                if (TimePicker.getCurrentItemPosition() == 0 ) {
                    Time_String = TimeArray.get(0);
                }else if (TimePicker.getCurrentItemPosition() == 1) {
                    Time_String = TimeArray.get(1);
                }else if (TimePicker.getCurrentItemPosition() == 2) {
                    Time_String = TimeArray.get(2);
                }else if (TimePicker.getCurrentItemPosition() == 3) {
                    Time_String = TimeArray.get(3);
                }else if (TimePicker.getCurrentItemPosition() == 4) {
                    Time_String = TimeArray.get(4);
                }else if (TimePicker.getCurrentItemPosition() == 5) {
                    Time_String = TimeArray.get(5);
                }else if (TimePicker.getCurrentItemPosition() == 6) {
                    Time_String = TimeArray.get(6);
                }else if (TimePicker.getCurrentItemPosition() == 7) {
                    Time_String = TimeArray.get(7);
                }else if (TimePicker.getCurrentItemPosition() == 8) {
                    Time_String = TimeArray.get(8);
                }else if (TimePicker.getCurrentItemPosition() == 9) {
                    Time_String = TimeArray.get(9);
                }else if (TimePicker.getCurrentItemPosition() == 10) {
                    Time_String = TimeArray.get(10);
                }else if (TimePicker.getCurrentItemPosition() == 11) {
                    Time_String = TimeArray.get(11);
                }else if (TimePicker.getCurrentItemPosition() == 12){
                    Time_String = TimeArray.get(12);
                }else if (TimePicker.getCurrentItemPosition() == 13){
                    Time_String = TimeArray.get(13);
                }else if (TimePicker.getCurrentItemPosition() == 14){
                    Time_String = TimeArray.get(14);
                }else if (TimePicker.getCurrentItemPosition() == 15){
                    Time_String = TimeArray.get(15);
                }else if (TimePicker.getCurrentItemPosition() == 16){
                    Time_String = TimeArray.get(16);
                }else if (TimePicker.getCurrentItemPosition() == 17){
                    Time_String = TimeArray.get(17);
                }


                TimePicker.setData(TimeArray);

                DateText.setText(Time_String);

            }
        });

        RejectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animateBackgroundHide();
                animateStatusBarBlackToBlue();
                wheelContainer.startAnimation(Scale_out);
                wheelContainer.setVisibility(View.GONE);
            }
        });

        return view;
    }

    public void PlusClicked(){
        if (Floor == 3) {
            FloorText.setText("4 طبقه");
            Floor = 4;
            if (Size == 1){
                cost = stair1Int*4;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
            else if (Size == 2){
                cost = stair2Int*4;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
            else if (Size == 3){
                cost = stair3Int*4;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
        }
        else if (Floor == 4) {
            FloorText.setText("5 طبقه");
            Floor = 5;
            if (Size == 1){
                cost = stair1Int*5;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
            else if (Size == 2){
                cost = stair2Int*5;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
            else if (Size == 3){
                cost = stair3Int*5;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
        }
        else if (Floor == 5) {
            FloorText.setText("6 طبقه");
            Floor = 6;
            if (Size == 1){
                cost = stair1Int*6;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
            else if (Size == 2){
                cost = stair2Int*6;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
            else if (Size == 3){
                cost = stair3Int*6;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
        }
        else if (Floor == 6) {
            FloorText.setText("7 طبقه");
            Floor = 7;
            if (Size == 1){
                cost = stair1Int*7;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
            else if (Size == 2){
                cost = stair2Int*7;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
            else if (Size == 3){
                cost = stair3Int*7;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
        }
        else if (Floor == 7) {
            FloorText.setText("8 طبقه");
            Floor = 8;
            if (Size == 1){
                cost = stair1Int*8;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
            else if (Size == 2){
                cost = stair2Int*8;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
            else if (Size == 3){
                cost = stair3Int*8;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
        }
    }

    public void MinusClicked() {
        if (Floor == 8) {
            FloorText.setText("7 طبقه");
            Floor = 7;
            if (Size == 1){
                cost = stair1Int*7;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
            else if (Size == 2){
                cost = stair2Int*7;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
            else if (Size == 3){
                cost = stair3Int*7;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
        }
        else if (Floor == 7) {
            FloorText.setText("6 طبقه");
            Floor = 6;
            if (Size == 1){
                cost = stair1Int*6;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
            else if (Size == 2){
                cost = stair2Int*6;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
            else if (Size == 3){
                cost = stair3Int*6;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
        }
        else if (Floor == 6) {
            FloorText.setText("5 طبقه");
            Floor = 5;
            if (Size == 1){
                cost = stair1Int*5;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
            else if (Size == 2){
                cost = stair2Int*5;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
            else if (Size == 3){
                cost = stair3Int*5;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
        }
        else if (Floor == 5) {
            FloorText.setText("4 طبقه");
            Floor = 4;
            if (Size == 1){
                cost = stair1Int*4;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
            else if (Size == 2){
                cost = stair2Int*4;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
            else if (Size == 3){
                cost = stair3Int*4;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
        }
        else if (Floor == 4) {
            FloorText.setText("3 طبقه");
            Floor = 3;
            if (Size == 1){
                cost = stair1Int*3;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
            else if (Size == 2){
                cost = stair2Int*3;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
            else if (Size == 3){
                cost = stair3Int*3;
                Cost.setText("هزینه سفارش: " + cost + " تومان");
            }
        }
    }

    public void nextClicked(){
        SharedPreferences.Editor editorInfo = InfoSHP.edit();
        editorInfo.putString("date", DateText.getText().toString());
        String floors = FloorText.getText().toString();
        floors = floors.substring(0, 1);
        editorInfo.putString("floor", floors);
        editorInfo.apply();

        SharedPreferences costSHP = getActivity().getSharedPreferences("cost", Context.MODE_PRIVATE);
        SharedPreferences.Editor costEditor = costSHP.edit();
        costEditor.putString("costInt", String.valueOf(cost));
        costEditor.apply();

        SecondStairsFragment secondStairsFragment = new SecondStairsFragment();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().add(R.id.container1, secondStairsFragment)
                .addToBackStack("secondStairsFrag")
                .commit();
    }

    private void animateStatusBarBlueToBlack() {
        ValueAnimator animator = ObjectAnimator.ofFloat(0f, 1f).setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    getActivity().getWindow().setStatusBarColor(blueToBlack.with(v));

                }
            }
        });
        animator.start();
    }

    public void animateStatusBarBlackToBlue() {
        ValueAnimator animator = ObjectAnimator.ofFloat(0f, 1f).setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    getActivity().getWindow().setStatusBarColor(blackToBlue.with(v));
                }
            }
        });
        animator.start();
    }

    private void animateBackgroundShow(){
        ValueAnimator animator = ObjectAnimator.ofFloat(0f, 0.7f).setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                BottomDarkBack.setAlpha((float)animation.getAnimatedValue());
                DarkBack.setAlpha((float)animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    public void animateBackgroundHide(){
        ValueAnimator animator = ObjectAnimator.ofFloat(0.7f, 0f).setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                BottomDarkBack.setAlpha((float)animation.getAnimatedValue());
                DarkBack.setAlpha((float)animation.getAnimatedValue());

                if (DarkBack.getAlpha() == 0){
                    BottomDarkBack.setVisibility(View.GONE);
                    DarkBack.setVisibility(View.GONE);
                }
            }
        });
        animator.start();
    }

    public boolean isVisibleNow(){
        if (wheelContainer.getVisibility() == View.VISIBLE){
            return true;
        }
        else
            return false;
    }
}
