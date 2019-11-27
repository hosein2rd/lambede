package com.lambede.lamer.fragments;


import android.app.Activity;
import android.content.Context;
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
import com.bumptech.glide.Glide;
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



public class NewServicesFragment extends Fragment {

    ImageView GIF, BlueLayer;
    Button sendService;
    String token;
    TextView tvNewServiceTitle;
    SharedPreferences Token;
    EditText SuggestionEditText;
    AVLoadingIndicatorView AV;
    RelativeLayout relativeLayout;
    Typeface IranYekanBold;
//    private FirebaseAnalytics mFirebaseAnalytics;
//    private Tracker mTracker;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_services, container, false);

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
//        mTracker.setScreenName("Recommend View");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        sendService = view.findViewById(R.id.service_send_suggestion);
        SuggestionEditText = view.findViewById(R.id.service_suggestion_edittext);
        AV = view.findViewById(R.id.avi);
        relativeLayout = view.findViewById(R.id.Relative);
        tvNewServiceTitle = view.findViewById(R.id.service_title);
        BlueLayer = getActivity().findViewById(R.id.blueback);


        IranYekanBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");
        sendService.setTypeface(IranYekanBold);
        tvNewServiceTitle.setTypeface(IranYekanBold);

        Token = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        token = Token.getString("token", "");

        GIF = view.findViewById(R.id.service_gif);
        Glide.with(getContext()).load(R.drawable.loader_gif).into(GIF);

        sendService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SuggestionEditText.getText().toString().length() == 0){
                    popupForEmptyField();
                }
                else {
                    BlueLayer.setVisibility(View.VISIBLE);
                    AV.setVisibility(View.VISIBLE);
                    AV.show();
                    relativeLayout.setVisibility(View.VISIBLE);
                    relativeLayout.bringToFront();
                    sendNewService();
                }
            }
        });

        return view;
    }

    public void sendNewService(){

        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://www.lambede.com/api/v3/orders/newService",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("Response", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("true")){
                                popupForSuccessSend();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        SuggestionEditText.setText(null);

                        BlueLayer.setVisibility(View.GONE);
                        AV.setVisibility(View.GONE);
                        AV.hide();
                        relativeLayout.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.e("Error.Response", "Error");


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
                params.put("service", SuggestionEditText.getText().toString());

                return params;
            }
        };
        Singleton.getInstance(getActivity()).addToRequestqueue(postRequest);
    }

    public void popupForSuccessSend(){
        PromptDialog promptDialog = new PromptDialog(getContext());
        promptDialog.setDialogType(PromptDialog.DIALOG_TYPE_ERROR);
        promptDialog.setAnimationEnable(true);
        promptDialog.setTitleText(R.string.message);
        promptDialog.setContentText(R.string.suggestion_successfuly);
        promptDialog.setDismissingListener(R.string.ok, new PromptDialog.OnDismissingListener() {
            @Override
            public void onClick(PromptDialog dialog) {
                dialog.dismiss();
            }
        });
        promptDialog.show();
    }

    public void popupForEmptyField(){
        PromptDialog promptDialog = new PromptDialog(getContext());
        promptDialog.setDialogType(PromptDialog.DIALOG_TYPE_ERROR);
        promptDialog.setAnimationEnable(true);
        promptDialog.setTitleText(R.string.attention);
        promptDialog.setContentText(R.string.enter_suggest);
        promptDialog.setDismissingListener(R.string.ok, new PromptDialog.OnDismissingListener() {
            @Override
            public void onClick(PromptDialog dialog) {
                dialog.dismiss();
            }
        });
        promptDialog.show();
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
