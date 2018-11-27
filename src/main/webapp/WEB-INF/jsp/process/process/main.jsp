<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="java.lang.Math"%>
<%@page import = "beans.Operations" %>

<% Integer ResId = new Integer(0); %>

<%@ include file = "/authheader.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Production process in place</title>
</head>
<body>
<%@ include file = "/header.jsp" %>

<center><h2>Operations being executed in production process</h2></center>
<table class="mtable" align="center" border="1">
<tr><th>#</th><th>Name</th><th>Time, min</th></tr>
<%

List tpOpList = (List)session.getAttribute("tpOpList");

Iterator it1 = tpOpList.iterator();
Long number = new Long(0);
Double time = new Double(0);
while (it1.hasNext()){
    number += 10;
    Operations curOp = (Operations)it1.next();
    time += curOp.getOpDuration();
    %>

<tr>
    <td>
        <%= number %>
    </td>
    <td>
        <%= curOp.getOpName() %>
    </td>
    <td>
        <%= curOp.getOpDuration() %>        
    </td>
</tr>

    <%
}
%>
<tr>
    <td colspan="3" align="right">
        Time for the full production cycle: <%= (new Double(Math.round(time*10)))/10 %> min.
    </td>
</tr>
<tr>
    <td colspan="3" align="right">
        <a href="javascript:document.location.href='<%= response.encodeURL("/Ralter/Process/Process?act=edit") %>'">[Edit]</a>
    </td>
</tr>
</table>



<%@ include file = "/footer.jsp" %>
</body>
</html>
<%@ include file = "/authfooter.jsp" %>