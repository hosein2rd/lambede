package com.lambede.lamer.fragments;


import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import com.lambede.lamer.R;
import com.lambede.lamer.pop_up.PromptDialog;
import com.lambede.lamer.utilities.QuestionItems;


public class HelpFragment extends Fragment {

    ListView listView;
    ArrayList<QuestionItems> questionData = new ArrayList<QuestionItems>();
    Typeface IranYekanBold;
    TextView tvHelpTitle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        listView = view.findViewById(R.id.help_listview);
        tvHelpTitle = view.findViewById(R.id.help_title);
        IranYekanBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");
        tvHelpTitle.setTypeface(IranYekanBold);


        questionData.add(new QuestionItems(
                "چه طوری سفارش تمیز کاری ثبت کنیم؟",
                "از طریق سفارش جدید، نوع سفارش خود را انتخاب کنید و در چند مرحله ساده درخواست خود را ثبت کنید."));
        questionData.add(new QuestionItems(
                "چه زمان هایی میتوانیم درخواست خود را ثبت کنیم؟",
                "شما از طریق تقویم موجود در بخش اطلاعات سفارش، میتوانید روزهای قابل سفارش را بررسی کنید."));
        questionData.add(new QuestionItems(
                "چگونه با پشتیبانی لم بده تماس بگیریم؟",
                "با شماره 28429292 تماس حاصل فرمایید. همکاران ما آماده پاسخگویی به شما هستند."));
        questionData.add(new QuestionItems(
                "اگر از سرویس راضی نبودیم چه کاری باید انجام دهیم؟",
                "تلاش ما مبنی بر جلب رضایت شماست. اگر نارضایتی به وجود آمد، با پشتیبانی تماس بگیرید."));
        questionData.add(new QuestionItems(
                "هزینه تمیزکاری چقدر است؟",
                "هزینه 4 ساعت تیمزکاری، 69000 تومان، 6 ساعت، 79000 تومان و 8 ساعت، 89000 تومان می باشد."));
        questionData.add(new QuestionItems(
                "آیا میتوان به صورت منظم سفارش داد؟",
                "بله شما میتوانید برای نظافت خانه با محل کارتون به صورت منظم روی ما حساب کنید و از تخفیفات ویژه ما بهره مند بشید. برای اطلاعات بیشتر با ما تماس بگیرید."));
        questionData.add(new QuestionItems(
                "چه مواردی را باید رعایت کنیم؟",
                "لطفا در زمان تعیین شده در محل مورد نظر حضور داشته باشید، از دادن انعام خودداری فرمایید و سفارش خود را فقط از طریق لم بده ثبت نمایید."));

        QuestionAdapter questionAdapter;
        questionAdapter = new QuestionAdapter(questionData);
        listView.setAdapter(questionAdapter);

        return view;
    }

    private class QuestionAdapter extends BaseAdapter {
        public ArrayList<QuestionItems> mlistnewsDataAdpater ;

        public QuestionAdapter(ArrayList<QuestionItems>  mlistnewsDataAdpater) {
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
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater mInflater = getActivity().getLayoutInflater();
            View myView = mInflater.inflate(R.layout.questions, null);

            final QuestionItems item = mlistnewsDataAdpater.get(position);

            TextView question = (TextView)myView.findViewById(R.id.question);
            question.setText(item.question);
            question.setTypeface(IranYekanBold);

            TextView answer = (TextView)myView.findViewById(R.id.answer);
            answer.setText(item.answer);

            return myView;
        }

    }

}
