package com.lcc.entity;

import java.io.Serializable;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  ActivityEntity
 */
public class ActivityEntity implements Serializable {

    /**
     * id : 1
     * mid : cf16cb2b3ea8de606a72f726df599f2f
     * created_time : 16-05-17 01:42:12
     * activity_pic : /upload/images/20160517/9f2c5d8fb9e4822d451296097f27dc9a.jpg
     * activity_title : 活动标题活动标题活动标题活动标题活动标题
     * created_by : 活动标题
     * is_used : 1
     */

    private int id;
    private String mid;
    private String created_time;
    private String activity_pic;
    private String activity_title;
    private String created_by;
    private String is_used;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getActivity_pic() {
        return activity_pic;
    }

    public void setActivity_pic(String activity_pic) {
        this.activity_pic = activity_pic;
    }

    public String getActivity_title() {
        return activity_title;
    }

    public void setActivity_title(String activity_title) {
        this.activity_title = activity_title;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getIs_used() {
        return is_used;
    }

    public void setIs_used(String is_used) {
        this.is_used = is_used;
    }
}
