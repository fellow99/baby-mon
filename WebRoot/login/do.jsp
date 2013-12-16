<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.scribe.model.OAuthConfig" %>
<%@ page import="org.scribe.model.Token" %>
<%@ page import="org.scribe.builder.api.Api" %>
<%@ page import="org.scribe.oauth.OAuthService" %>
<%@ page import="com.fellow.business.EveryCloudBusiness" %>
<%
String provider = request.getParameter("p");
if(provider == null || provider.length() == 0){
	out.println("请选择登录服务。<a href='../'>返回</a>>>");
	return;
}

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(
		request.getSession().getServletContext());

EveryCloudBusiness everyCloudBusiness = ctx.getBean("everyCloudBusiness", EveryCloudBusiness.class);
OAuthConfig oauthConfig = everyCloudBusiness.getOAuthConfig(provider);
Api oauth2API = everyCloudBusiness.getOAuthAPI(provider);

OAuthService oauthService = oauth2API.createService(oauthConfig);

Token EMPTY_TOKEN = null;
String authorizationUrl = oauthService.getAuthorizationUrl(EMPTY_TOKEN);

response.sendRedirect(authorizationUrl);
return;
%>