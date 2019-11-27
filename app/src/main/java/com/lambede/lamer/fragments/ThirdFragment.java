package com.lambede.lamer.fragments;


import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.analytics.Tracker;
//import com.google.firebase.analytics.FirebaseAnalytics;

import com.lambede.lamer.R;
import com.lambede.lamer.pop_up.PromptDialog;
import com.lambede.lamer.utilities.AnalyticsApplication;


public class ThirdFragment extends Fragment {


    public ThirdFragment() {
        // Required empty public constructor
    }

    FrameLayout Next;
    ImageView cabinet, frige, furniture;
    ImageView tick_cabinet, tick_frige, tick_furniture;
    CardView cabinetCard, frigeCard, furnitureCard;
    RelativeLayout frige_container, cabinet_container, furniture_container;
    TextView Cost, tvThirdTitle, tvThirdCabinetText, tvThirdFurnitureText, tvThirdFridgeText;
    SharedPreferences orderInfo;
    SharedPreferences.Editor orderInfoEdit;
    Boolean cabinetCheck = false, frigeCheck = false, furnitureCheck = false;
    Typeface IranYekanBold;
    int CostInt;
    int extra1Int, extra2Int, extra3Int;
    String extra1String, extra2String, extra3String;
    String CostString;
//    private FirebaseAnalytics mFirebaseAnalytics;
//    private Tracker mTracker;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        // Obtain the FirebaseAnalytics instance.
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
//
//        //Analytics Codes
//        // Obtain the shared Tracker instance.
//        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
//        mTracker = application.getDefaultTracker();
//        mTracker.setScreenName("Resident Order 3");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Next = view.findViewById(R.id.third_button);

        Cost = view.findViewById(R.id.third_cost);

        cabinet = view.findViewById(R.id.third_cabinet_img);
        frige = view.findViewById(R.id.third_frige_img);
        furniture = view.findViewById(R.id.third_furniture_img);

        tick_cabinet = view.findViewById(R.id.third_cabinet_tick);
        tick_frige = view.findViewById(R.id.third_frige_tick);
        tick_furniture = view.findViewById(R.id.third_furniture_tick);

        cabinetCard = view.findViewById(R.id.third_cabinet_card);
        frigeCard = view.findViewById(R.id.third_frige_card);
        furnitureCard = view.findViewById(R.id.third_furniture_card);

        frige_container = view.findViewById(R.id.third_fridge_container);
        cabinet_container = view.findViewById(R.id.third_cabinet_container);
        furniture_container = view.findViewById(R.id.third_furniture_container);

        tvThirdCabinetText = view.findViewById(R.id.third_cabinet_title);
        tvThirdFurnitureText = view.findViewById(R.id.third_furniture_title);
        tvThirdFridgeText = view.findViewById(R.id.third_frige_title);

        tvThirdTitle = view.findViewById(R.id.third_title);

        SharedPreferences prices = getActivity().getSharedPreferences("prices", Context.MODE_PRIVATE);
        CostString = prices.getString("priceInt", "");
        CostInt = Integer.parseInt(CostString);

        IranYekanBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");
        tvThirdTitle.setTypeface(IranYekanBold);
        tvThirdFridgeText.setTypeface(IranYekanBold);
        tvThirdCabinetText.setTypeface(IranYekanBold);
        tvThirdFurnitureText.setTypeface(IranYekanBold);
        Cost.setTypeface(IranYekanBold);


        SharedPreferences priceSHP = getActivity().getSharedPreferences("prices", Context.MODE_PRIVATE);

        SharedPreferences CostSHP = getActivity().getSharedPreferences("cost", Context.MODE_PRIVATE);
        Cost.setText(CostSHP.getString("cost", "" + "hvvvvvvvvvvvv"));

        orderInfo = getActivity().getSharedPreferences("extra", Context.MODE_PRIVATE);
        orderInfoEdit = orderInfo.edit();
        orderInfoEdit.putString("cabinet", "ندارد");
        orderInfoEdit.putString("frige", "ندارد");
        orderInfoEdit.putString("furniture", "ندارد");
        orderInfoEdit.apply();

        SharedPreferences pricesSHPSplash = getActivity().getSharedPreferences("prices", Context.MODE_PRIVATE);
        extra1String = pricesSHPSplash.getString("extra 1", "0");
        extra2String = pricesSHPSplash.getString("extra 2", "0");
        extra3String = pricesSHPSplash.getString("extra 3", "0");

