<%@ page language="java" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<%@ include file="/_inc/head.jsp"%>
<%@ include file="/_inc/js.jsp"%>

</head>

<body>

<%@ include file="/_inc/header.jsp"%>

<%@ include file="/_inc/menu.jsp"%>

<div id="menu-list">
    <ul>
        <li><a href="../report/report-date.jsp?report=date">日报表</a></li>
        <li><a href="../report/report-date.jsp?report=date-chart">日图表</a></li>
        <li><a href="../report/report-date.jsp?report=date-chart-milk">牛奶占比</a></li>
        <li><a href="../report/report-date.jsp?report=week-chart">周图表</a></li>
        <li><a href="../report/report-date.jsp?report=weight-chart">体重图表</a></li>
        <li><a href="../report/report-date.jsp?report=height-chart">身高图表</a></li>
        <li><a href="../report/report-date.jsp?report=time-chart-milk">喂奶时间分布</a></li>
    </ul>
</div>

<%@ include file="/_inc/footer.jsp"%>

</body>
</html>