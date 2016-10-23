package com.lcc.entity;

import java.io.Serializable;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  关注
 */
public class GzBean implements Serializable {

    /**
     * id : 2
     * mid : b287887b0d802705dd212ca7a7e118d5
     * me : 13287878449
     * you : 18813149871
     * created_time : 2016-06-13 12:21:50
     * updated_time : 2016-06-13 12:21:50
     */

    private String id;
    private String mid;
    private String me;
    private String you;
    private String created_time;
    private String updated_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMe() {
        return me;
    }

    public void setMe(String me) {
        this.me = me;
    }

    public String getYou() {
        return you;
    }

    public void setYou(String you) {
        this.you = you;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }
}
