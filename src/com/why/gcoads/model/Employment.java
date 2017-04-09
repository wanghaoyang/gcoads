package com.why.gcoads.model;

public class Employment {

    private String eid;
    private String province;
    private String city;
    private String jiuyefangxiang;
    private boolean iszhuanyefangxiang;
    private double jiuyelv;
    private boolean iskaoyan;
    private Graduate graduate;
    
    public String getEid() {
        return eid;
    }
    public void setEid(String eid) {
        this.eid = eid;
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
    public String getJiuyefangxiang() {
        return jiuyefangxiang;
    }
    public void setJiuyefangxiang(String jiuyefangxiang) {
        this.jiuyefangxiang = jiuyefangxiang;
    }
    public boolean isIszhuanyefangxiang() {
        return iszhuanyefangxiang;
    }
    public void setIszhuanyefangxiang(boolean iszhuanyefangxiang) {
        this.iszhuanyefangxiang = iszhuanyefangxiang;
    }
    public double getJiuyelv() {
        return jiuyelv;
    }
    public void setJiuyelv(double jiuyelv) {
        this.jiuyelv = jiuyelv;
    }
    public boolean isIskaoyan() {
        return iskaoyan;
    }
    public void setIskaoyan(boolean iskaoyan) {
        this.iskaoyan = iskaoyan;
    }
    public Graduate getGraduate() {
        return graduate;
    }
    public void setGraduate(Graduate graduate) {
        this.graduate = graduate;
    }
    
}
