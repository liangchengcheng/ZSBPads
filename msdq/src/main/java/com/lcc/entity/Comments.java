package com.lcc.entity;

import java.io.Serializable;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  Comments
 */
public class Comments implements Serializable {

    /**
     * id : 24
     * mid : 46f337bddcb925c166bfac9acf96dea0
     * content : 你这写的确实是不错的呢。哈哈
     * author : 18813149872
     * z_num : 0
     * created_time : 2016年05月07日12:53:37
     * type : 0
     * nid : 46f337bddcb925c166bfac9acf96dea6
     * pid :
     * replay_author : 18813149871
     * phone : 18813149872
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
    private String content;
    private String author;
    private String z_num;
    private String created_time;
    private String type;
    private String nid;
    private String pid;
    private String replay_author;
    private String phone;
    private String nickname;
    private String xb;
    private String email;
    private String created_at;
    private String jf;
    private String qm;
    private String zy;
    private String user_image;
    private String title;
    private String replay_nickname;

    public String getReplay_nickname() {
        return replay_nickname;
    }

    public void setReplay_nickname(String replay_nickname) {
        this.replay_nickname = replay_nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getZ_num() {
        return z_num;
    }

    public void setZ_num(String z_num) {
        this.z_num = z_num;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getReplay_author() {
        return replay_author;
    }

    public void setReplay_author(String replay_author) {
        this.replay_author = replay_author;
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
