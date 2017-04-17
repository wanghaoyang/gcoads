package com.why.gcoads.model;

import java.util.Date;

public class Graduate {
    private int gid;
    private String xuehao;
    private String studentname;
    private String studentgender;
    private Date ruxueshijian;
    private Date biyeshijian;
    private EducationalLevel xueli;
    private String xueyuan;
    private String xibie;
    private String zhuanye;
    private String graduatecertificatenum;
    private String gstatus;
    private String province;
    private String city;
    private int elid;

    public Graduate() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Graduate(Student stu) {
        this.xuehao = stu.getXuehao();
        this.studentname = stu.getStudentname();
        this.studentgender = stu.getStudentgender();
        this.ruxueshijian = stu.getRuxueshijian();
        this.xueyuan = stu.getXueyuan();
        this.xibie = stu.getXibie();
        this.zhuanye = stu.getZhuanye();
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getXuehao() {
        return xuehao;
    }

    public void setXuehao(String xuehao) {
        this.xuehao = xuehao;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getStudentgender() {
        return studentgender;
    }

    public void setStudentgender(String studentgender) {
        this.studentgender = studentgender;
    }

    public Date getRuxueshijian() {
        return ruxueshijian;
    }

    public void setRuxueshijian(Date ruxueshijian) {
        this.ruxueshijian = ruxueshijian;
    }

    public Date getBiyeshijian() {
        return biyeshijian;
    }

    public void setBiyeshijian(Date biyeshijian) {
        this.biyeshijian = biyeshijian;
    }

    public EducationalLevel getXueli() {
        return xueli;
    }

    public void setXueli(EducationalLevel xueli) {
        this.xueli = xueli;
    }

    public String getXueyuan() {
        return xueyuan;
    }

    public void setXueyuan(String xueyuan) {
        this.xueyuan = xueyuan;
    }

    public String getXibie() {
        return xibie;
    }

    public void setXibie(String xibie) {
        this.xibie = xibie;
    }

    public String getZhuanye() {
        return zhuanye;
    }

    public void setZhuanye(String zhuanye) {
        this.zhuanye = zhuanye;
    }

    public String getGraduatecertificatenum() {
        return graduatecertificatenum;
    }

    public void setGraduatecertificatenum(String graduatecertificatenum) {
        this.graduatecertificatenum = graduatecertificatenum;
    }

    public String getGstatus() {
        return gstatus;
    }

    public void setGstatus(String gstatus) {
        this.gstatus = gstatus;
    }

    public int getElid() {
        return elid;
    }

    public void setElid(int elid) {
        this.elid = elid;
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

    @Override
    public String toString() {
        return "Graduate [gid=" + gid + ", xuehao=" + xuehao + ", studentname=" + studentname + ", studentgender="
                + studentgender + ", ruxueshijian=" + ruxueshijian + ", biyeshijian=" + biyeshijian + ", xueli=" + xueli
                + ", xueyuan=" + xueyuan + ", xibie=" + xibie + ", zhuanye=" + zhuanye + ", graduatecertificatenum="
                + graduatecertificatenum + ", gstatus=" + gstatus + ", province=" + province + ", city=" + city
                + ", elid=" + elid + "]";
    }

    

}
