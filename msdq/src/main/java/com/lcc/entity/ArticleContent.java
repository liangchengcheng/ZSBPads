package com.lcc.entity;

import java.io.Serializable;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  ArticleContent
 */
public class ArticleContent implements Serializable {

    /**
     * id : null
     * content : 编辑推荐：</strong>稀土掘金</a>，这是一个针对技术开发者的一个应用，你可以在掘金上获取最新最优质的技术干货，不仅仅是Android知识、前端、后端以至于产品和设计都有涉猎，想成为全栈工程师的朋友不要错过！
     * fid : 46f337bddcb925c166bfac9acf96dea6
     * mid : null
     * author : null
     * nid : null
     * created_time : null
     * updated_time : null
     * type : null
     */

    private Object id;
    private String content;
    private String fid;
    private Object mid;
    private Object author;
    private Object nid;
    private Object created_time;
    private Object updated_time;
    private Object type;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
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

    public Object getMid() {
        return mid;
    }

    public void setMid(Object mid) {
        this.mid = mid;
    }

    public Object getAuthor() {
        return author;
    }

    public void setAuthor(Object author) {
        this.author = author;
    }

    public Object getNid() {
        return nid;
    }

    public void setNid(Object nid) {
        this.nid = nid;
    }

    public Object getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Object created_time) {
        this.created_time = created_time;
    }

    public Object getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(Object updated_time) {
        this.updated_time = updated_time;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }
}
