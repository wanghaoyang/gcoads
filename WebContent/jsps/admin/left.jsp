<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
  <head>
    <title>左侧导航</title>
    <script type="text/javascript" src="<c:url value='/static/jquery/jquery-1.5.1.js'/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/style/left.css'/>" >
</head>

<body id="bg">

<div class="container">

    <div class="leftsidebar_box">
        <div class="line"></div>
        <dl class="system_log">
            <dt>用户管理<img src="/gcoads/static/images/left/select_xl01.png" ></dt>
            <dd><a href="<c:url value='/admin/UserManagementServlet?method=findUser'/>" target="body">用户信息管理</a></dd>
        </dl>
    
        <dl class="custom">
            <dt>毕业生管理<img src="/gcoads/static/images/left/select_xl01.png"></dt>
            <dd><a href="/admin/GraduateManagementServlet?method=findStudent" target="body">毕业生基本信息管理</a></dd>
            <dd><a href="/admin/GraduateManagementServlet?method=findGraduate" target="body">毕业生毕业信息管理</a></dd>
            <dd><a href="/admin/GraduateManagementServlet?method=findVerifyEducation" target="body">毕业生学历认证管理</a></dd>
            <dd><a href="/admin/GraduateManagementServlet?method=addStudent" target="body">毕业生信息录入</a></dd>
        </dl>
    
        <dl class="channel">
            <dt>学历管理<img src="/gcoads/static/images/left/select_xl01.png"></dt>
            <dd><a href="/admin/EducationManagementServlet?method=addEducationalLevel" target="body">学历信息管理</a></dd>
        </dl>
        <dl class="app">
            <dt>支付管理<img src="/gcoads/static/images/left/select_xl01.png"></dt>
            <dd><a href="#">支付渠道管理</a></dd>
            <dd><a href="#">支付费用管理</a></dd>
        </dl>
    
        <dl class="statistics">
            <dt>统计分析<img src="/gcoads/static/images/left/select_xl01.png" ></img></dt>
            <dd><a href="#">就业统计</a></dd>
        </dl>
    
    </div>

</div>

<script type="text/javascript" src="<c:url value='/static/jquery/jquery-1.5.1.js'/>"></script>
<script type="text/javascript">
$(".leftsidebar_box dt img").attr("src","/gcoads/static/images/left/select_xl01.png");
$(function(){
    $(".leftsidebar_box dd").hide();
    $(".leftsidebar_box dt").click(function(){
        $(this).parent().find('dd').removeClass("menu_chioce");
        $(".leftsidebar_box dt img").attr("src","/gcoads/static/images/left/select_xl01.png");
        $(this).parent().find('img').attr("src","/gcoads/static/images/left/select_xl.png");
        $(".menu_chioce").slideUp(); 
        $(this).parent().find('dd').slideToggle();
        $(this).parent().find('dd').addClass("menu_chioce");
    });
})
</script>
</body>
</html>

