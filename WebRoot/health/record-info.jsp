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
$(document).ready(function(){
	infoHistory()
});


function infoHistory(){
	var dateFrom = null;
	var dateTo = null;
	var d = 1000*60*60*24;
	var time = new Date().getTime();

    dateFrom = new Date();
    dateFrom.setTime(Math.floor(time / d - 3) * d);
    dateFrom = toDatetime(dateFrom.getTime(), "yyyy-MM-dd");
    dateTo = new Date();
    dateTo.setTime(Math.floor(time / d + 2) * d);
    dateTo = toDatetime(dateTo.getTime(), "yyyy-MM-dd");

	var type = "info";
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
			$("#section_info_history").setTemplateURL("_tpl/info-history.html");
			$("#section_info_history").processTemplate(data);
		},
		error: function(xhr, status, error){
	        alert(status + " " + error);  
		}
	});
}

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
			<label class="label" for="info">信息:</label>
			<input id="info" name="info" type="text" value="" size="30"/>
		</p>
		<p>
			<input name="amount" type="hidden" value="0"/>
			<input name="type" type="hidden" value="info" />
			<input name="submit" value="提交" type="submit" />
		</p>
   </form>
</section>


<section id="section_info_history">
</section>

<%@ include file="/_inc/footer.jsp"%>

</body>
</html>