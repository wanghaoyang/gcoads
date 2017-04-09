package com.why.gcoads.model;

public class User {
    
    private String uid;//主键
    private String loginname;//登录名
    private String loginpass;//登录密码
    private String email;//邮箱
    private String role;//权限
    private boolean status;//状态，true表示已激活，或者未激活
    private String activationCode;//激活码，它是唯一值！即每个用户的激活码是不同的！
    private boolean deleted;//被删除，true表示已被删除，或者未被删除
    // 注册表单
    private String reloginpass;//确认密码
    private String verifyCode;//验证码
    
    // 修改密码表单
    private String newpass;//新密码

    public String getReloginpass() {
        return reloginpass;
    }
    public void setReloginpass(String reloginpass) {
        this.reloginpass = reloginpass;
    }
    public String getVerifyCode() {
        return verifyCode;
    }
    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    
    
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getLoginname() {
        return loginname;
    }
    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }
    public String getLoginpass() {
        return loginpass;
    }
    public void setLoginpass(String loginpass) {
        this.loginpass = loginpass;
    }
    public String getRole() {
        return role;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getEmail() {
        return email;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public String getActivationCode() {
        return activationCode;
    }
    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
    public String getNewpass() {
        return newpass;
    }
    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }
    @Override
    public String toString() {
        return "User [uid=" + uid + ", loginname=" + loginname + ", loginpass=" + loginpass + ", role=" + role
                + ", email=" + email + ", status=" + status + ", activationCode=" + activationCode + ", reloginpass="
                + reloginpass + ", verifyCode=" + verifyCode + ", newpass=" + newpass + "]";
    }
    

}