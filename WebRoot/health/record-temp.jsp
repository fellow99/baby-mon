<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@page import="java.text.*"%>
<%
SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");

%>
<!doctype html>
<html>
<head>
<title>天天小宝贝日志</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<%@ include file="/_inc/js.jsp"%>

<script type="text/javascript">
function submitForm(){
	$("#form").ajaxSubmit({
	    type: 'post',  
	    url: "record-action.do?method=insert",
		success: function(data, status){
	        window.location = "../index.jsp";
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

<section id="section_form">
   <form id="form" name="form" method="post" action="" onsubmit="submitForm();return false">
		<p>
			<label class="label" for="date">日期:</label>
			<input name="date" type="text" value="<%=formatDate.format(new Date())%>" size="12"/>
		</p>
		<p>
			<label class="label" for="time">时间:</label>
			<input name="time" type="text" value="<%=formatTime.format(new Date())%>" size="8"/>
		</p>
		<p>
			<label class="label" for="amount">体温:</label>
			<input name="amount" type="text" value="" size="8"/> 度
		</p>
		<p>
			<label class="label" for="info">信息:</label>
			<input name="info" type="text" value="" size="20"/>
		</p>
		<p>
			<input name="type" type="hidden" value="temp" />
			<input name="submit" value="提交" type="submit" />
		</p>
   </form>
</section>

<%@ include file="/_inc/footer.jsp"%>

</body>
</html>