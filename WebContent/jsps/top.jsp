<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
  <head>
    <title>top</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
<style type="text/css">
    body {
        background: #15B69A;
        margin: 0px;
        color: #ffffff;
    }
    a {
        text-decoration:none;
        color: #ffffff;
        font-weight: 900;
    } 
    a:hover {
        text-decoration: underline;
        color: #ffffff;
        font-weight: 900;
    }
</style>
  </head>
  <body>
<h1 style="text-align: center;">毕业生学历证明系统</h1>
<div style="font-size: 10pt; line-height: 10px; text-align: right;">

    <%-- 根据用户是否登录，显示不同的链接 --%>
    <c:choose>
        <c:when test="${empty sessionScope.sessionUser }">
              <a href="<c:url value='/jsps/user/login.jsp'/>" target="_parent">登录</a>&nbsp;|&nbsp;
              <a href="<c:url value='/jsps/user/regist.jsp'/>" target="_parent">注册</a>&nbsp;&nbsp;
        </c:when>
        <c:otherwise>
        <span>用户：</span>
        <c:choose>
        <c:when test="${'毕业生用户' eq sessionScope.sessionUser.role } "><a href="<c:url value='/jsps/graduate/student.jsp'/>" target="body">${sessionScope.sessionUser.loginname }</a>&nbsp;|&nbsp;</c:when>
        <c:otherwise>${sessionScope.sessionUser.loginname }&nbsp;|&nbsp;</c:otherwise>
        </c:choose>
              
              <a href="<c:url value='/jsps/user/pwd.jsp'/>" target="body">修改密码</a>&nbsp;|&nbsp;
              <a href="<c:url value='/UserServlet?method=quit'/>" target="_parent">退出</a>&nbsp;&nbsp;
        </c:otherwise>
    </c:choose>
</div>
  </body>
</html>
