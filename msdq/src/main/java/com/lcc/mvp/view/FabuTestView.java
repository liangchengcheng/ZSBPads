package com.lcc.mvp.view;

import com.lcc.entity.FavEntity;
import com.lcc.entity.TestEntity;

import java.util.List;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  FabuTestView
 */
public interface FabuTestView extends BaseView {

    void getLoading();

    void getDataEmpty();

    void getDataFail(String msg);

    void refreshOrLoadFail(String msg);

    void refreshDataSuccess(List<TestEntity> list);

    void loadMoreWeekDataSuccess(List<TestEntity> entities);

}
