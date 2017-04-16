package com.why.gcoads.servlet.User;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.why.gcoads.commons.CommonUtils;
import com.why.gcoads.model.EducationalLevel;
import com.why.gcoads.model.Graduate;
import com.why.gcoads.model.PageBean;
import com.why.gcoads.model.Role;
import com.why.gcoads.model.Student;
import com.why.gcoads.model.User;
import com.why.gcoads.service.educationallevel.EducationalLevelService;
import com.why.gcoads.service.graduate.GraduateService;
import com.why.gcoads.servlet.BaseServlet;
import com.why.gcoads.utils.StringUtil;

/**
 * Servlet implementation class GraduateManagementServlet
 */
@WebServlet("/GraduateServlet")
public class GraduateServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private GraduateService graduateService = new GraduateService();
    private EducationalLevelService educationalLevelService = new EducationalLevelService();
    private static String[] condition1 = new String[] { "姓名", "学号", "学院" };
    private static String[] condition2 = new String[] { "姓名", "学号", "毕业年份" };
    private static String[] national = { "汉族", "壮族", "满族", "回族", "苗族", "维吾尔族", "土家族", "彝族", "蒙古族", "藏族", "布依族", "侗族",
            "瑶族", "朝鲜族", "白族", "哈尼族", "哈萨克族", "黎族", "傣族", "畲族", "傈僳族", "仡佬族", "东乡族", "高山族", "拉祜族", "水族", "佤族", "纳西族",
            "羌族", "土族", "仫佬族", "锡伯族", "柯尔克孜族", "达斡尔族", "景颇族", "毛南族", "撒拉族", "布朗族", "塔吉克族", "阿昌族", "普米族", "鄂温克族", "怒族",
            "京族", "基诺族", "德昂族", "保安族", "俄罗斯族", "裕固族", "乌孜别克族", "门巴族", "鄂伦春族", "独龙族", "塔塔尔族", "赫哲族", "珞巴族" };
    private static String[] zhengzhimianmao = { "群众", "共青团员", "中共党员", "民革党员", "民盟盟员", "民建会员", "民进会员", "农工党党员", "致公党党员",
            "九三学社社员", "台盟盟员", "无党派人士" };

    public String addStudentByForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Map<String, String[]> parameterMap = req.getParameterMap();

        Map<String, Object> beanMap = new HashMap<String, Object>();
        Map<String, String> errorMap = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        for (String key : parameterMap.keySet()) {
            if ("chushengriqi".equals(key) || "biyeshijian".equals(key) || "ruxueshijian".equals(key)) {
                String str = parameterMap.get(key)[0];
                try {
                    Date date = sdf.parse(str);
                    beanMap.put(key, date);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    if ("chushengriqi".equals(key)) {
                        str = "2000-01-01";
                        beanMap.put(key, str);
                    }
                }
            } else {
                beanMap.put(key, parameterMap.get(key)[0]);
            }
        }
        try {
            errorMap = graduateService.addGraduateInfoByForm(beanMap);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        req.setAttribute("errorMap", errorMap);
        PageBean<Student> pageStudent = new PageBean<Student>();
        pageStudent.setPc(1);
        pageStudent.setPs(10);
        pageStudent.setUrl("/gcoads/admin/GraduateManagementServlet?method=findStudent");
        pageStudent = graduateService.findStudentByPager(pageStudent, StringUtil.Empty, StringUtil.Empty);
        req.setAttribute("pageBean", pageStudent);
        if (errorMap.size() == 0) {
            req.setAttribute("msg", "添加成功");
            return "f:/jsps/admin/student/list.jsp";
        } else {
            req.setAttribute("msg", "添加失败，请重新添加!");
            return "f:/jsps/admin/student/list.jsp";
        }
    }

    public String updateGraduateByXuehao(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        EducationalLevel educationalLevel = CommonUtils.toBean(req.getParameterMap(), EducationalLevel.class);
        if (educationalLevel == null && StringUtil.isNullOrEmpty(educationalLevel.getEducationallevel())) {
            educationalLevel = new EducationalLevel();
            educationalLevel.setEducationallevel("无");
            educationalLevel.setElid(1);
        }
        Graduate graduate = CommonUtils.toBean(req.getParameterMap(), Graduate.class);
        if (graduate == null) {
            graduate = new Graduate();
        }
        graduate.setXueli(educationalLevel);
        System.out.println(graduate);
        Map<String, String> errorMap = graduateService.updateGraduateByXuehao(graduate);
        if (errorMap.size() == 0) {
            req.setAttribute("msg", "更新成功");
        } else {
            req.setAttribute("msg", "更新失败");
        }
        PageBean<Graduate> pageGraduate = new PageBean<Graduate>();
        pageGraduate.setPc(1);
        pageGraduate.setPs(10);
        pageGraduate.setUrl("/gcoads/admin/GraduateManagementServlet?method=findGraduate");
        pageGraduate = graduateService.findGraduateByPager(pageGraduate, StringUtil.Empty, StringUtil.Empty);
        req.setAttribute("pageBean", pageGraduate);
        return "f:/jsps/admin/graduate/list.jsp";
    }

    public String findGraduateByLoginname(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("sessionUser");
        if (user == null) {
            return "r:/jsps/user/login.jsp";
        }
        if (Role.毕业生.toString().equals(user.getRole())) {
            String xuehao = graduateService.findXuehaoByshenfenzhenghao(user.getLoginname());
            Student student = graduateService.findStudentByXuehao(xuehao);
            Graduate graduate = graduateService.findGraduateByXuehao(xuehao);
            req.setAttribute("national", national);
            req.setAttribute("zhengzhimianmao", zhengzhimianmao);
            req.setAttribute("student", student);
            req.setAttribute("graduate", graduate);
            return "f:/jsps/student/studentdetail.jsp";
        }
        return "r:/jsps/user/login.jsp";
    }
}
