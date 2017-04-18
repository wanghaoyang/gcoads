<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询结果</title>
<script type="text/javascript"
	src="<c:url value='/static/jquery/jquery-1.5.1.js'/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/static/style/admin/search.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/static/style/result.css'/>">
<script type="text/javascript"
	src="<c:url value='/static/js/common/common.js'/>"></script>
<script type="text/javascript">
	$(function() {
		$("#flashMsg").show(300).delay(2000).hide(300);
	})
</script>
</head>
<body style="text-align: center;">
	<div id="funTitleDiv">学历查询</div>
	<div id="tableDiv">
		<div id="searchDiv">
			<form action="<c:url value='/GraduateServlet'/>" method="get"
				target="body" id="form1">
				<input type="hidden" name="method" value="findGraduateEdu" /> <input
					id="value" type="text" name="value" placeholder="请输入学号或身份证号"
					value="${value }" /> <span> <a
					href="javascript:document.getElementById('form1').submit();"><img
						id="searchImg" align="top" border="0"
						src="/gcoads/static/images/btn.bmp" /></a>
				</span>
			</form>
		</div>
		
		<c:if test="${!empty student }">
		<table border=1 cellspacing="0" cellpadding="0" id="resultTab">
			<thead>
				<tr style="background-color: #FFF;">
					<th>姓名</th>
					<th>专业</th>
					<th>毕业时间</th>
					<th>学历</th>
					<th></th>
				</tr>
			</thead>
			<tr>
				<td>${student.studentname }</td>
				<td>${student.zhuanye }</td>
				<td>${student.biyeshijian }</td>
				<td>${graduate.xueli.educationallevel }</td>
				<td><a href="">申请学历证明</a></td>
		</table>
		</c:if>
		<div id="flashMsg">${msg }</div>
	</div>
</body>
</html>