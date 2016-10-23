package com.lcc.entity;

/**
 * 地址选择必须的bean
 */
public class AreaInfo {

    private Integer id;
    private Integer areaCode;
    private String areaName;
    private String areaShortName;
    private String parentCode;
    private Integer level;
    private boolean leaf;
    private boolean isMunicipality;
    private boolean isSpecial;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaShortName() {
        return areaShortName;
    }

    public void setAreaShortName(String areaShortName) {
        this.areaShortName = areaShortName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isMunicipality() {
        return isMunicipality;
    }

    public void setMunicipality(boolean municipality) {
        isMunicipality = municipality;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public void setSpecial(boolean special) {
        isSpecial = special;
    }
}
