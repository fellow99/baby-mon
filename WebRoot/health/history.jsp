<%@ page language="java" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<%@ include file="/_inc/head.jsp"%>
<%@ include file="/_inc/js.jsp"%>

<script type="text/javascript">

$(document).ready(function(){
	query()
});

function query(){
	var dateFrom = null;
	var dateTo = null;
	var type = null;
	var d = 1000*60*60*24;
	var time = new Date().getTime();

	var date = $("#query_date").val();
	if(date == "today"){
	    dateFrom = new Date();
	    dateFrom.setTime(Math.floor(time / d) * d);
	    dateFrom = toDatetime(dateFrom.getTime(), "yyyy-MM-dd");
	    dateTo = new Date();
	    dateTo.setTime(Math.floor(time / d + 2) * d);
	    dateTo = toDatetime(dateTo.getTime(), "yyyy-MM-dd");
	} else if(date == "week"){
	    dateFrom = new Date();
	    dateFrom.setTime(Math.floor(time / d - 7) * d);
	    dateFrom = toDatetime(dateFrom.getTime(), "yyyy-MM-dd");
	    dateTo = new Date();
	    dateTo.setTime(Math.floor(time / d + 2) * d);
	    dateTo = toDatetime(dateTo.getTime(), "yyyy-MM-dd");
	} else if(date == "month"){
	    dateFrom = new Date();
	    dateFrom.setTime(Math.floor(time / d - 30) * d);
	    dateFrom = toDatetime(dateFrom.getTime(), "yyyy-MM-dd");
	    dateTo = new Date();
	    dateTo.setTime(Math.floor(time / d + 2) * d);
	    dateTo = toDatetime(dateTo.getTime(), "yyyy-MM-dd");
	} else {
	    dateFrom = new Date();
	    dateFrom.setTime(Math.floor(time / d - 3000) * d);
	    dateFrom = toDatetime(dateFrom.getTime(), "yyyy-MM-dd");
	    dateTo = new Date();
	    dateTo.setTime(Math.floor(time / d + 2) * d);
	    dateTo = toDatetime(dateTo.getTime(), "yyyy-MM-dd");
	}

	type = $("#query_type").val();
	$.ajax({type:"POST",
		async:false,
		url:"record-data.do?method=history",
		data: {
			dateFrom: dateFrom,
			dateTo: dateTo,
			type: type
		},
		dataType:"json",
		success: function(data, status){
			$("#section_history").setTemplateURL("_tpl/history.html");
			$("#section_history").setParam("readonly", true);
			$("#section_history").processTemplate(data);
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

<section id="section_query">
	<select id="query_date" onchange="query()">
		<option value="today" selected>今日</option>
		<option value="week">近一周</option>
		<option value="month">近一月</option>
		<option value="all">所有</option>
	</select>
	<select id="query_type" onchange="query()">
		<option value="" selected>所有</option>
        <option value="info">信息</option>
        <option value="milk">喝奶</option>
        <option value="shit">大便</option>
        <option value="weight">体重</option>
        <option value="temp">体温</option>
        <option value="heart">心率</option>
        <option value="height">身高</option>
        <option value="juice">挤奶</option>
	</select>
</section>

<section id="section_history">
</section>

<%@ include file="/_inc/footer.jsp"%>

</body>
</html>