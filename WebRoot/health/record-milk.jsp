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
			<input id="amount" name="amount" type="text" value="170" size="8"/> ML
		</p>
		<p>
			<label class="label" for="info"></label>
			<input type="button" value="130" onclick="$('#amount').val(this.value)" />
			<input type="button" value="170" onclick="$('#amount').val(this.value)" />
			<input type="button" value="+10" onclick="$('#amount').val(parseInt($('#amount').val()) + 10)" />
			<input type="button" value="-10" onclick="$('#amount').val(parseInt($('#amount').val()) - 10)" />
		</p>
		<p>
			<label class="label" for="info">信息:</label>
			<input id="info" name="info" type="text" value="牛奶" size="20"/>
		</p>
		<p>
			<label class="label" for="info"></label>
			<input type="button" value="人奶" onclick="$('#info').val(this.value)" />
			<input type="button" value="牛奶" onclick="$('#info').val(this.value)" />
		</p>
		<p>
			<input name="type" type="hidden" value="milk" />
			<input name="submit" value="提交" type="submit" />
		</p>
   </form>
</section>

<%@ include file="/_inc/footer.jsp"%>

</body>
</html>