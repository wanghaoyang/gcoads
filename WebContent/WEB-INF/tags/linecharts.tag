<%@ tag pageEncoding="UTF-8" isELIgnored="false" body-content="empty"%>
<%--自定义div容器id--%>
<%@attribute name="container" required="true" %>
<%--自定义标题--%>
<%@attribute name="title" required="true" %>
<%--自定义子标题--%>
<%@attribute name="subtitle" required="false" %>
<%--自定义数据请求url--%>
<%@attribute name="urls" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="<c:url value='/static/js/echarts-2.2.7/build/dist/jquery.min.js'/>"></script>
<script src="<c:url value='/static/js/echarts-2.2.7/build/dist/echarts-all.js'/>"></script>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts图表
    var myChart = echarts.init(document.getElementById('${container}'));
    var option={
        title : {
            text: '${title}',
            subtext: '${subtitle}'
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:[]
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable: true,
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : []
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLabel : {
                    formatter: '{value} '
                }
            }
        ],
        series : []
    };
    //采用ajax异步请求数据
    function getemploymentstatistics(key, value, subtext){
        $.ajax({
            type:'post',
            url:'${urls}',
            data:{'key':key,'value':value},
            dataType:'json',
            success:function(result){
                if(result){
                    //将返回的category和series对象赋值给options对象内的category和series
                    option.xAxis[0].data = result.axis;
                    option.legend.data = result.legend;
                    var series_arr=result.series;
                    for(var i=0;i<series_arr.length;i++){
                        option.series[i] = result.series[i];
                    }
                    myChart.hideLoading();
                    option.title.subtext=subtext;
                    myChart.setOption(option,true);
                }
             },
            error:function(errMsg){
                console.error("加载数据失败")
            }
        });
    }
    // 为echarts对象加载数据
   // myChart.setOption(option);
</script>