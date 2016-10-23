package com.lcc.utils;

import com.lcc.frame.data.DataManager;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  ChangeState
 */
public class ChangeState {

    public static void UserLogout() {
        DataManager.deleteAllUser();
        SharePreferenceUtil.setUserTk("");
    }
}
