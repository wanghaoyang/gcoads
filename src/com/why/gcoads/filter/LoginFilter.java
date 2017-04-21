package com.why.gcoads.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.why.gcoads.model.Role;
import com.why.gcoads.model.User;

public class LoginFilter implements Filter {
    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        /*
         * 1. 获取session中的user 2. 判断是否为null > 如果为null：保存错误信息，转发到msg.jsp >
         * 如果不为null：放行
         */
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Object user = req.getSession().getAttribute("sessionUser");
        if (user == null) {
            if (req.getRequestURI().startsWith("/gcoads/UserServlet") || req.getRequestURI().startsWith("/gcoads/ajax") || req.getRequestURI().startsWith("/gcoads/VerifyCodeServlet")) {
                chain.doFilter(request, response);
            } else if (req.getRequestURI().startsWith("/gcoads/images/") || req.getRequestURI().startsWith("/gcoads/static/")) {
                chain.doFilter(request, response);// 放行
            } else {
                req.getRequestDispatcher("/index.jsp").forward(req, response);
            }
        } else {
            if (req.getRequestURI().startsWith("/gcoads/jsps/admin/") || req.getRequestURI().startsWith("/gcoads/admin/")){
                if (Role.管理员.toString().equals(((User)user).getRole())){
                    chain.doFilter(request, response);// 放行
                } else {
                    req.setAttribute("msg", "你没有权限！");
                    req.setAttribute("code", "error");
                    req.getRequestDispatcher("/jsps/msg.jsp").forward(req, response);
                }
            }else {
                chain.doFilter(request, response);// 放行
            }
        }
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
    }
}
