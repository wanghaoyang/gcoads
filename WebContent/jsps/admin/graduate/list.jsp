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
                $("#value").attr('placeholder','请输入毕业年份');
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
  <div id="funTitleDiv">毕业生信息列表</div>
  <div id="tableDiv">
    <div id="searchDiv">
      <form action="<c:url value='/admin/GraduateManagementServlet'/>"
        method="get" target="body" id="form1">
        <input type="hidden" name="method" value="findGraduate" />
        <input id="value" type="text" name="value" placeholder="请输入姓名" value="${value }"/>
        <input type="hidden" id="field" name="field" value="" />
        <select id="condition">
          <option value="1" selected>姓名</option>
          <option value="2">学号</option>
          <option value="3">毕业年份</option>
        </select>
          
        <span> <a
          href="javascript:document.getElementById('form1').submit();"><img
            id="searchImg" align="top" border="0"
            src="/gcoads/static/images/btn.bmp" /></a>
        </span>
      </form>
    </div>

    <table cellspacing="0" cellpadding="0" id="userListTab">
      <thead>
        <tr style="background-color: #FFF;">
          <th></th>
          <th>学号</th>
          <th>姓名</th>
          <th>学院</th>
          <th>学历</th>
          <th>毕业时间</th>
          <th>状态</th>
        </tr>
      </thead>
      <c:forEach varStatus="status" items="${pageBean.beanList }"
        var="graduate">
        <tr>
          <td>${(pageBean.pc - 1) * pageBean.ps + status.index + 1}</td>
          <td>${graduate.xuehao}</td>
          <td>${graduate.studentname }</td>
          <td>${graduate.xueyuan }</td>
          <td>${graduate.xueli.educationalLevel }</td>
          <td>${graduate.biyeshijian }</td>
          <td>${graduate.gstatus }</td>
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