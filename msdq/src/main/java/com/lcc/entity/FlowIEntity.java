package com.lcc.entity;

import java.io.Serializable;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  FlowIEntity(我关注的人)
 */
public class FlowIEntity implements Serializable {

    /**
     * id : 2
     * mid : 6bca713d9cee7b513942f98124c264bd
     * me : 18813149871
     * you : 18813149801
     * created_time : 16-06-13 12:21:36
     * updated_time : 16-06-13 12:21:36
     * phone : 18813149801
     * nickname : xiaochengcheng
     * xb : 男
     * email : 1038127753@qq.com
     * created_at : 2016年05月06日22:45:46
     * jf : 10
     * qm : 人生自古谁无死
     * zy : 程序员
     * user_image : http://ww1.sinaimg.cn/crop.7.22.1192.1192.1024/5c6defebjw8epti0r9noaj20xc0y1n0x.jpg
     */

    private String id;
    private String mid;
    private String me;
    private String you;
    private String created_time;
    private String updated_time;
    private String phone;
    private String nickname;
    private String xb;
    private String email;
    private String created_at;
    private String jf;
    private String qm;
    private String zy;
    private String user_image;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getJf() {
        return jf;
    }

    public void setJf(String jf) {
        this.jf = jf;
    }

    public String getQm() {
        return qm;
    }

    public void setQm(String qm) {
        this.qm = qm;
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }
}
