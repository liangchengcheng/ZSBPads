package com.lcc.mvp.view;

import com.lcc.entity.ArticleContent;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  MenuContentView
 */
public interface MenuContentView extends BaseView {

    void Loading();

    void getFail(String msg);

    void getSuccess(ArticleContent result);

    void FavSuccess();

    void FavFail(String msg);

    void UnFavSuccess();

    void UnFavFail(String msg);

}
