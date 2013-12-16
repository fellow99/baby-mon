<%@ page language="java" pageEncoding="UTF-8"%><%@ page import="com.fellow.business.EveryCloudBusiness" %>
<%@ page import="com.fellow.every.user.UserInfo" %>
<%
UserInfo user = (UserInfo)session.getAttribute(EveryCloudBusiness.SESSION_LOGIN_USER_INFO);
String provider = (String)session.getAttribute(EveryCloudBusiness.SESSION_LOGIN_PROVIDER);
%>
<!doctype html>
<html>
<head>
<%@ include file="/_inc/head.jsp"%>
<%@ include file="/_inc/js.jsp"%>

<script type="text/javascript">

var user = {};
user.id = "<%=user.getId()%>";
user.name = "<%=user.getName()%>";
user.nickname = "<%=user.getNickname()%>";
user.urlHead = "<%=(user.getUrlHead() == null ? "" : user.getUrlHead())%>";

$(document).ready(function(){
	$.ajax({type:"POST",
		async:false,
		url:"profile-data.do?method=info",
		dataType:"json",
		success: function(data, status){
			data.user = user;
			$("#section_info").setTemplateURL("_tpl/info.html");
			$("#section_info").processTemplate(data);
		},
		error: function(xhr, status, error){
	        alert(status + " " + error);  
		}
	});
});


function create(){
	$.ajax({type:"POST",
		async:false,
		url:"profile-action.do?method=create",
		dataType:"json",
		success: function(data, status){
			window.location = "./";
		},
		error: function(xhr, status, error){
	        alert(status + " " + error);  
		}
	});
}

function deletes(){
	$.ajax({type:"POST",
		async:false,
		url:"profile-action.do?method=delete",
		dataType:"json",
		success: function(data, status){
			window.location = "./";
		},
		error: function(xhr, status, error){
	        alert(status + " " + error);  
		}
	});
}

function reset(){
	$.ajax({type:"POST",
		async:false,
		url:"profile-action.do?method=reset",
		dataType:"json",
		success: function(data, status){
			window.location = "./";
		},
		error: function(xhr, status, error){
	        alert(status + " " + error);  
		}
	});
}

function update(){
	$.ajax({type:"POST",
		async:false,
		url:"profile-action.do?method=update",
		dataType:"json",
		success: function(data, status){
			window.location = "./";
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

<section id="section_info">
</section>

<%@ include file="/_inc/footer.jsp"%>

</body>
</html>