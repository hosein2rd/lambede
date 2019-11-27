package com.lambede.lamer.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.analytics.Tracker;
//import com.google.firebase.analytics.FirebaseAnalytics;
import com.lambede.lamer.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lambede.lamer.pop_up.PromptDialog;
import com.lambede.lamer.utilities.AnalyticsApplication;
import com.lambede.lamer.utilities.Singleton;
import com.lambede.lamer.activities.StartActivity;


public class EditFragment extends Fragment {

    RelativeLayout SignOut;
    TextView Phone, tvEditTitle;
    EditText Name, Mail;
    String token, phone, mail, name;
    Button UpdatingInfo;
    SharedPreferences infoSHP;
    AVLoadingIndicatorView AV;
    RelativeLayout relativeLayout;
    Typeface IranYekanBold;
    ImageView BlueLayer;
//    private FirebaseAnalytics mFirebaseAnalytics;
//    private Tracker mTracker;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

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
//        mTracker.setScreenName("Edit Profile");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Phone = view.findViewById(R.id.edit_phonenumber_edittext);
        Name = view.findViewById(R.id.edit_name);
        Mail = view.findViewById(R.id.edit_mail_text);
        UpdatingInfo = view.findViewById(R.id.edit_set);
        AV = view.findViewById(R.id.avi);
        relativeLayout = view.findViewById(R.id.Relative);
        tvEditTitle = view.findViewById(R.id.edit_title);
        BlueLayer = getActivity().findViewById(R.id.blueback);

        IranYekanBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");
        UpdatingInfo.setTypeface(IranYekanBold);
        tvEditTitle.setTypeface(IranYekanBold);



        infoSHP = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        phone = infoSHP.getString("phone", "");
        mail = infoSHP.getString("email", "");
        name = infoSHP.getString("name", "");
        Phone.setText(phone);
        Name.setText(name);
        Mail.setText(mail);

        SharedPreferences Token = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        token = Token.getString("token", "");


        UpdatingInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Name.getText().toString().trim().length() == 0 || Mail.getText().toString().trim().length() == 0){
                    popupForEmptyField();
                }
                else {
                    String MailValidation = Mail.getText().toString();
                    if (isValidMail(MailValidation)){
                        AV.show();
                        BlueLayer.setVisibility(View.VISIBLE);
                        AV.setVisibility(View.VISIBLE);
                        relativeLayout.setVisibility(View.VISIBLE);
                        updateInfo();
                    }
                    else {
                        popupForInvalidEmail();
                    }
                }
            }
        });

        SignOut = view.findViewById(R.id.logout_container);
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutClicked();
            }
        });

        return view;
    }

    public void signOutClicked(){
        popUpForSignout();
    }

    public void updateInfo(){
        infoSHP = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        mail = infoSHP.getString("email", "");
        name = infoSHP.getString("name", "");
        if (Mail.getText().toString().equals(mail) && Name.getText().toString().equals(name)){
            popupForNoChange();
        }
        else {
            StringRequest postRequest = new StringRequest(Request.Method.POST, "https://www.lambede.com/api/v3/users/updateInfo",
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

                            if (response.equals("{\"success\":\"true\"}")){

                                popUpForUpdateInfo();

                                SharedPreferences info = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
                                SharedPreferences.Editor infoEditor = info.edit();
                                infoEditor.putString("name", Name.getText().toString());
                                infoEditor.putString("email", Mail.getText().toString());
                                infoEditor.apply();

                                Name.setText(infoSHP.getString("name", ""));
                                Mail.setText(infoSHP.getString("email", ""));

                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.e("Error.Response", "Error");
                            BlueLayer.setVisibility(View.GONE);
                            AV.hide();
                            AV.setVisibility(View.GONE);
                            relativeLayout.setVisibility(View.GONE);
                            popupForOnError();
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
                    params.put("name", Name.getText().toString());
                    params.put("email", Mail.getText().toString());

                    return params;
                }
            };
            Singleton.getInstance(getActivity()).addToRequestqueue(postRequest);
        }
    }

    public void popUpForSignout(){
        new PromptDialog(getContext())
                .setDialogType(PromptDialog.DIALOG_TYPE_EXIT)
                .setAnimationEnable(true)
                .setTitleText(R.string.logout)
                .setContentText(R.string.exit_from_acc)
                .setDismissingListener(R.string.yes, new PromptDialog.OnDismissingListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {

                        SharedPreferences info = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
                        SharedPreferences.Editor infoEditor = info.edit();
                        infoEditor.clear().apply();

                        SharedPreferences myordersLengthSHP = getActivity().getSharedPreferences("length of my orders", Context.MODE_PRIVATE);


                        for (int i = myordersLengthSHP.getInt("length", 0) - 1; i >= 0; i--){
                            SharedPreferences myOrderSHP = getActivity().getSharedPreferences("order" + i, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = myOrderSHP.edit();
                            editor.clear().apply();
                        }

                        SharedPreferences.Editor myorderlengthEditor = myordersLengthSHP.edit();
                        myorderlengthEditor.clear().apply();

                        SharedPreferences orderInfo = getActivity().getSharedPreferences("order info", Context.MODE_PRIVATE);
                        SharedPreferences.Editor orderInfoEdit = orderInfo.edit();
                        orderInfoEdit.clear().apply();

//                        SharedPreferences prices = getActivity().getSharedPreferences("prices", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor pricesEditor = prices.edit();
//                        pricesEditor.clear().apply();

                        SharedPreferences hoursSHP = getActivity().getSharedPreferences("hours", Context.MODE_PRIVATE);
                        SharedPreferences.Editor hoursEditor = hoursSHP.edit();
                        hoursEditor.clear().apply();

                        SharedPreferences CostSHP = getActivity().getSharedPreferences("cost", Context.MODE_PRIVATE);
                        SharedPreferences.Editor CostEditor = CostSHP.edit();
                        CostEditor.clear().apply();

//                        SharedPreferences TimeSHP = getActivity().getSharedPreferences("times", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor timeEditor = TimeSHP.edit();
//                        timeEditor.clear().apply();

                        SharedPreferences ExtraOrderInfoSHP = getActivity().getSharedPreferences("extra", Context.MODE_PRIVATE);
                        SharedPreferences.Editor ExtraEditor = ExtraOrderInfoSHP.edit();
                        ExtraEditor.clear().apply();

                        SharedPreferences CostSHPE = getActivity().getSharedPreferences("info of carwash", Context.MODE_PRIVATE);
                        SharedPreferences.Editor CostEditors = CostSHPE.edit();
                        CostEditors.clear().apply();

                        SharedPreferences AddressSHP = getActivity().getSharedPreferences("address", Context.MODE_PRIVATE);
                        SharedPreferences.Editor addressEditor = AddressSHP.edit();
                        addressEditor.clear().apply();

                        Intent homeIntent = new Intent(getActivity(), StartActivity.class);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(homeIntent);

//                        Intent intent = new Intent(getActivity(), StartActivity.class);
//                        startActivity(intent);

                        getActivity().finish();

                        dialog.dismiss();
                    }
                }).setExitListener(R.string.no, new PromptDialog.OnExitListener() {
            @Override
            public void onClick(PromptDialog dialog) {
                dialog.dismiss();

            }
        }).show();
    }

    public void popUpForUpdateInfo(){
        new PromptDialog(getContext())
                .setDialogType(PromptDialog.DIALOG_TYPE_ERROR)
                .setAnimationEnable(true)
                .setTitleText(R.string.message)
                .setContentText(R.string.changed_successfuly)
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

    public void popupForEmptyField(){
        new PromptDialog(getContext())
                .setDialogType(PromptDialog.DIALOG_TYPE_ERROR)
                .setAnimationEnable(true)
                .setTitleText(R.string.error)
                .setContentText(R.string.compelete_field)
                .setDismissingListener(R.string.ok, new PromptDialog.OnDismissingListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private boolean isValidMail(String email) {
        boolean check;
        Pattern p;
        Matcher m;

        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        p = Pattern.compile(EMAIL_STRING);

        m = p.matcher(email);
        check = m.matches();

        return check;
    }

    public void popupForInvalidEmail(){
        new PromptDialog(getContext())
                .setDialogType(PromptDialog.DIALOG_TYPE_ERROR)
                .setAnimationEnable(true)
                .setTitleText(R.string.error)
                .setContentText(R.string.invalid_mail)
                .setDismissingListener(R.string.ok, new PromptDialog.OnDismissingListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void popupForNoChange(){
        BlueLayer.setVisibility(View.GONE);
        AV.hide();
        AV.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.GONE);
        new PromptDialog(getContext())
                .setDialogType(PromptDialog.DIALOG_TYPE_ERROR)
                .setAnimationEnable(true)
                .setTitleText(R.string.message)
                .setContentText(R.string.no_change)
                .setDismissingListener(R.string.ok, new PromptDialog.OnDismissingListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

}
