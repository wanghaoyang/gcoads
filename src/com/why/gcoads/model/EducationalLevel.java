package com.why.gcoads.model;

import java.util.Date;

public class EducationalLevel {

    private int elid;//主键
    private String educationallevel;//学历名称
    private Date createtime;
    
    public int getElid() {
        return elid;
    }
    public void setElid(int elid) {
        this.elid = elid;
    }
    public String getEducationallevel() {
        return educationallevel;
    }
    public void setEducationallevel(String educationallevel) {
        this.educationallevel = educationallevel;
    }
    
	public Date getCreatetime() {
        return createtime;
    }
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
    @Override
    public String toString() {
        return "EducationalLevel [elid=" + elid + ", educationallevel=" + educationallevel + ", createtime="
                + createtime + "]";
    }
    
    
}
