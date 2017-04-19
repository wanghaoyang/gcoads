package com.why.gcoads.dao.educational;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.why.gcoads.model.EducationalLevel;
import com.why.gcoads.model.PageBean;
import com.why.gcoads.utils.StringUtil;
import com.why.gcoads.utils.jdbc.TxQueryRunner;

public class EducationalLevelDao {

    private QueryRunner qr = new TxQueryRunner();

    /**
     * 校验学历是否已经存在
     * 
     * @param educationalLevel
     * @return
     * @throws SQLException
     */
    public boolean ajaxValidateEducationalLevel(EducationalLevel educationalLevel) throws SQLException {
        String sql = "select count(1) from t_educationallevel where educationalLevel=?";
        Number number = (Number) qr.query(sql, new ScalarHandler(), educationalLevel.getEducationallevel());
        return number.intValue() == 0;
    }

    /**
     * 添加学历
     * 
     * @param educationalLevel
     * @throws SQLException
     */
    public void addEducationalLevel(EducationalLevel educationalLevel) throws SQLException {
        String sql = "INSERT INTO t_educationallevel (educationallevel,) VALUES (?)";
        qr.update(sql, educationalLevel.getEducationallevel());
    }

    /**
     * 更新学历信息
     * 
     * @param educationalLevel
     * @throws SQLException
     */
    public void updateEducationalLevelByElid(EducationalLevel educationalLevel) throws SQLException {
        String sql = "UPDATE t_educationallevel set educationallevel=? where elid = ?";
        qr.update(sql, educationalLevel.getEducationallevel(), educationalLevel.getElid());
    }

    /**
     * 通过学历名字查询学历信息
     * 
     * @param educationalLevel
     * @return
     * @throws SQLException
     */
    public EducationalLevel findEducationalLevelByEducationalName(String educationalLevel) throws SQLException {
        String sql = "select * from t_educationallevel where educationalLevel=?";
        return qr.query(sql, new BeanHandler<EducationalLevel>(EducationalLevel.class), educationalLevel);
    }

    public int findEducationalLevelNumByEducationalName(String educationalLevel) throws SQLException {
        String sql = "select count(1) from t_educationallevel where educationalLevel=?";
        Number number = (Number) qr.query(sql, new ScalarHandler(), educationalLevel);

        return number.intValue();// 得到了总记录数
    }

    /**
     * 分页查询，通过学历名字模糊查询学历信息
     * 
     * @param pageEducationalLevel
     * @param educationallevel
     * @return
     * @throws SQLException
     */
    public PageBean<EducationalLevel> findEducationalLevelByPager(PageBean<EducationalLevel> pageEducationalLevel,
            String educationallevel) throws SQLException {

        if (pageEducationalLevel == null) {
            pageEducationalLevel = new PageBean<EducationalLevel>();
            pageEducationalLevel.setPc(1);
            pageEducationalLevel.setPs(10);
        }

        if (StringUtil.isNullOrEmpty(educationallevel)) {
            educationallevel = StringUtil.Empty;
        } else {
            educationallevel.replace("\\", "\\\\");
            educationallevel.replace("%", "\\%");
            educationallevel.replace("_", "\\_");
            educationallevel.replace("'", "\'");
            educationallevel.replace("\"", "\\\"");
        }
        educationallevel = "%" + educationallevel + "%";

        String sql = "select count(1) from t_educationallevel where elid != 1 and educationallevel like ?";
        Number number = (Number) qr.query(sql, new ScalarHandler(), educationallevel);
        int tr = number.intValue();// 得到了总记录数
        pageEducationalLevel.setTr(tr);
        if (tr == 0) {
            pageEducationalLevel.setPc(1);
            pageEducationalLevel.setBeanList(new ArrayList<EducationalLevel>());
            return pageEducationalLevel;
        }
        if (pageEducationalLevel.getPc() > pageEducationalLevel.getTp()) {
            pageEducationalLevel.setPc(pageEducationalLevel.getTp());
        }

        sql = "select elid, educationallevel from t_educationallevel where elid != 1 and educationallevel like ? order by elid asc limit ?,? ";

        List<EducationalLevel> beanList = qr.query(sql, new BeanListHandler<EducationalLevel>(EducationalLevel.class),
                educationallevel, (pageEducationalLevel.getPc() - 1) * pageEducationalLevel.getPs(),
                pageEducationalLevel.getPs());
        pageEducationalLevel.setBeanList(beanList);

        return pageEducationalLevel;
    }

    /**
     * 查询所有的学历信息
     * 
     * @return
     * @throws SQLException
     */
    public List<EducationalLevel> findAll() throws SQLException {

        String sql = "select elid, educationallevel from t_educationallevel order by elid asc";

        List<EducationalLevel> beanList = qr.query(sql, new BeanListHandler<EducationalLevel>(EducationalLevel.class));

        return beanList;
    }
}
