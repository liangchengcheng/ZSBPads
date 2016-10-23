package com.lcc.mvp.view;

import com.lcc.entity.Article;
import com.lcc.entity.XtNewsEntity;

import java.util.List;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  XtNewsView
 */
public interface XtNewsView extends BaseView {

    void getLoading();

    void getDataEmpty();

    void getDataFail(String msg);

    void getDataSuccess(List<XtNewsEntity> list);
}
