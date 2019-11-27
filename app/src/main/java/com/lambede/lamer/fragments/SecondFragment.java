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
import android.util.Log;
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


public class SecondFragment extends Fragment {


    public SecondFragment() {
        // Required empty public constructor
    }

    Button  Plus, Minus;
    public RelativeLayout WheelContainer;
    CardView NoCard, MaleCard, FemaleCard;
    FrameLayout Next;
    TextView LamerText, NoText, MaleText, FemaleText, DateText, WheelTitle, CostTextView,
            tvSecondTitle, tvSecondDateTitle, tvSecondGender1Title, tvSecondGender2Title,
            tvSecondLamersTitle;
    public ImageView MaleImage, FemaleImage, AcceptTime, RejectTime, DarkBack, BottomDarkBack;
    WheelPicker TimePicker;
    ArrayList<String> TimeArray = new ArrayList<>();
    String Time_String;
    int Sex = 0, Lamer = 1;
    SharedPreferences TimeSHP;
    String cost;
    int costInt;
    Typeface IranYekanBold;
//    private FirebaseAnalytics mFirebaseAnalytics;
//    private Tracker mTracker;
    public Animation Scale_out, Scale_in;

    AnimatedColor blueToBlack;
    AnimatedColor blackToBlue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        // Obtain the FirebaseAnalytics instance.
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
//
//        //Analytics Codes
//        // Obtain the shared Tracker instance.
//        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
//        mTracker = application.getDefaultTracker();
//        mTracker.setScreenName("Resident Order 2");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Next = view.findViewById(R.id.second_button);
        LamerText = view.findViewById(R.id.second_lamer);
        Plus = view.findViewById(R.id.second_plus);
        Minus = view.findViewById(R.id.second_minus);
        NoCard = view.findViewById(R.id.second_ncard);
        MaleCard = view.findViewById(R.id.second_mcard);
        FemaleCard = view.findViewById(R.id.second_fcard);
        NoText = view.findViewById(R.id.second_ntext);
        MaleText = view.findViewById(R.id.second_mtext);
        FemaleText = view.findViewById(R.id.second_ftext);
        MaleImage = view.findViewById(R.id.second_male);
        FemaleImage = view.findViewById(R.id.second_female);
        DateText = view.findViewById(R.id.second_datetext);
        AcceptTime = view.findViewById(R.id.second_timeaccept);
        RejectTime = view.findViewById(R.id.second_timereject);
        TimePicker = view.findViewById(R.id.second_maintimewheel);
        WheelTitle = view.findViewById(R.id.second_wheeltitle);
        DarkBack = view.findViewById(R.id.second_darkback);
        WheelContainer = view.findViewById(R.id.second_wheel_container);
        BottomDarkBack = getActivity().findViewById(R.id.darkback);
        CostTextView = view.findViewById(R.id.second_cost);
        tvSecondDateTitle = view.findViewById(R.id.second_datetitle);
        tvSecondGender1Title = view.findViewById(R.id.second_sextitle1);
        tvSecondGender2Title = view.findViewById(R.id.second_sextitle2);
        tvSecondLamersTitle = view.findViewById(R.id.second_lamertitle);
        tvSecondTitle = view.findViewById(R.id.second_title);

