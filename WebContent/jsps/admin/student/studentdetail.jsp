<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>毕业生详情</title>
<script type="text/javascript"
  src="<c:url value='/static/jquery/jquery-1.5.1.js'/>"></script>
<link rel="stylesheet" type="text/css"
  href="<c:url value='/static/style/admin/studentdetail.css'/>">
<script type="text/javascript">
$(function() {
    $("#shenfenzhenghao").blur(function() {
                        var value = $("#shenfenzhenghao").val();
                        if (!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value)) {
                            alert("错误的身份证号！");
                        }
                    })
    $("#email").blur(function() {
                        var value = $("#email").val();
                        if (!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
                                .test(value)) {
                            alert("错误的Email格式！");
                        }
                    })
})
function _delete(){
    var value = $("#xuehao").val();
    $("#dialogMsgDiv2",parent.document).text("你确定要删除？");
    $("#dialog2",parent.document).show();
    $(".closeDivImg",parent.document).click(function() {
        $(".dialogMsgDiv",parent.document).text("");
        $("#dialog1",parent.document).hide();
        $("#dialog2",parent.document).hide();
    })
    var aaa = $("#Nobutton",parent.document).click(function() {
        $(".dialogMsgDiv",parent.document).text("");
        $("#dialog1",parent.document).hide();
        $("#dialog2",parent.document).hide();
    })
    $("#Yesbutton",parent.document).click(function() {
        $(".dialogMsgDiv",parent.document).text("");
        $("#dialog1",parent.document).hide();
        $("#dialog2",parent.document).hide();
        if ("" != value){
            document.location="/gcoads/admin/GraduateManagementServlet?method=deleteStudents&xuehaos="+value;
        }
    })
}
</script>
</head>
<body>
  <div>
    <form id="singleInfoForm" method="post"
      action="<c:url value='/admin/GraduateManagementServlet?' />">
      <input type="hidden" name="method" value="updateStudentByXuehao">
      <table >
        <tr>
          <td class="star">*</td>
          <td>考生号：</td>
          <td><input type="text" id="kaoshenghao" name="kaoshenghao" required="required"
            value="${student.kaoshenghao }"></td>
        </tr>
        <tr>
          <td class="star" >*</td>
          <td>学号：</td>
          <td><input type="text" id="xuehao" name="xuehao" required="required"
            value="${student.xuehao }"></td>
        </tr>
        <tr>
          <td class="star">*</td>
          <td>身份证号：</td>
          <td><input type="text" id="shenfenzhenghao" name="shenfenzhenghao" required="required"
            value="${student.shenfenzhenghao }"></td>
          <td class="star">*</td>
          <td>出生日期：</td>
          <td><input type="date" id="chushengriqi" name="chushengriqi" required="required"
            value="${student.chushengriqi }"></td>
        </tr>
        <tr>
          <td class="star">*</td>
          <td>姓名：</td>
          <td><input type="text" id="studentname" name="studentname" required="required"
            value="${student.studentname }"></td>
        </tr>
        <tr>
          <td class="star">*</td>
          <td>性别：</td>
          <td><input type="radio" id="studentgender" name="studentgender"
            name="studentgender" value="男"
            <c:if test="${student.studentgender eq '男' }">checked</c:if>>男<input
            type="radio" id="studentgender" name="studentgender"
            <c:if test="${student.studentgender eq '女' }">checked</c:if>
            value="女 ">女</td>
        </tr>
        <tr>
          <td class="star">*</td>
          <td>民族：</td>
          <td><select id="minzu" name="minzu">
              <c:forEach items="${national}" var="str">
                <option
                  <c:if test="${student.minzu eq str}">selected</c:if>>${str}</option>
              </c:forEach>
          </select></td>
          <td class="star">*</td>
          <td>政治面貌：</td>
          <td><select id="zhengzhimianmao" name="zhengzhimianmao">
              <c:forEach items="${zhengzhimianmao}" var="str">
                <option
                  <c:if test="${student.zhengzhimianmao eq str}">selected</c:if>>${str}</option>
              </c:forEach>
          </select></td>
        </tr>
        <tr>
          <td class="star">*</td>
          <td>学院：</td>
          <td><input type="text" id="xueyuan" name="xueyuan" required="required"
            value="${student.xueyuan }"></td>
          <td class="star">*</td>
          <td>系别：</td>
          <td><input type="text" id="xibie" name="xibie" required="required"
            value="${student.xibie }"></td>
        </tr>
        <tr>
          <td class="star">*</td>
          <td>班级：</td>
          <td><input type="text" id="banji" name="banji" required="required"
            value="${student.banji }"></td>
        </tr>
        <tr>
          <td class="star">*</td>
          <td>专业：</td>
          <td><input type="text" id="zhuanye" name="zhuanye" required="required"
            value="${student.zhuanye }"></td>
          <td></td>
          <td>专业方向：</td>
          <td><input type="text" id="zhuanyefangxiang" name="zhuanyefangxiang"
            value="${student.zhuanyefangxiang }"></td>
        </tr>
        <tr>
          <td></td>
          <td>师范生类别：</td>
          <td><input type="radio" class="shifanshengleibie" name="shifanshengleibie"
            <c:if test="${student.shifanshengleibie eq '师范类' }">checked</c:if>
            name="shifanshengleibie" value="师范类">师范类<input
            type="radio" class="shifanshengleibie"
            name="shifanshengleibie"
            <c:if test="${student.shifanshengleibie eq '非师范类' }">checked</c:if>
            value="非师范类">非师范类</td>
        </tr>
        <tr>
          <td></td>
          <td>学制：</td>
          <td><select id="xuezhi" name="xuezhi">
              <c:forEach var="x" begin="1" end="7">
                <option value="${x}"
                  <c:if test="${student.xuezhi eq x}">selected</c:if>>${x}</option>
              </c:forEach>
          </select>年</td>
          <td></td>
          <td>培养方式：</td>
          <td><input type="text" id="peiyangfangshi" name="peiyangfangshi"
            value="${peiyangfangshi }"></td>
        </tr>
        <tr>
          <td></td>
          <td>入学时间：</td>
          <td><input type="date" id="ruxueshijian" name="ruxueshijian"
            value="${student.ruxueshijian }"></td>
          <td></td>
          <td>毕业时间：</td>
          <td><input type="date" id="biyeshijian" name="biyeshijian"
            value="${student.biyeshijian }"></td>
        </tr>
        <tr>
          <td class="star">*</td>
          <td>邮箱：</td>
          <td><input type="text" id="email" name="email"
            value="${student.email }"></td>
          <td class="star">*</td>
          <td>生源所在地：</td>
          <td><input type="text" id="shengyuansuozaidi" name="shengyuansuozaidi" required="required"
            value="${student.shengyuansuozaidi }"></td>
        </tr>
        <tr>
          <td></td>
          <td>家庭地址：</td>
          <td><input type="text" id="address" name="address"
            value="${student.address }"></td>
        </tr>
        <tr>
          <td colspan="7" id="buttonTd"><span> <input
              type="submit" id="save" value="保存">
          </span> <span><input type="button" id="delete" value="删除" onclick="_delete()">
          </span></td>
        </tr>
      </table>
    </form>
  </div>
</body>
</html>