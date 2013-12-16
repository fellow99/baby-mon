<%@ page language="java" pageEncoding="UTF-8"%>
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
%>

<%
String provider = request.getParameter("p");
if(provider == null || provider.length() == 0){
	out.println("请选择登录服务。<a href='../'>返回</a>>>");
	return;
}

String code = request.getParameter("code");
if(code == null || code.length() == 0){
	out.println("无法获取登录码。<a href='../'>返回</a>>>");
	return;
}

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(
		request.getSession().getServletContext());

ProfileBusiness profileBusiness = ctx.getBean("profileBusiness", ProfileBusiness.class);
EveryCloudBusiness everyCloudBusiness = ctx.getBean("everyCloudBusiness", EveryCloudBusiness.class);
OAuthConfig oauthConfig = everyCloudBusiness.getOAuthConfig(provider);
Api oauth2API = everyCloudBusiness.getOAuthAPI(provider);
UserAPI userAPI = everyCloudBusiness.getUserAPI(provider);

OAuthService oauthService = oauth2API.createService(oauthConfig);

Verifier verifier = new Verifier(code);
Token EMPTY_TOKEN = null;
Token token = oauthService.getAccessToken(EMPTY_TOKEN, verifier);

AccessToken accessToken = everyCloudBusiness.createAccessToken(provider);
if(accessToken == null && accessToken.getAccessToken() == null){
	out.println("无法获取登录票据。<a href='../'>返回</a>>>");
	return;
}
accessToken.load(token.getRawResponse());
UserInfo user = userAPI.myInfo(accessToken);
String userId = user.getId();

profileBusiness.saveAccessToken(provider, userId, token.getRawResponse());

session.setAttribute(EveryCloudBusiness.SESSION_LOGIN_PROVIDER, provider);
session.setAttribute(EveryCloudBusiness.SESSION_LOGIN_ACCESS_TOKEN, accessToken);
session.setAttribute(EveryCloudBusiness.SESSION_LOGIN_USER_INFO, user);

addCookie(response, "LOGIN_PROVIDER", provider, 30*24*60*60);
addCookie(response, "LOGIN_USER_ID", user.getId(), 30*24*60*60);

response.sendRedirect("../index.jsp");
return;
%>