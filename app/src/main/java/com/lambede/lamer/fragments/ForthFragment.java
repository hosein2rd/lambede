package com.lambede.lamer.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.analytics.Tracker;
//import com.google.firebase.analytics.FirebaseAnalytics;
import com.lambede.lamer.R;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.lambede.lamer.pop_up.PromptDialog;
import com.lambede.lamer.utilities.AnalyticsApplication;
import com.lambede.lamer.utilities.Singleton;


public class ForthFragment extends Fragment {

    TextView tvCost, tvDescription, tvForthTitle, tvAddressTitle, tvVoucherTitle;
    int iCost;
    EditText etVoucher, etAddress;
    ImageView ivVoucher, BlueLayer;
    int offPercent;
    SharedPreferences priceSHP, ExtraOrderInfoSHP, orderInfoSHP, hoursSHP, AddressSHP;
    boolean haveVoucher = false;
    Button bOrder;
    String sToken, sType, sSize, sHours, sDate, sGender, sLamers, sCabinet, sFridge, sFurniture, sCost;
    String offer, offerString = " ", sCostBefore, sVoucher;
    AVLoadingIndicatorView AV;
    RelativeLayout relativeLayout;
    Typeface IranYekanBold;
//    private FirebaseAnalytics mFirebaseAnalytics;
//    private Tracker mTracker;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //------------------------------------------------------------------------------------------
        //Hide keyboard on screen touch.
        View view = inflater.inflate(R.layout.fragment_forth, container, false);
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


        //------------------------------------------------------------------------------------------
        //Analytics
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
//        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
//        mTracker = application.getDefaultTracker();
//        mTracker.setScreenName("Resident Order Final");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());


        //------------------------------------------------------------------------------------------
        //Declaration
        tvCost = view.findViewById(R.id.forth_finalcost);
        etVoucher = view.findViewById(R.id.forth_discoun);
        ivVoucher = view.findViewById(R.id.forth_check);
        tvDescription = view.findViewById(R.id.forth_description);
        bOrder = view.findViewById(R.id.forth_button);
        etAddress = view.findViewById(R.id.forth_address);
        AV = view.findViewById(R.id.avi);
        relativeLayout = view.findViewById(R.id.Relative);
        tvForthTitle = view.findViewById(R.id.forth_title);
        tvAddressTitle = view.findViewById(R.id.forth_address_title);
        tvVoucherTitle = view.findViewById(R.id.forth_Discount_title);
        BlueLayer = getActivity().findViewById(R.id.blueback);


        //------------------------------------------------------------------------------------------
        //Fonts
        IranYekanBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");
        tvCost.setTypeface(IranYekanBold);
        tvForthTitle.setTypeface(IranYekanBold);
        tvAddressTitle.setTypeface(IranYekanBold);
        tvVoucherTitle.setTypeface(IranYekanBold);
        bOrder.setTypeface(IranYekanBold);


        //------------------------------------------------------------------------------------------
        //Get and set saved address
        AddressSHP = getActivity().getSharedPreferences("address", Context.MODE_PRIVATE);
        etAddress.setText(AddressSHP.getString("address", ""));

        priceSHP = getActivity().getSharedPreferences("prices", Context.MODE_PRIVATE);
        tvCost.setText("قیمت نهایی : " + priceSHP.getString("priceInt", "") + " تومان");
        iCost = Integer.parseInt(priceSHP.getString("priceInt", ""));

        hoursSHP = getActivity().getSharedPreferences("hours", Context.MODE_PRIVATE);
//        hoursEditor = hoursSHP.edit();

        ExtraOrderInfoSHP = getActivity().getSharedPreferences("extra", Context.MODE_PRIVATE);
        orderInfoSHP = getActivity().getSharedPreferences("order info", Context.MODE_PRIVATE);
        sType = orderInfoSHP.getString("type", "default");
        sSize = orderInfoSHP.getString("size", "defaulte");
        sHours = hoursSHP.getString("hours", "");
        sDate = orderInfoSHP.getString("date", "");
        sGender = orderInfoSHP.getString("gender of lamer", "");
        sLamers = orderInfoSHP.getString("lamer count", "");
        sLamers = sLamers.substring(0, 1);
        sCabinet = ExtraOrderInfoSHP.getString("cabinet", "default");
        sFridge = ExtraOrderInfoSHP.getString("frige", "default");
        sFurniture = ExtraOrderInfoSHP.getString("furniture", "default");
        sCost = String.valueOf(iCost);
        sCostBefore = sCost;



