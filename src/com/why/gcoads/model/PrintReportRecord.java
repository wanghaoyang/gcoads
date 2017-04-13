package com.why.gcoads.model;

public class PrintReportRecord {

    private int pprid;// 主键
    private String loginname;// 支付人的用户名
    private String reportname;// 学历证明文件名
    private String reportpath;// 学历证明文件路径
    private String printdatetime;// 打印时间
    private String printpagenum;// 打印页数
    private String printstatus;// 打印状态
    private Graduate graduate;// 毕业生信息

    public int getPprid() {
        return pprid;
    }

    public void setPrid(int pprid) {
        this.pprid = pprid;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
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

    public String getPrintdatetime() {
        return printdatetime;
    }

    public void setPrintdatetime(String printdatetime) {
        this.printdatetime = printdatetime;
    }

    public String getPrintpagenum() {
        return printpagenum;
    }

    public void setPrintpagenum(String printpagenum) {
        this.printpagenum = printpagenum;
    }

    public String getPrintstatus() {
        return printstatus;
    }

    public void setPrintstatus(String printstatus) {
        this.printstatus = printstatus;
    }

    public Graduate getGraduate() {
        return graduate;
    }

    public void setGraduate(Graduate graduate) {
        this.graduate = graduate;
    }

}
