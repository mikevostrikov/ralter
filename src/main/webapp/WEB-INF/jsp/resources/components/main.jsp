<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="java.text.DateFormat"%>
<%@page import = "beans.Components" %>

<% Integer ResId = new Integer(0); %>

<%@ include file = "/authheader.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Components management</title>
</head>
<body>
<%@ include file = "/header.jsp" %>

<center><h2>Components management</h2></center>
<table class="mtable" align="center" border="1">
<tr><th>Name</th><th>Stock</th><th>Producer</th><th>Supply date</th><th></th></tr>
<%

List coList = (List)session.getAttribute("coList");

DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.CANADA);

for (int i = 0; i < coList.size(); i++) {
    Components theComp = (Components)coList.get(i);
    out.println("<tr><td>"+
            theComp.getCoName()+
               "</td>");
    out.println("<td>"+
            theComp.getCoQuantity()+
               "</td>");
    out.println("<td>"+
            theComp.getCoManufacturer()+
               "</td>");
    out.println("<td>"+
            df.format(theComp.getCoDate())+
               "</td>");
    out.println("<td><a href=\""+
               response.encodeURL("/Ralter/Resources/Components?act=show&coId=")+
               theComp.getCoId()+
               "\"><img src=\"/Ralter/pics/show.jpg\" title=\"View\"></a>"+
               "<a href=\""+
               response.encodeURL("/Ralter/Resources/Components?act=editexisting&coId=")+
               theComp.getCoId()+
               "\"><img src=\"/Ralter/pics/edit.jpg\" title=\"Edit\"></a>"+
               "<a href=\""+
               response.encodeURL("/Ralter/Resources/Components?act=delete&coId=")+
               theComp.getCoId()+
               "\"><img src=\"/Ralter/pics/del.jpg\" title=\"Delete\"></a>"+
            "</td></tr>");
}

%>
<tr>
    <td colspan="5" align="right">
        <a href="javascript:document.location.href='<%= response.encodeURL("/Ralter/Resources/Components?act=add") %>'">[Add component]</a>
    </td>
</tr>
</table>



<%@ include file = "/footer.jsp" %>
</body>
</html>
<%@ include file = "/authfooter.jsp" %>