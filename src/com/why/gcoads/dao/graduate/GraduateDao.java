package com.why.gcoads.dao.graduate;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import com.why.gcoads.model.EducationalLevel;
import com.why.gcoads.model.Graduate;
import com.why.gcoads.model.PageBean;
import com.why.gcoads.model.Student;
import com.why.gcoads.model.User;
import com.why.gcoads.utils.jdbc.TxQueryRunner;

public class GraduateDao {

	private QueryRunner qr = new TxQueryRunner();

	/**
	 * 添加学生信息
	 * 
	 * @param user
	 * @throws SQLException
	 */
	public void addStudent(Student stu) throws SQLException {
		String sql = "insert into t_student values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = { stu.getSid(), stu.getKaoshenghao(),
				stu.getShenfenzhenghao(), stu.getXuehao(),
				stu.getStudentname(), stu.getStudentgender(), stu.getMinzu(),
				stu.getZhengzhimianmao(), stu.getZhuanye(),
				stu.getZhuanyefangxiang(), stu.getPeiyangfangshi(),
				stu.getXuezhi(), stu.getRuxueshijian(), stu.getBiyeshijian(),
				stu.isIsshifansheng(), stu.getXueyuan(), stu.getXibie(),
				stu.getBanji(), stu.getChushengriqi(),
				stu.getShengyuansuozaidi(), stu.getMail(), stu.getAddress() };

		qr.update(sql, params);
	}

	/**
	 * 添加毕业生信息
	 * 
	 * @param user
	 * @throws SQLException
	 */
	public void addGraduate(Graduate gra) throws SQLException {
		String sql = "insert into t_graduate values(?,?,?,?,?,?,?,?,?)";
		Object[] params = { gra.getGid(), gra.getStudentname(),
				gra.getStudentgender(), gra.getBiyeshijian(), gra.getXueli(),
				gra.getXueyuan(), gra.getXibie(), gra.getBanji(),
				gra.getGstatus() };

		qr.update(sql, params);
	}

	@Test
	public void T() {
		PageBean<Student> pageStudent = null;
		String field = "学号";
		String value = " ";
		try {
			PageBean<Student> findStudentByPage = new GraduateDao()
					.findStudentByPage(pageStudent, field, value);
			System.out.println(findStudentByPage.getBeanList());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 查询用户
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

	public int deleteUsers(String[] uids) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "update t_user set deleted='1' where uid in (";
		if (uids != null) {
			for (int i = 0; i < uids.length; i++) {
				sql += "?";
				if (i < uids.length - 1) {
					sql += ", ";
				}
			}
		}
		sql += ")";

		int row = qr.update(sql, uids);
		return row;
	}

}
