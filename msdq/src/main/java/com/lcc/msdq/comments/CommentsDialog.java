package com.lcc.msdq.comments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.lcc.entity.Comments;
import com.lcc.msdq.R;

import zsbpj.lccpj.frame.FrameManager;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年11月27日14:19:38
 * Description:  dialog
 */
public class CommentsDialog extends Dialog{
    private Context context;
    private Comments data;

    public CommentsDialog(Context context,Comments data) {
        super(context);
        this.context=context;
        this.data=data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.comment_menu_item);
        findViewById(R.id.tv_replay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onChoice(data);
            }
        });
        findViewById(R.id.tv_jb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameManager.getInstance().toastPrompt("待开发");
                dismiss();
            }
        });
    }

    private onChoiceListener listener;

    public void setOnChoiceListener(onChoiceListener listener) {
        this.listener = listener;
    }

    public interface onChoiceListener {
        void onChoice(Comments data);
    }

}
