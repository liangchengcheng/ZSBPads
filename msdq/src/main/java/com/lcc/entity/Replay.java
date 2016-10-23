package com.lcc.entity;

import java.io.Serializable;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2016年07月01日18:07:13
 * Description:  回复
 */
public class Replay implements Serializable {

    private String content;
    private String author;
    private String type;
    private String nid;
    private String pid;
    private String replay_author;
    private String author_code;
    private String replay_nickname;

    public String getReplay_nickname() {
        return replay_nickname;
    }

    public void setReplay_nickname(String replay_nickname) {
        this.replay_nickname = replay_nickname;
    }

    public String getAuthor_code() {
        return author_code;
    }

    public void setAuthor_code(String author_code) {
        this.author_code = author_code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplay_author() {
        return replay_author;
    }

    public void setReplay_author(String replay_author) {
        this.replay_author = replay_author;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
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
}
