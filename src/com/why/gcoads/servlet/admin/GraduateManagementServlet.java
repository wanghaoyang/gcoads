package com.why.gcoads.servlet.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.why.gcoads.commons.CommonUtils;
import com.why.gcoads.model.EducationalLevel;
import com.why.gcoads.model.Graduate;
import com.why.gcoads.model.PageBean;
import com.why.gcoads.model.Student;
import com.why.gcoads.model.User;
import com.why.gcoads.service.educationallevel.EducationalLevelService;
import com.why.gcoads.service.graduate.GraduateService;
import com.why.gcoads.servlet.BaseServlet;
import com.why.gcoads.utils.StringUtil;

/**
 * Servlet implementation class GraduateManagementServlet
 */
@WebServlet("/admin/GraduateManagementServlet")
public class GraduateManagementServlet extends BaseServlet {
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

    /**
     * 获取当前页码
     * 
     * @param req
     * @return
     */
    private int getPc(HttpServletRequest req) {
        int pc = 1;
        String param = req.getParameter("pc");
        if (param != null && !param.trim().isEmpty()) {
            try {
                pc = Integer.parseInt(param);
            } catch (RuntimeException e) {

            }
        }
        return pc;
    }

    /**
     * 截取url，页面中的分页导航中需要使用它做为超链接的目标！
     * 
     * @param req
     * @return
     */
    private String getUrl(HttpServletRequest req) {
        String url = req.getRequestURI() + "?" + req.getQueryString();
        /*
         * 如果url中存在pc参数，截取掉，如果不存在那就不用截取。
         */
        int index = url.lastIndexOf("&pc=");
        if (index != -1) {
            url = url.substring(0, index);
        }
        return url;
    }

    public String findStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /*
         * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
         */
        int pc = getPc(req);
        /*
         * 2. 得到url：...
         */
        String url = getUrl(req);
        /*
         * 3. 获取查询条件
         */

        PageBean<Student> pageStudent = new PageBean<Student>();
        pageStudent.setPc(pc);
        pageStudent.setPs(10);

        String field = req.getParameter("field");
        String value = req.getParameter("value");
        if (StringUtil.isNullOrEmpty(field)) {
            field = "姓名";
        }
        /*
         * 4. 得到PageBean
         */
        pageStudent = graduateService.findStudentByPager(pageStudent, field, value);
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/admin/student/list.jsp
         */
        pageStudent.setUrl(url);

