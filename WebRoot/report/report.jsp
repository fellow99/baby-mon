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
	$("#section_report").setTemplateURL("report.do?method=html&report=<%=report%>");
	$("#section_report").processTemplate({});
});
</script>

</head>

<body>

<%@ include file="/_inc/header.jsp"%>

<%@ include file="/_inc/menu.jsp"%>

<section id="section_report">
</section>

<%@ include file="/_inc/footer.jsp"%>

</body>
</html>