package com.lcc.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lcc.msdq.R;

public class UpdateDialog extends Dialog {

    private TextView tv_title;
    private ProgressBar progress_view;

    public UpdateDialog(Context context) {
        super(context, R.style.progress_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.progressbar_item);
        this.setCancelable(true);
        tv_title = (TextView) findViewById(R.id.tv_title);
        progress_view = (ProgressBar) findViewById(R.id.progress_view);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.setCancelable(false);
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        lp.alpha = 1.0f;
        dialogWindow.setAttributes(lp);
    }

    public void setTitle(int progress) {
        Message message = handler.obtainMessage();
        message.obj = progress;
        handler.sendMessage(message);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("xxxxx",msg.obj.toString());
            tv_title.setText("正在下载..." + msg.obj.toString() + "%");
            progress_view.setProgress((Integer) msg.obj);
            if (progress_view.getProgress() == 100) {
                dismiss();
            }
        }
    };
}
