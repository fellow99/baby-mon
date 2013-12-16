<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%!
void addCookie(HttpServletResponse response,String name,String value,int maxAge){
    Cookie cookie = new Cookie(name,value);
    cookie.setPath("/");
    if(maxAge>0)  cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
}
%>
<%
addCookie(response, "LOGIN_PROVIDER", null, 0);
addCookie(response, "LOGIN_USER_ID", null, 0);
addCookie(response, "LOGIN_USER_NAME", null, 0);
addCookie(response, "LOGIN_USER_NICKNAME", null, 0);
%>
<%session.invalidate();%>
<%response.sendRedirect("./login.jsp");%>
