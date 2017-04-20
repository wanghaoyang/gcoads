package com.why.gcoads.model;

import java.util.Date;

public class Student {

    private int sid;// 主键
    private String kaoshenghao;// 考生号
    private String shenfenzhenghao;// 身份证号
    private String xuehao;// 学号
    private String studentname;// 学生姓名
    private String studentgender;// 学生性别
    private String minzu;// 民族
    private String zhengzhimianmao;// 政治面貌
    private String zhuanye;// 专业
    private String zhuanyefangxiang;// 专业方向
    private String peiyangfangshi;// 培养方式
    private int xuezhi;// 学制
    private Date ruxueshijian;// 入学时间
    private Date biyeshijian;// 毕业时间
    private String shifanshengleibie;// 师范生类别
    private String xueyuan;// 学院
    private String xibie;// 系别
    private String banji;// 班级
    private Date chushengriqi;// 出生日期
    private String shengyuansuozaidi;// 生源所在地
    private String email;// 邮箱
    private String address;// 家庭地址

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getKaoshenghao() {
        return kaoshenghao;
    }

    public void setKaoshenghao(String kaoshenghao) {
        this.kaoshenghao = kaoshenghao;
    }

    public String getShenfenzhenghao() {
        return shenfenzhenghao;
    }

    public void setShenfenzhenghao(String shenfenzhenghao) {
        this.shenfenzhenghao = shenfenzhenghao;
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

    public String getMinzu() {
        return minzu;
    }

    public void setMinzu(String minzu) {
        this.minzu = minzu;
    }

    public String getZhengzhimianmao() {
        return zhengzhimianmao;
    }

    public void setZhengzhimianmao(String zhengzhimianmao) {
        this.zhengzhimianmao = zhengzhimianmao;
    }

    public String getZhuanye() {
        return zhuanye;
    }

    public void setZhuanye(String zhuanye) {
        this.zhuanye = zhuanye;
    }

    public String getZhuanyefangxiang() {
        return zhuanyefangxiang;
    }

    public void setZhuanyefangxiang(String zhuanyefangxiang) {
        this.zhuanyefangxiang = zhuanyefangxiang;
    }

    public String getPeiyangfangshi() {
        return peiyangfangshi;
    }

    public void setPeiyangfangshi(String peiyangfangshi) {
        this.peiyangfangshi = peiyangfangshi;
    }

    public int getXuezhi() {
        return xuezhi;
    }

    public void setXuezhi(int xuezhi) {
        this.xuezhi = xuezhi;
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

    public String getShifanshengleibie() {
        return shifanshengleibie;
    }

    public void setShifanshengleibie(String shifanshengleibie) {
        this.shifanshengleibie = shifanshengleibie;
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

    public String getBanji() {
        return banji;
    }

    public void setBanji(String banji) {
        this.banji = banji;
    }

    public Date getChushengriqi() {
        return chushengriqi;
    }

    public void setChushengriqi(Date chushengriqi) {
        this.chushengriqi = chushengriqi;
    }

    public String getShengyuansuozaidi() {
        return shengyuansuozaidi;
    }

    public void setShengyuansuozaidi(String shengyuansuozaidi) {
        this.shengyuansuozaidi = shengyuansuozaidi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student [sid=" + sid + ", kaoshenghao=" + kaoshenghao
                + ", shenfenzhenghao=" + shenfenzhenghao + ", xuehao=" + xuehao
                + ", studentname=" + studentname + ", studentgender="
                + studentgender + ", minzu=" + minzu + ", zhengzhimianmao="
                + zhengzhimianmao + ", zhuanye=" + zhuanye
                + ", zhuanyefangxiang=" + zhuanyefangxiang
                + ", peiyangfangshi=" + peiyangfangshi + ", xuezhi=" + xuezhi
                + ", ruxueshijian=" + ruxueshijian + ", biyeshijian="
                + biyeshijian + ", shifanshengleibie=" + shifanshengleibie
                + ", xueyuan=" + xueyuan + ", xibie=" + xibie + ", banji="
                + banji + ", chushengriqi=" + chushengriqi
                + ", shengyuansuozaidi=" + shengyuansuozaidi + ", email="
                + email + ", address=" + address + "]";
    }

}