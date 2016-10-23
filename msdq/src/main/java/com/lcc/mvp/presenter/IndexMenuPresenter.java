package com.lcc.mvp.presenter;

import com.lcc.entity.Article;

public interface IndexMenuPresenter {

    void getData(int page,String type);

    void loadMore(int page,String type);

    void refresh(int page,String type);
}
