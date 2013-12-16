<%@ page language="java" pageEncoding="UTF-8"%>
<%
String report = request.getParameter("report");
%>
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
	var d = 1000*60*60*24;
	var time = new Date().getTime();

	var date = $("#query_date").val();
	if(date == "week"){
	    dateFrom = (Math.floor(time / d - 7) * d);
	    dateTo = (Math.floor(time / d + 2) * d);
	} else if(date == "month"){
	    dateFrom = (Math.floor(time / d - 30) * d);
	    dateTo = (Math.floor(time / d + 2) * d);
	} else if(date == "2month"){
	    dateFrom = (Math.floor(time / d - 60) * d);
	    dateTo = (Math.floor(time / d + 2) * d);
	} else {
	    dateFrom = (Math.floor(time / d - 3000) * d);
	    dateTo = (Math.floor(time / d + 2) * d);
	}

	$("#section_report").setTemplateURL("report.do?method=html&report=<%=report%>&dateFrom=" + dateFrom + "&dateTo=" + dateTo);
	//$("#section_report").setTemplateURL("report.do?method=html&report=<%=report%>");
	$("#section_report").processTemplate({});
}
</script>

</head>

<body>

<%@ include file="/_inc/header.jsp"%>

<%@ include file="/_inc/menu.jsp"%>

<section id="section_query">
	<select id="query_date" onchange="query()">
		<option value="week" selected>近一周</option>
		<option value="month">近一月</option>
		<option value="2month">近两月</option>
		<option value="all">所有</option>
	</select>
</section>
<section id="section_report">
</section>

<%@ include file="/_inc/footer.jsp"%>

</body>
</html>