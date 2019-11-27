package com.lambede.lamer.pop_up;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.TextView;

import com.lambede.lamer.R;


public class PromptDialog extends Dialog {

    public static final int DIALOG_TYPE_EXIT = 0;
    public static final int DIALOG_TYPE_ERROR = 1;


    private AnimationSet mAnimIn, mAnimOut;
    private View mDialogView;
    private TextView mTitleTv, mContentTv, mDismissingBtn, mExitBtn;
    private OnDismissingListener mOnDismissingListener;
    private OnExitListener mOnExitListener;
    private int mDialogType;
    private boolean mIsShowAnim;
    private CharSequence mTitle, mContent, mDismissingBtnText, mExitBtnText;

    public PromptDialog(Context context) {
        this(context, 0);
    }

    public PromptDialog(Context context, int theme) {
        super(context, R.style.color_dialog);
        init();
    }

    private void init() {
        mAnimIn = AnimationLoader.getInAnimation(getContext());
        mAnimOut = AnimationLoader.getOutAnimation(getContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        initListener();

    }

    private void initView() {

        if(DIALOG_TYPE_EXIT == mDialogType) {
            View contentView = View.inflate(getContext(), R.layout.pop_up_exit, null);
            setContentView(contentView);
            resizeDialog();

            mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
            mTitleTv = (TextView) contentView.findViewById(R.id.tvTitle);
            mContentTv = (TextView) contentView.findViewById(R.id.tvContent);
            mDismissingBtn = (TextView) contentView.findViewById(R.id.btnNo);
            mExitBtn = (TextView) contentView.findViewById(R.id.btnYes);

            mTitleTv.setText(mTitle);
            mContentTv.setText(mContent);
            mDismissingBtn.setText(mDismissingBtnText);
            mExitBtn.setText(mExitBtnText);

        }else if(DIALOG_TYPE_ERROR == mDialogType){

            View contentView = View.inflate(getContext(), R.layout.pop_up_error, null);
            setContentView(contentView);
            resizeDialog();

            mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
            mTitleTv = (TextView) contentView.findViewById(R.id.tvTitle);
            mContentTv = (TextView) contentView.findViewById(R.id.tvContent);
            mDismissingBtn = (TextView) contentView.findViewById(R.id.btnNo);

            mTitleTv.setText(mTitle);
            mContentTv.setText(mContent);
            mDismissingBtn.setText(mDismissingBtnText);
        }


    }

    private void resizeDialog() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int)(DisplayUtil.getScreenSize(getContext()).x * 0.7);
        getWindow().setAttributes(params);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startWithAnimation(mIsShowAnim);
    }

    public int getDialogType() {
        return mDialogType;
    }

    private void startWithAnimation(boolean showInAnimation) {
        if (showInAnimation) {
            mDialogView.startAnimation(mAnimIn);
        }
    }

    private void dismissWithAnimation(boolean showOutAnimation) {
        if (showOutAnimation) {
            mDialogView.startAnimation(mAnimOut);
        } else {
            super.dismiss();
        }
    }

    private void initAnimListener() {


        mAnimOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mDialogView.post(new Runnable() {
                    @Override
                    public void run() {
                        callDismiss();
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public PromptDialog setAnimationEnable(boolean enable) {
        mIsShowAnim = enable;
        return this;
    }

    public PromptDialog setTitleText(CharSequence title) {
        mTitle = title;
        return this;
    }

    public PromptDialog setTitleText(int resId) {
        return setTitleText(getContext().getString(resId));
    }

    public PromptDialog setContentText(CharSequence content) {
        mContent = content;
        return this;
    }

    public PromptDialog setContentText(int resId) {
        return setContentText(getContext().getString(resId));
    }

    public TextView getTitleTextView() {
        return mTitleTv;
    }

    public TextView getContentTextView() {
        return mContentTv;
    }

    public PromptDialog setDialogType(int type) {
        mDialogType = type;
        return this;
    }

    public PromptDialog setAnimationIn(AnimationSet animIn) {
        mAnimIn = animIn;
        return this;
    }

    public PromptDialog setAnimationOut(AnimationSet animOut) {
        mAnimOut = animOut;
        initAnimListener();
        return this;
    }

    private void initListener() {


        if(DIALOG_TYPE_EXIT == mDialogType) {

            mDismissingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnDismissingListener != null) {
                        mOnDismissingListener.onClick(PromptDialog.this);
                    }
                }
            });


            mExitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnExitListener != null) {
                        mOnExitListener.onClick(PromptDialog.this);
                    }
                }
            });

            initAnimListener();

        }else if(DIALOG_TYPE_ERROR == mDialogType){
            mDismissingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnDismissingListener != null) {
                        mOnDismissingListener.onClick(PromptDialog.this);
                    }
                }
            });


            initAnimListener();

        }
    }

    @Override
    public void dismiss() {
        dismissWithAnimation(mIsShowAnim);
    }

    private void callDismiss() {
        super.dismiss();
    }

    public PromptDialog setDismissingListener(CharSequence btnText, OnDismissingListener l) {
        mDismissingBtnText = btnText;
        return setDismissingListener(l);
    }

    public PromptDialog setDismissingListener(int stringResId, OnDismissingListener l) {
        return setDismissingListener(getContext().getString(stringResId), l);
    }

    public PromptDialog setDismissingListener(OnDismissingListener l) {
        mOnDismissingListener = l;
        return this;
    }

    public interface OnDismissingListener {
        void onClick(PromptDialog dialog);
    }

    public PromptDialog setExitListener(int stringResId, OnExitListener l ) {
        return setExitListener(getContext().getString(stringResId), l);
    }

    public interface OnExitListener {
        void onClick(PromptDialog dialog);
    }

    public PromptDialog setExitListener(OnExitListener l) {
        mOnExitListener = l;
        return this;
    }

    public PromptDialog setExitListener(CharSequence btnText, OnExitListener l) {
        mExitBtnText = btnText;
        return setExitListener(l);
    }

}
