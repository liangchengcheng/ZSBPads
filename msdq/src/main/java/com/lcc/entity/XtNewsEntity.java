package com.lcc.entity;

import java.io.Serializable;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  XtNewsEntity(官方未读消息)
 */
public class XtNewsEntity implements Serializable{

    /**
     * id : 1
     * message_body : 你好你好你好，今天晚上服务器要更新了，2016年07月19日07:30:46发。
     * title : 服务器更新通知
     * readed : 0
     * created_time : 2016年07月19日07:31:08
     */

    private int id;
    private String message_body;
    private String title;
    private String readed;
    private String created_time;
    private String ok;

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage_body() {
        return message_body;
    }

    public void setMessage_body(String message_body) {
        this.message_body = message_body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReaded() {
        return readed;
    }

    public void setReaded(String readed) {
        this.readed = readed;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }
}
