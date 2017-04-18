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
          <td></td>
          <td>学历：</td>
          <td><select id="xueli" name="educationallevel">
              <c:forEach items="${educationalLevelList }"
                var="educationalLevel">
                <option value="${educationalLevel.educationallevel}"
                  <c:if test="${graduate.elid == educationalLevel.elid}">selected</c:if>>${educationalLevel.educationallevel}</option>
              </c:forEach>
          </select></td>
        </tr>
        <tr>
          <td></td>
          <td>毕业状态：</td>
          <td><input type="radio" class="gstatus" name="gstatus"
            value="未毕业"
            <c:if test="${graduate.gstatus eq '未毕业' }" >checked</c:if>>未毕业
            <input type="radio" class="gstatus" name="gstatus"
            value="待审核"
            <c:if test="${graduate.gstatus eq '待审核' }" >checked</c:if>>待审核
            <input type="radio" class="gstatus" name="gstatus"
            value="毕业"
            <c:if test="${graduate.gstatus eq '毕业' }">checked</c:if>>毕业</td>
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
          <td></td>
          <td>就业城市：</td>
          <td colspan="4"><select
            onchange="chinaChange(this,document.getElementById('chengshi'));"
            style="width: 30%;" id="shengfen" name="shengfen">
              <option value="请选择市区">请选择省份</option>
              <option value="北京市">北京市</option>
              <option value="天津市">天津市</option>
              <option value="上海市">上海市</option>
              <option value="重庆市">重庆市</option>
              <option value="河北省">河北省</option>
              <option value="山西省">山西省</option>
              <option value="辽宁省">辽宁省</option>
              <option value="吉林省">吉林省</option>
              <option value="黑龙江省">黑龙江省</option>
              <option value="江苏省">江苏省</option>
              <option value="浙江省">浙江省</option>
              <option value="安徽省">安徽省</option>
              <option value="福建省">福建省</option>
              <option value="江西省">江西省</option>
              <option value="山东省">山东省</option>
              <option value="河南省">河南省</option>
              <option value="湖北省">湖北省</option>
              <option value="湖南省">湖南省</option>
              <option value="广东省">广东省</option>
              <option value="海南省">海南省</option>
              <option value="四川省">四川省</option>
              <option value="贵州省">贵州省</option>
              <option value="云南省">云南省</option>
              <option value="陕西省">陕西省</option>
              <option value="甘肃省">甘肃省</option>
              <option value="青海省">青海省</option>
              <option value="台湾省">台湾省</option>
              <option value="广西壮族自治区">广西壮族自治区</option>
              <option value="内蒙古自治区">内蒙古自治区</option>
              <option value="西藏自治区">西藏自治区</option>
              <option value="宁夏回族自治区">宁夏回族自治区</option>
              <option value="新疆维吾尔自治区">新疆维吾尔自治区</option>
              <option value="香港特别行政区">香港特别行政区</option>
              <option value="澳门特别行政区">澳门特别行政区</option>
          </select><input type="hidden" id="province" name="province"
            value="${graduate.province }"> <select
            name="chengshi" id="chengshi"
            onchange="selectchengshi(this)" style="width: 30%;">
              <option value="请选择市区">请选择市区</option>
          </select><input type="hidden" id="city" name="city"
            value="${graduate.city}"></td>
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