        if (ExtraOrderInfoSHP.getString("cabinet", "").equals("ندارد") &&
                ExtraOrderInfoSHP.getString("frige", "").equals("ندارد") &&
                ExtraOrderInfoSHP.getString("furniture", "").equals("ندارد")){
            tvDescription.setText("سفارش " + orderInfoSHP.getString("lamer count", "") + " " + orderInfoSHP.getString("gender of lamer", "") + " برای تاریخ " + orderInfoSHP.getString("date", "") );
        }
        else if (!ExtraOrderInfoSHP.getString("cabinet", "").equals("ندارد") &&
                ExtraOrderInfoSHP.getString("frige", "").equals("ندارد") &&
                ExtraOrderInfoSHP.getString("furniture", "").equals("ندارد")){
            tvDescription.setText("سفارش " + orderInfoSHP.getString("lamer count", "") + " " + orderInfoSHP.getString("gender of lamer", "") + " + تمیزکاری + "+ "کابینت" +" برای تاریخ " + orderInfoSHP.getString("date", "") );
        }
        else if (ExtraOrderInfoSHP.getString("cabinet", "").equals("ندارد") &&
                !ExtraOrderInfoSHP.getString("frige", "").equals("ندارد") &&
                ExtraOrderInfoSHP.getString("furniture", "").equals("ندارد")){
            tvDescription.setText("سفارش " + orderInfoSHP.getString("lamer count", "") + " " + orderInfoSHP.getString("gender of lamer", "") + " + تمیزکاری + "+ "یخچال" +" برای تاریخ " + orderInfoSHP.getString("date", "") );
        }
        else if (ExtraOrderInfoSHP.getString("cabinet", "").equals("ندارد") &&
                ExtraOrderInfoSHP.getString("frige", "").equals("ندارد") &&
                !ExtraOrderInfoSHP.getString("furniture", "").equals("ندارد")){
            tvDescription.setText("سفارش " + orderInfoSHP.getString("lamer count", "") + " " + orderInfoSHP.getString("gender of lamer", "") + " + تمیزکاری + "+ "جابجایی وسایل" +" برای تاریخ " + orderInfoSHP.getString("date", "") );
        }
        else if (!ExtraOrderInfoSHP.getString("cabinet", "").equals("ندارد") &&
                !ExtraOrderInfoSHP.getString("frige", "").equals("ندارد") &&
                ExtraOrderInfoSHP.getString("furniture", "").equals("ندارد")){
            tvDescription.setText("سفارش " + orderInfoSHP.getString("lamer count", "") + " " + orderInfoSHP.getString("gender of lamer", "") + " + تمیزکاری + "+ "یخچال " + "+ کابینت" + " برای تاریخ " + orderInfoSHP.getString("date", "") );
        }
        else if (!ExtraOrderInfoSHP.getString("cabinet", "").equals("ندارد") &&
                ExtraOrderInfoSHP.getString("frige", "").equals("ندارد") &&
                !ExtraOrderInfoSHP.getString("furniture", "").equals("ندارد")){
            tvDescription.setText("سفارش " + orderInfoSHP.getString("lamer count", "") + " " + orderInfoSHP.getString("gender of lamer", "") + " + تمیزکاری + "+ "کابینت " + "+ جابجایی وسایل" + " برای تاریخ " + orderInfoSHP.getString("date", "") );
        }
        else if (ExtraOrderInfoSHP.getString("cabinet", "").equals("ندارد") &&
                !ExtraOrderInfoSHP.getString("frige", "").equals("ندارد") &&
                !ExtraOrderInfoSHP.getString("furniture", "").equals("ندارد")){
            tvDescription.setText("سفارش " + orderInfoSHP.getString("lamer count", "") + " " + orderInfoSHP.getString("gender of lamer", "") + " + تمیزکاری + "+ "یخچال " + "+ جابجایی وسایل" + " برای تاریخ " + orderInfoSHP.getString("date", "") );
        }
        else if (!ExtraOrderInfoSHP.getString("cabinet", "").equals("ندارد") &&
                !ExtraOrderInfoSHP.getString("frige", "").equals("ندارد") &&
                !ExtraOrderInfoSHP.getString("furniture", "").equals("ندارد")){
            tvDescription.setText("سفارش " + orderInfoSHP.getString("lamer count", "") + " " + orderInfoSHP.getString("gender of lamer", "") + " + تمیزکاری + " + " یخچال + " + "کابینت " + "+ جابجایی وسایل" + " برای تاریخ " + orderInfoSHP.getString("date", "") );
        }

        bOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderClicked();
            }
        });

        SharedPreferences previousCostSHP = getActivity().getSharedPreferences("prices", Context.MODE_PRIVATE);
        SharedPreferences.Editor previousCostED = previousCostSHP.edit();
        previousCostED.putString("previousCost", String.valueOf(iCost)).apply();

        ivVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!haveVoucher) {

                    if (etVoucher.getText().toString().length() == 0){
                        popupForEmptyCode();
                    }
                    else {
                        BlueLayer.setVisibility(View.VISIBLE);
                        AV.show();
                        AV.setVisibility(View.VISIBLE);
                        relativeLayout.setVisibility(View.VISIBLE);
                        checkVoucher();
                    }

                } else {
                    ivVoucher.setImageResource(R.drawable.voucher_check);
                    iCost = Integer.parseInt(sCostBefore);
                    haveVoucher = false;
                    tvCost.setText("قیمت نهایی : " + String.valueOf(iCost) + " تومان");
                    etVoucher.setText(null);
                    offerString = "";
                }
            }
        });

        return view;
    }

    public void checkVoucher(){
        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://www.lambede.com/api/v3/orders/voucher",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            ivVoucher.setImageResource(R.drawable.cancel);

                            offer = response.substring(14, response.length()-1);
                            offerString = etVoucher.getText().toString();

                            offPercent = Integer.parseInt(offer);

                            int costoffer = (offPercent * iCost)/100;

                            iCost = iCost - costoffer;

                            sCost = String.valueOf(iCost);

                            BlueLayer.setVisibility(View.GONE);
                            AV.hide();
                            AV.setVisibility(View.GONE);
                            relativeLayout.setVisibility(View.GONE);

                            tvCost.animate().alpha(0f).setDuration(100);

                            sVoucher = etVoucher.getText().toString();

                            haveVoucher = true;

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    tvCost.setText("قیمت نهایی : " + String.valueOf(iCost) + " تومان");
                                    tvCost.animate().alpha(1f).setDuration(100);
                                    popupForValidCode();
                                }
                            }, 100);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            popupForInvalidCode();
                            BlueLayer.setVisibility(View.GONE);
                            AV.hide();
                            AV.setVisibility(View.GONE);
                            relativeLayout.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        BlueLayer.setVisibility(View.GONE);
                        AV.setVisibility(View.GONE);
                        AV.hide();
                        relativeLayout.setVisibility(View.GONE);

                        if (error instanceof NetworkError || error instanceof AuthFailureError ||
                                error instanceof ParseError || error instanceof TimeoutError){
                            popupForOnError();
                        }
                        else {
                            int statusCode = error.networkResponse.statusCode;

                            Log.e("status code", statusCode + "");

                            if (statusCode == 404){
                                popupForInvalidCode();
                            }
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("voucher", etVoucher.getText().toString());

                return params;
            }
        };
        Singleton.getInstance(getActivity()).addToRequestqueue(postRequest);
    }

    public void orderClicked(){

        if (etAddress.getText().toString().trim().length() <= 10){
            popupForEmptyAddress();
        }
        else {
            BlueLayer.setVisibility(View.VISIBLE);
            AV.show();
            AV.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.VISIBLE);
            postingOrder();
        }

    }

    public void postingOrder(){
        SharedPreferences Token = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        sToken = Token.getString("token", "");
        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://www.lambede.com/api/v3/orders/Order",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String url = jsonObject.getString("url");
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                            SharedPreferences AddressSHP = getActivity().getSharedPreferences("address", Context.MODE_PRIVATE);
                            SharedPreferences.Editor addressEditor = AddressSHP.edit();
                            addressEditor.putString("address", etAddress.getText().toString()).apply();

                            SharedPreferences idSHP = getActivity().getSharedPreferences("id", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = idSHP.edit();
                            String id = jsonObject.getString("id");
                            editor.putString("id", id).apply();
                            startActivity(browserIntent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        BlueLayer.setVisibility(View.GONE);
                        AV.hide();
                        AV.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        popupForOnError();

                        BlueLayer.setVisibility(View.GONE);
                        AV.hide();
                        AV.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.GONE);
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("token", sToken);
                return headers;
            }

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("type", sType);
                params.put("size", sSize);
                params.put("hour", sHours);
                params.put("dueDate", sDate);
                params.put("gender", sGender);
                params.put("lamers", sLamers);
                params.put("cabinet", sCabinet);
                params.put("fridge", sFridge);
                params.put("furniture", sFurniture);
                params.put("address", etAddress.getText().toString());
                params.put("price", String.valueOf(iCost));
                params.put("app", "android");
                params.put("voucher", offerString);

                return params;
            }
        };
        Singleton.getInstance(getActivity()).addToRequestqueue(postRequest);
    }

    //------------------------------------------------------------------------------------------
    //PopUp

    public void popupForInvalidCode(){
        new PromptDialog(getContext())
                .setDialogType(PromptDialog.DIALOG_TYPE_ERROR)
                .setAnimationEnable(true)
                .setTitleText(R.string.message)
                .setContentText(R.string.invalid_voucher_code)
                .setDismissingListener(R.string.ok, new PromptDialog.OnDismissingListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void popupForValidCode(){
        new PromptDialog(getContext())
                .setDialogType(PromptDialog.DIALOG_TYPE_ERROR)
                .setAnimationEnable(true)
                .setTitleText(R.string.message)
                .setContentText(R.string.voucher)
                .setDismissingListener(R.string.ok, new PromptDialog.OnDismissingListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void popupForEmptyAddress(){
        new PromptDialog(getContext())
                .setDialogType(PromptDialog.DIALOG_TYPE_ERROR)
                .setAnimationEnable(true)
                .setTitleText(R.string.message)
                .setContentText(R.string.empty_address)
                .setDismissingListener(R.string.ok, new PromptDialog.OnDismissingListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void popupForEmptyCode(){
        new PromptDialog(getContext())
                .setDialogType(PromptDialog.DIALOG_TYPE_ERROR)
                .setAnimationEnable(true)
                .setTitleText(R.string.message)
                .setContentText(R.string.enter_voucher)
                .setDismissingListener(R.string.ok, new PromptDialog.OnDismissingListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void popupForOnError(){
        new PromptDialog(getContext())
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
}
