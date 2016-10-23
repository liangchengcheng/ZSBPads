package com.lcc.frame.fragment.state;


import com.lcc.msdq.R;

public class ContentState extends AbstractShowState implements ShowState {

    @Override
    public void show(boolean animate) {
        showViewById(R.id.epf_content, animate);
    }

    @Override
    public void dismiss(boolean animate) {
        dismissViewById(R.id.epf_content, animate);
    }

}
