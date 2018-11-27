<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="java.text.DateFormat"%>
<%@page import = "beans.Operations" %>

<% Integer ResId = new Integer(0); %>

<%@ include file = "/authheader.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Manage operations</title>
</head>
<body>
<%@ include file = "/header.jsp" %>

<center><h2>Operations list</h2></center>
<table class="mtable" align="center" border="1">
<tr><th>Name</th><th>Time</th><th>Complexity</th><th></th></tr>
<%

List opList = (List)session.getAttribute("opList");

for (int i = 0; i < opList.size(); i++) {
    Operations theOp = (Operations)opList.get(i);

    out.println("<tr><td>"+
            theOp.getOpName()+
               "</td>");
    out.println("<td>"+
            theOp.getOpDuration()+
               " min</td>");
    out.println("<td>"+
            theOp.getOpComplexity()+
               "</td>");
    out.println("<td><a href=\""+
               response.encodeURL("/Ralter/Process/Operations?act=show&opId=")+
               theOp.getOpId()+
               "\"><img src=\"/Ralter/pics/show.jpg\" title=\"Просмотреть\"></a>"+
               "<a href=\""+
               response.encodeURL("/Ralter/Process/Operations?act=editexisting&opId=")+
               theOp.getOpId()+
               "\"><img src=\"/Ralter/pics/edit.jpg\" title=\"Редактировать\"></a>"+
               "<a href=\""+
               response.encodeURL("/Ralter/Process/Operations?act=delete&opId=")+
               theOp.getOpId()+
               "\"><img src=\"/Ralter/pics/del.jpg\" title=\"Удалить\"></a>"+
            "</td></tr>");
}

%>
<tr>
    <td colspan="6" align="right">
        <a href="javascript:document.location.href='<%= response.encodeURL("/Ralter/Process/Operations?act=add") %>'">[New operation]</a>
    </td>
</tr>
</table>



<%@ include file = "/footer.jsp" %>
</body>
</html>
<%@ include file = "/authfooter.jsp" %>