package com.lcc.mvp.presenter;

public interface JSPresenter {

    void getData(int page, String fid, String type);

    void loadMore(int page, String fid, String type);

    void refresh(int page, String fid, String type);
}
