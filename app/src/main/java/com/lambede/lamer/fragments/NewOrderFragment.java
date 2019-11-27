package com.lambede.lamer.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.analytics.Tracker;
//import com.google.firebase.analytics.FirebaseAnalytics;

import com.lambede.lamer.R;
import com.lambede.lamer.pop_up.PromptDialog;
import com.lambede.lamer.utilities.AnalyticsApplication;


public class NewOrderFragment extends Fragment {

    CardView Stairs, Work, Home, Suggestion, CarWash;
    //    ImageView BottomDarkBack;
//    private FirebaseAnalytics mFirebaseAnalytics;
//    private Tracker mTracker;
    Typeface IranYekanBold;
    TextView tvNewOrderTitle, tvNewOrderHome, tvNewOrderWork,
            tvNewOrderStairs, tvNewOrderCarwash, tvNewOrderSuggest;
    CardView carwashCard, suggestCard;


    public NewOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neworder, container, false);

//        // Obtain the FirebaseAnalytics instance.
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

        //Analytics Codes
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
//        mTracker = application.getDefaultTracker();
//        mTracker.setScreenName("User Signed In");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Stairs = view.findViewById(R.id.neworder_stairscard);
        Work = view.findViewById(R.id.neworder_workcard);
        Home = view.findViewById(R.id.neworder_homecard);
//        BottomDarkBack = getActivity().findViewById(R.id.darkback);
        Suggestion = view.findViewById(R.id.neworder_suggestcard);
        CarWash = view.findViewById(R.id.neworder_carcard);
        tvNewOrderTitle = view.findViewById(R.id.neworder_title);
        tvNewOrderHome = view.findViewById(R.id.neworder_hometext);
        tvNewOrderCarwash = view.findViewById(R.id.neworder_cartext);
        tvNewOrderStairs = view.findViewById(R.id.neworder_stairstext);
        tvNewOrderWork = view.findViewById(R.id.neworder_worktext);
        tvNewOrderSuggest = view.findViewById(R.id.neworder_suggesttext);

        IranYekanBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");
        tvNewOrderTitle.setTypeface(IranYekanBold);
        tvNewOrderWork.setTypeface(IranYekanBold);
        tvNewOrderSuggest.setTypeface(IranYekanBold);
        tvNewOrderCarwash.setTypeface(IranYekanBold);
        tvNewOrderHome.setTypeface(IranYekanBold);
        tvNewOrderStairs.setTypeface(IranYekanBold);

        SharedPreferences carwashVisible = getActivity().getSharedPreferences("prices", Context.MODE_PRIVATE);

        Boolean isCarwashVisible = carwashVisible.getBoolean("carwash",false);

//        if (!isCarwashVisible) {
//
//            CarWash.setVisibility(View.GONE);
//            Suggestion.setVisibility(View.GONE);
//        }


//        BottomDarkBack.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBarNormal));
        }

        Stairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstStairsFragment firstfragment = new FirstStairsFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.container1, firstfragment)
                        .addToBackStack("firstStairsFrag")
                        .commit();

                SharedPreferences HomeSHP = getActivity().getSharedPreferences("order info", Context.MODE_PRIVATE);
                SharedPreferences.Editor HomeEditor = HomeSHP.edit();
                HomeEditor.putString("type", "راه پله").apply();
            }
        });

        Work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirstFragment firstfragment = new FirstFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.container1, firstfragment)
                        .addToBackStack("firstFrag")
                        .commit();

                SharedPreferences HomeSHP = getActivity().getSharedPreferences("order info", Context.MODE_PRIVATE);
                SharedPreferences.Editor HomeEditor = HomeSHP.edit();
                HomeEditor.putString("type", "محل کار").apply();
            }
        });

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstFragment firstfragment = new FirstFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.container1, firstfragment)
                        .addToBackStack("firstFrag")
                        .commit();

                SharedPreferences HomeSHP = getActivity().getSharedPreferences("order info", Context.MODE_PRIVATE);
                SharedPreferences.Editor HomeEditor = HomeSHP.edit();
                HomeEditor.putString("type", "خانه").apply();
            }
        });

        Suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewServicesFragment newServicesFragment = new NewServicesFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.container1, newServicesFragment)
//                        .addToBackStack("tab1")
                        .commit();
            }
        });

        CarWash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstCarWashFragment carWashFragment = new FirstCarWashFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.container1, carWashFragment)
                        .addToBackStack("firstCarwashFrag")
                        .commit();
            }
        });

        return view;
    }
}