        Scale_in = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_in);
        Scale_out = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_out);

        blueToBlack = new AnimatedColor(ContextCompat.getColor(getActivity(), R.color.colorStatusBarNormal), ContextCompat.getColor(getActivity(), R.color.colorStatusBarDate));
        blackToBlue = new AnimatedColor(ContextCompat.getColor(getActivity(), R.color.colorStatusBarDate), ContextCompat.getColor(getActivity(), R.color.colorStatusBarNormal));

        IranYekanBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");
        CostTextView.setTypeface(IranYekanBold);
        Minus.setTypeface(IranYekanBold);
        Plus.setTypeface(IranYekanBold);
        MaleText.setTypeface(IranYekanBold);
        FemaleText.setTypeface(IranYekanBold);
        NoText.setTypeface(IranYekanBold);
        LamerText.setTypeface(IranYekanBold);
        DateText.setTypeface(IranYekanBold);
        tvSecondTitle.setTypeface(IranYekanBold);
        tvSecondGender1Title.setTypeface(IranYekanBold);
        tvSecondGender2Title.setTypeface(IranYekanBold);
        tvSecondLamersTitle.setTypeface(IranYekanBold);
        tvSecondDateTitle.setTypeface(IranYekanBold);

        Sex = 0;
        Lamer = 1;

        SharedPreferences CostSHP = getActivity().getSharedPreferences("cost", Context.MODE_PRIVATE);
        CostTextView.setText(CostSHP.getString("cost", ""));

        TimeSHP = getActivity().getSharedPreferences("times", Context.MODE_PRIVATE);

        SharedPreferences orderInfo = getActivity().getSharedPreferences("order info", Context.MODE_PRIVATE);
        SharedPreferences.Editor orderInfoEdit = orderInfo.edit();
        orderInfoEdit.putString("gender of lamer", "آقا").apply();

        TimeArray = new ArrayList<>();
        for (int i = 0; i < 18; i++){
            TimeArray.add(TimeSHP.getString(i + "", "دوشنبه 17 اسفند"));
        }

        DateText.setText(TimeArray.get(0));

        MaleSelectedDefault();

        NoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Sex == 0 || Sex == 1 || Sex == 2)
                {
                    Sex = 0;
                    NoCard.setCardBackgroundColor(Color.parseColor("#329ba6"));
                    NoText.setTextColor(Color.parseColor("#FFFFFF"));
                    MaleCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    MaleImage.setBackgroundResource(R.drawable.male_cyan);
                    MaleText.setTextColor(Color.parseColor("#329ba6"));
                    FemaleCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    FemaleImage.setBackgroundResource(R.drawable.female_cyan);
                    FemaleText.setTextColor(Color.parseColor("#329ba6"));

                    changeCost();
                }
            }
        });

        MaleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Sex == 0 || Sex == 1)
                {
                    Sex = 2;
                    NoCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    NoText.setTextColor(Color.parseColor("#329ba6"));
                    MaleCard.setCardBackgroundColor(Color.parseColor("#329ba6"));
                    MaleImage.setBackgroundResource(R.drawable.male_white);
                    MaleText.setTextColor(Color.parseColor("#FFFFFF"));
                    FemaleCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    FemaleImage.setBackgroundResource(R.drawable.female_cyan);
                    FemaleText.setTextColor(Color.parseColor("#329ba6"));

                    changeCost();
                }
            }
        });

        FemaleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Sex == 0 || Sex == 2)
                {
                    Sex = 1;
                    NoCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    NoText.setTextColor(Color.parseColor("#329ba6"));
                    MaleCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    MaleImage.setBackgroundResource(R.drawable.male_cyan);
                    MaleText.setTextColor(Color.parseColor("#329ba6"));
                    FemaleCard.setCardBackgroundColor(Color.parseColor("#329ba6"));
                    FemaleImage.setBackgroundResource(R.drawable.female_white);
                    FemaleText.setTextColor(Color.parseColor("#FFFFFF"));

                    changeCost();
                }
            }
        });

        Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Lamer == 2) { LamerText.setText("3 متخصص"); Lamer = 3; changeCost();}
                if (Lamer == 1) { LamerText.setText("2 متخصص"); Lamer = 2; changeCost();}
            }
        });

        Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Lamer == 2) { LamerText.setText("1 متخصص"); Lamer = 1; changeCost();}
                if (Lamer == 3) { LamerText.setText("2 متخصص"); Lamer = 2; changeCost();}
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences orderInfo = getActivity().getSharedPreferences("order info", Context.MODE_PRIVATE);
                SharedPreferences.Editor orderInfoEdit = orderInfo.edit();
                orderInfoEdit.putString("lamer count", LamerText.getText().toString());
                if (Sex == 1){
                    orderInfoEdit.putString("gender of lamer", "خانوم");
                }
                else if (Sex == 2){
                    orderInfoEdit.putString("gender of lamer", "آقا");
                }
                else if (Sex == 0){
                    orderInfoEdit.putString("gender of lamer", "تفاوتی ندارد");
                }
                orderInfoEdit.putString("date", DateText.getText().toString());
                orderInfoEdit.apply();

                ThirdFragment thirdfragment = new ThirdFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.container1, thirdfragment)
                        .addToBackStack("thirdFrag")
                        .commit();
            }

        });

        DateText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                animateStatusBarBlueToBlack();
                BottomDarkBack.setVisibility(View.VISIBLE);
                DarkBack.setVisibility(View.VISIBLE);
                WheelContainer.setVisibility(View.VISIBLE);
                WheelContainer.startAnimation(Scale_in);
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
//                }

                Typeface IRANYekanFaNum = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");

                TimePicker.setData(TimeArray);
                TimePicker.setTypeface(IRANYekanFaNum);

            }
        });

        AcceptTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animateStatusBarBlackToBlue();
                WheelContainer.startAnimation(Scale_out);
                animateBackgroundHide();
                WheelContainer.setVisibility(View.GONE);

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
                WheelContainer.startAnimation(Scale_out);
                WheelContainer.setVisibility(View.GONE);
            }
        });

        return view;
    }

    public void MaleSelectedDefault(){
        Sex = 2;
        NoCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        NoText.setTextColor(Color.parseColor("#329ba6"));
        MaleCard.setCardBackgroundColor(Color.parseColor("#329ba6"));
        MaleImage.setBackgroundResource(R.drawable.male_white);
        MaleText.setTextColor(Color.parseColor("#FFFFFF"));
        FemaleCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        FemaleImage.setBackgroundResource(R.drawable.female_cyan);
        FemaleText.setTextColor(Color.parseColor("#329ba6"));
        changeCost();
    }

    public void changeCost(){
        SharedPreferences hoursSHP = getActivity().getSharedPreferences("hours", Context.MODE_PRIVATE);
        String hours = hoursSHP.getString("hours", "");

        SharedPreferences prices = getActivity().getSharedPreferences("prices", Context.MODE_PRIVATE);
        SharedPreferences.Editor priceIntEdit = prices.edit();

        if (Sex == 2 || Sex == 0){
            if (hours.equals("4")){
                cost = prices.getString("male 4 hours", "10");
                costInt = Integer.parseInt(cost);
                if (Lamer == 1){
                    cost = String.valueOf(costInt);
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
                else if (Lamer == 2){
                    cost = String.valueOf(costInt*2);
                    costInt = costInt*2;
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
                else if (Lamer == 3){
                    cost = String.valueOf(costInt*3);
                    costInt = costInt*3;
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
            }
            else if (hours.equals("6")){
                cost = prices.getString("male 6 hours", "20");
                costInt = Integer.parseInt(cost);
                if (Lamer == 1){
                    cost = String.valueOf(costInt);
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
                else if (Lamer == 2){
                    cost = String.valueOf(costInt*2);
                    costInt = costInt*2;
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
                else if (Lamer == 3){
                    cost = String.valueOf(costInt*3);
                    costInt = costInt*3;
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
            }
            else if (hours.equals("8")){
                cost = prices.getString("male 8 hours", "30");
                costInt = Integer.parseInt(cost);
                if (Lamer == 1){
                    cost = String.valueOf(costInt);
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
                else if (Lamer == 2){
                    cost = String.valueOf(costInt*2);
                    costInt = costInt*2;
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
                else if (Lamer == 3){
                    cost = String.valueOf(costInt*3);
                    costInt = costInt*3;
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
            }
            else if (hours.equals("10")){
                cost = prices.getString("male 10 hours", "40");
                costInt = Integer.parseInt(cost);
                if (Lamer == 1){
                    cost = String.valueOf(costInt);
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
                else if (Lamer == 2){
                    cost = String.valueOf(costInt*2);
                    costInt = costInt*2;
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
                else if (Lamer == 3){
                    cost = String.valueOf(costInt*3);
                    costInt = costInt*3;
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
            }
        }
        else if(Sex == 1){
            if (hours.equals("4")){
                cost = prices.getString("female 4 hours", "10");
                costInt = Integer.parseInt(cost);
                if (Lamer == 1){
                    cost = String.valueOf(costInt);
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
                else if (Lamer == 2){
                    cost = String.valueOf(costInt*2);
                    costInt = costInt*2;
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
                else if (Lamer == 3){
                    cost = String.valueOf(costInt*3);
                    costInt = costInt*3;
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
            }
            else if (hours.equals("6")){
                cost = prices.getString("female 6 hours", "20");
                costInt = Integer.parseInt(cost);
                if (Lamer == 1){
                    cost = String.valueOf(costInt);
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
                else if (Lamer == 2){
                    cost = String.valueOf(costInt*2);
                    costInt = costInt*2;
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
                else if (Lamer == 3){
                    cost = String.valueOf(costInt*3);
                    costInt = costInt*3;
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
            }
            else if (hours.equals("8")){
                cost = prices.getString("female 8 hours", "30");
                costInt = Integer.parseInt(cost);
                if (Lamer == 1){
                    cost = String.valueOf(costInt);
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
                else if (Lamer == 2){
                    cost = String.valueOf(costInt*2);
                    costInt = costInt*2;
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
                else if (Lamer == 3){
                    cost = String.valueOf(costInt*3);
                    costInt = costInt*3;
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
            }
            else if (hours.equals("10")){
                cost = prices.getString("female 10 hours", "40");
                costInt = Integer.parseInt(cost);
                if (Lamer == 1){
                    cost = String.valueOf(costInt);
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
                else if (Lamer == 2){
                    cost = String.valueOf(costInt*2);
                    costInt = costInt*2;
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
                else if (Lamer == 3){
                    cost = String.valueOf(costInt*3);
                    costInt = costInt*3;
                    CostTextView.setText("هزینه سفارش: " + cost + " تومان");
                    priceIntEdit.putString("priceInt", String.valueOf(costInt)).apply();
                }
            }
        }

        SharedPreferences CostSHP = getActivity().getSharedPreferences("cost", Context.MODE_PRIVATE);
        SharedPreferences.Editor costEditor = CostSHP.edit();
        costEditor.putString("cost", CostTextView.getText().toString()).apply();
    }

    public void animateStatusBarBlueToBlack() {
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

    public void animateBackgroundShow(){
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
        if (WheelContainer.getVisibility() == View.VISIBLE){
            return true;
        }
        else
           return false;
    }
}
