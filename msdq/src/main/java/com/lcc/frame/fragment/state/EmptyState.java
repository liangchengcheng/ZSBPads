package com.lcc.frame.fragment.state;

import com.lcc.msdq.R;

public class EmptyState extends AbstractShowState {
    @Override
    public void show(boolean animate) {
        showViewById(R.id.epf_empty, animate);
    }

    @Override
    public void dismiss(boolean animate) {
        dismissViewById(R.id.epf_empty, animate);
    }
}
