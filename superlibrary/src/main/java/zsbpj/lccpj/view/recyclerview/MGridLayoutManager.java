package zsbpj.lccpj.view.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import zsbpj.lccpj.view.recyclerview.adapter.LoadMoreRecyclerAdapter;

public class MGridLayoutManager extends GridLayoutManager {

    public MGridLayoutManager(Context context, final int spanCount, final LoadMoreRecyclerAdapter mAdapter) {
        super(context, spanCount);
        setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mAdapter.getItemViewType(position)) {
                    case LoadMoreRecyclerAdapter.TYPE_FOOTER:
                        return spanCount;
                    case LoadMoreRecyclerAdapter.TYPE_ITEM:
                        return 1;
                    default:
                        return -1;
                }
            }
        });
    }
}
