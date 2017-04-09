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

public class LoginFilter implements Filter {
    public void destroy() {

    }

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
            if (req.getRequestURI().contains("UserServlet") || req.getRequestURI().contains("ajax") || req.getRequestURI().contains("VerifyCodeServlet")) {
                chain.doFilter(request, response);
            } else { 
                req.getRequestDispatcher("/index.jsp").forward(req, response);
            }
        } else {
            chain.doFilter(request, response);// 放行
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {

    }
}
