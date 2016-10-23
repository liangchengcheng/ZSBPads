package com.lcc.mvp.presenter;

import com.lcc.entity.CompanyTest;

public interface LookCompanyAnswerPresenter {

    void getData(int page, String fid);

    void loadMore(int page, String fid);

    void refresh(int page, String fid);

    void Fav(CompanyTest article, String type);

    void UnFav(CompanyTest article);

}
