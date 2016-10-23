package com.lcc.entity;

import java.io.Serializable;

public class HrEntity implements Serializable {

    private String name;
    private String jj;
    private String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJj() {
        return jj;
    }

    public void setJj(String jj) {
        this.jj = jj;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
