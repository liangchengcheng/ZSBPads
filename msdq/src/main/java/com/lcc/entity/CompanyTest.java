package com.lcc.entity;

import java.io.Serializable;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  CompanyTest
 */
public class CompanyTest implements Serializable{


    /**
     * id : 1
     * mid : 2f7f12aa4606c2a1d8eb7a34d1864dab
     * fid : 1cddd741560e7d90ebf9112b989ba955
     * title : 本文将从一个简单的例子开始，逐步完善我们的程序
     * author : 18813149871
     * type : 技术
     * summary : 本文将从一个简单的例子开始，逐步完善我们的程序本文将从一个简单的例子开始，逐步完善我们的程序本文将从一个简单的例子开始，逐步完善我们的程序本文将从一个简单的例子开始，逐步完善我们的程序本文将从一个简单的例子开始，逐步完善我们的程序本文将从一个简单的例子开始，逐步完善我们的程序本文将从一个简单的例子开始，逐步完善我们的程序
     * created_time : 16-06-09 01:52:58
     * updated_time : 16-06-09 01:52:58
     * look_num : 0
     * z_num : 0
     * question_image : http://img4.imgtn.bdimg.com/it/u=3835306900,299404236&fm=21&gp=0.jpg
     * phone : 18813149871
     * nickname : xiaochengcheng
     * xb : 男
     * email : 1038127753@qq.com
     * created_at : 2016年05月07日12:53:37
     * jf : 10
     * qm : 我走的很慢，但是我不会停的
     * zy : 程序员
     * user_image : http://h.hiphotos.baidu.com/image/h%3D200/sign=71cd4229be014a909e3e41bd99763971/472309f7905298221dd4c458d0ca7bcb0b46d442.jpg
     */

    private String id;
    private String mid;
    private String fid;
    private String title;
    private String author;
    private String type;
    private String summary;
    private String created_time;
    private String updated_time;
    private String l_num;
    private String z_num;
    private String c_num;

    public String getL_num() {
        return l_num;
    }

    public void setL_num(String l_num) {
        this.l_num = l_num;
    }

    public String getC_num() {
        return c_num;
    }

    public void setC_num(String c_num) {
        this.c_num = c_num;
    }

    private String question_image;
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

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public String getZ_num() {
        return z_num;
    }

    public void setZ_num(String z_num) {
        this.z_num = z_num;
    }

    public String getQuestion_image() {
        return question_image;
    }

    public void setQuestion_image(String question_image) {
        this.question_image = question_image;
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
