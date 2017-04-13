<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学历信息列表</title>
<script type="text/javascript" src="<c:url value='/static/jquery/jquery-1.5.1.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/static/style/admin/search.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/style/admin/user/list.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/static/style/pager/pager.css'/>">
<script type="text/javascript" src="<c:url value='/static/js/common/common.js'/>"></script>
<script type="text/javascript">
    function _go() {
        var pc = $("#pageCode").val();//获取文本框中的当前页码
        if(!/^[1-9]\d*$/.test(pc)) {//对当前页码进行整数校验
            alert('请输入正确的页码！');
            return;
        }
        if(pc > ${pageBean.tp}) {//判断当前页码是否大于最大页
            alert('请输入正确的页码！');
            return;
        }
        location = "${pageBean.url}&pc=" + pc;
    }
</script>
</head>
<body>
  <div id="tableDiv">
    <div id="searchDiv">
      <div>毕业生信息列表</div>
      <form action="<c:url value='/admin/EducationalLevelManagementServlet'/>"
        method="get" target="body" id="form1">
        <input type="hidden" name="method" value="findEducationalLevel" /> <input
          id="username" type="text" name="educationalLevel" placeholder="请输入学历名称" value="${educationalLevel }"/>
        <span> <a
          href="javascript:document.getElementById('form1').submit();"><img
            id="searchImg" align="top" border="0"
            src="/gcoads/static/images/btn.bmp" /></a>
        </span>
      </form>
    </div>

    <table cellspacing="0" cellpadding="0" id="userListTab">
      <thead>
        <tr>
          <th></th>
          <th>学历号</th>
          <th>学历名称</th>
        </tr>
      </thead>
      <c:forEach varStatus="status" items="${pageBean.beanList }"
        var="educationalLevel">
        <tr>
          <td>${(pageBean.pc - 1) * pageBean.ps + status.index + 1}</td>
          <td>${educationalLevel.elid}</td>
          <td>${educationalLevel.educationalLevel }</td>
        </tr>
      </c:forEach>
    </table>
  </div>
  <div style="float: left; width: 100%; text-align: center;">
    <br />
    <%@include file="/jsps/pager/pager.jsp"%>
  </div>
</body>
</html>