<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
  <head>
    <title>主页</title>
    <script type="text/javascript" src="<c:url value='/static/jquery/jquery-1.5.1.js'/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/style/main.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/style/dialog.css'/>">
    <script type="text/javascript" >
    </script>
</head>
  
  <body style="height: 640px;">
    <table class="table" align="center">
    <tr class="trTop">
        <td colspan="2" class="tdTop">
            <iframe frameborder="0" src="<c:url value='/jsps/admin/top.jsp'/>" name="top"></iframe>
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
    <div id="dialog1" class="dialog">
	    <div id="backgroundDiv"></div>
	    <div id="dialogDiv" class="dialogDiv">
	    <div class="closeDivImg"><img src="<c:url value='/static/images/BTN_Close_16x16.png'/>"></div>
	      <div id="dialogMsgDiv1" class="dialogMsgDiv">${msg }</div>
	      <div id="buttonDiv">
	        <input id="OKbutton" type="button" value="OK">
	      </div>
	    </div>
    </div>
    <div id="dialog2" class="dialog">
        <div id="backgroundDiv"></div>
        <div id="dialogDiv" class="dialogDiv">
	    <div class="closeDivImg close" onclick="close()"><img src="<c:url value='/static/images/BTN_Close_16x16.png'/>"></div>
          <div id="dialogMsgDiv2" class="dialogMsgDiv">${msg }</div>
          <div id="buttonDiv">
            <input id="Yesbutton" type="button" value="Yes">
            <input id="Nobutton" type="button" value="No" >
          </div>
        </div>
    </div>
  </body>
</html>
