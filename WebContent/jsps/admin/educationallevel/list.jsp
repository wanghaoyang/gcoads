<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学历信息列表</title>
<script type="text/javascript"
  src="<c:url value='/static/jquery/jquery-1.5.1.js'/>"></script>
<script type="text/javascript"
  src="<c:url value='/static/jquery/jquery.poshytip.min.js'/>"></script>
<link rel="stylesheet" type="text/css"
  href="<c:url value='/static/jquery/tip-yellowsimple/tip-yellowsimple.css'/>">
<link rel="stylesheet" type="text/css"
  href="<c:url value='/static/style/admin/search.css'/>">
<link rel="stylesheet" type="text/css"
  href="<c:url value='/static/style/admin/user/list.css'/>">
<link rel="stylesheet" type="text/css"
  href="<c:url value='/static/style/pager/pager.css'/>">
<script type="text/javascript"
  src="<c:url value='/static/js/common/common.js'/>"></script>
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
    function _addEducationalLevel() {
        var name = $("#educationallevelname").val();//获取文本框中的值
        if(""==name) {//
            $("#flashMsg").text("学历名不能为空!")
            $("#flashMsg").show(300).delay(2000).hide(300);
            return;
        }
        $("#form2").submit();
    }
    $(function() {
        $("#flashMsg").show(300).delay(2000).hide(300);
    })
    
    function changetoedit(obj, id){
        if(obj==true){
            $("#name"+id).css("display","none");
            $("#educationallevel"+id).css("display","");
            $("#edit"+id).css("display","none");
            $("#saveandcancel"+id).css("display","");
            $("#educationallevel"+id).focus();
        }else{
            $("#name"+id).css("display","");
            $("#educationallevel"+id).css("display","none");
            $("#edit"+id).css("display","");
            $("#saveandcancel"+id).css("display","none");
            $("#educationallevel"+id).blur();
        }
    }
    function _edit(obj) {
        var $td = $(obj).parents('tr').children('td');
        var index = $td.eq(0).text();
        var rows = $("#userListTab tr").length;
        for (var i=1;i<rows;i++) {
            changetoedit(false, i);
        }
        changetoedit(true, index);
    }
    function _cancel(obj) {
        var $td = $(obj).parents('tr').children('td');
        var index = $td.eq(0).text();
        var rows = $("#userListTab tr").length;
        for (var i=1;i<rows;i++) {
            changetoedit(false, i);
        }
        changetoedit(false, index);
    }
    function _save(obj) {
        var $td = $(obj).parents('tr').children('td');
        var index = $td.eq(0).text();
        var elid = $("#elid"+index).val();
        var educationallevel = $("#educationallevel"+index).val();
        alert(educationallevel)
        if(elid>1 && ""!=educationallevel && educationallevel.length<=15){
            document.location="/gcoads/admin/EducationalLevelManagementServlet?method=updateEducationalLevel&elid="+elid+"&educationallevel="+educationallevel;
        }
    }
</script>
</head>
<body>
  <div id="funTitleDiv">学历信息列表</div>
  <div id="tableDiv">
    <div id="searchDiv">
      <form
        action="<c:url value='/admin/EducationalLevelManagementServlet'/>"
        method="get" target="body" id="form1">
        <input type="hidden" name="method" value="findEducationalLevel" />
        <input id="username" type="text" name="educationalLevel"
          placeholder="请输入学历名称" maxlength="15"
          value="${educationalLevel }" style="width: 200px" /> <span>
          <a
          href="javascript:document.getElementById('form1').submit();"><img
            id="searchImg" align="top" border="0"
            src="/gcoads/static/images/btn.bmp" /></a>
        </span>
      </form>
      <div id="addeducational"
        style="position: absolute; top: 38px; right: 300px;">
        <form
          action="<c:url value='/admin/EducationalLevelManagementServlet'/>"
          id="form2">
          <input type="hidden" name="method" value="addEducationalLevel" />
          <input type="text" id="educationallevelname"
            name="educationallevelname" placeholder="请输入要新增的学历名称"
            maxlength="15"
            style="width: 200px; height: 30px; border-style: solid; margin: 0px; border-color: #15B69A;" /><span><input
            type="button" value="新增学历" onclick="_addEducationalLevel()"
            value="${educationallevelname }"
            style="height: 30px; width: 75px; background-color: #15B69A; border: 0; margin-left: 5px; color: #FFF; font-size: 14px; font-weight: 600;" /></span>
          <span id="msg">${errorCode }</span>
        </form>
      </div>
    </div>

    <table cellspacing="0" cellpadding="0" id="userListTab">
      <thead>
        <tr style="background-color: #FFF;">
          <th></th>
          <th>学历号</th>
          <th>学历名称</th>
          <th>操作</th>
        </tr>
      </thead>
      <c:forEach varStatus="status" items="${pageBean.beanList }"
        var="educationalLevel">
        <tr>
          <td>${(pageBean.pc - 1) * pageBean.ps + status.index + 1}</td>
          <td>${educationalLevel.elid}<input type="hidden"
            id="elid${status.index + 1 }" name="elid"
            value="${educationalLevel.elid }">
          </td>
          <td><span id="name${status.index + 1 }">${educationalLevel.educationallevel }</span>
            <input id="educationallevel${status.index + 1 }"
            name="educationallevel"
            value="${educationalLevel.educationallevel }" maxlength="15"
            style="display: none;"></td>
          <td><span onclick="_edit(this)"
            id="edit${status.index + 1 }" class="clickspan">编辑</span><span
            id="saveandcancel${status.index + 1 }" style="display: none"><span
              class="clickspan" onclick="_save(this)">保存</span>  /  <span
              class="clickspan" id="cancel${status.index + 1}"
              onclick="_cancel(this)">取消</span></span></td>
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