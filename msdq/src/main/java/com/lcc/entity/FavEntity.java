package com.lcc.entity;

import java.io.Serializable;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  FavEntity
 */
public class FavEntity implements Serializable {

    /**
     * id : 1
     * mid : f1696859a19dbafa7e41b86ed05b65fa
     * author : 18813149871
     * nid : 46f337bddcb925c166bfac9acf96dea6
     * fav_title : 如何简单高效的学习android的呢？
     * created_time : 16-06-12 11:43:15
     * updated_time : 16-06-12 11:43:15
     * type : 面试感想
     */

    private String id;
    private String mid;
    private String author;
    private String nid;
    private String fav_title;
    private String c_time;

    public String getU_time() {
        return u_time;
    }

    public void setU_time(String u_time) {
        this.u_time = u_time;
    }

    public String getC_time() {
        return c_time;
    }

    public void setC_time(String c_time) {
        this.c_time = c_time;
    }

    private String u_time;
    private String type;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getFav_title() {
        return fav_title;
    }

    public void setFav_title(String fav_title) {
        this.fav_title = fav_title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
