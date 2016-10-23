package com.lcc.mvp.view;

import com.lcc.entity.ActivityEntity;
import com.lcc.entity.Answer;
import com.lcc.entity.WeekData;

import java.util.List;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:
 */
public interface IndexView extends BaseView{

    void getFail(String msg);

    void getSuccess(List<ActivityEntity> list);

    void getWeekDataLoading();

    void getWeekDataEmpty();

    void getWeekDataFail(String msg);

    void refreshWeekDataSuccess(List<WeekData> list);

    void loadMoreWeekDataSuccess(List<WeekData> entities);

}
