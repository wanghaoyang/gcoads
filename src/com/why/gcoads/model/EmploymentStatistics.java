package com.why.gcoads.model;

public class EmploymentStatistics {

    private int year;
    private String xueyuan;
    private int graduatecount;
    private int employmentcount;
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public String getXueyuan() {
        return xueyuan;
    }
    public void setXueyuan(String xueyuan) {
        this.xueyuan = xueyuan;
    }
    public int getGraduatecount() {
        return graduatecount;
    }
    public void setGraduatecount(int graduatecount) {
        this.graduatecount = graduatecount;
    }
    public int getEmploymentcount() {
        return employmentcount;
    }
    public void setEmploymentcount(int employmentcount) {
        this.employmentcount = employmentcount;
    }
    @Override
    public String toString() {
        return "EmploymentStatistics [year=" + year + ", xueyuan=" + xueyuan
                + ", graduatecount=" + graduatecount + ", employmentcount="
                + employmentcount + "]";
    }
    
}