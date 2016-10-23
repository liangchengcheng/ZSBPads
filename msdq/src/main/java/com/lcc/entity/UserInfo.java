package com.lcc.entity;

import java.io.Serializable;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  UserInfo
 */
public class UserInfo implements Serializable {
    private String nickname;
    private String xb;
    private String email;
    private String qm;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getQm() {
        return qm;
    }

    public void setQm(String qm) {
        this.qm = qm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }
}
