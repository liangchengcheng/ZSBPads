package com.lcc.mvp.view;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  ComStateView(看这个公司是否被收藏)
 */
public interface ComStateView extends BaseView{

    /**
     * 标识是否被收藏了
     */
    void isHaveFav(boolean isfav);

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
