<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" tagdir="/WEB-INF/tags/" %>
<html>
<head>
    <title>123</title>
</head>
<body>
  <div id="main" style="height: 400px;"></div>
  <c:linecharts container="main" title="测试标签" subtitle="测试子标签" urls="/gcoads/StatisticsServlet?method=ajaxEmploymentStatistics"></c:linecharts>
</body>
</html>