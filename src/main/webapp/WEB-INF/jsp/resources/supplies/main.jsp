<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="java.text.DateFormat"%>
<%@page import = "beans.Supplies" %>

<% Integer ResId = new Integer(0); %>

<%@ include file = "/authheader.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Supplies management</title>
</head>
<body>
<%@ include file = "/header.jsp" %>

<center><h2>Supplies list</h2></center>
<table class="mtable" align="center" border="1">
<tr><th>Name</th><th>Stock</th><th>Producer</th><th>Price per unit</th><th></th></tr>
<%

List suList = (List)session.getAttribute("suList");

for (int i = 0; i < suList.size(); i++) {
    Supplies theSup = (Supplies)suList.get(i);
    out.println("<tr><td>"+
            theSup.getSuName()+
               "</td>");
    out.println("<td>"+
            theSup.getSuQuantity()+" "+theSup.getSuMeasure()+
               "</td>");
    out.println("<td>"+
            theSup.getSuManufacturer()+
               "</td>");
    out.println("<td>"+
            theSup.getSuPrice().toString()+
               "</td>");
    out.println("<td><a href=\""+
               response.encodeURL("/Ralter/Resources/Supplies?act=show&suId=")+
               theSup.getSuId()+
               "\"><img src=\"/Ralter/pics/show.jpg\" title=\"View\"></a>"+
               "<a href=\""+
               response.encodeURL("/Ralter/Resources/Supplies?act=editexisting&suId=")+
               theSup.getSuId()+
               "\"><img src=\"/Ralter/pics/edit.jpg\" title=\"Edit\"></a>"+
               "<a href=\""+
               response.encodeURL("/Ralter/Resources/Supplies?act=delete&suId=")+
               theSup.getSuId()+
               "\"><img src=\"/Ralter/pics/del.jpg\" title=\"Delete\"></a>"+
            "</td></tr>");
}

%>
<tr>
    <td colspan="5" align="right">
        <a href="javascript:document.location.href='<%= response.encodeURL("/Ralter/Resources/Supplies?act=add") %>'">[Add supply]</a>
    </td>
</tr>
</table>



<%@ include file = "/footer.jsp" %>
</body>
</html>
<%@ include file = "/authfooter.jsp" %>