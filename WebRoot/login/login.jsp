<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%!
Map<String,Cookie> cookieMap(HttpServletRequest request){  
    Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
    Cookie[] cookies = request.getCookies();
    if(null!=cookies){
        for(Cookie cookie : cookies){
            cookieMap.put(cookie.getName(), cookie);
        }
    }
    return cookieMap;
}
%>
<%
Map<String,Cookie> cookieMap = cookieMap(request);
Cookie providerCookie = cookieMap.get("LOGIN_PROVIDER");
Cookie userIdCookie = cookieMap.get("LOGIN_USER_ID");

String provider = (providerCookie == null ? null : providerCookie.getValue());
String userId = (userIdCookie == null ? null : userIdCookie.getValue());
boolean cookie = !(provider == null || userId == null || provider.length() == 0 || userId.length() == 0);

if(cookie){
	response.sendRedirect("cookie.jsp");
	return;
}
%>
<!doctype html>
<html>
<head>
<%@ include file="/_inc/head.jsp"%>
<%@ include file="/_inc/js.jsp"%>
</head>

<body>

<%@ include file="/_inc/header.jsp"%>

<div id="menu-list">
    <ul>
    	<%if(cookie){%>
    	<li><b>您已登录</b></li>
    	<li><a href="cookie.jsp">继续使用</a></li>
    	<li><b>&nbsp;</b></li>
    	<li><a href="logout.jsp">注销</a></li>
    	<%} else {%>
    	<li><b>请选择登录方式</b></li>
        <li><a href="do.jsp?p=weibo">新浪微博登录</a></li>
		<li><a href="do.jsp?p=qq">腾讯微博登录</a></li>
		<li><a href="do.jsp?p=baidu">百度账号登录</a></li>
		<li><a href="do.jsp?p=renren">人人账号登录</a></li>
		<li><a href="do.jsp?p=360">360账号登录</a></li>
		<%}%>
    </ul>
</div>

<%@ include file="/_inc/footer.jsp"%>

</body>
</html>