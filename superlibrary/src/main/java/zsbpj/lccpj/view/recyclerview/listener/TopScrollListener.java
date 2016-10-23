package zsbpj.lccpj.view.recyclerview.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public  abstract class TopScrollListener extends RecyclerView.OnScrollListener {
    private boolean hasPlay = false;

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
            hasPlay = true;
            start();
        } else if (hasPlay) {
            hasPlay = false;
            pause();
        }
    }

    protected abstract void start();

    protected abstract void pause();

}

