package com.why.gcoads.dao.student;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.why.gcoads.model.PageBean;
import com.why.gcoads.model.Student;
import com.why.gcoads.utils.StringUtil;
import com.why.gcoads.utils.jdbc.TxQueryRunner;

public class StudentDao {

    private QueryRunner qr = new TxQueryRunner();

    /**
     * 添加学生信息
     * 
     * @param student
     * @throws SQLException
     */
    public void addStudent(Student student) throws SQLException {
        String sql = "insert into t_student (kaoshenghao,shenfenzhenghao,xuehao,studentname,studentgender,minzu,zhengzhimianmao,zhuanye,zhuanyefangxiang,peiyangfangshi,xuezhi,ruxueshijian,biyeshijian,shifanshengleibie,xueyuan,xibie,banji,chushengriqi,shengyuansuozaidi,email,address) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        System.out.println(sql);
        Object[] params = { student.getKaoshenghao(), student.getShenfenzhenghao(), student.getXuehao(),
                student.getStudentname(), student.getStudentgender(), student.getMinzu(), student.getZhengzhimianmao(),
                student.getZhuanye(), student.getZhuanyefangxiang(), student.getPeiyangfangshi(), student.getXuezhi(),
                student.getRuxueshijian(), student.getBiyeshijian(), student.getShifanshengleibie(),
                student.getXueyuan(), student.getXibie(), student.getBanji(), student.getChushengriqi(),
                student.getShengyuansuozaidi(), student.getEmail(), student.getAddress() };

        qr.update(sql, params);
    }

    /**
     * 更新学生信息
     * 
     * @param student
     * @return
     * @throws SQLException
     */
    public int updateStudent(Student student) throws SQLException {
        String sql = "update t_student set kaoshenghao=?,shenfenzhenghao=?,studentname=?,studentgender=?,minzu=?,zhengzhimianmao=?,zhuanye=?,zhuanyefangxiang=?,peiyangfangshi=?,xuezhi=?,ruxueshijian=?,biyeshijian=?,shifanshengleibie=?,xueyuan=?,xibie=?,banji=?,chushengriqi=?,shengyuansuozaidi=?,email=?,address=? where xuehao=?";
        Object[] params = { student.getKaoshenghao(), student.getShenfenzhenghao(), student.getStudentname(),
                student.getStudentgender(), student.getMinzu(), student.getZhengzhimianmao(), student.getZhuanye(),
                student.getZhuanyefangxiang(), student.getPeiyangfangshi(), student.getXuezhi(),
                student.getRuxueshijian(), student.getBiyeshijian(), student.getShifanshengleibie(),
                student.getXueyuan(), student.getXibie(), student.getBanji(), student.getChushengriqi(),
                student.getShengyuansuozaidi(), student.getEmail(), student.getAddress(), student.getXuehao() };

        int row = qr.update(sql, params);
        return row;
    }

    /**
     * 通过主键查询学生信息
     * 
     * @param sid
     * @return
     * @throws SQLException
     */
    public Student findStudentBySid(int sid) throws SQLException {
        String sql = "select * from t_student where sid = ?";
        return qr.query(sql, new BeanHandler<Student>(Student.class), sid);
    }

    /**
     * 通过学号查询学生信息
     * 
     * @param xuehao
     * @return
     * @throws SQLException
     */
    public Student findStudentByXuehao(String xuehao) throws SQLException {
        String sql = "select * from t_student where xuehao = ?";
        return qr.query(sql, new BeanHandler<Student>(Student.class), xuehao);
    }

    /**
     * 通过分页查询学生信息
     * 
     * @param pageStudent
     * @param field
     * @param value
     * @return
     * @throws SQLException
     */
    public PageBean<Student> findStudentByPager(PageBean<Student> pageStudent, String field, String value)
            throws SQLException {
        String sql = "select {0} from t_student where {1} order by biyeshijian DESC, ruxueshijian DESC";

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
        case "xueyuan":
            field = " xueyuan like ? ";
            value = "%" + value + "%";
            break;
        default:
            field = " studentname like ? ";
            value = "%" + value + "%";
            break;
        }

        if (pageStudent == null) {
            pageStudent = new PageBean<Student>();
            pageStudent.setPc(1);
            pageStudent.setPs(10);
        }

        Number number = (Number) qr.query(MessageFormat.format(sql, " count(1) ", field), new ScalarHandler(), value);

        int tr = number.intValue();// 得到了总记录数
        pageStudent.setTr(tr);

        if (tr == 0) {
            pageStudent.setBeanList(new ArrayList<Student>());
            pageStudent.setPc(1);
            pageStudent.setPs(10);
            return pageStudent;
        }
        if (pageStudent.getPc() > pageStudent.getTp()) {
            pageStudent.setPc(pageStudent.getTp());
        } else if (pageStudent.getPc() < 1) {
            pageStudent.setPc(1);
        }
        sql += " limit ?,? ";

        List<Student> beanList = qr.query(MessageFormat.format(sql, " * ", field),
                new BeanListHandler<Student>(Student.class), value, (pageStudent.getPc() - 1) * pageStudent.getPs(),
                pageStudent.getPs());
        pageStudent.setBeanList(beanList);

        return pageStudent;
    }

    /**
     * 通过学号批量删除学生
     * 
     * @param xuehao
     * @return
     * @throws SQLException
     */

    public int deleteStudentByXuehao(String[] xuehao) throws SQLException {
        // TODO Auto-generated method stub
        String sql = "update t_student set deleted = 1 where xuehao in (";
        if (xuehao != null) {
            for (int i = 0; i < xuehao.length; i++) {
                sql += "?";
                if (i < xuehao.length - 1) {
                    sql += ", ";
                }
            }
        }
        sql += ")";

        int row = qr.update(sql, xuehao);
        return row;
    }

    /**
     * 通过学号查询学生身份证号
     * 
     * @param xuehaos
     * @return
     * @throws SQLException
     */
    public List<Student> findStudentIdentifyIdByXuehao(String[] xuehaos) throws SQLException {
        // TODO Auto-generated method stub
        String sql = "select shenfenzhenghao from t_student where xuehao in (";
        if (xuehaos != null) {
            for (int i = 0; i < xuehaos.length; i++) {
                sql += "?";
                if (i < xuehaos.length - 1) {
                    sql += ", ";
                }
            }
        }
        sql += ")";

        return qr.query(sql, new BeanListHandler<Student>(Student.class), xuehaos);
    }
    
    /**
     * 通过学号查询学生身份证号
     * 
     * @param xuehaos
     * @return
     * @throws SQLException
     */
    public Student findStudentIdentifyIdByXuehao(String xuehao) throws SQLException {
        // TODO Auto-generated method stub
        String sql = "select shenfenzhenghao from t_student where xuehao = ?";
        
        return qr.query(sql, new BeanHandler<Student>(Student.class), xuehao);
    }

    /**
     * 判断学号、身份证号、考生号是否存在
     * 
     * @param student
     * @return
     * @throws SQLException
     */
    public boolean isExistStudent(Student student) throws SQLException {
        // TODO Auto-generated method stub
        String sql = "select count(1) from t_student where xuehao = ? or shenfenzhenghao = ? or kaoshenghao = ?";
        Object[] params = { student.getXuehao(), student.getShenfenzhenghao(), student.getKaoshenghao() };

        Number number = (Number) qr.query(sql, new ScalarHandler(), params);
        return number.intValue() > 0;
    }

}
