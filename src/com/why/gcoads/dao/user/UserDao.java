package com.why.gcoads.dao.user;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.why.gcoads.model.PageBean;
import com.why.gcoads.model.User;
import com.why.gcoads.utils.jdbc.TxQueryRunner;

public class UserDao {

	private QueryRunner qr = new TxQueryRunner();

	/**
	 * 按uid和password查询
	 * 
	 * @param uid
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public boolean findByUidAndPassword(String uid, String password)
			throws SQLException {
		String sql = "select count(*) from t_user where uid=? and loginpass=? and !deleted";
		Number number = (Number) qr.query(sql, new ScalarHandler(), uid,
				password);
		return number.intValue() > 0;
	}

	/**
	 * 修改密码
	 * 
	 * @param uid
	 * @param password
	 * @throws SQLException
	 */
	public void updatePassword(String uid, String password) throws SQLException {
		String sql = "update t_user set loginpass=? where uid=? and !deleted";
		qr.update(sql, password, uid);
	}

	/**
	 * 查询学生用户信息
	 * 
	 * @throws SQLException
	 */
	public User findUserByLoginnameOrUid(String filed, String value) throws SQLException {
		String sql = "select uid,loginname,role,email from t_student where {0} = ?  and !deleted";
		
		if ("uid".equals(filed)) {
			sql = MessageFormat.format(sql, filed);
		} else if ("loginname".equals(filed)) {
			sql = MessageFormat.format(sql, filed);
		}
		
		return qr.query(sql, new BeanHandler<User>(User.class), value);
	}

	/**
	 * 按用户名和密码查询
	 * 
	 * @param loginname
	 * @param loginpass
	 * @return
	 * @throws SQLException
	 */
	public User findByLoginnameAndLoginpass(String loginname, String loginpass)
			throws SQLException {
		String sql = "select * from t_user where loginname=? and loginpass=? and !deleted";
		return qr.query(sql, new BeanHandler<User>(User.class), loginname,
				loginpass);
	}

	/**
	 * 通过激活码查询用户
	 * 
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	public User findByCode(String code) throws SQLException {
		String sql = "select * from t_user where activationCode=? and !deleted";
		return qr.query(sql, new BeanHandler<User>(User.class), code);
	}

	/**
	 * 修改用户状态
	 * 
	 * @param uid
	 * @param status
	 * @throws SQLException
	 */
	public void updateStatus(String uid, boolean status) throws SQLException {
		String sql = "update t_user set status=? where uid=? and !deleted";
		qr.update(sql, status, uid);
	}

	/**
	 * 校验用户名是否注册
	 * 
	 * @param loginname
	 * @return
	 * @throws SQLException
	 */
	public boolean ajaxValidateLoginname(String loginname) throws SQLException {
		String sql = "select count(1) from t_user where loginname=? and !deleted";
		Number number = (Number) qr.query(sql, new ScalarHandler(), loginname);
		return number.intValue() == 0;
	}

	/**
	 * 校验Email是否注册
	 * 
	 * @param email
	 * @return
	 * @throws SQLException
	 */
	public boolean ajaxValidateEmail(String email) throws SQLException {
		String sql = "select count(1) from t_user where email=? and !deleted";
		Number number = (Number) qr.query(sql, new ScalarHandler(), email);
		return number.intValue() == 0;
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @throws SQLException
	 */
	public void add(User user) throws SQLException {
		String sql = "insert into t_user (uid,loginname,loginpass,email,role,status,activationCode,deleted) values(?,?,?,?,?,?,?,?)";
		Object[] params = { user.getUid(), user.getLoginname(),
				user.getLoginpass(), user.getEmail(), user.getRole(),
				user.isStatus(), user.getActivationCode(), false };
		qr.update(sql, params);
	}

	/**
	 * 查询用户
	 * 
	 * @param pageUser
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public PageBean<User> findUserByPage(PageBean<User> pageUser, String name)
			throws SQLException {
		if (pageUser == null) {
			pageUser = new PageBean<User>();
			pageUser.setPc(1);
			pageUser.setPs(10);
		}
		if (name == null) {
			name = "";
		} else {
			name.replace("\\", "\\\\");
			name.replace("%", "\\%");
			name.replace("_", "\\_");
			name.replace("'", "\'");
			name.replace("\"", "\\\"");
		}
		String sql = "select count(1) from t_user where !deleted and loginname like ?";
		Number number = (Number) qr.query(sql, new ScalarHandler(), "%" + name
				+ "%");
		int tr = number.intValue();// 得到了总记录数
		pageUser.setTr(tr);

		if (pageUser.getPc() > pageUser.getTp()) {
			pageUser.setPc(pageUser.getTp());
		}

		sql = "select uid, loginname, email, role, status from t_user where !deleted and loginname like ? order by num asc limit ?,?";

		List<User> beanList = qr.query(sql, new BeanListHandler<User>(
				User.class), "%" + name + "%", (pageUser.getPc() - 1)
				* pageUser.getPs(), pageUser.getPs());
		pageUser.setBeanList(beanList);

		return pageUser;
	}

	/**
	 * 设置用户账户处于被删除状态
	 * 
	 * @param uids
	 * @return
	 * @throws SQLException
	 */
	
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

	/**
	 * 批量彻底删除用户
	 * 
	 * @param Loginnames
	 * @return
	 * @throws SQLException
	 */
	public int deleteSudentUserByLoginname(String[] Loginnames) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "update t_user set deleted='1' where loginname in (";
		if (Loginnames != null) {
			for (int i = 0; i < Loginnames.length; i++) {
				sql += "?";
				if (i < Loginnames.length - 1) {
					sql += ", ";
				}
			}
		}
		sql += ")";

		int row = qr.update(sql, Loginnames);
		return row;
	}

}
