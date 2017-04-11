package com.why.gcoads.service.graduate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.why.gcoads.commons.CommonUtils;
import com.why.gcoads.commons.ReadExcelUtils;
import com.why.gcoads.dao.graduate.GraduateDao;
import com.why.gcoads.dao.student.StudentDao;
import com.why.gcoads.model.Graduate;
import com.why.gcoads.model.Student;

/**
 * 学生业务层
 *
 */
public class GraduateService {
    private GraduateDao graduateDao = new GraduateDao();
    private StudentDao studentDao = new StudentDao();

    public void addGraduateInfoByExcel(String filePath) {
        filePath = "D:/计算机2017年毕业生生源信息（273人）.xls";
        //filePath = "D:/student.xls";
        Map<Integer, Map<String, Object>> map = ReadExcelUtils.parseExcel(filePath);
        List<Integer> errorRows = new ArrayList<Integer>();
        int i = 1;
        for (; i <= map.size(); i++) {
            // System.out.println(map.get(i));
            Map<String, Object> beanMap = map.get(i);
            String birthDay = "00010101";
            Object obj = beanMap.get("shenfenzhenghao");
            birthDay = obj.toString().substring(6, 14);
            System.out.println(birthDay+"----"+i);
            beanMap.put("chushengriqi", birthDay);
            
            Student student;
            try {
                student = CommonUtils.toBean(beanMap, Student.class);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                errorRows.add(i);
            }
//            try {
//                studentDao.addStudent(student);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
        }
        System.out.println(errorRows);

    }
    @Test
    public void T(){
        GraduateService graduateService = new GraduateService();
        graduateService.addGraduateInfoByExcel("");
    }
}
