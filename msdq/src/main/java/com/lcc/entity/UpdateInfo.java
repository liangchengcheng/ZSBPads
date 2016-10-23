package com.lcc.entity;

import java.io.Serializable;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  UpdateInfo
 */
public class UpdateInfo implements Serializable {

    /**
     * id : 2
     * updated_time : 16-06-01 02:28:53
     * version_code : 2
     * version_name : v1.2.0
     * version_info : 1111
     * version_url : http://www.mytaxi.cn/download/nmjkvedio.apk
     * ok : 1
     */

    private int id;
    private String updated_time;
    private String version_code;
    private String version_name;
    private String version_info;
    private String version_url;
    private String ok;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getVersion_info() {
        return version_info;
    }

    public void setVersion_info(String version_info) {
        this.version_info = version_info;
    }

    public String getVersion_url() {
        return version_url;
    }

    public void setVersion_url(String version_url) {
        this.version_url = version_url;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }
}
