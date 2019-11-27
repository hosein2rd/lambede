package com.lambede.lamer.fragments;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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


public class FirstCarWashFragment extends Fragment {

    CardView FirstCard, SecondCard, ThirdCard;
    TextView FirstTextTitle, FirstTextContent;
    TextView SecondTextTitle, SecondTextContent;
    TextView ThirdTextTitle, ThirdTextContent, tvFirstCarwashTitle, tvFirstCarwashTypeTitle,
            tvFirstCarwashDateTitle, tvFirstCarwashModelTitle;
    int Type = 1;
    TextView Date;
    ArrayList<String> TimeArray = new ArrayList<>();
    ArrayList<String> hourArray = new ArrayList<>();
    SharedPreferences TimeSHP, prices;
    WheelPicker TimePicker, wheelPicker;
    String Time_String, Date_String = "9:00";
    ImageView  DarkBack, Accept, Reject, BottomDarkBack;
    public RelativeLayout wheelContainer;
    FrameLayout Next;
    int CostInt1, CostInt2, CostInt3;
    String CostString1, CostString2, CostString3;
    TextView Cost;
    int CostInt;
    String CostString;
    EditText Model;
    Typeface IranYekanBold;
//    private FirebaseAnalytics mFirebaseAnalytics;
//    private Tracker mTracker;
    Animation Scale_out, Scale_in;

