package com.lcc.mvp.view;

import com.lcc.entity.Comments;
import com.lcc.entity.UserGood;

import java.util.List;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  UserGoodView
 */
public interface ReplayView extends BaseView {

    void getLoading();

    void getDataEmpty();

    void getDataFail(String msg);

    void getDataSuccess(List<Comments> list);
}
