package com.why.gcoads.servlet.User;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.why.gcoads.commons.CommonUtils;
import com.why.gcoads.commons.GeneratePDF;
import com.why.gcoads.model.EducationalLevel;
import com.why.gcoads.model.Graduate;
import com.why.gcoads.model.PrintReportRecord;
import com.why.gcoads.model.Role;
import com.why.gcoads.model.Student;
import com.why.gcoads.model.User;
import com.why.gcoads.service.educationallevel.EducationalLevelService;
import com.why.gcoads.service.graduate.GraduateService;
import com.why.gcoads.service.printreportrecord.PrintReportRecordService;
import com.why.gcoads.servlet.BaseServlet;
import com.why.gcoads.utils.StringUtil;

/**
 * Servlet implementation class GraduateManagementServlet
 */
@WebServlet("/GraduateServlet")
public class GraduateServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private GraduateService graduateService = new GraduateService();
    private PrintReportRecordService printReportRecordService = new PrintReportRecordService();
    
    private EducationalLevelService educationalLevelService = new EducationalLevelService();
    private static String[] condition1 = new String[] { "姓名", "学号", "学院" };
    private static String[] condition2 = new String[] { "姓名", "学号", "毕业年份" };
    private static String[] national = { "汉族", "壮族", "满族", "回族", "苗族", "维吾尔族",
            "土家族", "彝族", "蒙古族", "藏族", "布依族", "侗族", "瑶族", "朝鲜族", "白族", "哈尼族",
            "哈萨克族", "黎族", "傣族", "畲族", "傈僳族", "仡佬族", "东乡族", "高山族", "拉祜族", "水族",
            "佤族", "纳西族", "羌族", "土族", "仫佬族", "锡伯族", "柯尔克孜族", "达斡尔族", "景颇族",
            "毛南族", "撒拉族", "布朗族", "塔吉克族", "阿昌族", "普米族", "鄂温克族", "怒族", "京族",
            "基诺族", "德昂族", "保安族", "俄罗斯族", "裕固族", "乌孜别克族", "门巴族", "鄂伦春族", "独龙族",
            "塔塔尔族", "赫哲族", "珞巴族" };
    private static String[] zhengzhimianmao = { "群众", "共青团员", "中共党员", "民革党员",
            "民盟盟员", "民建会员", "民进会员", "农工党党员", "致公党党员", "九三学社社员", "台盟盟员", "无党派人士" };

    public String updateStudent(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Student student = CommonUtils.toBean(req.getParameterMap(),
                Student.class);
        Graduate graduate = CommonUtils.toBean(req.getParameterMap(),
                Graduate.class);
        User user = (User) req.getSession().getAttribute("sessionUser");
        if (student == null || graduate == null || user == null
                || Role.毕业生.equals(user.getRole())) {
            return "r:/jsps/user/login.jsp";
        } else {
            String msg = graduateService.updateGraduateByLoginname(
                    user.getLoginname(), student, graduate);

            if ("success".equals(msg)) {
                req.setAttribute("code", "success");
                req.setAttribute("msg", "更新成功!");
            } else {
                req.setAttribute("code", "error");
                req.setAttribute("msg", "更新失败!");
            }
        }
        return "f:/jsps/student/msg.jsp";
    }

    public String findGraduateEdu(HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException {

        Student student = null;
        Graduate graduate = null;
        String param = req.getParameter("value");
        if (StringUtil.isNullOrEmpty(param)) {
            return "r:/jsps/graduateedu.jsp";
        }
        String shenfenzhenghao = "";
        Student stu = null;
        Graduate gra = null;
        String xuehao = graduateService.findXuehaoByshenfenzhenghao(param);
        if (!StringUtil.isNullOrEmpty(xuehao)) {
            stu = graduateService.findStudentByXuehao(xuehao);
            gra = graduateService.findGraduateByXuehao(xuehao);
        } else {
            stu = graduateService.findStudentByXuehao(param);
            gra = graduateService.findGraduateByXuehao(param);
        }
        if (stu != null && gra != null) {
            shenfenzhenghao = stu.getShenfenzhenghao();
            student = new Student();
            student.setStudentname(stu.getStudentname());
            student.setZhuanye(stu.getZhuanye());
            student.setBiyeshijian(stu.getBiyeshijian());
            graduate = new Graduate();
            graduate.setBiyeshijian(gra.getBiyeshijian());
            graduate.setXueli(gra.getXueli());
            req.setAttribute("student", student);
            req.setAttribute("graduate", graduate);
            req.setAttribute("value", param);
            req.setAttribute("idcard", shenfenzhenghao);
            return "f:/jsps/graduateedu.jsp";
        }

        req.setAttribute("msg", "学生不存在!");
        req.setAttribute("value", param);
        return "f:/jsps/graduateedu.jsp";
    }

    public String findGraduateByLoginname(HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("sessionUser");
        if (user == null) {
            return "r:/jsps/user/login.jsp";
        }
        if (Role.毕业生.toString().equals(user.getRole())) {
            String xuehao = graduateService.findXuehaoByshenfenzhenghao(user
                    .getLoginname());
            if (!StringUtil.isNullOrEmpty(xuehao)) {
                Student student = graduateService.findStudentByXuehao(xuehao);
                Graduate graduate = graduateService
                        .findGraduateByXuehao(xuehao);
                List<EducationalLevel> educationalLevelList = educationalLevelService
                        .findAllEducationalLevel();
                req.setAttribute("national", national);
                req.setAttribute("zhengzhimianmao", zhengzhimianmao);
                req.setAttribute("student", student);
                req.setAttribute("educationalLevelList", educationalLevelList);
                req.setAttribute("graduate", graduate);
                return "f:/jsps/student/studentdetail.jsp";
            }
        }
        return "r:/jsps/user/login.jsp";
    }
    
    public String getGraduatePDF (HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException {
        
        String param = req.getParameter("value");
        User user = (User) req.getSession().getAttribute("sessionUser");
        if (user == null) {
            return "r:/jsps/user/login.jsp";
        }
        
        String xuehao = graduateService.findXuehaoByshenfenzhenghao(param);
        Student stu = null;
        Graduate gra = null;
        
        if (!StringUtil.isNullOrEmpty(xuehao)) {
            stu = graduateService.findStudentByXuehao(xuehao);
        } else {
            stu = graduateService.findStudentByXuehao(param);
        }

        PrintReportRecord printReportRecord = printReportRecordService.findPrintReportRecordByshenfenzhenghao(user.getLoginname(), stu.getShenfenzhenghao());
        
        if (printReportRecord != null){
            String savePath = this.getServletContext().getRealPath("/pdf/");
            savePath = savePath.substring(0, savePath.indexOf("\\"))+ "\\" + req.getContextPath().substring(1) +"\\pdf" +"\\";
            File file = new File(savePath);
            // 判断上传文件的保存目录是否存在
            if (!file.exists() && !file.isDirectory()) {
                System.out.println(savePath + "目录不存在，需要创建");
                // 创建目录
                file.mkdirs();
            }
            String fileName = new SimpleDateFormat("yyyy_MM_dd").format(new Date()) + "_" + user.getLoginname() + "_" + param;
            try {
                GeneratePDF.writeSimplePdf(savePath, fileName, printReportRecord);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return "";
    }
    
    public String pay (HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException {
        
        
        
        String param = req.getParameter("value");
        User user = (User) req.getSession().getAttribute("sessionUser");
        if (user == null) {
            return "r:/jsps/user/login.jsp";
        }
        
        String xuehao = graduateService.findXuehaoByshenfenzhenghao(param);
        Student stu = null;
        Graduate gra = null;
        
        if (!StringUtil.isNullOrEmpty(xuehao)) {
            stu = graduateService.findStudentByXuehao(xuehao);
        } else {
            stu = graduateService.findStudentByXuehao(param);
        }
        
        PrintReportRecord printReportRecord = printReportRecordService.findPrintReportRecordByshenfenzhenghao(user.getLoginname(), stu.getShenfenzhenghao());
        
        if (printReportRecord != null){
            String savePath = this.getServletContext().getRealPath("/pdf/");
            savePath = savePath.substring(0, savePath.indexOf("\\"))+ "\\" + req.getContextPath().substring(1) +"\\pdf" +"\\";
            File file = new File(savePath);
            // 判断上传文件的保存目录是否存在
            if (!file.exists() && !file.isDirectory()) {
                System.out.println(savePath + "目录不存在，需要创建");
                // 创建目录
                file.mkdirs();
            }
            String fileName = new SimpleDateFormat("yyyy_MM_dd").format(new Date()) + "_" + user.getLoginname() + "_" + param;
            try {
                GeneratePDF.writeSimplePdf(savePath, fileName, printReportRecord);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return "";
    }
    
    
    
    
//    @Test
//    public void TPDF(){
//        String param= "";
//        String xuehao = graduateService.findXuehaoByshenfenzhenghao(param);
//        Student stu = null;
//        Graduate gra = null;
//
//        if (!StringUtil.isNullOrEmpty(xuehao)) {
//            stu = graduateService.findStudentByXuehao(xuehao);
//        } else {
//            stu = graduateService.findStudentByXuehao(param);
//        }
//
//        PrintReportRecord printReportRecord = printReportRecordService.findPrintReportRecordByshenfenzhenghao(user.getLoginname(), stu.getShenfenzhenghao());
//
//        if (printReportRecord != null){
//            String savePath = this.getServletContext().getRealPath("/pdf/");
//            savePath = savePath.substring(0, savePath.indexOf("\\"))+ "\\" + req.getContextPath().substring(1) +"\\pdf" +"\\";
//            File file = new File(savePath);
//            // 判断上传文件的保存目录是否存在
//            if (!file.exists() && !file.isDirectory()) {
//                System.out.println(savePath + "目录不存在，需要创建");
//                // 创建目录
//                file.mkdirs();
//            }
//            String fileName = new SimpleDateFormat("yyyy_MM_dd").format(new Date()) + "_" + user.getLoginname() + "_" + param;
//            try {
//                GeneratePDF.writeSimplePdf(savePath, fileName, printReportRecord);
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//    }
    
}
