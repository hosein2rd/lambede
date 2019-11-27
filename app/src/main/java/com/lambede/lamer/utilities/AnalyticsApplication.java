
package com.lambede.lamer.utilities;

import android.app.Application;
//import com.google.android.gms.analytics.GoogleAnalytics;
//import com.google.android.gms.analytics.Tracker;
import com.lambede.lamer.R;
import com.onesignal.OneSignal;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
// * This is/k Tracker}.
// */
public class AnalyticsApplication extends Application {

//    private static GoogleAnalytics sAnalytics;
//    private static Tracker sTracker;

    @Override
    public void onCreate() {
        super.onCreate();

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANYekanRegularMobile(FaNum).ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

//        sAnalytics = GoogleAnalytics.getInstance(this);
    }

//    /**
//     * Gets the default {@link Tracker} for this {@link Application}.
//     * @return tracker
//     */
//    synchronized public Tracker getDefaultTracker() {
//        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
//        if (sTracker == null) {
//            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
//        }
//
//        return sTracker;
//    }
}

