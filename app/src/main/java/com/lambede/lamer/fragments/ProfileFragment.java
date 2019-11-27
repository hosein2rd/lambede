package com.lambede.lamer.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lambede.lamer.R;
import com.lambede.lamer.pop_up.PromptDialog;


public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    CardView Support, Edit, Share, Help;
    //    ImageView BottomDarkBack;
    TextView Name, tvProfileTitle;
    Typeface IranYekanBold;
    SharedPreferences proFragment;
    SharedPreferences.Editor editorFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Support = view.findViewById(R.id.profile_support);
        Edit = view.findViewById(R.id.profile_edit);
        Share = view.findViewById(R.id.profile_share);
        Help = view.findViewById(R.id.profile_help);
//        BottomDarkBack = getActivity().findViewById(R.id.darkback);
        Name = view.findViewById(R.id.profile_name);
        tvProfileTitle = view.findViewById(R.id.profile_title);

        IranYekanBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");

        Name.setTypeface(IranYekanBold);
        tvProfileTitle.setTypeface(IranYekanBold);

        proFragment = getActivity().getSharedPreferences("fragment", Context.MODE_PRIVATE);
        editorFragment = proFragment.edit();

        SharedPreferences infoSHP = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);

        Name.setText(infoSHP.getString("name", ""));

//        BottomDarkBack.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBarNormal));
        }

        Support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callIntent();
            }
        });

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMySpecificationsFragment();
            }
        });

        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToShareFragment();
            }
        });

        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHelpFragment();
            }
        });

        return view;
    }



    //functions:

    public void callIntent(){
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:+982128429292"));
        startActivity(callIntent);
    }

    public void goToMySpecificationsFragment(){
        editorFragment.putString("fragment", "edit").apply();

        EditFragment editFragment = new EditFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container3, editFragment,"findThisFragment")
//                .addToBackStack("back")
                .commit();
    }

    public void goToShareFragment(){

        editorFragment.putString("fragment", "share").apply();

        ShareFragment shareFragment = new ShareFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container3, shareFragment,"findThisFragment")
//                .addToBackStack("back")
                .commit();
    }

    public void goToHelpFragment(){
        editorFragment.putString("fragment", "help").apply();

        HelpFragment helpFragment = new HelpFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container3, helpFragment,"findThisFragment")
//                .addToBackStack("back")
                .commit();
    }

}
