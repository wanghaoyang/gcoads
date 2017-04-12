package com.why.gcoads.model;

public class EducationalLevel {

    private int elid;//主键
    private String educationallevel;//学历名称
    
    public int getElid() {
        return elid;
    }
    public void setElid(int elid) {
        this.elid = elid;
    }
    public String getEducationalLevel() {
        return educationallevel;
    }
    public void setEducationalLevel(String educationallevel) {
        this.educationallevel = educationallevel;
    }
	@Override
	public String toString() {
		return "EducationalLevel [elid=" + elid + ", educationallevel="
				+ educationallevel + "]";
	}
    
}
