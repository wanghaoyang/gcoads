package com.why.gcoads.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.why.gcoads.exception.user.UserException;
import com.why.gcoads.model.PageBean;
import com.why.gcoads.model.User;
import com.why.gcoads.service.user.UserService;
import com.why.gcoads.servlet.BaseServlet;
import com.why.gcoads.utils.StringUtil;

/**
 * Servlet implementation class UserManagementServlet
 */
@WebServlet("/admin/UserManagementServlet")
public class UserManagementServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();
	
	/**
     * 获取当前页码
     * @param req
     * @return
     */
    private int getPc(HttpServletRequest req) {
        int pc = 1;
        String param = req.getParameter("pc");
        if(param != null && !param.trim().isEmpty()) {
            try {
                pc = Integer.parseInt(param);
            } catch(RuntimeException e) {
                
            }
        }
        return pc;
    }
    
    /**
     * 截取url，页面中的分页导航中需要使用它做为超链接的目标！
     * @param req
     * @return
     */
    private String getUrl(HttpServletRequest req) {
        String url = req.getRequestURI() + "?" + req.getQueryString();
        /*
         * 如果url中存在pc参数，截取掉，如果不存在那就不用截取。
         */
        int index = url.lastIndexOf("&pc=");
        if(index != -1) {
            url = url.substring(0, index);
        }
        return url;
    }
	
	
	public String findUser(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
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
        
        PageBean<User> pageUser = new PageBean<User>();
        pageUser.setPc(pc);
        pageUser.setPs(10);
        
        String name = req.getParameter("username");
        /*
         * 4. 得到PageBean
         */
        pageUser = userService.findUserByPager(pageUser, name);
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/admin/user/list.jsp
         */
        pageUser.setUrl(url);
        req.setAttribute("pageBean", pageUser);
        req.setAttribute("username", name);
        return "f:/jsps/admin/user/list.jsp";
    }
	
	public String resetPassword(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
	    PageBean<User> pageUser = null;
        String uid = req.getParameter("userid");
        try {
            userService.resetPassword(uid, (User)req.getSession().getAttribute("sessionUser"));
            pageUser = userService.findUserByPager(null, StringUtil.Empty);
            if (pageUser != null) {
                pageUser.setUrl("/gcoads/admin/UserManagementServlet?method=findUser");
            }
        } catch (UserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        req.setAttribute("pageBean", pageUser);
        req.setAttribute("msg", "密码重置成功！");
        return "f:/jsps/admin/user/list.jsp";
    }
	
	public String deleteUsers(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
	    PageBean<User> pageUser = null;
        String uid = req.getParameter("userids");
        String[] uids = uid.split(",");
        String msg = "";
        if (uids != null) {
            try {
                int row = userService.deleteUsers(uids, (User)req.getSession().getAttribute("sessionUser"));
                if (row < 0) {
                    msg = "删除失败！";
                } else {
                    msg = row+"个用户被成功删除！";
                }
                pageUser = userService.findUserByPager(null, StringUtil.Empty);
                if (pageUser != null) {
                    pageUser.setUrl("/gcoads/admin/UserManagementServlet?method=findUser");
                }
            } catch (UserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        req.setAttribute("pageBean", pageUser);
        req.setAttribute("msg", msg);
        return "f:/jsps/admin/user/list.jsp";
    }
}
