package com.lcc.entity;

import java.io.Serializable;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  Type1
 */
public class Type1 implements Serializable {

    private String id;
    private String n_name;
    private String n_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getN_id() {
        return n_id;
    }

    public void setN_id(String n_id) {
        this.n_id = n_id;
    }

    public String getN_name() {
        return n_name;
    }

    public void setN_name(String n_name) {
        this.n_name = n_name;
    }
}
