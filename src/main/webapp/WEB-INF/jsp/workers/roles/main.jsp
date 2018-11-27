<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import = "beans.Roles" %>
<%@page import = "beans.Users" %>

<% Integer ResId = new Integer(0); %>

<%@ include file = "/authheader.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>System roles management</title>
</head>
<body>
<%@ include file = "/header.jsp" %>

<center><h2>System roles management</h2></center>
<table class="mtable" align="center" border="1">
<tr><th>Role</th><th>Users</th><th></th></tr>
<%

List roList = (List)session.getAttribute("roList");

for (int i = 0; i < roList.size(); i++) {
    Roles theRole = (Roles)roList.get(i);
    out.println("<tr><td>"+
            theRole.getRoName()+
               "</td><td>");
    Set usSet = (Set)theRole.getUserses();
    Iterator it = usSet.iterator();
    for(int j=0; j<usSet.size()-1; j++){
        Users theUs = (Users)it.next();
        out.println("<a href=\""+response.encodeURL("/Ralter/Workers/Users?act=show&usId=")+theUs.getUsId()+"\">"+theUs.getUsLogin().trim()+"</a>, ");
    }
    if (usSet.size()>0){
        Users theUs = (Users)it.next();
        out.println("<a href=\""+response.encodeURL("/Ralter/Workers/Users?act=show&usId=")+theUs.getUsId()+"\">"+theUs.getUsLogin().trim()+"</a>");
    }
    out.println("<td><a href=\""+
               response.encodeURL("/Ralter/Workers/Roles?act=show&roId=")+
               theRole.getRoId()+
               "\"><img src=\"/Ralter/pics/show.jpg\" title=\"View\"></a>"+
               "<a href=\""+
               response.encodeURL("/Ralter/Workers/Roles?act=existedit&roId=")+
               theRole.getRoId()+
               "\"><img src=\"/Ralter/pics/edit.jpg\" title=\"Edit\"></a>"+
            "</td></tr>");
}

%>
<tr>
    <td colspan="4" align="right">
        <a href="javascript:document.location.href='<%= response.encodeURL("/Ralter/Workers/Roles?act=add") %>'">[Add role]</a>
    </td>
</tr>
</table>



<%@ include file = "/footer.jsp" %>
</body>
</html>
<%@ include file = "/authfooter.jsp" %>