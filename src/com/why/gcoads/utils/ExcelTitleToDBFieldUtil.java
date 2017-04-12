package com.why.gcoads.utils;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ExcelTitleToDBFieldUtil {

    private static Map<String, String> dbFieldMap = new HashMap<String, String>();

    static {
        dbFieldMap.put("考生号", "kaoshenghao");
        dbFieldMap.put("身份证号", "shenfenzhenghao");
        dbFieldMap.put("学号", "xuehao");
        dbFieldMap.put("姓名", "studentname");
        dbFieldMap.put("性别", "studentgender");
        dbFieldMap.put("民族", "minzu");
        dbFieldMap.put("政治面貌", "zhengzhimianmao");
        dbFieldMap.put("专业", "zhuanye");
        dbFieldMap.put("专业方向", "zhuanyefangxiang");
        dbFieldMap.put("培养方式", "peiyangfangshi");
        dbFieldMap.put("学制", "xuezhi");
        dbFieldMap.put("入学时间", "ruxueshijian");
        dbFieldMap.put("毕业时间", "biyeshijian");
        dbFieldMap.put("师范生类别", "shifanshengleibie");
        dbFieldMap.put("学院名称", "xueyuan");
        dbFieldMap.put("所在系名称", "xibie");
        dbFieldMap.put("所在班级", "banji");
        dbFieldMap.put("出生日期", "chushengriqi");
        dbFieldMap.put("生源所在地", "shengyuansuozaidi");
        dbFieldMap.put("邮箱", "email");
        dbFieldMap.put("家庭地址", "address");
        dbFieldMap.put("学历", "xueli");
    }

    public static Map<String, String> getDBFieldMap() {
        return dbFieldMap;
    }
    @Test
    public void asd(){
        System.out.println(dbFieldMap.size());
    }
}
