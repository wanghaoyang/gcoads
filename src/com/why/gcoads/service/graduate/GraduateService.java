package com.why.gcoads.service.graduate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.why.gcoads.commons.CommonUtils;
import com.why.gcoads.commons.ReadExcelUtils;
import com.why.gcoads.commons.VerifyDateFormat;
import com.why.gcoads.dao.educational.EducationalLevelDao;
import com.why.gcoads.dao.graduate.GraduateDao;
import com.why.gcoads.dao.student.StudentDao;
import com.why.gcoads.dao.user.UserDao;
import com.why.gcoads.model.EducationalLevel;
import com.why.gcoads.model.Graduate;
import com.why.gcoads.model.PageBean;
import com.why.gcoads.model.Role;
import com.why.gcoads.model.Student;
import com.why.gcoads.model.User;
import com.why.gcoads.utils.StringUtil;
import com.why.gcoads.utils.jdbc.JdbcUtils;

/**
 * 学生业务层
 *
 */
public class GraduateService {
	private GraduateDao graduateDao = new GraduateDao();
	private StudentDao studentDao = new StudentDao();
	private UserDao userDao = new UserDao();
	private EducationalLevelDao educationalLevelDao = new EducationalLevelDao();

	/**
	 * 通过excel导入学生
	 * 
	 * @param filePath
	 * @return
	 */
	public Map<String, List<Integer>> addGraduateInfoByExcel(String filePath) {
		if (StringUtil.isNullOrEmpty(filePath)) {
			return null;
		}
		// filePath = "D:/cloud/Qsync/gcoads/conf/student.xls";
		// filePath = "D:/student.xls";
		Map<String, List<Integer>> errorMap = new HashMap<String, List<Integer>>();
		List<Integer> errorRows = new ArrayList<Integer>();
		Map<Integer, Map<String, Object>> map = ReadExcelUtils
				.parseExcel(filePath);
		int i = 1;
		for (; i <= map.size(); i++) {
			Map<String, Object> beanMap = map.get(i);

			Student student;
			Graduate graduate;
			User user;
			EducationalLevel educationalLevel;
			try {
				for (Entry<String, Object> entry : beanMap.entrySet()) {
					if (StringUtil.isNullOrEmpty(entry.getValue())) {
						errorRows.add(i);
					} else if ("shenfenzhenghao".equals(entry.getKey())
							&& entry.getValue().toString().length() != 18) {
						errorRows.add(i);
					} else if ("email".equals(entry.getKey())
							&& !entry
									.getValue()
									.toString()
									.matches(
											"^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
						errorRows.add(i);
					}
				}
				if (errorRows.size() == 0) {
					Object obj = beanMap.get("shenfenzhenghao");
					String birthDay = obj.toString().substring(6, 14);
					beanMap.put("chushengriqi", birthDay);
					educationalLevel = educationalLevelDao
							.findEducationalLevelByEducationalName(beanMap.get(
									"xueli").toString());
					beanMap.put("xueli", educationalLevel);
					student = CommonUtils.toBean(beanMap, Student.class);
					graduate = CommonUtils.toBean(beanMap, Graduate.class);
					user = CommonUtils.toBean(beanMap, User.class);
					user.setLoginname(student.getShenfenzhenghao());
					user.setLoginpass(student.getShenfenzhenghao().substring(
							student.getShenfenzhenghao().length() - 6));
					user.setUid(CommonUtils.uuid());
					user.setActivationCode(CommonUtils.uuid()
							+ CommonUtils.uuid());
					user.setStatus(true);
					user.setRole(Role.毕业生.toString());
					if (!studentDao.isExistStudent(student)) {
						JdbcUtils.beginTransaction();
						if (StringUtil.isNullOrEmpty(graduate.getXueli())) {
							graduate.setGstatus("未审核");
							graduate.setElid(1);
						} else {
							graduate.setGstatus("毕业");
							graduate.setElid(graduate.getXueli().getElid());
						}
						studentDao.updateStudent(student);
						graduateDao.addGraduate(graduate);
						userDao.add(user);
						JdbcUtils.commitTransaction();
					} else {
						errorRows.add(i);
						errorMap.put("excelErrorRowsOfStudentExisted",
								errorRows);
					}
				} else {
					errorRows.add(i);
					errorMap.put("excelErrorRowsOfDateFormatError", errorRows);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
		}
		return errorMap;
	}

	/**
	 * 通过表单添加学生
	 * @param beanMap
	 * @return
	 */
	public Map<String, String> addGraduateInfoByForm(Map<String, Object> beanMap) {
		Map<String, String> errorMap = new HashMap<String, String>();
		try {
			verifyGraduateInfoInForm(beanMap, errorMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return errorMap;
	}

	private void verifyGraduateInfoInForm(Map<String, Object> beanMap,
			Map<String, String> errorMap) throws Exception {
		Student student;
		Graduate graduate;
		User user;
		EducationalLevel educationalLevel;
		for (Entry<String, Object> entry : beanMap.entrySet()) {

		}
		Object obj = beanMap.get("shenfenzhenghao");
		String birthDay = obj.toString().substring(6, 14);
		beanMap.put("chushengriqi", birthDay);
		educationalLevel = CommonUtils.toBean(beanMap, EducationalLevel.class);
		educationalLevel = educationalLevelDao
				.findEducationalLevelByEducationalName(educationalLevel
						.getEducationalLevel());
		beanMap.put("xueli", educationalLevel);
		student = CommonUtils.toBean(beanMap, Student.class);

		VerifyDateFormat.VerifyStudentDateFormat(errorMap, student);
		if (errorMap.size() == 0) {
			graduate = CommonUtils.toBean(beanMap, Graduate.class);
			user = CommonUtils.toBean(beanMap, User.class);
			user.setLoginname(student.getShenfenzhenghao());
			user.setLoginpass(student.getShenfenzhenghao().substring(
					student.getShenfenzhenghao().length() - 6));
			user.setUid(CommonUtils.uuid());
			user.setActivationCode(CommonUtils.uuid() + CommonUtils.uuid());
			user.setStatus(true);
			user.setRole(Role.毕业生.toString());
			if (!studentDao.isExistStudent(student)) {
				JdbcUtils.beginTransaction();
				if (StringUtil.isNullOrEmpty(graduate.getXueli())) {
					graduate.setGstatus("未审核");
					graduate.setElid(1);
				} else {
					graduate.setGstatus("毕业");
					graduate.setElid(graduate.getXueli().getElid());
				}
				studentDao.addStudent(student);
				graduateDao.addGraduate(graduate);
				userDao.add(user);
				JdbcUtils.commitTransaction();
			}
		}
	}

	public Map<String, String> updateStudentByXuehao(Student student) {
		Map<String, String> errorMap = new HashMap<String, String>();

		VerifyDateFormat.VerifyStudentDateFormat(errorMap, student);

		if (errorMap.size() == 0) {
			try {
				if (studentDao.isExistStudent(student)) {
					JdbcUtils.beginTransaction();

					int row = studentDao.updateStudent(student);
					if (row > 0) {
						Graduate graduate = new Graduate(student);
						graduate.setGid(graduateDao.findGraduateByXuehao(
								student.getXuehao()).getGid());
						graduateDao.updateGraduateBasicInfo(graduate);
					}
					JdbcUtils.commitTransaction();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
		}

		return errorMap;
	}

	public Map<String, String> updateGraduateByXuehao(Graduate graduate) {
		Map<String, String> errorMap = new HashMap<String, String>();

		if (errorMap.size() == 0) {
			try {
				EducationalLevel educationalLevel = educationalLevelDao
						.findEducationalLevelByEducationalName(graduate
								.getXueli().getEducationalLevel());
				graduate.setXueli(educationalLevel);
				graduateDao.updateGraduateInfoByXuehao(graduate);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
		}

		return errorMap;
	}

	public int deleteStudentByXuehaos(String[] xuehaos) {

		int row = 0;
		try {
			List<Student> students = studentDao
					.findStudentIdentifyIdByXuehao(xuehaos);
			if (students != null && students.size() > 0) {
				List<String> loginnameList = new ArrayList<String>();
				for (Student student : students) {
					loginnameList.add(student.getShenfenzhenghao());
				}
				JdbcUtils.beginTransaction();
				row = studentDao.deleteStudentByXuehao(xuehaos);
				graduateDao.deleteGraduateByXuehao(xuehaos);
				userDao.deleteSudentUserByLoginname((String[]) loginnameList
						.toArray());
				JdbcUtils.commitTransaction();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return row;
	}

	public PageBean<Student> findStudentByPager(PageBean<Student> pageStudent,
			String field, String value) {
		try {
			if (!StringUtil.isNullOrEmpty(field)) {
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
			pageStudent = studentDao.findStudentByPager(pageStudent, field,
					value);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return pageStudent;
	}

	public PageBean<Graduate> findGraduateByPager(
			PageBean<Graduate> pageGraduate, String field, String value) {
		try {
			if (!StringUtil.isNullOrEmpty(field)) {
				switch (field) {
				case "学号":
					field = " xuehao = ? ";
					break;
				case "届毕业生":
					field = " year(biyeshijian) = ? ";
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
			pageGraduate = graduateDao.findGraduateByPager(pageGraduate, field,
					value);
			List<EducationalLevel> educationalLevelList = educationalLevelDao
					.findAll();

			if (pageGraduate.getBeanList() != null) {
				for (Graduate graduate : pageGraduate.getBeanList()) {
					for (EducationalLevel educationalLevel : educationalLevelList) {
						if (graduate.getElid() == educationalLevel.getElid()) {
							graduate.setXueli(educationalLevel);
							break;
						}
					}
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return pageGraduate;
	}

	@Test
	public void T() {
		GraduateService graduateService = new GraduateService();
		graduateService.addGraduateInfoByExcel("");
		String str = "123123111101010000";
		System.out.println(str.substring(str.length() - 6));
	}
}
