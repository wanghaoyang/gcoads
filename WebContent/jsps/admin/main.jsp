<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
  <head>
    
    <title>主页</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/style/main.css'/>">
</head>
  
  <body style="height: 640px;">
    <table class="table" align="center">
    <tr class="trTop">
        <td colspan="2" class="tdTop">
            <iframe frameborder="0" src="<c:url value='/jsps/top.jsp'/>" name="top"></iframe>
        </td>
    </tr>
    <tr>
        <td class="tdLeft">
            <iframe frameborder="0" src="<c:url value='/jsps/admin/left.jsp'/>" name="left"></iframe>
        </td>
        <td style="border-top-width: 0px;">
            <iframe frameborder="0" src="<c:url value='/jsps/body.jsp'/>" name="body"></iframe>
        </td>
    </tr>
    </table>
    <%-- <%@include file="/jsps/dialog.jsp"%> --%>
  </body>
</html>
