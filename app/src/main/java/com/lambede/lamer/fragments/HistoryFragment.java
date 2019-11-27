package com.lambede.lamer.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.analytics.Tracker;
//import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

import com.lambede.lamer.R;
import com.lambede.lamer.utilities.AnalyticsApplication;
import com.lambede.lamer.utilities.HistoryItems;
import com.lambede.lamer.pop_up.PromptDialog;


public class HistoryFragment extends Fragment {

    public HistoryFragment() {
        // Required empty public constructor
    }


    ListView listView;
    ArrayList<HistoryItems> items = new ArrayList<HistoryItems>();
//    ImageView BottomDarkBack;
    int home = 0,work = 1, stairs = 2, car = 3;
    RelativeLayout relativeLayout;
    Typeface IranYekanBold;
    TextView tvHistoryTitle;
//    private FirebaseAnalytics mFirebaseAnalytics;
//    private Tracker mTracker;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        // Obtain the FirebaseAnalytics instance.
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
//
//        //Analytics Codes
//        // Obtain the shared Tracker instance.
//        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
//        mTracker = application.getDefaultTracker();
//        mTracker.setScreenName("My Orders");
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        listView = view.findViewById(R.id.history_listview);
        relativeLayout = view.findViewById(R.id.no_order);
        tvHistoryTitle = view.findViewById(R.id.history_title);


        relativeLayout.setVisibility(View.GONE);

        IranYekanBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");
        tvHistoryTitle.setTypeface(IranYekanBold);

//        getMyOrders();

        items.add(new HistoryItems(
                car,
                "سفارش تمیزکاری ",
                "تاریخ تمیزکاری : " + "empty",
                "هزینه سفارش : " ,"empty"+ " " + "تومان",
                "جزئیات سفارش : " + "empty" + " کارگر " + "gender" + "empty" + " ساعت تمیزکاری"));

        items.add(new HistoryItems(
                stairs,
                "سفارش تمیزکاری ",
                "تاریخ تمیزکاری : " + "empty",
                "هزینه سفارش : " ,"empty"+ " " + "تومان",
                "جزئیات سفارش : " + "empty" + " کارگر " + "gender" + "empty" + " ساعت تمیزکاری"));

        items.add(new HistoryItems(
                home,
                "سفارش تمیزکاری ",
                "تاریخ تمیزکاری : " + "empty",
                "هزینه سفارش : " ,"empty"+ " " + "تومان",
                "جزئیات سفارش : " + "empty" + " کارگر " + "gender" + "empty" + " ساعت تمیزکاری"));

        items.add(new HistoryItems(
                work,
                "سفارش تمیزکاری ",
                "تاریخ تمیزکاری : " + "empty",
                "هزینه سفارش : " ,"empty"+ " " + "تومان",
                "جزئیات سفارش : " + "empty" + " کارگر " + "gender" + "empty" + " ساعت تمیزکاری"));


