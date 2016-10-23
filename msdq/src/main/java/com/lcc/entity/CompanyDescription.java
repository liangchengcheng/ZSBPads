package com.lcc.entity;

import java.io.Serializable;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  CompanyDescription(公司的简介)
 */
public class CompanyDescription implements Serializable {

    /**
     * id : 1
     * mid : 51a305ea254faecb8105ac05314acc1b
     * company_name : 212121
     * company_image : /upload/images/20160524/7e92853b18dc8160a9c472d0a9f8aece.png
     * company_phone : 12312312
     * company_description : ewerrwerwerwe
     * author : 212121211312312312
     * location : 3131231
     * province :
     * city :
     * area :
     * look_num : 0
     * z_num : 0
     */
    private int id;
    private String mid;
    private String company_name;
    private String company_image;
    private String company_phone;
    private String company_description;
    private String author;
    private String location;
    private String province;
    private String city;
    private String area;
    private String l_num;
    private String z_num;
    private String c_num;
    private String areaid;

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_image() {
        return company_image;
    }

    public void setCompany_image(String company_image) {
        this.company_image = company_image;
    }

    public String getCompany_phone() {
        return company_phone;
    }

    public void setCompany_phone(String company_phone) {
        this.company_phone = company_phone;
    }

    public String getCompany_description() {
        return company_description;
    }

    public void setCompany_description(String company_description) {
        this.company_description = company_description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLook_num() {
        return l_num;
    }

    public void setLook_num(String look_num) {
        this.l_num = look_num;
    }

    public String getZ_num() {
        return z_num;
    }

    public void setZ_num(String z_num) {
        this.z_num = z_num;
    }
}
