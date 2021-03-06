package com.why.gcoads.service.user;

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;

import com.why.gcoads.commons.CommonUtils;
import com.why.gcoads.dao.user.UserDao;
import com.why.gcoads.exception.user.UserException;
import com.why.gcoads.mail.Mail;
import com.why.gcoads.mail.MailUtils;
import com.why.gcoads.model.PageBean;
import com.why.gcoads.model.Role;
import com.why.gcoads.model.User;
import com.why.gcoads.utils.jdbc.JdbcUtils;

/**
 * 用户模块业务层
 *
 */
public class UserService {
	private UserDao userDao = new UserDao();

	/**
	 * 修改密码
	 * 
	 * @param uid
	 * @param newPass
	 * @param oldPass
	 * @throws UserException
	 */
	public void updatePassword(String uid, String newPass, String oldPass)
			throws UserException {

		try {
			/*
			 * 1. 校验老密码
			 */
			boolean bool = userDao.findByUidAndPassword(uid, oldPass);
			if (!bool) {// 如果老密码错误
				throw new UserException("老密码错误！");
			}

			/*
			 * 2. 修改密码
			 */
			userDao.updatePassword(uid, newPass);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void resetPassword(String uid, User user) throws UserException {

		try {
			/*
			 * 1. 校验权限
			 */
			User u = userDao.findByLoginnameAndLoginpass(user.getLoginname(),
					user.getLoginpass());
			if (!Role.管理员.toString().equals(u.getRole())) {// 如果非管理员
				throw new UserException("你没有权限！");
			}

			/*
			 * 2. 修改密码
			 */
			userDao.updatePassword(uid, "000000");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 登录功能
	 * 
	 * @param user
	 * @return
	 */
	public User login(User user) {
		try {
			return userDao.findByLoginnameAndLoginpass(user.getLoginname(),
					user.getLoginpass());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 激活功能
	 * 
	 * @param code
	 * @throws UserException
	 */
	public void activatioin(String code) throws UserException {
		/*
		 * 1. 通过激活码查询用户 2. 如果User为null，说明是无效激活码，抛出异常，给出异常信息（无效激活码） 3.
		 * 查看用户状态是否为true，如果为true，抛出异常，给出异常信息（请不要二次激活） 4. 修改用户状态为true
		 */
		try {
			User user = userDao.findByCode(code);
			if (user == null)
				throw new UserException("无效的激活码！");
			if (user.isStatus())
				throw new UserException("您已经激活过了，不要二次激活！");
			userDao.updateStatus(user.getUid(), true);// 修改状态
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 用户名注册校验
	 * 
	 * @param loginname
	 * @return
	 */
	public boolean ajaxValidateLoginname(String loginname) {
		try {
			return userDao.ajaxValidateLoginname(loginname);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Email校验
	 * 
	 * @param email
	 * @return
	 */
	public boolean ajaxValidateEmail(String email) {
		try {
			return userDao.ajaxValidateEmail(email);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 注册功能
	 * 
	 * @param user
	 */
	public void regist(User user) {
		/*
		 * 1. 数据的补齐
		 */
		user.setUid(CommonUtils.uuid());
		user.setRole(Role.企业用户.toString());
		user.setStatus(false);
		user.setActivationCode(CommonUtils.uuid() + CommonUtils.uuid());
		user.setDeleted(false);
		/*
		 * 2. 准备发邮件
		 */
		/*
		 * 把配置文件内容加载到prop中
		 */
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader()
					.getResourceAsStream("email_template.properties"));
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		/*
		 * 登录邮件服务器，得到session
		 */
		String host = prop.getProperty("host");// 服务器主机名
		String name = prop.getProperty("username");// 登录名
		String pass = prop.getProperty("password");// 登录密码
		Session session = MailUtils.createSession(host, name, pass);

		/*
		 * 创建Mail对象
		 */
		String from = prop.getProperty("from");
		String to = user.getEmail();
		String subject = prop.getProperty("subject");
		// MessageForm.format方法会把第一个参数中的{0},使用第二个参数来替换。
		String content = MessageFormat.format(prop.getProperty("content"),
				user.getActivationCode());
		Mail mail = new Mail(from, to, subject, content);
		/*
		 * 3. 向数据库插入，发送邮件
		 */
		try {
			JdbcUtils.beginTransaction();
			userDao.add(user);
			MailUtils.send(session, mail);
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public PageBean<User> findUserByPager(PageBean<User> pageUser, String name) {
		if (pageUser == null) {
			pageUser = new PageBean<User>();
			pageUser.setPc(1);
			pageUser.setPs(10);
		}
		if (name == null) {
			name = "";
		}
		try {
			pageUser = userDao.findUserByPage(pageUser, name);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return pageUser;
	}

	public int deleteUsers(String[] uids, User user) throws UserException {

		List<String> uidList = new ArrayList<String>();

		try {
			/*
			 * 1. 校验权限
			 */
			User u = userDao.findByLoginnameAndLoginpass(user.getLoginname(),
					user.getLoginpass());
			if (!Role.管理员.toString().equals(u.getRole())) {// 如果非管理员
				throw new UserException("你没有权限！");
			}
			/*
			 * 2. 修改密码
			 */

			int row = userDao.deleteUsers(uids);
			return row;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
