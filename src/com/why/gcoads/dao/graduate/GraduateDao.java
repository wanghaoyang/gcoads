package com.why.gcoads.dao.graduate;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.why.gcoads.model.Graduate;
import com.why.gcoads.model.PageBean;
import com.why.gcoads.utils.StringUtil;
import com.why.gcoads.utils.jdbc.TxQueryRunner;

public class GraduateDao {

    private QueryRunner qr = new TxQueryRunner();

    /**
     * 添加毕业生信息
     * 
     * @param graduate
     * @throws SQLException
     */
    public void addGraduate(Graduate graduate) throws SQLException {

        String sql = "insert into t_graduate(xuehao,studentname,studentgender,ruxueshijian,biyeshijian,xueyuan,xibie,zhuanye,Graduatecertificatenum,gstatus,elid,province,city,deleted) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] params = { graduate.getXuehao(), graduate.getStudentname(), graduate.getStudentgender(),
                graduate.getRuxueshijian(), graduate.getBiyeshijian(), graduate.getXueyuan(), graduate.getXibie(),
                graduate.getZhuanye(), graduate.getGraduatecertificatenum(), graduate.getGstatus(),
                graduate.getElid(),graduate.getProvince(),graduate.getCity(),false };

        qr.update(sql, params);
    }

    /**
     * 更新毕业生信息
     * 
     * @param graduate
     * @throws SQLException
     */
    public void updateGraduateInfoByXuehao(Graduate graduate) throws SQLException {

        String sql = "update t_graduate set xueyuan=?,xibie=?,zhuanye=?, biyeshijian=?, graduatecertificatenum = ?, gstatus = ?, elid = ?, province=?,city=? where xuehao = ? and !deleted";
        Object[] params = { graduate.getXueyuan(),graduate.getXibie(),graduate.getZhuanye(),graduate.getBiyeshijian(), graduate.getGraduatecertificatenum(), graduate.getGstatus(),
                graduate.getXueli().getElid(), graduate.getProvince(), graduate.getCity(), graduate.getXuehao() };

        qr.update(sql, params);
        sql = "update t_student set xueyuan=?,xibie=?,zhuanye=?,biyeshijian = ? where xuehao = ? and !deleted";
        qr.update(sql, graduate.getXueyuan(),graduate.getXibie(),graduate.getZhuanye(),graduate.getBiyeshijian(),graduate.getXuehao());
    }

    /**
     * 更新毕业生基本信息
     * 
     * @param graduate
     * @throws SQLException
     */
    public void updateGraduateBasicInfo(Graduate graduate) throws SQLException {
        String sql = "update t_graduate set xuehao=?, studentname = ?,studentgender = ?,ruxueshijian=?,biyeshijian=?,xueyuan= ?, xibie= ?, zhuanye= ? where !deleted and gid = ?";
        Object[] params = { graduate.getXuehao(), graduate.getStudentname(), graduate.getStudentgender(),
                graduate.getRuxueshijian(),graduate.getBiyeshijian(), graduate.getXueyuan(), graduate.getXibie(), graduate.getZhuanye(),
                graduate.getGid() };

        qr.update(sql, params);
    }

    /**
     * 通过学号查询毕业生信息
     * 
     * @param xuehao
     * @throws SQLException
     */
    public Graduate findGraduateByXuehao(String xuehao) throws SQLException {

        String sql = "select * from t_graduate where !deleted and xuehao = ?";

        return qr.query(sql, new BeanHandler<Graduate>(Graduate.class), xuehao);
    }

    /**
     * 查询毕业生
     * 
     * @param pageGraduate
     * @param field
     * @param value
     * @return
     * @throws SQLException
     */
    public PageBean<Graduate> findGraduateByPager(PageBean<Graduate> pageGraduate, String field, String value)
            throws SQLException {
        String sql = "select {0} from t_graduate where !deleted and {1} order by biyeshijian DESC, ruxueshijian DESC";

        if (value == null) {
            value = StringUtil.Empty;
        } else {
            value.replace("\\", "\\\\");
            value.replace("%", "\\%");
            value.replace("_", "\\_");
            value.replace("'", "\'");
            value.replace("\"", "\\\"");
        }

        switch (field.trim()) {
        case "xuehao":
            field = " xuehao = ? ";
            break;
        case "biyeshijian":
            field = " year(biyeshijian) = ? ";
            break;
        default:
            field = " studentname like ? ";
            value = "%" + value + "%";
            break;
        }

        if (pageGraduate == null) {
            pageGraduate = new PageBean<Graduate>();
            pageGraduate.setPc(1);
            pageGraduate.setPs(10);
        }

        Number number = (Number) qr.query(MessageFormat.format(sql, " count(1) ", field), new ScalarHandler(), value);

        int tr = number.intValue();// 得到了总记录数
        pageGraduate.setTr(tr);

        if (tr == 0) {
            pageGraduate.setBeanList(new ArrayList<Graduate>());
            pageGraduate.setPc(1);
            pageGraduate.setPs(10);
            return pageGraduate;
        }

        if (pageGraduate.getPc() > pageGraduate.getTp()) {
            pageGraduate.setPc(pageGraduate.getTp());
        } else if (pageGraduate.getPc() < 1) {
            pageGraduate.setPc(1);
        }
        sql += " limit ?,? ";

        List<Graduate> beanList = qr.query(MessageFormat.format(sql, " * ", field),
                new BeanListHandler<Graduate>(Graduate.class), value, (pageGraduate.getPc() - 1) * pageGraduate.getPs(),
                pageGraduate.getPs());
        pageGraduate.setBeanList(beanList);

        return pageGraduate;
    }

    /**
     * 通过学号批量删除毕业生
     * 
     * @param xuehaos
     * @return
     * @throws SQLException
     */
    public int deleteGraduateByXuehao(String[] xuehaos) throws SQLException {
        // TODO Auto-generated method stub
        String sql = "update t_graduate set deleted = 1 where xuehao in (";
        if (xuehaos != null) {
            for (int i = 0; i < xuehaos.length; i++) {
                sql += "?";
                if (i < xuehaos.length - 1) {
                    sql += ", ";
                }
            }
        }
        sql += ")";

        int row = qr.update(sql, xuehaos);
        return row;
    }

}
