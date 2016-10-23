package com.lcc.mvp.view;

import com.lcc.entity.GzBean;
import com.lcc.entity.otherUserInfo;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  GetUserInfoView(获取用户的简单的个人资料)
 */
public interface GetUserInfoView extends BaseView{

    void getLoading();

    void getDataEmpty();

    void getDataFail(String msg);

    void getDataSuccess(otherUserInfo otherUserInfo);

    /**
     * 标识是否被关注
     */
    void HaveGz(GzBean bean);

    void GzSuccess();

    void GzFail(String msg);

    void unGzSuccess();

    void unGzFail(String msg);

}
