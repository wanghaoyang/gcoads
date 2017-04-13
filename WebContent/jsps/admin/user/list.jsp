<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息列表</title>
<script type="text/javascript" src="<c:url value='/static/jquery/jquery-1.5.1.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/static/style/admin/user/search.css'/>">
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
    function _delete(){
        var chk_value =[]; 
        $('input[name="userCheck"]:checked').each(function(){ 
            chk_value.push($(this).val()); 
        });
        if(window.confirm("您确定要删除吗？")){
            document.location="/admin/UserManagementServlet?method=deleteUsers&userids="+chk_value;
        }
    }
</script>
</head>
<body>
  <div id="tableDiv">
    <div id="searchDiv">
      <div>用户信息管理</div>
      <form action="<c:url value='/admin/UserManagementServlet'/>"
        method="get" target="body" id="form1">
        <input type="hidden" name="method" value="findUser" /> <input
          id="username" type="text" name="username" placeholder="请输入用户名" value="${username }"/>
        <span> <a
          href="javascript:document.getElementById('form1').submit();"><img
            id="searchImg" align="top" border="0"
            src="/gcoads/static/images/btn.bmp" /></a>
        </span> <span id="deleteAllSpan"><input type="button" value="删除" id="deleteBtn" onclick="_delete()"></span>
      </form>
    </div>

    <table border="1" cellspacing="0" cellpadding="0" id="userListTab">
      <thead>
        <tr>
          <th></th>
          <th>用户名</th>
          <th>邮箱</th>
          <th>角色</th>
          <th>状态</th>
          <th>操作</th>
          <th id="checkboxTd"><input type="checkbox" id="selectAll"
            onclick="selectAll()" />全选</th>
        </tr>
      </thead>
      <c:forEach varStatus="status" items="${pageBean.beanList }"
        var="user">
        <tr>
          <td>${(pageBean.pc - 1) * pageBean.ps + status.index + 1}</td>
          <td>${user.loginname}</td>
          <td>${user.email }</td>
          <td>${user.role }</td>
          <td><input type="radio" name="status${status.index}"
            value="1" ${user.status? 'checked':''} disabled />已激活 <input
            type="radio" name="status${status.index}" value="1"
            ${user.status? '':'checked'} disabled />未激活</td>
          <td><a href="/gcoads/admin/UserManagementServlet?method=resetPassword&userid=${user.uid }">重置密码</a></td>
          <td><input type="checkbox" id="subcheck" name="userCheck"
            onclick="setSelectAll()" value="${user.uid }"/></td>
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