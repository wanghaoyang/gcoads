<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网银支付</title>
<script type="text/javascript"
  src="<c:url value='/static/jquery/jquery-1.5.1.js'/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/static/style/pay.css'/>">
</head>
<body>
<div class="divContent">
    <span >${yh }</span>
</div>
	<div style="border: 5px solid #efeae5; width: 880px; height: 430px; margin-left: 50px; margin-top: 5px;">
	<div style="margin: 60px 0 0 30px;">
		<div id="orderDiv">
			<div id="orderDivTop">订单信息</div>
			<div id="tableDiv">
				<table id="orderTab">
					<tr class="redBFont">
						<td>商户名称：</td>
						<td>毕业生学历证明系统</td>
					</tr>
					<tr>
						<td class="redBFont" style="text-align: right;">金额：</td>
						<td><span class="redBFont">RMB</span><span id="cost">${payRecord.totalcost }</span></td>
					</tr>
					<tr>
					<tr>
						<td>订单编号：</td>
						<td>${payRecord.payid }</td>
					</tr>
					<td>订单时间：</td>
					<td>${ payRecord.paystartdatetime}</td>
					</tr>
					<tr>
						<td>订单说明：</td>
						<td>学历证明</td>
					</tr>
				</table>
			</div>
		</div>

		<div id="loginBankDiv">
			<div id="bankDivTop">网银支付</div>
			<form method="post" action="<c:url value='/PayServlet'/>" >
			<input type="hidden" name="method" value="finishPay">
			<input type="hidden" name="prid" value="${payRecord.prid}">
				<div style="text-align: center; margin-top: 20px;">
					<span class="redBFont">*</span><span>请仔细核对左侧订单信息，再输入卡号和密码</span>
				</div>
				<div style="text-align: center; margin-top: 20px;">
					<div id="msg" style="color: #F00;height: 20px;">${msg }</div>
					<div >
						<span>卡号:</span><input type="text" id="bankloginname" class="bankinput" name="bankloginname" maxlength="18" required>
					</div>
					<div style="margin-top: 20px;">
						<span>密码:</span><input type="password" id="bankpassword" class="bankinput" name="bankpassword" maxlength="16" required>
					</div>
				</div>
				<div style="text-align: center;margin-top:30px;">
				<span><input type="submit" id="pay" value="支付"></span>
				<span><input type="reset" id="reset" value="重填"></span>
				</div>
			</form>
		</div>
		</div>
	</div>
</body>
<script type="text/javascript">
$(".bankinput").focus(function(){
	$("#msg").text("");
})
</script>
</html>