package com.lcc.mvp.view;

import com.lcc.entity.Article;
import com.lcc.entity.CompanyTest;

import java.util.List;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:
 */
public interface JSView extends BaseView {

    /**
     * 第一次加载
     */
    void getLoading();

    /**
     * 获取空数据
     */
    void getDataEmpty();

    /**
     * 获取数据失败
     */
    void getDataFail(String msg);

    void refreshOrLoadFail(String msg);

    void refreshDataSuccess(List<CompanyTest> list);

    void loadMoreWeekDataSuccess(List<CompanyTest> entities);

}
