package com.lcc.mvp.presenter;

public interface FlowPresenter {

    void getMyData(int page, String type);

    void loadMyMore(int page, String type);

    void refreshMy(int page, String type);

    void getYouData(int page, String type);

    void loadYouMore(int page, String type);

    void refreshYou(int page, String type);
}
