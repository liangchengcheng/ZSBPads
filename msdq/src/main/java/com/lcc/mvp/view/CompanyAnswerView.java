package com.lcc.mvp.view;

import com.lcc.entity.Answer;
import com.lcc.entity.CompanyAnswer;

import java.util.List;

public interface CompanyAnswerView extends BaseView{

    void getLoading();

    void getDataEmpty();

    void getDataFail(String msg);

    void refreshOrLoadFail(String msg);

    void refreshView(List<CompanyAnswer> entities);

    void loadMoreView(List<CompanyAnswer> entities);

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
}
