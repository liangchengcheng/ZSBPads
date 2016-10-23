package com.lcc.mvp.presenter;

import com.lcc.entity.CompanyTest;
import com.lcc.entity.TestEntity;

public interface CompanyAnswerPresenter {

    void getData(int page,String fid);

    void loadMore(int page, String fid);

    void refresh(int page, String fid);

    void Fav(CompanyTest article, String type);

    void UnFav(CompanyTest article);

}
