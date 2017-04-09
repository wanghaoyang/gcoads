<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/style/dialog.css'/>">
<title>dialog</title>
</head>
<body id="dialogBody">
  <div id="backgroundDiv">
    <div id="dialogDiv">
      <div id="dialogMsgDiv">${msg }</div>
      <div id="buttonDiv">
        <input id="OKbutton" type="button" value="OK">
      </div>
    </div>
  </div>
</body>
</html>