        extra1Int = Integer.parseInt(extra1String);
        extra2Int = Integer.parseInt(extra2String);
        extra3Int = Integer.parseInt(extra3String);

        cabinet_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cabinetClicked();
            }
        });

        frige_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fridgeClicked();
            }
        });

        furniture_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                furnitureClicked();
            }
        });


        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (cabinetCheck){
//                    orderInfoEdit.putString("cabinet", "دارد");
//                }
//                if (frigeCheck){
//                    orderInfoEdit.putString("frige", "دارد");
//                }
//                if (furnitureCheck){
//                    orderInfoEdit.putString("furniture", "دارد");
//                }
//                orderInfoEdit.apply();

                ForthFragment forthfragment = new ForthFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().add(R.id.container1, forthfragment)
                        .addToBackStack("forthFrag")
                        .commit();
            }
        });

        return view;
    }

    //functions:

    public void cabinetClicked(){
        if (!cabinetCheck){
            cabinet.setImageResource(R.drawable.cabinet_true);
            tick_cabinet.setImageResource(R.drawable.check_cyan);
            cabinetCheck = true;
            orderInfoEdit.putString("cabinet", "دارد").apply();
            CostInt = CostInt + extra1Int;
            Cost.setText("هزینه سفارش : " + CostInt + " تومان");
            SharedPreferences priceSHP = getActivity().getSharedPreferences("prices", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = priceSHP.edit();
            editor.putString("priceInt", String.valueOf(CostInt)).apply();
        }
        else if (cabinetCheck){
            cabinet.setImageResource(R.drawable.cabinet_false);
            tick_cabinet.setImageResource(R.drawable.check_gray);
            cabinetCheck = false;
            orderInfoEdit.putString("cabinet", "ندارد").apply();
            CostInt = CostInt - extra1Int;
            Cost.setText("هزینه سفارش : " + CostInt + " تومان");
            SharedPreferences priceSHP = getActivity().getSharedPreferences("prices", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = priceSHP.edit();
            editor.putString("priceInt", String.valueOf(CostInt)).apply();
        }
    }

    public void fridgeClicked(){

        if (!frigeCheck){
            frige.setImageResource(R.drawable.fridge_true);
            tick_frige.setImageResource(R.drawable.check_cyan);
            frigeCheck = true;
            orderInfoEdit.putString("frige", "دارد").apply();
            CostInt = CostInt + extra2Int;
            Cost.setText("هزینه سفارش : " + CostInt + " تومان");
            SharedPreferences priceSHP = getActivity().getSharedPreferences("prices", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = priceSHP.edit();
            editor.putString("priceInt", String.valueOf(CostInt)).apply();
        } else if (frigeCheck){
            frige.setImageResource(R.drawable.fridge_false);
            tick_frige.setImageResource(R.drawable.check_gray);
            orderInfoEdit.putString("frige", "ندارد").apply();
            frigeCheck = false;
            CostInt = CostInt - extra2Int;
            Cost.setText("هزینه سفارش : " + CostInt + " تومان");
            SharedPreferences priceSHP = getActivity().getSharedPreferences("prices", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = priceSHP.edit();
            editor.putString("priceInt", String.valueOf(CostInt)).apply();
        }
    }

    public void furnitureClicked(){

        if (!furnitureCheck){
            furniture.setImageResource(R.drawable.furniture_true);
            tick_furniture.setImageResource(R.drawable.check_cyan);
            furnitureCheck = true;
            orderInfoEdit.putString("furniture", "دارد").apply();
            CostInt = CostInt + extra3Int;
            Cost.setText("هزینه سفارش : " + CostInt + " تومان");
            SharedPreferences priceSHP = getActivity().getSharedPreferences("prices", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = priceSHP.edit();
            editor.putString("priceInt", String.valueOf(CostInt)).apply();

        } else if (furnitureCheck){
            furniture.setImageResource(R.drawable.furniture_false);
            tick_furniture.setImageResource(R.drawable.check_gray);
            furnitureCheck = false;
            orderInfoEdit.putString("furniture", "ندارد").apply();
            CostInt = CostInt - extra3Int;
            Cost.setText("هزینه سفارش : " + CostInt + " تومان");
            SharedPreferences priceSHP = getActivity().getSharedPreferences("prices", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = priceSHP.edit();
            editor.putString("priceInt", String.valueOf(CostInt)).apply();

        }

    }

}