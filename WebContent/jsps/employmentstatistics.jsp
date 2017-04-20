<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="est" tagdir="/WEB-INF/tags/" %>
<html>
<head>
    <title>毕业就业统计</title>
</head>
<body>
<div style="margin:30px 0 0 30px">
<div><span>统计方式:</span查看>
<select id="key" name="key" style="border: 2px solid #15B69A;width: 100px;height: 30px;">
<option value="year" selected>毕业年份</option>
<option value="xueyuan">学院名称</option>
</select>
<select id="value" name="value" style="border: 2px solid #15B69A;height: 30px;">
</select>
</div>
  <div id="main" style="height: 450px;"></div>
  <est:linecharts container="main" title="毕业就业情况统计" subtitle="" urls="/gcoads/StatisticsServlet?method=ajaxEmploymentStatistics"></est:linecharts>
  </div>
</body>
<script type="text/javascript">
	$(function(){
		getCondition();
		
		getemploymentstatistics($("#key").val(), $("#value").val(),$("#value").val()+"届毕业生各学院毕就业统计");
	})
	$("#key").change(function() {
	    getCondition();
	    var key = $("#key").val();
        var value = $("#value").val();
	    var value = $("#value").val();
        if ("xueyuan"== key){
            subtitle = value + "各届毕业生毕就业统计";
        } else{
            subtitle = value + "届毕业生各学院毕就业统计";
        }
        getemploymentstatistics(key, value,subtitle);
    })
    $("#value").change(function() {
    	var key = $("#key").val();
        var value = $("#value").val();
        if ("xueyuan"== key){
        	subtitle = value + "各届毕业生毕就业统计";
        } else{
        	subtitle = value + "届毕业生各学院毕就业统计";
        }
    	getemploymentstatistics(key, value,subtitle);
    })
	function getCondition(){
	    var condition = $("#key").val();
	    $.ajax({
	        type:'post',
	        async: false,
	        url:'/gcoads/StatisticsServlet?method=ajaxFindValue',
	        data:{'key':condition},
	        success:function(data){
	            if(data != null && "" != data){
	                //将返回的category和series对象赋值给options对象内的category和series
                /* option.xAxis[0].data = result.axis;
                option.legend.data = result.legend;
                var series_arr=result.series;
                for(var i=0;i<series_arr.length;i++){
                    option.series[i] = result.series[i];
                }
                myChart.hideLoading();
                myChart.setOption(option); */
                var arr = data.split(',');
                $("#value").empty();
                for (i = 0; i < arr.length; i++) {
                    if (arr[i] != ""){
                        $("#value").append("<option value='"+trim(arr[i])+"'>"+trim(arr[i])+"</option>");
                    }
                }
            }
         },
        error:function(errMsg){
            console.error("加载数据失败")
        }
    });
    $("#value option:first").attr("selected",true);
}
function trim(s){
    return s.replace(/(^\s*)|(\s*$)/g, "");
}
</script>

</html>