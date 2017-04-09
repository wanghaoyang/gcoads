function _change() {
	$("#vCode").attr("src", "/gcoads/VerifyCodeServlet?" + new Date().getTime());
}

//复选框事件  
//全选、取消全选的事件  
function selectAll(){  
    if ($("#selectAll").attr("checked")) {  
        $(":checkbox").attr("checked", true);  
    } else {  
        $(":checkbox").attr("checked", false);  
    }  
}  
//子复选框的事件  
function setSelectAll(){  
    //当没有选中某个子复选框时，SelectAll取消选中  
    if (!$("#subcheck").checked) {  
        $("#selectAll").attr("checked", false);  
    }  
    var chsub = $("input[type='checkbox'][id='subcheck']").length; //获取subcheck的个数  
    var checkedsub = $("input[type='checkbox'][id='subcheck']:checked").length; //获取选中的subcheck的个数  
    if (checkedsub == chsub) {  
        $("#selectAll").attr("checked", true);  
    }  
} 