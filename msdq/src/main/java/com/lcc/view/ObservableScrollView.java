package com.lcc.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import zsbpj.lccpj.frame.FrameManager;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  ObservableScrollView
 */
public class ObservableScrollView extends ScrollView {

    private ScrollViewListener scrollViewListener = null;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        View view = (View) getChildAt(getChildCount() - 1);
        int d = view.getBottom();
        d -= (getHeight() + getScrollY());
        if (d <= 20) {
            if (scrollViewListener != null) {
                scrollViewListener.onScrollChangedToBottom();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        View childView = getChildAt(0);
        if (scrollViewListener != null) {
            if (childView  != null && childView .getMeasuredHeight() <= getScrollY() + getHeight()) {
                scrollViewListener.onScrollChangedToBottom();
            }
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                handler.sendMessageDelayed(handler.obtainMessage(), 20);
                break;
        }
        return super.onTouchEvent(ev);
    }

    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            View childView = getChildAt(0);
            if (scrollViewListener != null) {
                if (childView  != null && childView .getMeasuredHeight() <= getScrollY() + getHeight()) {
                    scrollViewListener.onScrollChangedToBottom();
                }
            }
        };
    };

    public interface ScrollViewListener {

        void onScrollChangedToBottom();

    }

}
