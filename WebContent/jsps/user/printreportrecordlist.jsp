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
    $(function() {
        $("#flashMsg").show(300).delay(2000).hide(300);
    })
    
</script>
</head>
<body>
  <div id="funTitleDiv">认证记录信息列表</div>
  <div id="tableDiv">
    

    <table cellspacing="0" cellpadding="0" id="userListTab">
      <thead>
        <tr style="background-color: #FFF;">
          <th></th>
          <th>学历证明编号</th>
          <th>学历证明打印时间</th>
          <th>学历证明打印页数</th>
          <th>学历证明打印状态</th>
        </tr>
      </thead>
      <c:forEach varStatus="status" items="${pageBean.beanList }"
        var="printreportrecord">
        <tr>
          <td>${(pageBean.pc - 1) * pageBean.ps + status.index + 1}</td>
          <td>${printreportrecord.docnum}</td>
          <td>${printreportrecord.printdatetime }</td>
          <td>${printreportrecord.printpagenum }</td>
          <td><c:choose>
              <c:when test="${printreportrecord.printstatus }">已打印</c:when>
              <c:otherwise><a href="<c:url value='/PayServlet?method=loadPDF&prrid=${ printreportrecord.prrid}'/>" target="_top">立即打印</a></c:otherwise>
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