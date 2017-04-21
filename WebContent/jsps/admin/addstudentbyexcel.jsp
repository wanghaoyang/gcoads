<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加毕业生</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/static/style/css.css'/>">
<script type="text/javascript"
	src="<c:url value='/static/jquery/jquery-1.5.1.js'/>"></script>
<script type="text/javascript">
	function handleFile() {
		$("#uploadfilepath").val($("#excel").val());
	}
	function submitFile() {
		//$("#uploadfilepath").val($("#excel").val());
		doCheck();
		$("#uploadExcelForm").submit();
		$("#tipmsg").text("上传解析中,请勿关闭,请稍后。。。");
		
	}
	function doCheck() {
		var file = $("#excel").val();
		if (file == '' || file == null) {
			$("#error").html("请选择所要上传的文件！");
		} else {
			var index = file.lastIndexOf(".");
			if (index < 0) {
				$("#error").html("上传的文件格式不正确，请选择97-2003Excel文件(*.xls)！");
			} else {
				var ext = file.substring(index + 1, file.length);
				if (ext != "xls") {
					$("#error").html("上传的文件格式不正确，请选择97-2003Excel文件(*.xls)！");
				} else {
					$("#error").hide();
					return true;
				}
			}
		}
		return false;
	}
</script>
</head>
<body>
	<div>
		单击<a href="<c:url value='/admin/DownloadFileServlet?method=downloadExcelFile'/>">这里</a>excel模板
		<div id="error">${msg }</div>
		<form id="uploadExcelForm" enctype="multipart/form-data" method="post"
			action="<c:url value='/admin/GraduateManagementServlet?method=addStudentByExcel' />">
			<input id="uploadfilepath" name="uploadfilepath" type="text"
				placeholder="请选择excel文件" value="${uploadfilepath }"> <input
				id="excel" type="file" name="excel" onchange="handleFile()"
				accept="application/vnd.ms-excel" />
			<br /> <input id="upload" onclick="submitFile()" type="button"
				value="上传" />
		</form>
    <%-- <form name="uploadform" action="<c:url value='/admin/GraduateManagementServlet?method=addStudentByExcel' />" ENCTYPE="multipart/form-data">
        <table border="1" width="450" cellpadding="4" cellspacing="2" bordercolor="#9BD7FF">
        <tr>
            <td width="100%" colspan="2">

                            文件：<input name="x" size="40" type="file">

            </td>
        </tr>
        </table>
        <br/><br/>

        <table>
        <tr><td align="center"><input name="upload" type="submit" value="开始上传"/></td></tr>
        </table>

</form> --%>
	</div>
	<div id="tipmsg" style="color: #F00;"></div>
</body>
</html>