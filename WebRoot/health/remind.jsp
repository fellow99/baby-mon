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
		url:"remind-data.do?method=last",
		dataType:"json",
		success: function(data, status){
			$("#section_last").setTemplateURL("_tpl/last.html");
			$("#section_last").processTemplate(data);
		},
		error: function(xhr, status, error){
	        alert(status + " " + error);  
		}
	});
	$.ajax({type:"POST",
		async:false,
		url:"remind-data.do?method=today",
		dataType:"json",
		success: function(data, status){
			$("#section_today").setTemplateURL("_tpl/sum.html");
			$("#section_today").processTemplate(data);
		},
		error: function(xhr, status, error){
	        alert(status + " " + error);  
		}
	});
	$.ajax({type:"POST",
		async:false,
		url:"remind-data.do?method=yesterday",
		dataType:"json",
		success: function(data, status){
			$("#section_yesterday").setTemplateURL("_tpl/sum.html");
			$("#section_yesterday").processTemplate(data);
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

<div>最近</div>
<section id="section_last">
</section>
<hr align="left" width="50%"/>
<div>今天总量</div>
<section id="section_today">
</section>
<hr align="left" width="50%"/>
<div>昨天总量</div>
<section id="section_yesterday">
</section>

<%@ include file="/_inc/footer.jsp"%>

</body>
</html>