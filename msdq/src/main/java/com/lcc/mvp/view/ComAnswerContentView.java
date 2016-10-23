package com.lcc.mvp.view;

import com.lcc.entity.AnswerContent;
import com.lcc.entity.FavAndGoodState;

public interface ComAnswerContentView extends BaseView{

    void getLoading();

    void getDataEmpty();

    void getDataFail(String msg);

    void getDataSuccess(AnswerContent msg);
    
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

    void getStateSuccess(FavAndGoodState msg);

    void getStateFail(String msg);

    void GoodSuccess();

    void GoodFail(String msg);

    void UnGoodSuccess();

    void UnGoodFail(String msg);
}
