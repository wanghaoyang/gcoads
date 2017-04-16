package com.why.gcoads.service.graduate;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
        Map<Integer, Map<String, Object>> map = ReadExcelUtils.parseExcel(filePath);
        int i = 1;
        for (; i <= map.size(); i++) {
            Map<String, Object> beanMap = map.get(i);

            Student student;
            Graduate graduate;
            User user;
            EducationalLevel educationalLevel;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            try {
                for (Entry<String, Object> entry : beanMap.entrySet()) {
                    if (StringUtil.isNullOrEmpty(entry.getValue())) {
                        errorRows.add(i);
                    } else if ("shenfenzhenghao".equals(entry.getKey()) && entry.getValue().toString().length() != 18) {
                        errorRows.add(i);
                    } else if ("email".equals(entry.getKey()) && !entry.getValue().toString()
                            .matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
                        errorRows.add(i);
                    } else if ("ruxueshijian".equals(entry.getKey())){
                        if(entry.getValue() == null || StringUtil.isNullOrEmpty(entry.getValue().toString())){
                            beanMap.remove("ruxueshijian");
                        } else{
                            try {
                            Date date = sdf.parse(entry.getValue().toString());
                            beanMap.put("ruxueshijian", date);
                            } catch (ParseException e) {
                                beanMap.remove("ruxueshijian");
                            }
                        }
                    }else if ("biyeshijian".equals(entry.getKey())){
                        if(entry.getValue() == null || StringUtil.isNullOrEmpty(entry.getValue().toString())){
                            beanMap.remove("biyeshijian");
                        } else{
                            try {
                            Date date = sdf.parse(entry.getValue().toString());
                            beanMap.put("biyeshijian", date);
                            } catch (ParseException e) {
                                beanMap.remove("biyeshijian");
                            }
                        }
                    }
                }
                if (errorRows.size() == 0) {
                    Object obj = beanMap.get("shenfenzhenghao");
                    String birthDay = obj.toString().substring(6, 14);
                    try {
                        Date date = sdf.parse(birthDay);
                        beanMap.put("chushengriqi", date);
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        birthDay = "2000-01-01";
                        beanMap.put("chushengriqi", birthDay);
                    }
                    educationalLevel = educationalLevelDao
                            .findEducationalLevelByEducationalName(beanMap.get("xueli").toString());
                    beanMap.put("xueli", educationalLevel);
                    student = CommonUtils.toBean(beanMap, Student.class);
                    graduate = CommonUtils.toBean(beanMap, Graduate.class);
                    user = CommonUtils.toBean(beanMap, User.class);
                    user.setLoginname(student.getShenfenzhenghao());
                    user.setLoginpass(
                            student.getShenfenzhenghao().substring(student.getShenfenzhenghao().length() - 6));
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
                    } else {
                        errorRows.add(i);
                        errorMap.put("excelErrorRowsOfStudentExisted", errorRows);
                    }
                } else {
                    errorRows.add(i);
                    errorMap.put("excelErrorRowsOfDateFormatError", errorRows);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                try {
                    JdbcUtils.rollbackTransaction();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                throw new RuntimeException(e);
            }
        }
        return errorMap;
    }

    /**
     * 通过表单添加学生
     * 
     * @param beanMap
     * @return
     */
    public Map<String, String> addGraduateInfoByForm(Map<String, Object> beanMap) {
        Map<String, String> errorMap = new HashMap<String, String>();
        verifyGraduateInfoInForm(beanMap, errorMap);
        return errorMap;
    }

    private void verifyGraduateInfoInForm(Map<String, Object> beanMap, Map<String, String> errorMap) {
        Student student;
        Graduate graduate;
        User user;
        EducationalLevel educationalLevel;
        Object obj = beanMap.get("shenfenzhenghao");
        String birthDay = obj.toString().substring(6, 14);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date = sdf.parse(birthDay);
            beanMap.put("chushengriqi", date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            birthDay = "2000-01-01";
            beanMap.put("chushengriqi", birthDay);
        }
        educationalLevel = CommonUtils.toBean(beanMap, EducationalLevel.class);
        try {
            educationalLevel = educationalLevelDao
                    .findEducationalLevelByEducationalName(educationalLevel.getEducationallevel());
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e1);
        }
        
        if (educationalLevel == null){
            educationalLevel = new EducationalLevel();
            educationalLevel.setElid(1);
            educationalLevel.setEducationallevel("无");
        }
        beanMap.put("xueli", educationalLevel);
        student = CommonUtils.toBean(beanMap, Student.class);

        VerifyDateFormat.VerifyStudentDateFormat(errorMap, student);
        if (errorMap.size() == 0) {
            graduate = CommonUtils.toBean(beanMap, Graduate.class);
            user = CommonUtils.toBean(beanMap, User.class);
            user.setLoginname(student.getShenfenzhenghao());
            user.setLoginpass(student.getShenfenzhenghao().substring(student.getShenfenzhenghao().length() - 6));
            user.setUid(CommonUtils.uuid());
            user.setActivationCode(CommonUtils.uuid() + CommonUtils.uuid());
            user.setStatus(true);
            user.setRole(Role.毕业生.toString());
            try {
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
            } catch (SQLException e) {
                // TODO: handle exception
                try {
                    JdbcUtils.rollbackTransaction();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                throw new RuntimeException(e);
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
                        graduate.setGid(graduateDao.findGraduateByXuehao(student.getXuehao()).getGid());
                        graduateDao.updateGraduateBasicInfo(graduate);
                    }
                    JdbcUtils.commitTransaction();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                try {
                    JdbcUtils.rollbackTransaction();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                throw new RuntimeException(e);
            }
        }

        return errorMap;
    }

    public Map<String, String> updateGraduateByXuehao(Graduate graduate) {
        Map<String, String> errorMap = new HashMap<String, String>();

        if (errorMap.size() == 0) {
            try {
                JdbcUtils.beginTransaction();
                EducationalLevel educationalLevel = educationalLevelDao
                        .findEducationalLevelByEducationalName(graduate.getXueli().getEducationallevel());
                graduate.setXueli(educationalLevel);
                graduateDao.updateGraduateInfoByXuehao(graduate);
                JdbcUtils.commitTransaction();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                try {
                    JdbcUtils.rollbackTransaction();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                throw new RuntimeException(e);
            }
        }

        return errorMap;
    }

    public int deleteStudentByXuehaos(String[] xuehaos) {

        int row = 0;
        try {
            List<Student> students = studentDao.findStudentIdentifyIdByXuehao(xuehaos);
            if (students != null && students.size() > 0) {
                String[] loginnameList = new String[students.size()];
                int i = 0;
                for (Student student : students) {
                    loginnameList[i] = student.getShenfenzhenghao();
                    i++;
                }
                JdbcUtils.beginTransaction();
                row = studentDao.deleteStudentByXuehao(xuehaos);
                if (row > 0) {
                    graduateDao.deleteGraduateByXuehao(xuehaos);
                    userDao.deleteSudentUserByLoginname(loginnameList);
                    JdbcUtils.commitTransaction();
                } else {
                    JdbcUtils.rollbackTransaction();
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            try {
                JdbcUtils.rollbackTransaction();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            throw new RuntimeException(e);
        }
        return row;
    }

    public PageBean<Student> findStudentByPager(PageBean<Student> pageStudent, String field, String value) {
        try {
            switch (field) {
            case "学号":
                field = "xuehao";
                break;
            case "学院":
                field = "xueyuan";
                break;
            case "姓名":
                field = "studentname";
                break;
            default:
                field = StringUtil.Empty;
                value = StringUtil.Empty;
                break;
            }

            pageStudent = studentDao.findStudentByPager(pageStudent, field, value);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
        return pageStudent;
    }

    public PageBean<Graduate> findGraduateByPager(PageBean<Graduate> pageGraduate, String field, String value) {
        try {
            switch (field) {
            case "学号":
                field = " xuehao";
                break;
            case "毕业年份":
                field = " biyeshijian";
                break;
            case "姓名":
                field = " studentname ";
                break;
            default:
                field = StringUtil.Empty;
                value = StringUtil.Empty;
                break;
            }
            pageGraduate = graduateDao.findGraduateByPager(pageGraduate, field, value);
            List<EducationalLevel> educationalLevelList = educationalLevelDao.findAll();

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

    public Student findStudentByXuehao(String xuehao){
        Student stu = new Student();
        
        try {
            stu = studentDao.findStudentByXuehao(xuehao);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return stu;
    }
    public Graduate findGraduateByXuehao(String xuehao){
        Graduate gra = new Graduate();
        
        try {
            gra = graduateDao.findGraduateByXuehao(xuehao);
            List<EducationalLevel> educationalLevel = educationalLevelDao.findAll();
            for (EducationalLevel educationalLevel2 : educationalLevel) {
                if(gra.getElid()==educationalLevel2.getElid()){
                    gra.setXueli(educationalLevel2);
                    break;
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return gra;
    }
    
    public String findXuehaoByshenfenzhenghao(String xuehao){
        Student stu = new Student();
        try {
            stu = studentDao.findStudentByXuehao(xuehao);
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String shenfenzhenghao = stu != null ? stu.getShenfenzhenghao() : StringUtil.Empty;
        return shenfenzhenghao;
    }
    
    // @Test
    // public void T() {
    // GraduateService graduateService = new GraduateService();
    // graduateService.addGraduateInfoByExcel("");
    // String str = "123123111101010000";
    // System.out.println(str.substring(str.length() - 6));
    // }
}
