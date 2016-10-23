package com.lcc.frame.data;

import android.content.Context;
import android.widget.Toast;

import com.lcc.App;
import com.lcc.db.test.UserInfo;
import com.lcc.db.test.UserInfoDao;

import java.util.List;

import de.greenrobot.dao.query.Query;
import zsbpj.lccpj.frame.FrameManager;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日10:47:52
 * Description:  DataManager 管理保存的用户信息
 */
public class DataManager {

    /**
     * 根据主键删除实体
     */
    public static void deleteAllUser() {
        getNoteDao(FrameManager.getAppContext()).deleteAll();
    }


    public static UserInfoDao getNoteDao(Context context) {
        return ((App) context.getApplicationContext()).getDaoSession().getUserInfoDao();
    }

    /**
     * 向数据库里插入登录信息
     */
    public static void saveUserInfo(UserInfo userInfo) {
        getNoteDao(FrameManager.getAppContext()).deleteAll();
        getNoteDao(FrameManager.getAppContext()).insert(userInfo);

    }

    /**
     * 获取所有用户的信息
     */
    public static UserInfo getUserInfo() {
        Query query = getNoteDao(FrameManager.getAppContext()).queryBuilder().build();
        List<UserInfo> userInfos = query.list();
        if (userInfos != null && userInfos.size() > 0) {
            return userInfos.get(0);
        }
        return null;
    }

    public static String getUserName() {
        Query query = getNoteDao(FrameManager.getAppContext()).queryBuilder()
                .build();
        List<UserInfo> user = query.list();
        if (user != null && user.size() > 0) {
            return user.get(0).getPhone();
        }
        return "";
    }

    public static String getZY() {
        Query query = getNoteDao(FrameManager.getAppContext()).queryBuilder()
                .build();
        List<UserInfo> user = query.list();
        if (user != null && user.size() > 0) {
            return user.get(0).getZy();
        }
        return "";
    }

    /**
     * 这个修改未测试
     */
    public static void editUser(UserInfo userInfo) {
        UserInfoDao userInfoDao = getNoteDao(FrameManager.getAppContext());
        Query query = userInfoDao.queryBuilder()
                .build();
        List<UserInfo> users = query.list();
        if (users != null && users.size() > 0) {
            userInfoDao.update(userInfo);
        }
    }

}
