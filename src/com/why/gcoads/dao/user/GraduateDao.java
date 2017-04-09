package com.why.gcoads.dao.user;

import org.apache.commons.dbutils.QueryRunner;

import com.why.gcoads.model.Graduate;
import com.why.gcoads.model.PageBean;
import com.why.gcoads.utils.jdbc.TxQueryRunner;

public class GraduateDao {

    private QueryRunner qr = new TxQueryRunner();
    
    public PageBean<Graduate> findByPage(PageBean<Graduate> pageBeanGraduate, String str){
        if (pageBeanGraduate == null) {
            pageBeanGraduate = new PageBean<Graduate>();
            pageBeanGraduate.setPc(1);
            pageBeanGraduate.setPs(10);
        }
        
        
        
        return pageBeanGraduate;
    }
}
