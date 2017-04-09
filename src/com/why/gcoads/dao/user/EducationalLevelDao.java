package com.why.gcoads.dao.user;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.why.gcoads.model.EducationalLevel;
import com.why.gcoads.model.PageBean;
import com.why.gcoads.model.User;
import com.why.gcoads.utils.jdbc.TxQueryRunner;

public class EducationalLevelDao {
    
    private QueryRunner qr = new TxQueryRunner();
    
    /**
     * 校验学历是否已经存在
     * @param educationalLevel
     * @return
     * @throws SQLException
     */
    public boolean ajaxValidateEducationalLevel(String educationalLevel) throws SQLException {
        String sql = "select count(1) from t_educationallevel where educationalLevel=?";
        Number number = (Number)qr.query(sql, new ScalarHandler(), educationalLevel);
        return number.intValue() == 0;
    }
    
    public void addEducationalLevel(EducationalLevel educationalLevel) throws SQLException {
        String sql = "select count(1) from t_educationallevel where educationalLevel=?";
        Object[] params = {educationalLevel.getElid(), educationalLevel.getEducationalLevel()};
        qr.update(sql, params);
    }
    
    /**
     * 
     * @param pageEducationalLevel
     * @return
     * @throws SQLException
     */
    public PageBean<EducationalLevel> find(PageBean<EducationalLevel> pageEducationalLevel) throws SQLException {
        
        if (pageEducationalLevel == null){
            pageEducationalLevel = new PageBean<EducationalLevel>();
            pageEducationalLevel.setPc(1);
            pageEducationalLevel.setPs(10);
        }
        
        String sql = "select count(1) from t_educationallevel";
        Number number = (Number)qr.query(sql, new ScalarHandler());
        int tr = number.intValue();//得到了总记录数
        pageEducationalLevel.setTr(tr);
        
        if (pageEducationalLevel.getPc() > pageEducationalLevel.getTp()) {
            pageEducationalLevel.setPc(pageEducationalLevel.getTp());
        }
        
        sql = "select loginname, email,status from t_educationallevel limit ?,?";
        
        List<EducationalLevel> beanList = qr.query(sql, new BeanListHandler<EducationalLevel>(EducationalLevel.class), (pageEducationalLevel.getPc() - 1), pageEducationalLevel.getPs());
        pageEducationalLevel.setBeanList(beanList);
        
        return pageEducationalLevel;
    }
    
    
    
}
