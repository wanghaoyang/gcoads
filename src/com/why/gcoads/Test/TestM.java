package com.why.gcoads.Test;

import java.sql.SQLException;

import org.junit.Test;

import com.why.gcoads.dao.user.EducationalLevelDao;
import com.why.gcoads.enums.Role;

public class TestM {

    @Test
    public void TDao() throws SQLException{
        EducationalLevelDao eld = new EducationalLevelDao();
        
        boolean b = eld.ajaxValidateEducationalLevel("大专");
        
        System.out.println(b);
    }
    
    @Test
    public void TT(){
        System.out.println(Role.管理员.toString());
    }
}