    AnimatedColor blueToBlack;
    AnimatedColor blackToBlue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_car_wash, container, false);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                //Find the currently focused view, so we can grab the correct window token from it.
                View view = getActivity().getCurrentFocus();
                //If no view currently has focus, create a new one, just so we can grab a window token from it
                if (view == null) {
                    view = new View(getActivity());
                }
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                return true;
            }
        });

        // Obtain the FirebaseAnalytics instance.
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
//
//        //Analytics Codes
//        // Obtain the shared Tracker instance.
//        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
//        mTracker = application.getDefaultTracker();
//        mTracker.setScreenName("Carwash Order 1");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        FirstCard = view.findViewById(R.id.firstcarwash_scard);
        SecondCard = view.findViewById(R.id.firstcarwash_mcard);
        ThirdCard = view.findViewById(R.id.firstcarwash_lcard);
        FirstTextTitle = view.findViewById(R.id.firstcarwash_stitle);
        FirstTextContent = view.findViewById(R.id.firstcarwash_stext);
        SecondTextTitle = view.findViewById(R.id.firstcarwash_mtitle);
        SecondTextContent = view.findViewById(R.id.firstcarwash_mtext);
        ThirdTextTitle = view.findViewById(R.id.firstcarwash_ltitle);
        ThirdTextContent = view.findViewById(R.id.firstcarwash_ltext);
        Date = view.findViewById(R.id.firstcarwash_hour);
        TimePicker = view.findViewById(R.id.second_maintimewheel2);
        wheelPicker = view.findViewById(R.id.second_maintimewheel);
        BottomDarkBack = getActivity().findViewById(R.id.darkback);
        DarkBack = view.findViewById(R.id.second_darkback);
        wheelContainer = view.findViewById(R.id.second_wheel_container);
        Accept = view.findViewById(R.id.second_timeaccept);
        Reject = view.findViewById(R.id.second_timereject);
        Next = view.findViewById(R.id.firstcarwash_button);
        Cost = view.findViewById(R.id.firstcarwash_cost);
        Model = view.findViewById(R.id.firstcarwash_model);
        tvFirstCarwashTitle = view.findViewById(R.id.firstcarwash_title);
        tvFirstCarwashTypeTitle = view.findViewById(R.id.firstcarwash_titles);
        tvFirstCarwashDateTitle = view.findViewById(R.id.firstcarwash_hourtitle);
        tvFirstCarwashModelTitle = view.findViewById(R.id.firstcarwash_modeltitle);

        IranYekanBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");

        Cost.setTypeface(IranYekanBold);
        FirstTextTitle.setTypeface(IranYekanBold);
        SecondTextTitle.setTypeface(IranYekanBold);
        ThirdTextTitle.setTypeface(IranYekanBold);
        Model.setTypeface(IranYekanBold);
        tvFirstCarwashTitle.setTypeface(IranYekanBold);
        tvFirstCarwashTypeTitle.setTypeface(IranYekanBold);
        tvFirstCarwashDateTitle.setTypeface(IranYekanBold);
        tvFirstCarwashModelTitle.setTypeface(IranYekanBold);
        Date.setTypeface(IranYekanBold);

        Scale_in = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_in);
        Scale_out = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_out);

        blueToBlack = new AnimatedColor(ContextCompat.getColor(getActivity(), R.color.colorStatusBarNormal), ContextCompat.getColor(getActivity(), R.color.colorStatusBarDate));
        blackToBlue = new AnimatedColor(ContextCompat.getColor(getActivity(), R.color.colorStatusBarDate), ContextCompat.getColor(getActivity(), R.color.colorStatusBarNormal));


        FirstCard.setCardBackgroundColor(Color.parseColor("#329ba6"));
        FirstTextTitle.setTextColor(Color.parseColor("#FFFFFF"));
        FirstTextContent.setTextColor(Color.parseColor("#FFFFFF"));

        TimeSHP = getActivity().getSharedPreferences("times", Context.MODE_PRIVATE);
        prices = getActivity().getSharedPreferences("prices", Context.MODE_PRIVATE);

        CostString1 = prices.getString("car 1", "10");
        CostString2 = prices.getString("car 2", "20");
        CostString3 = prices.getString("car 3", "30");

        CostInt1 = Integer.parseInt(CostString1);
        CostInt2 = Integer.parseInt(CostString2);
        CostInt3 = Integer.parseInt(CostString3);

        Cost.setText("هزینه سفارش: " + CostString1 + " تومان");

        TimeArray = new ArrayList<>();
        for (int i = 0; i < 18; i++){
            TimeArray.add(TimeSHP.getString(i + "", "دوشنبه 17 اسفند"));
        }

        Time_String = TimeArray.get(0);

        hourArray = new ArrayList<>();
        for (int i = 9; i < 19; i++){
            hourArray.add("ساعت " + String.valueOf(i) + ":00");
        }

        Date.setText(TimeArray.get(0) + " ساعت 9:00");

        FirstCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstCardClicked();
            }
        });

        SecondCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondCardClicked();
            }
        });

        ThirdCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thirdCardClicked();
            }
        });

        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateClicked();
            }
        });

        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptClicked();
            }
        });

        Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectClicked();
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextClicked();
            }
        });

        return view;
    }

    public void firstCardClicked(){
        if (Type == 2 || Type == 3){

            Type = 1;

            FirstCard.setCardBackgroundColor(Color.parseColor("#329ba6"));
            FirstTextTitle.setTextColor(Color.parseColor("#FFFFFF"));
            FirstTextContent.setTextColor(Color.parseColor("#FFFFFF"));

            SecondCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            SecondTextTitle.setTextColor(Color.parseColor("#329ba6"));
            SecondTextContent.setTextColor(Color.parseColor("#329ba6"));

            ThirdCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            ThirdTextTitle.setTextColor(Color.parseColor("#329ba6"));
            ThirdTextContent.setTextColor(Color.parseColor("#329ba6"));

            Cost.setText("هزینه سفارش: " + CostString1 + " تومان");
        }
    }

    public void secondCardClicked(){
        if (Type == 1 || Type == 3){
            Type = 2;
            SecondCard.setCardBackgroundColor(Color.parseColor("#329ba6"));
            SecondTextTitle.setTextColor(Color.parseColor("#FFFFFF"));
            SecondTextContent.setTextColor(Color.parseColor("#FFFFFF"));

            FirstCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            FirstTextTitle.setTextColor(Color.parseColor("#329ba6"));
            FirstTextContent.setTextColor(Color.parseColor("#329ba6"));

            ThirdCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            ThirdTextTitle.setTextColor(Color.parseColor("#329ba6"));
            ThirdTextContent.setTextColor(Color.parseColor("#329ba6"));

            Cost.setText("هزینه سفارش: " + CostString2 + " تومان");
        }
    }

    public void thirdCardClicked(){
        if (Type == 2 || Type == 1){
            Type = 3;
            ThirdCard.setCardBackgroundColor(Color.parseColor("#329ba6"));
            ThirdTextTitle.setTextColor(Color.parseColor("#FFFFFF"));
            ThirdTextContent.setTextColor(Color.parseColor("#FFFFFF"));

            FirstCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            FirstTextTitle.setTextColor(Color.parseColor("#329ba6"));
            FirstTextContent.setTextColor(Color.parseColor("#329ba6"));

            SecondCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            SecondTextTitle.setTextColor(Color.parseColor("#329ba6"));
            SecondTextContent.setTextColor(Color.parseColor("#329ba6"));

            Cost.setText("هزینه سفارش: " + CostString3 + " تومان");
        }
    }

    public void dateClicked(){

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


        if (wheelPicker.getCurrentItemPosition() == 0 ) {
            Date_String = "9:00";
        }else if (wheelPicker.getCurrentItemPosition() == 1) {
            Date_String = "10:00";
        }else if (wheelPicker.getCurrentItemPosition() == 2) {
            Date_String = "11:00";
        }else if (wheelPicker.getCurrentItemPosition() == 3) {
            Date_String = "12:00";
        }else if (wheelPicker.getCurrentItemPosition() == 4) {
            Date_String = "13:00";
        }else if (wheelPicker.getCurrentItemPosition() == 5) {
            Date_String = "14:00";
        }else if (wheelPicker.getCurrentItemPosition() == 6) {
            Date_String = "15:00";
        }else if (wheelPicker.getCurrentItemPosition() == 7) {
            Date_String = "16:00";
        }else if (wheelPicker.getCurrentItemPosition() == 8) {
            Date_String = "17:00";
        }else if (wheelPicker.getCurrentItemPosition() == 9) {
            Date_String = "18:00";
        }

        Typeface IRANYekanFaNum = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");

        TimePicker.setData(TimeArray);
        TimePicker.setTypeface(IRANYekanFaNum);

        wheelPicker.setData(hourArray);
        wheelPicker.setTypeface(IRANYekanFaNum);
    }

    public void acceptClicked(){
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

        if (wheelPicker.getCurrentItemPosition() == 0 ) {
            Date_String = "9:00";
        }else if (wheelPicker.getCurrentItemPosition() == 1) {
            Date_String = "10:00";
        }else if (wheelPicker.getCurrentItemPosition() == 2) {
            Date_String = "11:00";
        }else if (wheelPicker.getCurrentItemPosition() == 3) {
            Date_String = "12:00";
        }else if (wheelPicker.getCurrentItemPosition() == 4) {
            Date_String = "13:00";
        }else if (wheelPicker.getCurrentItemPosition() == 5) {
            Date_String = "14:00";
        }else if (wheelPicker.getCurrentItemPosition() == 6) {
            Date_String = "15:00";
        }else if (wheelPicker.getCurrentItemPosition() == 7) {
            Date_String = "16:00";
        }else if (wheelPicker.getCurrentItemPosition() == 8) {
            Date_String = "17:00";
        }else if (wheelPicker.getCurrentItemPosition() == 9) {
            Date_String = "18:00";
        }

        Typeface IRANYekanFaNum = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");

        TimePicker.setData(TimeArray);
        TimePicker.setTypeface(IRANYekanFaNum);

        wheelPicker.setData(hourArray);
        wheelPicker.setTypeface(IRANYekanFaNum);

        Date.setText(Time_String + " ساعت " + Date_String);
    }

    public void rejectClicked(){
        animateBackgroundHide();
        animateStatusBarBlackToBlue();
        wheelContainer.startAnimation(Scale_out);
        wheelContainer.setVisibility(View.GONE);
    }

    public void nextClicked() {

        if (Model.getText().toString().trim().length() == 0) {
            popupForEmptyModel();
        } else {
            String type = "empty";

            if (Type == 1) {
                CostInt = CostInt1;
                CostString = CostString1;
                type = "روشویی";
            } else if (Type == 2) {
                CostInt = CostInt2;
                CostString = CostString2;
                type = "روشویی و توشویی";
            } else if (Type == 3) {
                CostInt = CostInt3;
                CostString = CostString3;
                type = "سرویس" + "VIP";
            }

            SharedPreferences CostSHP = getActivity().getSharedPreferences("info of carwash", Context.MODE_PRIVATE);
            SharedPreferences.Editor CostEditor = CostSHP.edit();
            CostEditor.putString("cost string", CostString);
            CostEditor.putInt("cost int", CostInt);
            CostEditor.putString("date", Time_String);
            CostEditor.putString("type", type);
            CostEditor.putString("model", Model.getText().toString());
            CostEditor.putString("hour", Date_String);
            CostEditor.apply();

            SecondCarwashFragment secondCarwashFragment = new SecondCarwashFragment();
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.beginTransaction().add(R.id.container1, secondCarwashFragment)
                        .addToBackStack("secondCarwashFrag")
                    .commit();
        }
    }

    public void popupForEmptyModel(){
        new PromptDialog(getContext())
                .setDialogType(PromptDialog.DIALOG_TYPE_ERROR)
                .setAnimationEnable(true)
                .setTitleText(R.string.message)
                .setContentText(R.string.enter_model)
                .setDismissingListener(R.string.ok, new PromptDialog.OnDismissingListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
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
