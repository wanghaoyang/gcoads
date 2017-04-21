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
            alert("123")
            return;
        }
        $("#form2").submit();
    }
    $(function() {
        $("#flashMsg").show(300).delay(2000).hide(300);
    })
</script>
</head>
<body>
  <div id="funTitleDiv">支付记录信息列表</div>
  <div id="tableDiv">
    <div id="searchDiv">
      <form action="<c:url value='/admin/PayRecordManagementServlet'/>"
        method="get" target="body" id="form1">
        <input type="hidden" name="method" value="findPayRecord" /> <input
          id="username" type="text" name="loginname"
          placeholder="请输入用户名" maxlength="15" value="${loginname }"
          style="width: 200px" /> <span> <a
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
          <th>用户名</th>
          <th>支付创建时间</th>
          <th>支付完成时间</th>
          <th>支付总费用</th>
          <th>认证记录数</th>
          <th>支付状态</th>
        </tr>
      </thead>
      <c:forEach varStatus="status" items="${pageBean.beanList }"
        var="payrecord">
        <tr>
          <td>${(pageBean.pc - 1) * pageBean.ps + status.index + 1}</td>
          <td title="${payrecord.loginname}">${payrecord.loginname}</td>
          <td title="${payrecord.paystartdatetime }">${payrecord.paystartdatetime }</td>
          <td title="${payrecord.payfinisheddatetime }">${payrecord.payfinisheddatetime }</td>
          <td title="${payrecord.totalcost }">${payrecord.totalcost }</td>
          <td>${payrecord.certificationquantity }</td>
          <td><c:choose>
              <c:when test="${payrecord.paystatus }">支付完成</c:when>
              <c:otherwise>未完成</c:otherwise>
            </c:choose></td>
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