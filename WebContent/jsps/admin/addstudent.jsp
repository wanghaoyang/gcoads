<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加毕业生</title>
<script type="text/javascript"
  src="<c:url value='/static/jquery/jquery-1.5.1.js'/>"></script>
<link rel="stylesheet" type="text/css"
  href="<c:url value='/static/style/admin/studentform.css'/>">
<script type="text/javascript">
    var national = [ "汉族", "壮族", "满族", "回族", "苗族", "维吾尔族", "土家族", "彝族", "蒙古族",
            "藏族", "布依族", "侗族", "瑶族", "朝鲜族", "白族", "哈尼族", "哈萨克族", "黎族", "傣族",
            "畲族", "傈僳族", "仡佬族", "东乡族", "高山族", "拉祜族", "水族", "佤族", "纳西族", "羌族",
            "土族", "仫佬族", "锡伯族", "柯尔克孜族", "达斡尔族", "景颇族", "毛南族", "撒拉族", "布朗族",
            "塔吉克族", "阿昌族", "普米族", "鄂温克族", "怒族", "京族", "基诺族", "德昂族", "保安族",
            "俄罗斯族", "裕固族", "乌孜别克族", "门巴族", "鄂伦春族", "独龙族", "塔塔尔族", "赫哲族", "珞巴族" ];
    var zhengzhimianmao = [ "群众", "共青团员", "中共党员", "民革党员", "民盟盟员", "民建会员",
            "民进会员", "农工党党员", "致公党党员", "九三学社社员", "台盟盟员", "无党派人士" ];
    window.onload = function() {
        var nat = document.getElementById("minzu");
        for (var i = 0; i < national.length; i++) {
            var option = document.createElement('option');
            option.value = national[i];
            var txt = document.createTextNode(national[i]);
            option.appendChild(txt);
            nat.appendChild(option);
        }
        var zzmm = document.getElementById("zhengzhimianmao");
        for (var i = 0; i < zhengzhimianmao.length; i++) {
            var option = document.createElement('option');
            option.value = zhengzhimianmao[i];
            var txt = document.createTextNode(zhengzhimianmao[i]);
            option.appendChild(txt);
            zzmm.appendChild(option);
        }
    }
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
</script>
</head>
<body>
  <div>
    <form id="singleInfoForm" method="post"
      action="<c:url value='/admin/GraduateManagementServlet' />">
      <input type="hidden" name="method" value="addStudentByForm">
      <table >
        <tr>
          <td class="star">*</td>
          <td>考生号：</td>
          <td><input type="text" id="kaoshenghao" name="kaoshenghao" maxlength="20" required="required"></td>
        </tr>
        <tr>
          <td class="star">*</td>
          <td>学号：</td>
          <td><input type="text" id="xuehao" name="xuehao" maxlength="15" required="required"></td>
        </tr>
        <tr>
          <td class="star">*</td>
          <td>身份证号：</td>
          <td><input type="text" id="shenfenzhenghao" maxlength="18" name="shenfenzhenghao" required="required"></td>
          <td id="star" class="star">*</td>
          <td>出生日期：</td>
          <td><input type="date" id="chushengriqi" name="chushengriqi"
            required="required"></td>
        </tr>
        <tr>
          <td class="star">*</td>
          <td>姓名：</td>
          <td><input type="text" id="studentname" maxlength="30" name="studentname"
            required="required"></td>
        </tr>
        <tr>
          <td class="star">*</td>
          <td>性别：</td>
          <td><input type="radio" id="studentgender"
            name="studentgender" value="男" checked>男<input
            type="radio" id="studentgender" name="studentgender"
            value="女">女</td>
        </tr>
        <tr>
          <td class="star">*</td>
          <td>民族：</td>
          <td><select id="minzu" name="minzu"></select></td>
          <td class="star">*</td>
          <td>政治面貌：</td>
          <td><select id="zhengzhimianmao" name="zhengzhimianmao"></select></td>
        </tr>
        <tr>
          <td class="star">*</td>
          <td>学院：</td>
          <td><input type="text" id="xueyuan" required="required" name="xueyuan"
            maxlength="30"></td>
          <td class="star">*</td>
          <td>系别：</td>
          <td><input type="text" id="xibie" required="required" name="xibie"
            maxlength="30"></td>
        </tr>
        <tr>
          <td class="star">*</td>
          <td>班级：</td>
          <td><input type="text" id="banji" required="required"
            maxlength="30"></td>
        </tr>
        <tr>
          <td class="star">*</td>
          <td>专业：</td>
          <td><input type="text" id="zhuanye" required="required" name="zhuanye"
            maxlength="30"></td>
          <td></td>
          <td>专业方向：</td>
          <td><input type="text" id="zhuanyefangxiang" name="zhuanyefangxiang"
            maxlength="30"></td>
        </tr>
        <tr>
          <td></td>
          <td>师范生类别：</td>
          <td><input type="radio" class="shifanshengleibie"
            name="shifanshengleibie" value="师范生">师范生<input type="radio"
            class="shifanshengleibie" name="shifanshengleibie" value="师范生" checked>非师范生</td>
        </tr>
        <tr>
          <td></td>
          <td>学制：</td>
          <td><select id="xuezhi" name="xuezhi"><c:forEach var="x"
                begin="1" end="7">
                <c:choose>
                  <c:when test="${x eq 4}">
                    <option selected>${x}</option>
                  </c:when>
                  <c:otherwise>
                    <option>${x}</option>
                  </c:otherwise>
                </c:choose>
              </c:forEach></select>年</td>
          <td></td>
          <td>培养方式：</td>
          <td><input type="text" id="peiyangfangshi" name="peiyangfangshi"
            value="${peiyangfangshi }" maxlength="30"></td>
        </tr>
        <tr>
          <td></td>
          <td>入学时间：</td>
          <td><input type="date" id="ruxueshijian" name="ruxueshijian"></td>
          <td></td>
          <td>毕业时间：</td>
          <td><input type="date" id="biyeshijian" name="biyeshijian"></td>
        </tr>
        <tr>
          <td></td>
          <td>学历：</td>
          <td><select id="xueli" name="educationalLevel">
              <c:forEach items="${educationalLevelList }"
                var="educationalLevel">
                <option value="${educationalLevel.educationallevel}">${educationalLevel.educationallevel}</option>
              </c:forEach>
          </select></td>
        </tr>
        <tr>
          <td></td>
          <td>毕业状态：</td>
          <td><input type="radio" class="gstatus" name="gstatus" value="师范生"
            checked>未毕业 <input type="radio" class="gstatus" value="待审核"
            name="gstatus">待审核 <input type="radio" value="待审核"
            class="gstatus" name="gstatus">毕业</td>
        </tr>
        <tr>
          <td></td>
          <td>邮箱：</td>
          <td><input type="text" id="email" name="email"></td>
          <td class="star">*</td>
          <td>生源所在地：</td>
          <td><input type="text" id="shengyuansuozaidi" name="shengyuansuozaidi" required="required"
            maxlength="30"></td>
        </tr>
        <tr>
          <td></td>
          <td>家庭地址：</td>
          <td><input type="text" id="address" name="address" maxlength="80"></td>
        </tr>
        <tr>
          <td colspan="6" id="buttonTd"><span> <input
              type="submit" id="save" value="提交">
          </span> <span> <input type="reset" id="reset" value="重置">
          </span></td>
        </tr>
      </table>
    </form>
  </div>
</body>
</html>