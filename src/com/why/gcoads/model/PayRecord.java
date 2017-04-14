package com.why.gcoads.model;

import java.util.Date;

public class PayRecord {
    
    private int prid;//主键
    private String loginname;//支付人的用户名
    private Date paystartdatetime;//产生时间
    private Date payfinisheddatetime;//支付完成时间
    private double totalcost;//总支付钱数
    private double certificationquantity;//认证学历数量
    private boolean paystatus;//支付状态
    public int getPrid() {
        return prid;
    }
    public void setPrid(int prid) {
        this.prid = prid;
    }
    public String getLoginname() {
        return loginname;
    }
    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }
    public Date getPaystartdatetime() {
        return paystartdatetime;
    }
    public void setPaystartdatetime(Date paystartdatetime) {
        this.paystartdatetime = paystartdatetime;
    }
    public Date getPayfinisheddatetime() {
        return payfinisheddatetime;
    }
    public void setPayfinisheddatetime(Date payfinisheddatetime) {
        this.payfinisheddatetime = payfinisheddatetime;
    }
    public double getTotalcost() {
        return totalcost;
    }
    public void setTotalcost(double totalcost) {
        this.totalcost = totalcost;
    }
    public double getCertificationquantity() {
        return certificationquantity;
    }
    public void setCertificationquantity(double certificationquantity) {
        this.certificationquantity = certificationquantity;
    }
    public boolean getPaystatus() {
        return paystatus;
    }
    public void setPaystatus(boolean paystatus) {
        this.paystatus = paystatus;
    }
}
