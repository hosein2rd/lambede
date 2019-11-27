package com.lambede.lamer.activities;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.analytics.Tracker;
//import com.google.firebase.analytics.FirebaseAnalytics;
import com.lambede.lamer.R;
import com.lambede.lamer.fragments.EditFragment;
import com.lambede.lamer.fragments.FirstCarWashFragment;
import com.lambede.lamer.fragments.FirstFragment;
import com.lambede.lamer.fragments.FirstStairsFragment;
import com.lambede.lamer.fragments.ForthFragment;
import com.lambede.lamer.fragments.HelpFragment;
import com.lambede.lamer.fragments.HistoryFragment;
import com.lambede.lamer.fragments.NewOrderFragment;
import com.lambede.lamer.fragments.NewServicesFragment;
import com.lambede.lamer.fragments.ProfileFragment;
import com.lambede.lamer.fragments.SecondCarwashFragment;
import com.lambede.lamer.fragments.SecondFragment;
import com.lambede.lamer.fragments.SecondStairsFragment;
import com.lambede.lamer.fragments.ShareFragment;
import com.lambede.lamer.fragments.ThirdFragment;
import com.lambede.lamer.pop_up.PromptDialog;
import com.lambede.lamer.utilities.AnalyticsApplication;
import com.lambede.lamer.utilities.AnimatedColor;
import com.lambede.lamer.utilities.BottomNavigationViewHelper;
import com.onesignal.OneSignal;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity {

    Fragment neworder = new NewOrderFragment();
    Fragment history = new HistoryFragment();
    Fragment profile = new ProfileFragment();
//    private FirebaseAnalytics mFirebaseAnalytics;
//    private Tracker mTracker;
    boolean tab1 = true, tab2 = false, tab3 = false;
    AnimatedColor blackToBlue;

    public void switchneworder() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container1, neworder)
//                .addToBackStack("Back")
                .commit();
    }

    public void switchhistory() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container2, history)
//                .addToBackStack("Back")
                .commit();
    }

    public void switchprofile() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container3, profile)
//                .addToBackStack("Back")
                .commit();

    }



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    FragmentTabHost container1, container2, container3;
    Fragment fragment1, fragment2, fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container1 = findViewById(R.id.container1);
        container2 = findViewById(R.id.container2);
        container3 = findViewById(R.id.container3);

        switchhistory();
        switchneworder();
        switchprofile();

        container1.setVisibility(View.VISIBLE);
        container2.setVisibility(View.GONE);
        container3.setVisibility(View.GONE);

