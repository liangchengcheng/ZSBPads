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

public class LoginDialog extends Dialog {


    public LoginDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.login_layout);
        this.setCancelable(true);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.setCancelable(false);
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        lp.alpha = 1.0f;
        dialogWindow.setAttributes(lp);
    }

}
