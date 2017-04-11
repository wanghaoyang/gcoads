package com.why.gcoads.dao.graduate;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.why.gcoads.model.Graduate;
import com.why.gcoads.model.PageBean;
import com.why.gcoads.utils.jdbc.TxQueryRunner;

public class GraduateDao {

    private QueryRunner qr = new TxQueryRunner();

    /**
     * 添加毕业生信息
     * 
     * @param user
     * @throws SQLException
     */
    public void addGraduate(Graduate gra) throws SQLException {

        String sql = "insert into t_graduate(xuehao,studentname,studentgender,biyeshijian,xueyuan,xibie,banji,gstatus,elid) values(?,?,?,?,?,?,?,?,?)";
        Object[] params = { gra.getXuehao(), gra.getStudentname(),
                gra.getStudentgender(), gra.getBiyeshijian(), gra.getXueli(),
                gra.getXueyuan(), gra.getXibie(), gra.getBanji(),
                gra.getGstatus() };

        qr.update(sql, params);
    }

    /**
     * 更新毕业生信息
     * 
     * @param user
     * @throws SQLException
     */
    public void updateGraduate(Graduate gra) throws SQLException {

        String sql = "update t_graduate set studentname = ?,studentgender = ?,biyeshijian = ?,xueyuan= ?, xibie= ?, banji= ?, gstatus = ?, elid = ? where xuehao = ?";
        Object[] params = { gra.getStudentname(), gra.getStudentgender(),
                gra.getBiyeshijian(), gra.getXueli(), gra.getXueyuan(),
                gra.getXibie(), gra.getBanji(), gra.getGstatus(),
                gra.getXuehao() };

        qr.update(sql, params);
    }

    /**
     * 查询毕业生
     * 
     * @throws SQLException
     */
    public PageBean<Graduate> findGraduateByPage(
            PageBean<Graduate> pageGraduate, String field, String value)
            throws SQLException {
        String sql = "select {0} from t_graduate where {1}";

        if (pageGraduate == null) {
            pageGraduate = new PageBean<Graduate>();
            pageGraduate.setPc(1);
            pageGraduate.setPs(10);
        }

        if (value == null) {
            value = " ";
        } else {
            value.replace("\\", "\\\\");
            value.replace("%", "\\%");
            value.replace("_", "\\_");
            value.replace("'", "\'");
            value.replace("\"", "\\\"");
        }

        if (field != null) {
            switch (field) {
            case "学号":
                field = " xuehao = ? ";
                break;
            case "身份证号":
                field = " shenfenzhenghao = ? ";
                break;
            case "姓名":
                field = " studentname like ? ";
                value = "%" + value + "%";
                break;
            default:
                field = " sid = ? ";
                value = "";
                break;
            }
        } else {
            field = " ? ";
            value = " 1 = 1 ";
        }

        Number number = (Number) qr.query(
                MessageFormat.format(sql, " count(1) ", field),
                new ScalarHandler(), value);

        int tr = number.intValue();// 得到了总记录数
        pageGraduate.setTr(tr);

        if (pageGraduate.getPc() > pageGraduate.getTp()) {
            pageGraduate.setPc(pageGraduate.getTp());
        } else if (pageGraduate.getPc() < 1) {
            pageGraduate.setPc(1);
        }
        sql += " limit ?,? ";
        List<Graduate> beanList = qr.query(
                MessageFormat.format(sql, " * ", field),
                new BeanListHandler<Graduate>(Graduate.class), value,
                (pageGraduate.getPc() - 1) * pageGraduate.getPs(),
                pageGraduate.getPs());
        pageGraduate.setBeanList(beanList);

        return pageGraduate;
    }

    public int deleteGraduateByXuehao(String[] xuehaos) throws SQLException {
        // TODO Auto-generated method stub
        String sql = "delete from t_graduate where xuehao in (";
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
