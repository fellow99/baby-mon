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
<%@ include file="/_inc/head.jsp"%>
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
			<label class="label" for="amount">分量:</label>
			<input name="amount" type="radio" value="1"/>极少
			<input name="amount" type="radio" value="2"/>较少
			<input name="amount" type="radio" value="3" checked/>适中
			<input name="amount" type="radio" value="4"/>较多
			<input name="amount" type="radio" value="5"/>极多
		</p>
		<p>
			<label class="label" for="info">信息:</label>
			<input id="info" name="info" type="text" value="" size="20"/>
		</p>
		<p>
			<label class="label" for="info"></label>
			<input type="button" value="金黄" onclick="$('#info').val(this.value)" />
			<input type="button" value="绿色" onclick="$('#info').val(this.value)" />
			<input type="button" value="拉稀" onclick="$('#info').val(this.value)" />
			<input type="button" value="泡沫" onclick="$('#info').val(this.value)" />
			<input type="button" value="硬块" onclick="$('#info').val(this.value)" />
		</p>
		<p>
			<input name="type" type="hidden" value="shit" />
			<input name="submit" value="提交" type="submit" />
		</p>
   </form>
</section>

<%@ include file="/_inc/footer.jsp"%>

</body>
</html>