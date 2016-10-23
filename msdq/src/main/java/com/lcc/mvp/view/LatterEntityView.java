package com.lcc.mvp.view;

import com.lcc.entity.LatterEntity;
import com.lcc.entity.Letter;

import java.util.List;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2016年07月24日12:06:21
 * Description:  LatterEntityView
 */
public interface LatterEntityView extends BaseView{

    void getLoading();

    void getDataEmpty();

    void getDataFail(String msg);

    void getDataSuccess(List<LatterEntity> list);

    void rePlaying();

    void replaySuccess();

    void replayFail();

}
