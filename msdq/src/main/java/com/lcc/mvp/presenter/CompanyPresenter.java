package com.lcc.mvp.presenter;

public interface CompanyPresenter {

    void loadMore(int id, int type, int page, int count);
    
    void refresh(int id, int type, int count);

}
