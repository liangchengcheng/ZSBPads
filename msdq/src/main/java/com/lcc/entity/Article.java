package com.lcc.entity;

import java.io.Serializable;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  Article
 */
public class Article implements Serializable {

    /**
     * id : 1
     * mid : 46f337bddcb925c166bfac9acf96dea6
     * title : FloatingActionButton滚动时的显示与隐藏小结
     * created_time : 16-06-04 10:48:27
     * updated_time : 16-06-04 10:48:27
     * author : 18813149871
     * l_num : 0
     * z_num : 0
     * type : 面试感想
     * source : 百度
     * image_url :
     * summary : 编辑推荐：稀土掘金，这是一个针对技术开发者的一个应用，你可以在掘金上获取最新最优质的技术干货，
     * 不仅仅是Android知识、前端、后端以至于产品和设计都有涉猎，想成为全栈工程师的朋友不要错过！
     * state : 2
     * phone : 18813149871
     * nickname : xiaochengcheng
     * xb : 男
     * email : 1038127753@qq.com
     * created_at : 2016年05月07日12:53:37
     * jf : 10
     * qm : 我走的很慢，但是我不会停的
     * zy : 程序员
     * user_image : http://h.hiphotos.baidu.com/image/h%3D200/sign=71cd4229be014a909e3e41bd99763971/
     * 472309f7905298221dd4c458d0ca7bcb0b46d442.jpg
     */

    private String id;
    private String mid;
    private String title;
    private String created_time;
    private String updated_time;
    private String author;
    private String l_num;
    private String z_num;
    private String type;
    private String source;
    private String image_url;
    private String summary;
    private String state;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getL_num() {
        return l_num;
    }

    public void setL_num(String l_num) {
        this.l_num = l_num;
    }

    public String getZ_num() {
        return z_num;
    }

    public void setZ_num(String z_num) {
        this.z_num = z_num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
