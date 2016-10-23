package com.lcc.mvp.view;

import com.lcc.entity.CompanyTest;
import com.lcc.entity.FavEntity;

import java.util.List;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:
 */
public interface FavView extends BaseView{

    void getLoading();

    void getDataEmpty();

    void getDataFail(String msg);

    void refreshOrLoadFail(String msg);

    void refreshDataSuccess(List<FavEntity> list);

    void loadMoreWeekDataSuccess(List<FavEntity> entities);

}