//        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        // Obtain the FirebaseAnalytics instance.
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //Analytics Codes
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
//        mTracker = application.getDefaultTracker();
//        mTracker.setScreenName((String) Build.MANUFACTURER + " " + Build.PRODUCT + " MainActivity");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
//
//        mTracker.send(new HitBuilders.EventBuilder()
//                .setCategory("Open")
//                .setAction("MainActivity")
//                .build());

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);

        blackToBlue = new AnimatedColor(ContextCompat.getColor(this, R.color.colorStatusBarDate), ContextCompat.getColor(this, R.color.colorStatusBarNormal));


        bottomNavigationView.setSelectedItemId(R.id.navigation_neworder);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_neworder:
                        tab3 = false;
                        tab2 = false;

                        if (tab1){
                            FragmentManager manager = getSupportFragmentManager();
                            manager.beginTransaction().replace(R.id.container1, neworder)
//                .addToBackStack("Back")
                                    .commit();
                        }

                        fragment1 = getSupportFragmentManager().findFragmentById(R.id.container1);
                        container1.setVisibility(View.VISIBLE);
                        container2.setVisibility(View.GONE);
                        container3.setVisibility(View.GONE);
                        if (container1.getVisibility() == View.VISIBLE){
                            tab1 = true;
                        }
                        break;

                    case R.id.navigation_history:
                        tab1 = false;
                        tab2 = false;
                        tab3 = false;
                        container1.setVisibility(View.GONE);
                        container2.setVisibility(View.VISIBLE);
                        container3.setVisibility(View.GONE);
                        break;

                    case R.id.navigation_profile:
                        tab2 = false;
                        tab1 = false;

                        if (tab3){
                            FragmentManager manager = getSupportFragmentManager();
                            manager.beginTransaction().replace(R.id.container3, profile)
//                .addToBackStack("Back")
                                    .commit();
                        }

                        fragment3 = getSupportFragmentManager().findFragmentById(R.id.container3);
                        container1.setVisibility(View.GONE);
                        container2.setVisibility(View.GONE);
                        container3.setVisibility(View.VISIBLE);
                        if (container3.getVisibility() == View.VISIBLE ){
                            tab3 = true;
                        }
                        break;
                }
                return true;

            }
        });
    }

    @Override
    public void onBackPressed() {
        Fragment fragment1 = getSupportFragmentManager().findFragmentById(R.id.container1);
        Fragment fragment2 = getSupportFragmentManager().findFragmentById(R.id.container2);
        Fragment fragment3 = getSupportFragmentManager().findFragmentById(R.id.container3);

        if (container1.getVisibility() == View.VISIBLE){
            if (fragment1 instanceof NewOrderFragment){
                popupForExit();
            }
            else if (fragment1 instanceof ForthFragment){
                SharedPreferences orderInfo = getSharedPreferences("extra", Context.MODE_PRIVATE);
                SharedPreferences.Editor orderInfoEdit = orderInfo.edit();
//                orderInfoEdit.clear().apply();
                orderInfoEdit.putString("cabinet", "ندارد");
                orderInfoEdit.putString("frige", "ندارد");
                orderInfoEdit.putString("furniture", "ندارد");
                orderInfoEdit.apply();
                Log.e("cabinet", orderInfo.getString("cabinet", "default"));

                getSupportFragmentManager().popBackStack("forthFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            else if (fragment1 instanceof ThirdFragment){

                getSupportFragmentManager().popBackStack("thirdFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            else if (fragment1 instanceof SecondFragment){

                if (((SecondFragment) fragment1).isVisibleNow()){
                    ((SecondFragment) fragment1).animateBackgroundHide();
                    ValueAnimator animator = ObjectAnimator.ofFloat(0f, 1f).setDuration(400);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float v = (float) animation.getAnimatedValue();
                            getWindow().setStatusBarColor(blackToBlue.with(v));
                        }
                    });
                    animator.start();
                    ((SecondFragment) fragment1).WheelContainer.startAnimation(((SecondFragment) fragment1).Scale_out);
                    ((SecondFragment) fragment1).WheelContainer.setVisibility(View.GONE);
                }
                else {

                    getSupportFragmentManager().popBackStack("secondFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
            else if (fragment1 instanceof SecondCarwashFragment){

                getSupportFragmentManager().popBackStack("secondCarwashFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            else if (
                    fragment1 instanceof FirstFragment ||
                            fragment1 instanceof FirstStairsFragment ||
                            fragment1 instanceof NewServicesFragment ||
                            fragment1 instanceof FirstCarWashFragment){
                if (fragment1 instanceof FirstCarWashFragment){
                    if (((FirstCarWashFragment) fragment1).isVisibleNow()){
                        ((FirstCarWashFragment) fragment1).animateBackgroundHide();
                        ValueAnimator animator = ObjectAnimator.ofFloat(0f, 1f).setDuration(400);
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float v = (float) animation.getAnimatedValue();
                                getWindow().setStatusBarColor(blackToBlue.with(v));
                            }
                        });
                        animator.start();
                        ((FirstCarWashFragment) fragment1).wheelContainer.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_out));
                        ((FirstCarWashFragment) fragment1).wheelContainer.setVisibility(View.GONE);
                    }
                    else {

                        getSupportFragmentManager().popBackStack("firstCarwashFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }
                }
                else if (fragment1 instanceof FirstStairsFragment){
                    if (((FirstStairsFragment) fragment1).isVisibleNow()){
                        ((FirstStairsFragment) fragment1).animateBackgroundHide();
                        ValueAnimator animator = ObjectAnimator.ofFloat(0f, 1f).setDuration(400);
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float v = (float) animation.getAnimatedValue();
                                getWindow().setStatusBarColor(blackToBlue.with(v));
                            }
                        });
                        animator.start();
                        ((FirstStairsFragment) fragment1).wheelContainer.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_out));
                        ((FirstStairsFragment) fragment1).wheelContainer.setVisibility(View.GONE);
                    }
                    else {

                        getSupportFragmentManager().popBackStack("firstStairsFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }
                }
                else {
                    NewOrderFragment newOrderFragment = new NewOrderFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.container1, newOrderFragment)
//                .addToBackStack("Back")
                            .commit();
                }
            }
            else if (fragment1 instanceof SecondStairsFragment){

                getSupportFragmentManager().popBackStack("secondStairsFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
        else if (container2.getVisibility() == View.VISIBLE){
            if (fragment2 instanceof HistoryFragment){
                popupForExit();
            }
        }
        else if (container3.getVisibility() == View.VISIBLE){
            if (fragment3 instanceof ProfileFragment){
                popupForExit();
            }
            else if (
                    fragment3 instanceof EditFragment ||
                            fragment3 instanceof HelpFragment ||
                            fragment3 instanceof ShareFragment){
                ProfileFragment profileFragment = new ProfileFragment();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.container3, profileFragment)
//                .addToBackStack("Back")
                        .commit();
            }
        }
    }

    public void popupForExit(){
//        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
//        homeIntent.addCategory( Intent.CATEGORY_HOME );
//        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(homeIntent);
//        finish();
//        System.exit(0);
//        Intent homeIntent = new Intent();
//        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(homeIntent);
//        finish();
//        System.exit(0);
        finishAffinity();
    }

}
