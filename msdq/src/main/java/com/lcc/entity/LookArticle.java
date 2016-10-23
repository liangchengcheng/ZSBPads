package com.lcc.entity;

import java.io.Serializable;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2016年08月26日08:56:56
 * Description:  通过我的收藏点击查看文章
 */
public class LookArticle implements Serializable {

    /**
     * id : 2
     * mid : 46f337bddcb925c166bfac9acf96dea6
     * title : FloatingActionButton滚动时的显示与隐藏小结
     * created_time : 2016-06-04 10:48:27
     * updated_time : 16-06-04 10:48:27
     * author : 18813149871
     * l_num : 1
     * z_num : 0
     * type : 面试准备
     * source : 百度
     * image_url :
     * summary : 编辑推荐：稀土掘金，这是一个针对技术开发者的一个应用，你可以在掘金上获取最新最优质的技术干货，不仅仅是Android知识、前端、后端以至于产品和设计都有涉猎，想成为全栈工程师的朋友不要错过！
     * state : 0
     * is_used : 0
     * content : oooo
     * fid : 46f337bddcb925c166bfac9acf96dea6
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
    private String is_used;
    private String content;
    private String fid;

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

    public String getIs_used() {
        return is_used;
    }

    public void setIs_used(String is_used) {
        this.is_used = is_used;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }
}
