package com.lambede.lamer.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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


public class SecondCarwashFragment extends Fragment {

    EditText Address, VoucherEditText;
    ImageView CheckingVoucher, BlueLayer;
    Button Order;
    TextView Cost, Description, tvSecondCarwashTitle, tvSecondCarwashAddressTitle, tvSecondCarwashVoucherTitle;
    SharedPreferences CostSHP;
    String CostString, Date, Type, Model, offer, offerString = " ", token, hours, sCostBefore;
    int CostInt;
    boolean checked = false;
    int cost;
    String codeOfVOucher;
    AVLoadingIndicatorView AV;
    RelativeLayout relativeLayout;
    Typeface IranYekanBold;
//    private FirebaseAnalytics mFirebaseAnalytics;
//    private Tracker mTracker;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_second_carwash, container, false);

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
//        mTracker.setScreenName("Carwash Order Final");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Address = view.findViewById(R.id.second_carwash_address);
        CheckingVoucher = view.findViewById(R.id.second_carwash_check);
        VoucherEditText = view.findViewById(R.id.second_carwash_discount);
        Order = view.findViewById(R.id.second_carwash_button);
        Cost = view.findViewById(R.id.second_carwash_finalcost);
        Description = view.findViewById(R.id.second_carwash_description);
        AV = view.findViewById(R.id.avi);
        relativeLayout = view.findViewById(R.id.Relative);
        tvSecondCarwashTitle = view.findViewById(R.id.second_carwash_title);
        tvSecondCarwashAddressTitle = view.findViewById(R.id.second_carwash_address_title);
        tvSecondCarwashVoucherTitle = view.findViewById(R.id.second_carwash_Discount_title);
        BlueLayer = getActivity().findViewById(R.id.blueback);

        CostSHP = getActivity().getSharedPreferences("info of carwash", Context.MODE_PRIVATE);
        CostString = CostSHP.getString("cost string", "");
        CostInt = CostSHP.getInt("cost int", 0);
        Date = CostSHP.getString("date", "");
        Type = CostSHP.getString("type", "");
        Model = CostSHP.getString("model", "");
        hours = CostSHP.getString("hour", "");
        sCostBefore = CostString;

        IranYekanBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");
        Cost.setTypeface(IranYekanBold);
        Order.setTypeface(IranYekanBold);
        tvSecondCarwashTitle.setTypeface(IranYekanBold);
        tvSecondCarwashVoucherTitle.setTypeface(IranYekanBold);
        tvSecondCarwashAddressTitle.setTypeface(IranYekanBold);

        SharedPreferences AddressSHP = getActivity().getSharedPreferences("address", Context.MODE_PRIVATE);
        Address.setText(AddressSHP.getString("address", ""));

        Description.setText("سفارش " + Type + " " + "خودروی " + Model + " " + "برای تاریخ " + Date + " ساعت " + hours);

        Cost.setText("قیمت نهایی : " + CostString + " تومان");

        CheckingVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VoucherEditText.length() == 0){
                    popupForEmptyCode();
                }
                else {

                    if (!checked) {

                        BlueLayer.setVisibility(View.VISIBLE);
                        AV.show();
                        AV.setVisibility(View.VISIBLE);
                        relativeLayout.setVisibility(View.VISIBLE);
                        checkVoucher();

                    } else {

                        CheckingVoucher.setImageResource(R.drawable.voucher_check);
                        cost = Integer.parseInt(sCostBefore);
                        checked = false;
                        Cost.setText("قیمت نهایی : " + String.valueOf(cost) + " تومان");
                        VoucherEditText.setText(null);
                        offerString = "";
                    }
                }
            }
        });

        Order.setOnClickListener(new View.OnClickListener() {
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
                        // response
                        offer = response.substring(14, response.length()-1);
                        offerString = VoucherEditText.getText().toString();

                        CheckingVoucher.setImageResource(R.drawable.cancel);

                        int offpercent = Integer.parseInt(offer);

                        cost = CostInt;

                        int costoffer = (offpercent * cost)/100;

                        cost = cost - costoffer;

                        CostString = String.valueOf(cost);

                        BlueLayer.setVisibility(View.GONE);
                        AV.setVisibility(View.GONE);
                        AV.hide();
                        relativeLayout.setVisibility(View.GONE);

                        Cost.animate().alpha(0f).setDuration(100);

                        codeOfVOucher = VoucherEditText.getText().toString();

                        checked = true;

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Cost.setText("قیمت نهایی : " + CostString + " تومان");
                                Cost.animate().alpha(1f).setDuration(100);
                                popupForValidCode();
                            }
                        }, 100);

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
                params.put("voucher", VoucherEditText.getText().toString());

                return params;
            }
        };
        Singleton.getInstance(getActivity()).addToRequestqueue(postRequest);
    }

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

    public void orderClicked(){

        if (Address.getText().toString().trim().length() == 0){
            popupForEmptyAddress();
        }
        else {

            BlueLayer.setVisibility(View.VISIBLE);
            AV.setVisibility(View.VISIBLE);
            AV.show();
            relativeLayout.setVisibility(View.VISIBLE);

            postingOrder();
        }
    }

    public void postingOrder(){
        SharedPreferences Token = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        token = Token.getString("token", "");
        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://www.lambede.com/api/v3/orders/Order",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Response", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String url = jsonObject.getString("url");

                            BlueLayer.setVisibility(View.GONE);
                            AV.setVisibility(View.GONE);
                            AV.hide();
                            relativeLayout.setVisibility(View.GONE);

                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(browserIntent);
                            SharedPreferences idSHP = getActivity().getSharedPreferences("id", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = idSHP.edit();

                            SharedPreferences AddressSHP = getActivity().getSharedPreferences("address", Context.MODE_PRIVATE);
                            SharedPreferences.Editor addressEditor = AddressSHP.edit();
                            addressEditor.putString("address", Address.getText().toString()).apply();

                            String id = jsonObject.getString("id");
                            editor.putString("id", id).apply();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.e("Error :", "Error");
                        Log.e("VoucherString", "----------------- ");

                        popupForOnError();

                        BlueLayer.setVisibility(View.GONE);
                        AV.setVisibility(View.GONE);
                        AV.hide();
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
                params.put("type", "کارواش");
                params.put("title", Type);
//                params.put("size", size);
//                params.put("hour", hours);
                params.put("dueDate", Date);
//                params.put("gender", gender);
//                params.put("lamers", lamers);
//                params.put("cabinet", cabinet);
//                params.put("fridge", frige);
//                params.put("furniture", furniture);
                params.put("address", Address.getText().toString());
                params.put("price", String.valueOf(cost));
                params.put("app", "android");
                params.put("carName", Model);
                params.put("voucher", offerString);
                params.put("dueHour", hours);

                return params;
            }
        };
        Singleton.getInstance(getActivity()).addToRequestqueue(postRequest);
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
