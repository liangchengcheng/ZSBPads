package com.lcc.mvp.presenter;

import com.lcc.entity.Replay;

public interface CommentsPresenter {

    void getData(int page, String nid);

    void loadMore(int page, String nid);

    void refresh(int page, String nid);

    void sendComments(Replay replay);
}
