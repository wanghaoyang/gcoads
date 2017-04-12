package com.why.gcoads.dao.graduate;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.why.gcoads.model.Graduate;
import com.why.gcoads.model.PageBean;
import com.why.gcoads.model.User;
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

		String sql = "insert into t_graduate(xuehao,studentname,studentgender,biyeshijian,xueyuan,xibie,banji,gstatus,elid) values(?,?,?,?,?,?,?,?,?)";
		Object[] params = { graduate.getXuehao(), graduate.getStudentname(),
				graduate.getStudentgender(), graduate.getBiyeshijian(),
				graduate.getXueyuan(), graduate.getXibie(),
				graduate.getBanji(), graduate.getGstatus(), graduate.getElid() };

		qr.update(sql, params);
	}

	/**
	 * 更新毕业生信息
	 * 
	 * @param graduate
	 * @throws SQLException
	 */
	public void updateGraduateInfoByXuehao(Graduate graduate)
			throws SQLException {

		String sql = "update t_graduate set gstatus = ?, elid = ? where xuehao = ?";
		Object[] params = { graduate.getGstatus(),
				graduate.getXueli().getElid(), graduate.getXuehao() };

		qr.update(sql, params);
	}

	/**
	 * 更新毕业生基本信息
	 * 
	 * @param graduate
	 * @throws SQLException
	 */
	public void updateGraduateBasicInfo(Graduate graduate) throws SQLException {

		String sql = "update t_graduateduate set studentname = ?,studentgender = ?,biyeshijian = ?,xueyuan= ?, xibie= ?, banji= ? where gid = ?";
		Object[] params = { graduate.getStudentname(),
				graduate.getStudentgender(), graduate.getBiyeshijian(),
				graduate.getXueli(), graduate.getXueyuan(),
				graduate.getXibie(), graduate.getBanji() };

		qr.update(sql, params);
	}

	/**
	 * 通过学学好查询毕业生信息
	 * 
	 * @param xuehao
	 * @throws SQLException
	 */
	public Graduate findGraduateByXuehao(String xuehao) throws SQLException {

		String sql = "select * from t_graduate where xuehao = ?";

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
	public PageBean<Graduate> findGraduateByPager(
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

	/**
	 * 通过学号批量删除毕业生
	 * @param xuehaos
	 * @return
	 * @throws SQLException
	 */
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
