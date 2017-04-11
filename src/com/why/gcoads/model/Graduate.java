package com.why.gcoads.model;

import java.util.Date;

public class Graduate {
    private int gid;
    private String xuehao;
    private String studentname;
    private String studentgender;
    private Date biyeshijian;
    private EducationalLevel xueli;
    private String xueyuan;
    private String xibie;
    private String banji;
    private String gstatus;
    private int elid;
    
    
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
    public String getBanji() {
        return banji;
    }
    public void setBanji(String banji) {
        this.banji = banji;
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
	@Override
	public String toString() {
		return "Graduate [gid=" + gid + ", xuehao=" + xuehao + ", studentname="
				+ studentname + ", studentgender=" + studentgender
				+ ", biyeshijian=" + biyeshijian + ", xueli=" + xueli
				+ ", xueyuan=" + xueyuan + ", xibie=" + xibie + ", banji="
				+ banji + ", gstatus=" + gstatus + ", elid=" + elid + "]";
	}

	
}
