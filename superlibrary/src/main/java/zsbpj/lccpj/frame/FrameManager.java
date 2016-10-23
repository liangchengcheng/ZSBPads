package zsbpj.lccpj.frame;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import zsbpj.lccpj.R;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2016年1月13日22:25:11
 * Description: Py
 */
public class FrameManager {
    private static final int MSG_TOAST = 1;
    private static FrameManager self = null;
    private static Toast mToast;
    private static Context appContext;

    private boolean mIsInited;

    private HandlerThread mHandlerThread;
    private Handler mHandler;

    public static Context getAppContext() {
        return appContext;
    }

    public static void setAppContext(Context context) {
        if (context != null) {
            appContext = context.getApplicationContext();
        }
    }

    public synchronized boolean isInited() {
        return mIsInited;
    }

    public synchronized boolean init() {
        if (mIsInited)
            return true;
        if (appContext == null)
            return false;
        mIsInited = true;
        return true;
    }

    public synchronized static void exit() {
        if (self != null) {
            self.release();
        }
    }

    private void release() {
        if (mToast != null) {
            mToast.cancel();
        }
        if (mHandler != null && mHandler.getLooper() != null) {
            mHandler.getLooper().quit();
            mHandler = null;
        }
    }

    private FrameManager() {
        super();
    }

    public synchronized static FrameManager getInstance() {
        if (self == null) {
            self = new FrameManager();
        }

        return self;
    }

    private synchronized void initGlobalHandler() {
        if (mHandler != null) {
            return;
        }

        mHandlerThread = new HandlerThread("GlobalThread");
        mHandlerThread.start();
        mToast = null;
        mHandler = new ToastHandler(mHandlerThread.getLooper());
    }

    private static class ToastHandler extends Handler {

        public ToastHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_TOAST:
                    toast((String) msg.obj);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    }

    private static void toast(final String prompt) {
        if (mToast == null) {
            mToast = Toast.makeText(appContext, prompt, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(prompt);
        }
        mToast.show();
    }

    public void toastPrompt(final String prompt) {
        if (mHandler == null) {
            initGlobalHandler();
        }

        Message msg = mHandler.obtainMessage(MSG_TOAST);
        msg.obj = prompt;
        mHandler.sendMessage(msg);
    }



}
