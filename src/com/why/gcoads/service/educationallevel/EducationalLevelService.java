package com.why.gcoads.service.educationallevel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.why.gcoads.dao.educational.EducationalLevelDao;
import com.why.gcoads.model.EducationalLevel;
import com.why.gcoads.model.PageBean;
import com.why.gcoads.utils.StringUtil;
import com.why.gcoads.utils.jdbc.JdbcUtils;

/**
 * 学历业务层
 * 
 */
public class EducationalLevelService {

    private EducationalLevelDao educationalLevelDao = new EducationalLevelDao();

    public String addEducationalLevel(EducationalLevel educationalLevel) {
        String msg = "";

        try {
            JdbcUtils.beginTransaction();
            educationalLevel.setCreatetime(new Date());
            educationalLevelDao.addEducationalLevel(educationalLevel);
            int count = educationalLevelDao
                    .findEducationalLevelNumByEducationalName(educationalLevel.getEducationallevel());
            if (count < 2) {
                JdbcUtils.commitTransaction();
            } else {
                JdbcUtils.rollbackTransaction();
                msg = "学历信息已存在!";
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return msg;
    }

    public String updateEducationalLevelByElid(EducationalLevel educationalLevel) {

        String error = "";
        
        try {
            JdbcUtils.beginTransaction();
            educationalLevelDao.updateEducationalLevelByElid(educationalLevel);
            int count = educationalLevelDao
                    .findEducationalLevelNumByEducationalName(educationalLevel.getEducationallevel());
            if (count < 2) {
                JdbcUtils.commitTransaction();
            } else {
                JdbcUtils.rollbackTransaction();
                error = "学历信息已存在!";
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
        return error;
    }

    public PageBean<EducationalLevel> findEducationalLevelByPager(PageBean<EducationalLevel> pageEducationalLevel,
            String educationalLevel) {
        try {
            if (StringUtil.isNullOrEmpty(educationalLevel)) {
                educationalLevel = StringUtil.Empty;
            }

            pageEducationalLevel = educationalLevelDao.findEducationalLevelByPager(pageEducationalLevel,
                    educationalLevel);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
        return pageEducationalLevel;
    }

    public List<EducationalLevel> findAllEducationalLevel() {
        List<EducationalLevel> list = new ArrayList<EducationalLevel>();
        try {
            list = educationalLevelDao.findAll();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
        return list;
    }

}
