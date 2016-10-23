package com.lcc.entity;

import java.io.Serializable;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  SendLatter
 */
public class SendLatter implements Serializable {

    /**
     * id : 6
     * mid : 13145423456678901234552345647901
     * from_w : 18813149871
     * to_w : 18813149873
     * message_body : 我的qq？1038127753啊
     * is_read : 0
     * created_time : 18-07-21 22:07:27
     */

    private String id;
    private String mid;
    private String from_w;
    private String to_w;
    private String message_body;
    private String is_read;
    private String created_time;

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

    public String getFrom_w() {
        return from_w;
    }

    public void setFrom_w(String from_w) {
        this.from_w = from_w;
    }

    public String getTo_w() {
        return to_w;
    }

    public void setTo_w(String to_w) {
        this.to_w = to_w;
    }

    public String getMessage_body() {
        return message_body;
    }

    public void setMessage_body(String message_body) {
        this.message_body = message_body;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }
}
