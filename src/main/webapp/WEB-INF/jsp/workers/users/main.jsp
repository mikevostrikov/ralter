<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import = "beans.Employees" %>
<%@page import = "beans.Users" %>

<% Integer ResId = new Integer(0); %>

<%@ include file = "/authheader.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User management</title>
</head>
<body>
<%@ include file = "/header.jsp" %>

<center><h2>User management</h2></center>
<table class="mtable" align="center" border="1">
<tr><th>Username</th><th>Password</th><th>Role</th><th>Owner</th><th></th></tr>
<%

List usList = (List)session.getAttribute("usList");

for (int i = 0; i < usList.size(); i++) {
    Users theUs = (Users)usList.get(i);
    out.println("<tr><td>"+
            theUs.getUsLogin()+"</td><td>"+
            theUs.getUsPassword()+"</td><td>");
    if(theUs.getRoles() != null)out.println(
            "<a href=\""+
               response.encodeURL("/Ralter/Workers/Roles?act=show&roId=")+
               theUs.getRoles().getRoId()+
               "\">"+theUs.getRoles().getRoName()+"</a>");
    out.println("</td><td>");
    if(theUs.getEmployees() != null)out.println(theUs.getEmployees().getEmName());
    out.println("</td><td>"+
               "<a href=\""+
               response.encodeURL("/Ralter/Workers/Users?act=show&usId=")+
               theUs.getUsId()+
               "\"><img src=\"/Ralter/pics/show.jpg\" title=\"View\"></a>"+
               "<a href=\""+
               response.encodeURL("/Ralter/Workers/Users?act=existedit&usId=")+
               theUs.getUsId()+
               "\"><img src=\"/Ralter/pics/edit.jpg\" title=\"Edit\"></a>"+
               "</td></tr>");
}

%>
<tr>
    <td colspan="5" align="right">
        <a href="javascript:document.location.href='<%= response.encodeURL("/Ralter/Workers/Users?act=add") %>'">[Add user]</a>
    </td>
</tr>
</table>



<%@ include file = "/footer.jsp" %>
</body>
</html>
<%@ include file = "/authfooter.jsp" %>