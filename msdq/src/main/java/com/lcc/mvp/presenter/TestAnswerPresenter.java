package com.lcc.mvp.presenter;

import com.lcc.entity.TestEntity;

public interface TestAnswerPresenter {

    void getData(int page,String fid);

    void loadMore(int page,String fid);

    void refresh(int page,String fid);

    void Fav(TestEntity article, String type);

    void UnFav(TestEntity article,String type);

    void getUserListData(int page,String nid);

}
