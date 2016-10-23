package com.lcc.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lcc.msdq.R;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  StretchyTextView
 */
public class StretchyTextView extends LinearLayout implements View.OnClickListener {
    //默认显示的最大行数
    private static final int DEFAULT_MAX_LINE_COUNT = 2;
    //当前展开标志显示的状态
    private static final int SPREADTEXT_STATE_NONE = 0;
    private static final int SPREADTEXT_STATE_RETRACT = 1;
    private static final int SPREADTEXT_STATE_SPREAD = 2;

    private TextView contentText;
    private TextView operateText;
    private RelativeLayout bottomTextLayout;

    private String shrinkup;
    private String spread;
    private int mState;
    private boolean flag = false;
    private int maxLineCount = DEFAULT_MAX_LINE_COUNT;
    private InnerRunnable runable;

    public StretchyTextView(Context context) {
        this(context, null);
    }

    public StretchyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        shrinkup = context.getString(R.string.retract);
        spread = context.getString(R.string.spread);
        View view = inflate(context, R.layout.stretchy_text_layout, this);
        view.setPadding(0, -1, 0, 0);
        contentText = (TextView) view.findViewById(R.id.content_textview);
        operateText = (TextView) view.findViewById(R.id.bottom_textview);
        bottomTextLayout = (RelativeLayout) view.findViewById(R.id.bottom_text_layout);
        setBottomTextGravity(Gravity.LEFT);
        operateText.setOnClickListener(this);
        runable = new InnerRunnable();
    }

    @Override
    public void onClick(View v) {
        flag = false;
        requestLayout();
    }

    public final void setContent(CharSequence charSequence) {
        contentText.setText(charSequence, TextView.BufferType.NORMAL);
        mState = SPREADTEXT_STATE_SPREAD;
        Log.d("setContent", "count lines="+contentText.getLineCount()+",flag="+flag);
        flag = false;
        requestLayout();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!flag) {
            flag = !flag;
            if (contentText.getLineCount() <= DEFAULT_MAX_LINE_COUNT) {
                mState = SPREADTEXT_STATE_NONE;
                operateText.setVisibility(View.GONE);
                contentText.setMaxLines(DEFAULT_MAX_LINE_COUNT + 1);
            }else {
                post(runable);
            }
        }
    }

    class InnerRunnable implements Runnable {
        @Override
        public void run() {
            if (mState == SPREADTEXT_STATE_SPREAD) {
                contentText.setMaxLines(maxLineCount);
                operateText.setVisibility(View.VISIBLE);
                operateText.setText(spread);
                mState = SPREADTEXT_STATE_RETRACT;
            } else if (mState == SPREADTEXT_STATE_RETRACT) {
                contentText.setMaxLines(Integer.MAX_VALUE);
                operateText.setVisibility(View.VISIBLE);
                operateText.setText(shrinkup);
                mState = SPREADTEXT_STATE_SPREAD;
            }
        }
    }

    public void setMaxLineCount(int maxLineCount) {
        this.maxLineCount = maxLineCount;
    }

    public void setContentTextColor(int color){
        this.contentText.setTextColor(color);
    }

    public void setContentTextSize(float size){
        this.contentText.setTextSize(size);
    }

    /**
     * 内容字体加粗
     */
    public void setContentTextBold(){
        TextPaint textPaint = contentText.getPaint();
        textPaint.setFakeBoldText(true);
    }
    /**
     * 设置展开标识的显示位置
     */
    public void setBottomTextGravity(int gravity){
        bottomTextLayout.setGravity(gravity);
    }
}