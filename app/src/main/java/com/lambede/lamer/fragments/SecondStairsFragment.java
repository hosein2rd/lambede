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


public class SecondStairsFragment extends Fragment {

    SharedPreferences InfoSHP, CostSHP, AddressSHP, TokenSHP;
    String sDate, sSize, sFloor, sCostBefore, token, sOffer, sVoucher = " ", sCost;
    EditText etAddress, etVoucher;
    boolean haveVoucher = false;
    ImageView ivVoucher;
    Button bOrder;
    TextView tvCost, tvSecondStairsTitle, tvSecondStairsAddressTitle, tvSecondStairsVoucherTitle;
    int iCost;
    AVLoadingIndicatorView AV;
    RelativeLayout relativeLayout;
    Typeface IranYekanBold;
    ImageView BlueLayer;
//    private FirebaseAnalytics mFirebaseAnalytics;
//    private Tracker mTracker;
    TextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_stairs, container, false);


        //------------------------------------------------------------------------------------------
        //Hide keyboard on screen touch.
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
//        mTracker.setScreenName("Stair Order Final");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());


        //------------------------------------------------------------------------------------------
        //Declaration
        etAddress = view.findViewById(R.id.second_stairs_address);
        ivVoucher = view.findViewById(R.id.second_stairs_check);
        etVoucher = view.findViewById(R.id.second_stairs_discount);
        bOrder = view.findViewById(R.id.second_stairs_button);
        tvCost = view.findViewById(R.id.second_stairs_finalcost);
        AV = view.findViewById(R.id.avi);
        relativeLayout = view.findViewById(R.id.Relative);
        tvSecondStairsTitle = view.findViewById(R.id.second_stairs_title);
        tvSecondStairsAddressTitle = view.findViewById(R.id.second_stairs_address_title);
        tvSecondStairsVoucherTitle = view.findViewById(R.id.second_stairs_Discount_title);
        BlueLayer = getActivity().findViewById(R.id.blueback);
        title = view.findViewById(R.id.second_stairs_description);

        //------------------------------------------------------------------------------------------
        //Fonts
        IranYekanBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");
        tvCost.setTypeface(IranYekanBold);
        bOrder.setTypeface(IranYekanBold);
        tvSecondStairsTitle.setTypeface(IranYekanBold);
        tvSecondStairsAddressTitle.setTypeface(IranYekanBold);
        tvSecondStairsVoucherTitle.setTypeface(IranYekanBold);

        //------------------------------------------------------------------------------------------
        //Get and set saved address
        AddressSHP = getActivity().getSharedPreferences("address", Context.MODE_PRIVATE);
        etAddress.setText(AddressSHP.getString("address", ""));

        //------------------------------------------------------------------------------------------
        //Get Token
        TokenSHP = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        token = TokenSHP.getString("token", "");

        //------------------------------------------------------------------------------------------
        //Get and set cost details
        CostSHP = getActivity().getSharedPreferences("cost", Context.MODE_PRIVATE);
        sCost = CostSHP.getString("costInt", "0");
        sCostBefore = sCost;
        iCost = Integer.parseInt(sCost);
        tvCost.setText("قیمت نهایی : " + sCost + " تومان");

        //------------------------------------------------------------------------------------------
        //Get and set order details
        InfoSHP = getActivity().getSharedPreferences("order info", Context.MODE_PRIVATE);
        sDate = InfoSHP.getString("date", "default");
        sSize = InfoSHP.getString("size", "default");
        sFloor = InfoSHP.getString("floor", "default");
        title.setText("سفارش تمیزکاری " + sFloor + " طبقه " + "برای تاریخ " + sDate);


        //------------------------------------------------------------------------------------------
        //Check voucher
        ivVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!haveVoucher) {

                    if(etVoucher.getText().toString().trim().length() == 0){
                        popupForEmptyCode();

                    }else {
                        BlueLayer.setVisibility(View.VISIBLE);
                        AV.show();
                        AV.setVisibility(View.VISIBLE);
                        relativeLayout.setVisibility(View.VISIBLE);
                        checkVoucher();
                    }

                }else {

                    ivVoucher.setImageResource(R.drawable.voucher_check);
                    iCost = Integer.parseInt(sCostBefore);
                    haveVoucher = false;
                    tvCost.setText("قیمت نهایی : " + String.valueOf(iCost) + " تومان");
                    etVoucher.setText("");
                    sVoucher = "";

                }
            }
        });

        //------------------------------------------------------------------------------------------
        //Order
        bOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderClicked();
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

                        sOffer = response.substring(14, response.length()-1);
                        sVoucher = etVoucher.getText().toString();
                        ivVoucher.setImageResource(R.drawable.cancel);


                        int offpercent = Integer.parseInt(sOffer);
                        int costoffer = (offpercent * Integer.parseInt(sCost))/100;
                        iCost = iCost - costoffer;
                        sCost = String.valueOf(iCost);

                        BlueLayer.setVisibility(View.GONE);
                        AV.hide();
                        AV.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.GONE);


                        haveVoucher = true;


                        tvCost.animate().alpha(0f).setDuration(100);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tvCost.setText("قیمت نهایی : " + String.valueOf(iCost) + " تومان");
                                tvCost.animate().alpha(1f).setDuration(100);
                                popupForValidCode();
                            }
                        }, 100);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        BlueLayer.setVisibility(View.GONE);
                        AV.hide();
                        AV.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.GONE);
                        popupForInvalidCode();
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

        if (!haveVoucher){
            SharedPreferences Token = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
            token = Token.getString("token", "");
            StringRequest postRequest = new StringRequest(Request.Method.POST, "https://www.lambede.com/api/v3/orders/order",
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String url = jsonObject.getString("url");
                                SharedPreferences idSHP = getActivity().getSharedPreferences("id", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = idSHP.edit();
                                String id = jsonObject.getString("id");
                                editor.putString("id", id).apply();
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
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
                            // error
                            Log.e("Error :", "Error");

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
                    headers.put("token", token);
                    return headers;
                }

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("type", "راهرو");
                    params.put("size", sSize);
                    params.put("dueDate", sDate);
                    params.put("gender", "آقا");
                    params.put("floors", sFloor);
                    params.put("address", etAddress.getText().toString());
                    params.put("price", String.valueOf(iCost));
                    params.put("app", "android");

                    return params;
                }
            };
            Singleton.getInstance(getActivity()).addToRequestqueue(postRequest);
        } else {
            SharedPreferences Token = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
            token = Token.getString("token", "");
            StringRequest postRequest = new StringRequest(Request.Method.POST, "https://www.lambede.com/api/v3/orders/order",
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.e("Response", response);

                            BlueLayer.setVisibility(View.GONE);
                            AV.hide();
                            AV.setVisibility(View.GONE);
                            relativeLayout.setVisibility(View.GONE);

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String url = jsonObject.getString("url");
                                SharedPreferences idSHP = getActivity().getSharedPreferences("id", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = idSHP.edit();

                                SharedPreferences AddressSHP = getActivity().getSharedPreferences("address", Context.MODE_PRIVATE);
                                SharedPreferences.Editor addressEditor = AddressSHP.edit();
                                addressEditor.putString("address", etAddress.getText().toString()).apply();

                                String id = jsonObject.getString("id");
                                editor.putString("id", id).apply();
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(browserIntent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Error.Response", "Error");

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
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    //headers.put("Content-Type", "application/json");
                    headers.put("token", token);
                    return headers;
                }

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("type", "راهرو");
                    params.put("size", sSize);
                    params.put("dueDate", sDate);
                    params.put("gender", "آقا");
                    params.put("floors", sFloor);
                    params.put("address", etAddress.getText().toString());
                    params.put("price", sCost);
                    params.put("app", "android");
                    params.put("voucher", sVoucher);

                    return params;
                }
            };
            Singleton.getInstance(getActivity()).addToRequestqueue(postRequest);
        }
    }



    //------------------------------------------------------------------------------------------
    //Pop Ups

    public void popupForInvalidCode(){
        new PromptDialog(getContext())
                .setDialogType(PromptDialog.DIALOG_TYPE_ERROR)
                .setAnimationEnable(true)
                .setTitleText(R.string.message)
                .setContentText(R.string.invalid_voucher_code)
                .setDismissingListener(R.string.ok, new PromptDialog.OnDismissingListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        BlueLayer.setVisibility(View.GONE);
                        AV.hide();
                        AV.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.GONE);
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