        req.setAttribute("pageBean", pageStudent);
        req.setAttribute("condition", condition1);
        req.setAttribute("field", field);
        req.setAttribute("value", value);
        return "f:/jsps/admin/student/list.jsp";
    }

    public String findGraduate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /*
         * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
         */
        int pc = getPc(req);
        /*
         * 2. 得到url：...
         */
        String url = getUrl(req);
        /*
         * 3. 获取查询条件
         */

        PageBean<Graduate> pageGraduate = new PageBean<Graduate>();
        pageGraduate.setPc(pc);
        pageGraduate.setPs(10);

        String field = req.getParameter("field");
        String value = req.getParameter("value");
        if (StringUtil.isNullOrEmpty(field)) {
            field = "姓名";
        }
        /*
         * 4. 得到PageBean
         */
        pageGraduate = graduateService.findGraduateByPager(pageGraduate, field, value);
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/admin/student/list.jsp
         */
        pageGraduate.setUrl(url);

        req.setAttribute("pageBean", pageGraduate);
        req.setAttribute("condition", condition2);
        req.setAttribute("field", field);
        req.setAttribute("value", value);
        return "f:/jsps/admin/graduate/list.jsp";
    }

    public String deleteStudents(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String msg = "删除失败！";
        String xuehao = req.getParameter("xuehaos");
        if (xuehao != null) {
            String[] xuehaos = xuehao.split(",");
            if (xuehaos != null) {
                try {
                    int row = graduateService.deleteStudentByXuehaos(xuehaos);
                    if (row < 0) {
                        msg = "删除失败！";
                    } else {
                        msg = row + "个学生被成功删除！";
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        PageBean<Student> pageStudent = new PageBean<Student>();
        pageStudent.setPc(1);
        pageStudent.setPs(10);
        pageStudent.setUrl("/gcoads/admin/GraduateManagementServlet?method=findStudent");
        pageStudent = graduateService.findStudentByPager(pageStudent, StringUtil.Empty, StringUtil.Empty);
        req.setAttribute("pageBean", pageStudent);
        req.setAttribute("msg", msg);
        req.setAttribute("condition", condition1);
        return "f:/jsps/admin/student/list.jsp";
    }

    public String addStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<EducationalLevel> educationalLevelList = educationalLevelService.findAllEducationalLevel();
        req.setAttribute("educationalLevelList", educationalLevelList);
        return "f:/jsps/admin/addstudent.jsp";
    }

    public String addStudentByFile(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        
        // List<EducationalLevel> educationalLevelList =
        // educationalLevelService.findAllEducationalLevel();
        // req.setAttribute("educationalLevelList", educationalLevelList);
        return "f:/jsps/admin/addstudentbyexcel.jsp";
    }

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
        req.setAttribute("condition", condition1);
        if (errorMap.size() == 0) {
            req.setAttribute("msg", "添加成功");
            return "f:/jsps/admin/student/list.jsp";
        } else {
            req.setAttribute("msg", "添加失败，请重新添加!");
            return "f:/jsps/admin/student/list.jsp";
        }
    }

    public String addStudentByExcel(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User)req.getSession().getAttribute("sessionUser");
        if(user == null) {
            return "r:/jsps/user/login.jsp";
        }
        
        Map<String, String> errorMap = new HashMap<String, String>();
        // 得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        String savePath = this.getServletContext().getRealPath("/excel/upload/");
        savePath = savePath.substring(0, savePath.indexOf("\\"))+ "\\" + req.getContextPath().substring(1) +"\\excel\\upload" +"\\";
        File file = new File(savePath);
        // 判断上传文件的保存目录是否存在
        if (!file.exists() && !file.isDirectory()) {
            System.out.println(savePath + "目录不存在，需要创建");
            // 创建目录
            file.mkdirs();
        }
        // 消息提示
        String message = "";
        try {
            // 使用Apache文件上传组件处理文件上传步骤：
            // 1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8");
            // 3、判断提交上来的数据是否是上传表单的数据
            if (!ServletFileUpload.isMultipartContent(req)) {
                // 按照传统方式获取数据
                req.setAttribute("msg", "数据导入失败, 请检查!");
                return "f:/jsps/admin/addstudentbyexcel.jsp";
            }
            // 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = upload.parseRequest(req);
            for (FileItem item : list) {
                // 如果fileitem中封装的是普通输入项的数据
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    System.out.println(name);
                    // 解决普通输入项的数据的中文乱码问题
                    String value = item.getString("UTF-8");
                    // value = new String(value.getBytes("iso8859-1"),"UTF-8");
                    System.out.println(name + "=" + value);
                } else {// 如果fileitem中封装的是上传文件
                    // 得到上传的文件名称，
                    String filename = item.getName();
                    System.out.println(filename);
                    if (filename == null || filename.trim().equals("")) {
                        continue;
                    }
                    // 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：
                    // c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                    // 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    filename = filename.substring(filename.lastIndexOf("\\") + 1);
                    filename = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "_"+ user.getLoginname() + "_" + filename;
                    // 获取item中的上传文件的输入流
                    InputStream in = item.getInputStream();
                    // 创建一个文件输出流
                    FileOutputStream out = new FileOutputStream(savePath + filename);
                    // 创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    // 判断输入流中的数据是否已经读完的标识
                    int len = 0;
                    // 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                    while ((len = in.read(buffer)) > 0) {
                        // 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\"
                        // + filename)当中
                        out.write(buffer, 0, len);
                    }
                    // 关闭输入流
                    in.close();
                    // 关闭输出流
                    out.close();
                    // 删除处理文件上传时生成的临时文件
                    item.delete();
                    // message = "文件上传成功！";
                    // ReadExcelUtils.parseExcel(savePath + filename);
                    try {
                        errorMap = graduateService.addGraduateInfoByExcel(savePath + filename);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            req.setAttribute("code", "error");
            req.setAttribute("msg", "数据导入失败, 请检查!");
            e.printStackTrace();
        }
        // req.setAttribute("message", message);
        // request.getRequestDispatcher("/message.jsp").forward(request,
        // response);
        if (errorMap.size() == 0) {
            req.setAttribute("code", "success");
            req.setAttribute("msg", "数据导入成功");
        } else {
            req.setAttribute("code", "error");
            req.setAttribute("errorRowsOfFormat", errorMap.get("errorRowsOfFormat"));
            req.setAttribute("errorRowsOfStuExist", errorMap.get("errorRowsOfStuExist"));
            req.setAttribute("toomanystu", errorMap.get("toomanystu"));
            req.setAttribute("msg", "存在数据导入失败, 请检查!");
        }

        return "f:/jsps/admin/msg.jsp";
    }

    public String findStudentByXuehao(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String xuehao = req.getParameter("xuehao");
        if (StringUtil.isNullOrEmpty(xuehao)) {
            xuehao = StringUtil.Empty;
        }
        Student student = graduateService.findStudentByXuehao(xuehao);
        req.setAttribute("national", national);
        req.setAttribute("zhengzhimianmao", zhengzhimianmao);
        req.setAttribute("student", student);
        return "f:/jsps/admin/student/studentdetail.jsp";
    }

    public String findGraduateByXuehao(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String xuehao = req.getParameter("xuehao");
        if (StringUtil.isNullOrEmpty(xuehao)) {
            xuehao = StringUtil.Empty;
        }
        Graduate graduate = graduateService.findGraduateByXuehao(xuehao);
        List<EducationalLevel> educationalLevelList = educationalLevelService.findAllEducationalLevel();
        req.setAttribute("national", national);
        req.setAttribute("zhengzhimianmao", zhengzhimianmao);
        req.setAttribute("educationalLevelList", educationalLevelList);
        req.setAttribute("graduate", graduate);
        System.out.println(graduate);
        return "f:/jsps/admin/graduate/graduatedetail.jsp";
    }

    public String updateStudentByXuehao(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Student student = CommonUtils.toBean(req.getParameterMap(), Student.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(student);
        Map<String, String> errorMap = graduateService.updateStudentByXuehao(student);
        if (errorMap.size() == 0) {
            req.setAttribute("msg", "更新成功");
        } else {
            req.setAttribute("msg", "更新失败!");
        }
        PageBean<Student> pageStudent = new PageBean<Student>();
        pageStudent.setPc(1);
        pageStudent.setPs(10);
        pageStudent.setUrl("/gcoads/admin/GraduateManagementServlet?method=findStudent");
        pageStudent = graduateService.findStudentByPager(pageStudent, StringUtil.Empty, StringUtil.Empty);
        req.setAttribute("pageBean", pageStudent);
        req.setAttribute("condition", condition1);
        return "f:/jsps/admin/student/list.jsp";
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
        req.setAttribute("condition", condition2);
        return "f:/jsps/admin/graduate/list.jsp";
    }
}
