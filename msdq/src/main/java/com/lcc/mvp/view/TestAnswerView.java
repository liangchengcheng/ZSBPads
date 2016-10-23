package com.lcc.mvp.view;

import com.lcc.entity.Answer;
import com.lcc.entity.FavEntity;
import com.lcc.entity.TestEntity;
import com.lcc.entity.UserListFav;

import java.util.List;

public interface TestAnswerView extends BaseView {

    void getLoading();

    void getDataEmpty();

    void getDataFail(String msg);

    void refreshOrLoadFail(String msg);

    void refreshView(List<Answer> entities);

    void loadMoreView(List<Answer> entities);

    /**
     * 标识是否被收藏了
     */
    void isHaveFav(boolean isfavEntity);

    /**
     * 收藏成功
     */
    void FavSuccess();

    /**
     * 收藏失败
     */
    void FavFail(String msg);

    /**
     * 取消收藏成功
     */
    void UnFavSuccess();

    /**
     * 取消收藏失败
     */
    void UnFavFail(String msg);

    //获取收藏用户列表

    void getUserListLoading();

    void getUserListEmpty();

    void getUserListFail(String msg);

    void UserListLoadFail(String msg);

    void refreshUserListView(List<UserListFav> entities);

    void loadMoreUserListView(List<UserListFav> entities);
}
