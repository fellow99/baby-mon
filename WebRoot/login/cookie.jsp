<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.scribe.model.Verifier" %>
<%@ page import="org.scribe.model.OAuthConfig" %>
<%@ page import="org.scribe.model.Token" %>
<%@ page import="org.scribe.builder.api.Api" %>
<%@ page import="org.scribe.oauth.OAuthService" %>
<%@ page import="com.fellow.business.ProfileBusiness" %>
<%@ page import="com.fellow.business.EveryCloudBusiness" %>
<%@ page import="com.fellow.every.user.UserAPI" %>
<%@ page import="com.fellow.every.user.UserInfo" %>
<%@ page import="com.fellow.every.auth.AccessToken" %>

<%!
void addCookie(HttpServletResponse response,String name,String value,int maxAge){
    Cookie cookie = new Cookie(name,value);
    cookie.setPath("/");
    if(maxAge>0)  cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
}

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
ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(
		request.getSession().getServletContext());

ProfileBusiness profileBusiness = ctx.getBean("profileBusiness", ProfileBusiness.class);
EveryCloudBusiness everyCloudBusiness = ctx.getBean("everyCloudBusiness", EveryCloudBusiness.class);

Map<String,Cookie> cookieMap = cookieMap(request);
Cookie providerCookie = cookieMap.get("LOGIN_PROVIDER");
Cookie userIdCookie = cookieMap.get("LOGIN_USER_ID");

String provider = (providerCookie == null ? null : providerCookie.getValue());
String userId = (userIdCookie == null ? null : userIdCookie.getValue());
AccessToken accessToken = 
	(provider == null || userId == null ? null : profileBusiness.getAccessToken(provider, userId));

if(provider == null || userId == null || accessToken == null){
	addCookie(response, "LOGIN_PROVIDER", null, 0);
	addCookie(response, "LOGIN_USER_ID", null, 0);

	response.sendRedirect("login.jsp");
	return;
}

UserAPI userAPI = everyCloudBusiness.getUserAPI(provider);

UserInfo user = userAPI.myInfo(accessToken);

session.setAttribute(EveryCloudBusiness.SESSION_LOGIN_PROVIDER, provider);
session.setAttribute(EveryCloudBusiness.SESSION_LOGIN_ACCESS_TOKEN, accessToken);
session.setAttribute(EveryCloudBusiness.SESSION_LOGIN_USER_INFO, user);

response.addCookie(new Cookie("LOGIN_PROVIDER", provider));
response.addCookie(new Cookie("LOGIN_USER_ID", user.getId()));

response.sendRedirect("../index.jsp");
return;
%>