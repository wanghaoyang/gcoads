package com.why.gcoads.service.educationallevel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.why.gcoads.commons.CommonUtils;
import com.why.gcoads.commons.ReadExcelUtils;
import com.why.gcoads.commons.VerifyDateFormat;
import com.why.gcoads.dao.educational.EducationalLevelDao;
import com.why.gcoads.model.EducationalLevel;
import com.why.gcoads.model.Graduate;
import com.why.gcoads.model.PageBean;
import com.why.gcoads.model.Role;
import com.why.gcoads.model.Student;
import com.why.gcoads.model.User;
import com.why.gcoads.utils.StringUtil;
import com.why.gcoads.utils.jdbc.JdbcUtils;

/**
 * 学历业务层
 * 
 */
public class EducationalLevelService {

    private EducationalLevelDao educationalLevelDao = new EducationalLevelDao();

    public Map<String, String> addGraduateInfoByExcel(
            EducationalLevel educationalLevel) {
        Map<String, String> errorMap = new HashMap<String, String>();
        if (StringUtil.isNullOrEmpty(educationalLevel.getEducationalLevel())) {
            errorMap.put("xueli", "学历不能为空!");
            return errorMap;
        }

        try {
            if (educationalLevelDao
                    .ajaxValidateEducationalLevel(educationalLevel)) {
                educationalLevelDao.addEducationalLevel(educationalLevel);
            } else {
                errorMap.put("xueli", "学历信息已存在!");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return errorMap;
    }

    public Map<String, String> updateEducationalLevelByElid(
            EducationalLevel educationalLevel) {
        Map<String, String> errorMap = new HashMap<String, String>();
        if (StringUtil.isNullOrEmpty(educationalLevel.getEducationalLevel())) {
            errorMap.put("xueli", "学历不能为空!");
            return errorMap;
        }

        try {
            if (educationalLevelDao
                    .ajaxValidateEducationalLevel(educationalLevel)) {
                educationalLevelDao
                        .updateEducationalLevelByElid(educationalLevel);
            } else {
                errorMap.put("xueli", "学历信息已存在!");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }

        return errorMap;
    }

    public PageBean<EducationalLevel> findEducationalLevelByPager(
            PageBean<EducationalLevel> pageEducationalLevel,
            String educationalLevel) {
        try {
            if (!StringUtil.isNullOrEmpty(educationalLevel)) {

                pageEducationalLevel = educationalLevelDao
                        .find(pageEducationalLevel, educationalLevel);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
        return pageEducationalLevel;
    }

}
