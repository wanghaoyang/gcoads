<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息列表</title>
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
    function _delete(){
        var chk_value =[]; 
        $('input[name="userCheck"]:checked').each(function(){ 
            chk_value.push($(this).val()); 
        });
        if (chk_value.length > 0){
        $("#dialogMsgDiv2",parent.document).text("你确定要删除？");
        $("#dialog2",parent.document).show();
        $(".closeDivImg",parent.document).click(function() {
        	$(".dialogMsgDiv",parent.document).text("");
            $("#dialog1",parent.document).hide();
            $("#dialog2",parent.document).hide();
		})
        var aaa = $("#Nobutton",parent.document).click(function() {
        	$(".dialogMsgDiv",parent.document).text("");
            $("#dialog1",parent.document).hide();
            $("#dialog2",parent.document).hide();
		})
        $("#Yesbutton",parent.document).click(function() {
        	$(".dialogMsgDiv",parent.document).text("");
            $("#dialog1",parent.document).hide();
            $("#dialog2",parent.document).hide();
            if (chk_value.length > 0){
                document.location="/gcoads/admin/UserManagementServlet?method=deleteUsers&userids="+chk_value;
            }
		})
        }
    }
    function _resetPwd(obj){
        var $td = $(obj).parents('td').parents('tr').children('td');
        var loginname = $td.eq(1).text();
        var uid = $td.eq(0).text();
        var uid =$("#subcheck"+uid).val();
        $("#dialogMsgDiv2",parent.document).text("你确定要重置"+loginname+"的密码？");
        $("#dialog2",parent.document).show();
        $(".closeDivImg",parent.document).click(function() {
        	$(".dialogMsgDiv",parent.document).text("");
            $("#dialog1",parent.document).hide();
            $("#dialog2",parent.document).hide();
		})
        var aaa = $("#Nobutton",parent.document).click(function() {
        	$(".dialogMsgDiv",parent.document).text("");
            $("#dialog1",parent.document).hide();
            $("#dialog2",parent.document).hide();
		})
        $("#Yesbutton",parent.document).click(function() {
        	$(".dialogMsgDiv",parent.document).text("");
            $("#dialog1",parent.document).hide();
            $("#dialog2",parent.document).hide();
            if (""!=uid){
                document.location="/gcoads/admin/UserManagementServlet?method=resetPassword&userid="+uid;
            }
		})
    }
    $(function() {
    	$("#flashMsg").show(300).delay(2000).hide(300);
	})
</script>
</head>
<body>
  <div id="funTitleDiv">用户信息管理</div>
  <div id="tableDiv">
    <div id="searchDiv">
      <form action="<c:url value='/admin/UserManagementServlet'/>"
        method="get" target="body" id="form1">
        <input type="hidden" name="method" value="findUser" /> <input
          id="username" type="text" name="username" placeholder="请输入用户名" value="${username }"/>
        <span> <a
          href="javascript:document.getElementById('form1').submit();"><img
            id="searchImg" align="top" border="0"
            src="/gcoads/static/images/btn.bmp" /></a>
        </span>
        <span id="deleteAllSpan"><input type="button" value="删除" id="deleteBtn" onclick="_delete()"></span>
      </form>
    </div>

    <table cellspacing="0" cellpadding="0" id="userListTab">
      <thead>
        <tr style="background-color: #FFF;">
          <th></th>
          <th>用户名</th>
          <th>邮箱</th>
          <th>角色</th>
          <th style="width: 130px;">状态</th>
          <th>操作</th>
          <th id="checkboxTd"><input type="checkbox" id="selectAll"
            onclick="selectAll()" />全选</th>
        </tr>
      </thead>
      <c:forEach varStatus="status" items="${pageBean.beanList }"
        var="user">
        <tr>
          <td>${(pageBean.pc - 1) * pageBean.ps + status.index + 1}</td>
          <td title="${user.loginname}">${user.loginname}</td>
          <td title="${user.email }">${user.email }</td>
          <td title="${user.role }">${user.role }</td>
          <td><input type="radio" name="status${status.index}"
            value="1" ${user.status? 'checked':''} disabled />已激活 <input
            type="radio" name="status${status.index}" value="1"
            ${user.status? '':'checked'} disabled />未激活</td>
          <td>
          <span class="clickspan" onclick="_resetPwd(this)">重置密码</span></td>
          <td><input type="checkbox" id="subcheck${status.index + 1}" name="userCheck"
            onclick="setSelectAll()" value="${user.uid }"/></td>
        </tr>
      </c:forEach>
    </table>
    <div id="flashMsg">${msg }</div>
  </div>
  <div style="float: left; width: 100%; text-align: center;">
    <br />
    <%@include file="/jsps/pager/pager.jsp"%>
  </div>
</body>
</html>