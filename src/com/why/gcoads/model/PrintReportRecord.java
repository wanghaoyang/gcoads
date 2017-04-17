package com.why.gcoads.model;

import java.util.Date;

public class PrintReportRecord {

    private int pprid;// 主键
    private String docnum;// 主键
    private String loginname;// 支付人的用户名
    private String shenfenzhenghao;//毕业生身份证号
    private String reportname;// 学历证明文件名
    private String reportpath;// 学历证明文件路径
    private Date printdatetime;// 打印时间
    private int printpagenum;// 打印页数
    private boolean printstatus;// 打印状态
    private Student student;// 毕业生基本信息
    private Graduate graduate;// 毕业生信息

    public int getPprid() {
        return pprid;
    }

    public void setPprid(int pprid) {
        this.pprid = pprid;
    }

    public String getDocnum() {
        return docnum;
    }

    public void setDocnum(String docnum) {
        this.docnum = docnum;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getShenfenzhenghao() {
        return shenfenzhenghao;
    }

    public void setShenfenzhenghao(String shenfenzhenghao) {
        this.shenfenzhenghao = shenfenzhenghao;
    }

    public String getReportname() {
        return reportname;
    }

    public void setReportname(String reportname) {
        this.reportname = reportname;
    }

    public String getReportpath() {
        return reportpath;
    }

    public void setReportpath(String reportpath) {
        this.reportpath = reportpath;
    }

    public Date getPrintdatetime() {
        return printdatetime;
    }

    public void setPrintdatetime(Date printdatetime) {
        this.printdatetime = printdatetime;
    }

    public int getPrintpagenum() {
        return printpagenum;
    }

    public void setPrintpagenum(int printpagenum) {
        this.printpagenum = printpagenum;
    }

    public boolean getPrintstatus() {
        return printstatus;
    }

    public void setPrintstatus(boolean printstatus) {
        this.printstatus = printstatus;
    }

    public Graduate getGraduate() {
        return graduate;
    }

    public void setGraduate(Graduate graduate) {
        this.graduate = graduate;
    }
    
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "PrintReportRecord [pprid=" + pprid + ", loginname=" + loginname
                + ", reportname=" + reportname + ", reportpath=" + reportpath
                + ", printdatetime=" + printdatetime + ", printpagenum="
                + printpagenum + ", printstatus=" + printstatus + ", graduate="
                + graduate + "]";
    }

}
