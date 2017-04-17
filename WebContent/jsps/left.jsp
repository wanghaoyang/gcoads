<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>
<html>
<head>
<title>左侧导航</title>
<script type="text/javascript"
  src="<c:url value='/static/jquery/jquery-1.5.1.js'/>"></script>
<link rel="stylesheet" type="text/css"
  href="<c:url value='/static/style/left.css'/>">
</head>

<body id="bg">

  <div class="container">
    <div class="leftsidebar_box">
      <div class="line"></div>
      <dl class="system_log">
        <dt>
          我的资料<img src="/gcoads/static/images/left/select_xl01.png">
        </dt>
        <dd>
          <a
            href="<c:url value='/GraduateServlet?method=findGraduateByLoginname'/>"
            target="body">个人信息</a>
        </dd>
        <dd>
          <a href="<c:url value='/jsps/user/pwd.jsp'/>" target="body">修改密码</a>
        </dd>
      </dl>

      <dl class="custom">
        <dt>
          学历证明<img src="/gcoads/static/images/left/select_xl01.png">
        </dt>
        <dd>
          <a href="<c:url value='/GraduateServlet?method=findGraduateEdu'/>"
            target="body">学历查询</a>
        </dd>
        <dd>
          <a
            href="<c:url value='/GraduateServlet?method=findGraduate'/>"
            target="body">申请学历证明</a>
        </dd>
        <c:if test="${!('企业用户' eq sessionScope.sessionUser.role) } ">
          <dl class="statistics">
            <dt>
              就业统计<img src="/gcoads/static/images/left/select_xl01.png"></img>
            </dt>
            <dd>
              <a href="#">就业统计</a>
            </dd>
          </dl>
        </c:if>
    </div>

  </div>
  <script type="text/javascript">
            $(".leftsidebar_box dt").css({
                "background-color" : "#15B69A"
            });
            $(".leftsidebar_box dt img").attr("src",
                    "/gcoads/static/images/left/select_xl01.png");
            $(function() {
                $(".leftsidebar_box dd").hide();
                $(".leftsidebar_box dt")
                        .click(
                                function() {
                                    $(".leftsidebar_box dt").css({
                                        "background-color" : "#15B69A"
                                    })
                                    $(this).css({
                                        "background-color" : "#86B69A"
                                    });
                                    $(this).parent().find('dd').removeClass(
                                            "menu_chioce");
                                    $(".leftsidebar_box dt img")
                                            .attr("src",
                                                    "/gcoads/static/images/left/select_xl01.png");
                                    $(this)
                                            .parent()
                                            .find('img')
                                            .attr("src",
                                                    "/gcoads/static/images/left/select_xl.png");
                                    $(".menu_chioce").slideUp();
                                    $(this).parent().find('dd').slideToggle();
                                    $(this).parent().find('dd').addClass(
                                            "menu_chioce");
                                });
            });
        </script>
</body>
</html>

