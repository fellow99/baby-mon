<%@ page language="java" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<%@ include file="/_inc/head.jsp"%>
<%@ include file="/_inc/js.jsp"%>

<script type="text/javascript">

$(document).ready(function(){
	loadInfo();
});

//加载已填写信息
function loadInfo(){
	$.ajax({type:"POST",
		async:false,
		url:"record-data.do?method=today",
		dataType:"json",
		success: function(data, status){
			$("#section_history").setTemplateURL("_tpl/history.html");
			$("#section_history").processTemplate(data);
		},
		error: function(xhr, status, error){
	        alert(status + " " + error);  
		}
	});
}

function deleteRecord(date, type, id){
	if(!confirm("是否确认删除该记录：" + date))return;

	$.ajax({
	    type: 'post',  
	    url: "record-action.do?method=delete" ,
	    data: {type: type, id: id},
		success: function(data, status){
	        window.location = "today.jsp";
	    },  
		error: function(xhr, status, error){
	        alert(status + " " + error);  
	    }  
	});
}
</script>

</head>

<body>

<%@ include file="/_inc/header.jsp"%>

<%@ include file="/_inc/menu.jsp"%>

<section id="section_history">
</section>

<%@ include file="/_inc/footer.jsp"%>

</body>
</html>