        historyAdapter historyAapter;
        historyAapter = new historyAdapter(items);
        listView.setAdapter(historyAapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBarNormal));
        }

        return view;
    }

    public void getMyOrders(){

        SharedPreferences preferences = getActivity().getSharedPreferences("length of my orders", Context.MODE_PRIVATE);

        items.clear();

        int size = preferences.getInt("length", 0);

        if (size == 0){
            relativeLayout.setVisibility(View.VISIBLE);
        }
        else {
            relativeLayout.setVisibility(View.GONE);
            for (int i = preferences.getInt("length", 0) - 1; i >= 0; i--) {
                SharedPreferences orders = getActivity().getSharedPreferences("order" + i, Context.MODE_PRIVATE);
                String type = orders.getString("type", "");
                if (type.equals("خانه")) {
                    items.add(new HistoryItems(
                            home,
                            "سفارش تمیزکاری " + type + " " + orders.getString("size", "empty"),
                            "تاریخ تمیزکاری : " + orders.getString("dueDate", "empty"),
                            "هزینه سفارش : " + orders.getString("price", "empty") + " " + "تومان",
                            "جزئیات سفارش : " + orders.getString("lamers", "empty") + " کارگر " + orders.getString("gender", "empty") + "، " + orders.getString("hour", "empty") + " ساعت تمیزکاری",
                            "آدرس : " + orders.getString("address", "empty")));
                } else if (type.equals("محل کار")) {
                    items.add(new HistoryItems(
                            work,
                            "سفارش تمیزکاری " + type + " " + orders.getString("size", "empty"),
                            "تاریخ تمیزکاری : " + orders.getString("dueDate", "empty"),
                            "هزینه سفارش : " + orders.getString("price", "empty") + " " + "تومان",
                            "جزئیات سفارش : " + orders.getString("lamers", "empty") + " کارگر " + orders.getString("gender", "empty") + "، " + orders.getString("hour", "empty") + " ساعت تمیزکاری",
                            "آدرس : " + orders.getString("address", "empty")));
                } else if (type.equals("راهرو")) {
                    items.add(new HistoryItems(
                            stairs,
                            "سفارش تمیزکاری " + type + " " + orders.getString("size", "empty"),
                            "تاریخ تمیزکاری : " + orders.getString("dueDate", "empty"),
                            "هزینه سفارش : " + orders.getString("price", "empty") + " " + "تومان",
                            "جزئیات سفارش : " + orders.getString("lamers", "empty") + " کارگر " + orders.getString("gender", "empty") + "، " + orders.getString("hour", "empty") + " ساعت تمیزکاری",
                            "آدرس : " + orders.getString("address", "empty")));
                } else if (type.equals("کارواش")) {
                    items.add(new HistoryItems(
                            car,
                            "سفارش تمیزکاری " + type,
                            "تاریخ تمیزکاری : " + orders.getString("dueDate", "empty"),
                            "هزینه سفارش : " + orders.getString("price", "empty") + " " + "تومان",
                            "جزئیات سفارش : " + orders.getString("lamers", "empty") + " کارگر " + orders.getString("gender", "empty") + "، " + orders.getString("hour", "empty") + " ساعت تمیزکاری",
                            "آدرس : " + orders.getString("address", "empty")));
                }

                historyAdapter historyAapter;
                historyAapter = new historyAdapter(items);
                listView.setAdapter(historyAapter);
            }
        }
    }

    //functions:

    private class historyAdapter extends BaseAdapter {
        public ArrayList<HistoryItems> mlistnewsDataAdpater ;

        public historyAdapter(ArrayList<HistoryItems>  mlistnewsDataAdpater) {
            this.mlistnewsDataAdpater=mlistnewsDataAdpater;
        }

        @Override
        public int getCount() {
            return mlistnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return 4;
        }

        @Override
        public int getItemViewType(int position) {
            HistoryItems image = mlistnewsDataAdpater.get(position);
            if (image.image == 0){
                return 0;
            }else
            if (image.image == 1){
                return 1;
            }else
            if (image.image == 2){
                return 2;
            }else
            if (image.image == 3){
                return 3;
            }

            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = getActivity().getLayoutInflater();

            if (getItemViewType(position) == 0) {
                View myView = mInflater.inflate(R.layout.history_items_home, null);

                final HistoryItems item = mlistnewsDataAdpater.get(position);

                ImageView image = myView.findViewById(R.id.item_home_image);

                TextView title = myView.findViewById(R.id.item_home_title);
                title.setText(item.title);
                title.setTypeface(IranYekanBold);

                TextView date = myView.findViewById(R.id.item_home_date);
                date.setText(item.date);

                TextView cost = myView.findViewById(R.id.item_home_cost);
                cost.setText(item.cost);


                TextView addrress = myView.findViewById(R.id.item_home_addrress);
                addrress.setText(item.addrress);

                return myView;
            }
            else if (getItemViewType(position) == 1){
                View myView = mInflater.inflate(R.layout.history_items_work, null);

                final HistoryItems item = mlistnewsDataAdpater.get(position);

                ImageView image = myView.findViewById(R.id.item_work_image);

                TextView title = myView.findViewById(R.id.item_work_title);
                title.setText(item.title);
                title.setTypeface(IranYekanBold);

                TextView date = myView.findViewById(R.id.item_work_date);
                date.setText(item.date);

                TextView cost = myView.findViewById(R.id.item_work_cost);
                cost.setText(item.cost);


                TextView addrress = myView.findViewById(R.id.item_work_addrress);
                addrress.setText(item.addrress);

                return myView;
            }
            else if (getItemViewType(position) == 2){
                View myView = mInflater.inflate(R.layout.history_items_stairs, null);

                final HistoryItems item = mlistnewsDataAdpater.get(position);

                ImageView image = myView.findViewById(R.id.item_stairs_image);

                TextView title = myView.findViewById(R.id.item_stairs_title);
                title.setText(item.title);
                title.setTypeface(IranYekanBold);


                TextView date = myView.findViewById(R.id.item_stairs_date);
                date.setText(item.date);

                TextView cost = myView.findViewById(R.id.item_stairs_cost);
                cost.setText(item.cost);


                TextView addrress = myView.findViewById(R.id.item_stairs_addrress);
                addrress.setText(item.addrress);

                return myView;
            }
            else if (getItemViewType(position) == 3){
                View myView = mInflater.inflate(R.layout.history_items_car, null);

                final HistoryItems item = mlistnewsDataAdpater.get(position);

                ImageView image = myView.findViewById(R.id.item_car_image);

                TextView title = myView.findViewById(R.id.item_car_title);
                title.setText(item.title);
                title.setTypeface(IranYekanBold);


                TextView date = myView.findViewById(R.id.item_car_date);
                date.setText(item.date);

                TextView cost = myView.findViewById(R.id.item_car_cost);
                cost.setText(item.cost);


                TextView addrress = myView.findViewById(R.id.item_car_addrress);
                addrress.setText(item.addrress);

                return myView;
            }

            return null;
        }

    }

}
