package com.why.gcoads.servlet.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import com.why.gcoads.commons.CommonUtils;
import com.why.gcoads.model.EducationalLevel;
import com.why.gcoads.model.PageBean;
import com.why.gcoads.service.educationallevel.EducationalLevelService;
import com.why.gcoads.servlet.BaseServlet;

/**
 * Servlet implementation class UserManagementServlet
 */
@WebServlet("/admin/EducationalLevelManagementServlet")
public class EducationalLevelManagementServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private EducationalLevelService educationalLevelService = new EducationalLevelService();

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

    @Test
    public void TT() {
        List<EducationalLevel> beanList = educationalLevelService
                .findEducationalLevelByPager(new PageBean<EducationalLevel>(),
                        "").getBeanList();
        for (EducationalLevel educationalLevel : beanList) {
            System.out.println(educationalLevel);
        }
    }

    public String findEducationalLevel(HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException {

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

        PageBean<EducationalLevel> pageEducationalLevel = new PageBean<EducationalLevel>();
        pageEducationalLevel.setPc(pc);
        pageEducationalLevel.setPs(10);

        String educationalLevel = req.getParameter("educationalLevel");
        /*
         * 4. 得到PageBean
         */
        pageEducationalLevel = educationalLevelService
                .findEducationalLevelByPager(pageEducationalLevel,
                        educationalLevel);
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/admin/educationallevel/list.jsp
         */
        pageEducationalLevel.setUrl(url);

        req.setAttribute("pageBean", pageEducationalLevel);
        req.setAttribute("educationalLevel", educationalLevel);
        return "f:/jsps/admin/educationallevel/list.jsp";
    }

    public String addStudentByForm(HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException {

        EducationalLevel formEducationalLevel = CommonUtils.toBean(
                req.getParameterMap(), EducationalLevel.class);
        Map<String, String> errorMap = null;

        try {
            errorMap = educationalLevelService
                    .addGraduateInfoByExcel(formEducationalLevel);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        req.setAttribute("errorMap", errorMap);
        return "f:/jsps/dialog.jsp";
    }

}
