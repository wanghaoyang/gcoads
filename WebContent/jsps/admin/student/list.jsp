<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        if(window.confirm("您确定要删除吗？")){
            document.location="/gcoads/admin/GraduateManagementServlet?method=deleteStudents&xuehaos="+chk_value;
        }
    }
    $(function(){
        $("#condition").change(function(){
          var text = $("#condition").find("option:selected").text();
          var selectedVal = $("#condition").find("option:selected").val();
          switch (selectedVal) {
            case "1":
                $("#value").attr('placeholder','请输入姓名');
                break;
            case "2":
                $("#value").attr('placeholder','请输入学号');
                break;
            case "3":
                $("#value").attr('placeholder','请输入学院');
                break;
            default:
                break;
            }
          $("#field").val(text);
        });
    })
</script>
</head>
<body>
  <div id="tableDiv">
    <div id="searchDiv">
      <div>学生信息列表</div>
      <form action="<c:url value='/admin/GraduateManagementServlet'/>"
        method="get" target="body" id="form1">
        <input type="hidden" name="method" value="findStudent" /> <input
          id="value" type="text" name="value" placeholder="请输入姓名" value="${value }"/>
          <input type="hidden" id="field" name="field" value="" />
        <select id="condition">
          <option value="1" selected>姓名</option>
          <option value="2">学号</option>
          <option value="3">学院</option>
        </select>
        
        <span> <a
          href="javascript:document.getElementById('form1').submit();"><img
            id="searchImg" align="top" border="0"
            src="/gcoads/static/images/btn.bmp" /></a>
        </span> <span id="deleteAllSpan"><input type="button" value="删除" id="deleteBtn" onclick="_delete()"></span>
      </form>
    </div>

    <table cellspacing="0" cellpadding="0" id="userListTab">
      <thead>
        <tr>
          <th></th>
          <th>学号</th>
          <th>姓名</th>
          <th>学院</th>
          <th>所在系名称</th>
          <th>所在班级</th>
          <th>专业</th>
          <th id="checkboxTd"><input type="checkbox" id="selectAll"
            onclick="selectAll()" />全选</th>
        </tr>
      </thead>
      <c:forEach varStatus="status" items="${pageBean.beanList }"
        var="student">
        <tr>
          <td>${(pageBean.pc - 1) * pageBean.ps + status.index + 1}</td>
          <td>${student.xuehao}</td>
          <td>${student.studentname }</td>
          <td>${student.xueyuan }</td>
          <td>${student.xibie }</td>
          <td>${student.banji }</td>
          <td>${student.zhuanye }</td>
          
          <td><input type="checkbox" id="subcheck" name="userCheck"
            onclick="setSelectAll()" value="${student.xuehao }"/></td>
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