package com.why.gcoads.dao.student;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.why.gcoads.model.PageBean;
import com.why.gcoads.model.Student;
import com.why.gcoads.utils.jdbc.TxQueryRunner;

public class StudentDao {

	private QueryRunner qr = new TxQueryRunner();

	/**
	 * 添加学生信息
	 * 
	 * @param user
	 * @throws SQLException
	 */
	public void addStudent(Student stu) throws SQLException {
		String sql = "insert into t_student (kaoshenghao,shenfenzhenghao,xuehao,studentname,studentgender,minzu,zhengzhimianmao,zhuanye,zhuanyefangxiang,peiyangfangshi,xuezhi,ruxueshijian,biyeshijian,isshifansheng,xueyuan,xibie,banji,chushengriqi,shengyuansuozaidi,mail,address) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = { stu.getKaoshenghao(), stu.getShenfenzhenghao(),
				stu.getXuehao(), stu.getStudentname(), stu.getStudentgender(),
				stu.getMinzu(), stu.getZhengzhimianmao(), stu.getZhuanye(),
				stu.getZhuanyefangxiang(), stu.getPeiyangfangshi(),
				stu.getXuezhi(), stu.getRuxueshijian(), stu.getBiyeshijian(),
				stu.isIsshifansheng(), stu.getXueyuan(), stu.getXibie(),
				stu.getBanji(), stu.getChushengriqi(),
				stu.getShengyuansuozaidi(), stu.getMail(), stu.getAddress() };

		qr.update(sql, params);
	}

	/**
	 * 更新学生信息
	 * 
	 * @param user
	 * @throws SQLException
	 */
	public void updateStudent(Student stu) throws SQLException {
		String sql = "update t_student set kaoshenghao=?,shenfenzhenghao=?,studentname=?,studentgender=?,minzu=?,zhengzhimianmao=?,zhuanye=?,zhuanyefangxiang=?,peiyangfangshi=?,xuezhi=?,ruxueshijian=?,biyeshijian=?,isshifansheng=?,xueyuan=?,xibie=?,banji=?,chushengriqi=?,shengyuansuozaidi=?,mail=?,address=? where xuehao=?";
		Object[] params = { stu.getKaoshenghao(), stu.getShenfenzhenghao(),
				stu.getStudentname(), stu.getStudentgender(), stu.getMinzu(),
				stu.getZhengzhimianmao(), stu.getZhuanye(),
				stu.getZhuanyefangxiang(), stu.getPeiyangfangshi(),
				stu.getXuezhi(), stu.getRuxueshijian(), stu.getBiyeshijian(),
				stu.isIsshifansheng(), stu.getXueyuan(), stu.getXibie(),
				stu.getBanji(), stu.getChushengriqi(),
				stu.getShengyuansuozaidi(), stu.getMail(), stu.getAddress(),
				stu.getXuehao() };

		qr.update(sql, params);
	}

	/**
	 * 查询学生信息
	 * 
	 * @throws SQLException
	 */
	public PageBean<Student> findStudentByPage(PageBean<Student> pageStudent,
			String field, String value) throws SQLException {
		String sql = "select {0} from t_student where {1}";

		if (pageStudent == null) {
			pageStudent = new PageBean<Student>();
			pageStudent.setPc(1);
			pageStudent.setPs(10);
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

		System.out.println(MessageFormat.format(sql, " count(1) ", field));

		Number number = (Number) qr.query(
				MessageFormat.format(sql, " count(1) ", field),
				new ScalarHandler(), value);

		int tr = number.intValue();// 得到了总记录数
		pageStudent.setTr(tr);

		if (pageStudent.getPc() > pageStudent.getTp()) {
			pageStudent.setPc(pageStudent.getTp());
		} else if (pageStudent.getPc() < 1) {
			pageStudent.setPc(1);
		}
		sql += " limit ?,? ";
		System.out.println(MessageFormat.format(sql, " * ", field));
		List<Student> beanList = qr.query(
				MessageFormat.format(sql, " * ", field),
				new BeanListHandler<Student>(Student.class), value,
				(pageStudent.getPc() - 1) * pageStudent.getPs(),
				pageStudent.getPs());
		pageStudent.setBeanList(beanList);

		return pageStudent;
	}

	public int deleteStudentByXuehao(String[] sids) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "delete from t_student where xuehao in (";
		if (sids != null) {
			for (int i = 0; i < sids.length; i++) {
				sql += "?";
				if (i < sids.length - 1) {
					sql += ", ";
				}
			}
		}
		sql += ")";

		int row = qr.update(sql, sids);
		return row;
	}

	public List<Student> findStudentIdentifyIdByXuehao(String[] xuehaos)
			throws SQLException {
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

		return qr.query(sql, new BeanListHandler<Student>(Student.class),
				xuehaos);
	}

}
