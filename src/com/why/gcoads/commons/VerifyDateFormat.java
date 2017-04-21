package com.why.gcoads.commons;

import java.util.Map;

import com.why.gcoads.model.Student;
import com.why.gcoads.utils.StringUtil;

/**
 * 验证数据
 *
 */
public class VerifyDateFormat {
    /**
     * 验证学生信息数据
     * 
     * @param map
     * @param stu
     * @return
     */
    public static Map<String, String> VerifyStudentDateFormat(Map<String, String> map, Student stu) {

        if (map != null && stu != null) {
            if (StringUtil.isNullOrEmpty(stu.getKaoshenghao())) {
                map.put("kaoshenghao", "考生号不能为空!");
            } else if(stu.getKaoshenghao().length() > 20){
                map.put("kaoshenghao", "考生号长度不能超过20!");
            }
            if (StringUtil.isNullOrEmpty(stu.getShenfenzhenghao())) {
                map.put("shenfenzhenghao", "身份证号不能为空!");
            } else if (stu.getShenfenzhenghao().length() != 18) {
                map.put("shenfenzhenghao", "身份证号长度不对!");
            }
            if (StringUtil.isNullOrEmpty(stu.getXuehao())) {
                map.put("xuehao", "学号不能为空!");
            } else if(stu.getXuehao().length() > 15){
                map.put("xuehao", "学号长度不能超过15!");
            }
            if (StringUtil.isNullOrEmpty(stu.getStudentname())) {
                map.put("studentname", "姓名不能为空!");
            } else if(stu.getStudentname().length() > 30){
                map.put("studentname", "姓名长度不能超过30!");
            }
            if (StringUtil.isNullOrEmpty(stu.getStudentgender())) {
                map.put("studentgender", "性别不能为空!");
            } else if(stu.getStudentgender().length() > 1){
                map.put("studentgender", "性别长度不能超过1!");
            }

            if (StringUtil.isNullOrEmpty(stu.getMinzu())) {
                map.put("minzu", "民族不能为空!");
            } else if(stu.getMinzu().length() > 7){
                map.put("minzu", "民族长度不能超过7!");
            }
            if (StringUtil.isNullOrEmpty(stu.getZhengzhimianmao())) {
                map.put("zhengzhimianmao", "政治面貌不能为空!");
            } else if(stu.getZhengzhimianmao().length() > 15){
                map.put("zhengzhimianmao", "政治面貌长度不能超过15!");
            }
            if (StringUtil.isNullOrEmpty(stu.getZhuanye())) {
                map.put("zhuanye", "专业不能为空!");
            } else if(stu.getZhuanye().length() > 30){
                map.put("zhuanye", "专业长度不能超过30!");
            }
            
//             if (StringUtil.isNullOrEmpty(stu.getZhuanyefangxiang())) {
//                 map.put("zhuanyefangxiang", "专业方向不能为空!");
//             } else {
             if (stu.getZhuanye().length() > 30) {
                map.put("zhuanyefangxiang", "专业方向长度不能超过30!");
             }
            // }
            // if (StringUtil.isNullOrEmpty(stu.getPeiyangfangshi())) {
            // map.put("peiyangfangshi", "培养方式不能为空!");
            // } else {
            //
            // }
             
             if (stu.getZhuanye().length() > 30) {
                 map.put("peiyangfangshi", "培养方式长度不能超过30!");
              }
             
            // if (StringUtil.isNullOrEmpty(stu.getXuezhi())) {
            // map.put("xuezhi", "学制不能为空!");
            // } else {
            //
            // }
            // if (StringUtil.isNullOrEmpty(stu.getRuxueshijian())) {
            // map.put("ruxueshijian", "入学时间不能为空!");
            // } else {
            //
            // }
            // if (StringUtil.isNullOrEmpty(stu.getBiyeshijian())) {
            // map.put("biyeshijian", "毕业时间不能为空!");
            // } else {
            //
            // }
            if (StringUtil.isNullOrEmpty(stu.getShifanshengleibie())) {
                map.put("shifanshengleibie", "师范生类别不能为空!");
            } else if(stu.getShifanshengleibie().length() > 10){
                map.put("shifanshengleibie", "师范生类别长度不能超过10!");
            }
            if (StringUtil.isNullOrEmpty(stu.getXueyuan())) {
                map.put("xueyuan", "学院名称不能为空!");
            } else if(stu.getXueyuan().length() > 30){
                map.put("xueyuan", "学院名称长度不能超过30!");
            }
            if (StringUtil.isNullOrEmpty(stu.getXibie())) {
                map.put("xibie", "所在系名称不能为空!");
            } else if(stu.getXibie().length() > 30){
                map.put("xibie", "所在系名称长度不能超过30!");
            }
             if (StringUtil.isNullOrEmpty(stu.getBanji())) {
                 map.put("banji", "所在班级不能为空!");
             } else if(stu.getBanji().length() > 30){
                 map.put("banji", "所在班级长度不能超过30!");
             }

            if (StringUtil.isNullOrEmpty(stu.getShengyuansuozaidi())) {
                map.put("shengyuansuozaidi", "生源所在地不能为空!");
            } else if(stu.getShengyuansuozaidi().length() > 30){
                map.put("shengyuansuozaidi", "所在班级长度不能超过30!");
            }
            if (StringUtil.isNullOrEmpty(stu.getEmail())) {
                map.put("email", "邮箱不能为空!");
            } else if (!stu.getEmail().matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
                map.put("email", "Email格式错误！");
            }else if (stu.getEmail().length() > 40) {
                map.put("email", "邮箱长度不能超过40!");
            }
            // if (StringUtil.isNullOrEmpty(stu.getAddress())) {
            // map.put("address", "家庭地址不能为空!");
            // } else {
            //
            // }
            if (stu.getEmail().length() > 150) {
                map.put("address", "家庭地址长度不能超过150!");
             }
        }

        return map;
    }
}
