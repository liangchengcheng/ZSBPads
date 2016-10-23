package com.lcc.mvp.presenter;

import com.lcc.entity.CompanyDescription;
import com.lcc.entity.TestEntity;

public interface ComStatePresenter {

    void getData(String fid);

    void Fav(CompanyDescription article, String type);

    void UnFav(CompanyDescription article, String type);
}
