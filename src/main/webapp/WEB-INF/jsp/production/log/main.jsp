<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import = "beans.Operations" %>
<%@page import = "beans.Log" %>
<%@page import = "beans.Products" %>

<% Integer ResId = new Integer(0); %>

<%@ include file = "/authheader.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Current production status</title>
</head>
<body>
<%@ include file = "/header.jsp" %>

<center><h2>Current production status</h2></center>
<table class="mtable" align="center" border="1">
<font align="left"><h4>List of flawed items</h4></font>
<tr><th>#</th><th>Item code</th><th>Operation on which the flaw was discovered</th><th></th></tr>
<%

List logProblProds = (List)session.getAttribute("logProblProds");
Iterator it1 = logProblProds.iterator();
beans.Log log = null;
while(it1.hasNext()){
    log = (beans.Log)it1.next();
        %>
<tr>
    <td>
        <%= log.getProducts().getPrId() %>
    </td>
    <td>
        <%= log.getProducts().getPrPassport() %>
    </td>
    <td>
        <%= log.getOperations().getOpName() %>
    </td>
    <td>
        <a href="<%= response.encodeURL("/Ralter/Production/Log?act=edit&loId="+log.getLoId()) %>">[Resolve]</a>
    </td>
</tr>
        <%
}
    %>
</table>
<br>
<table class="mtable" align="center" border="1">
<font align="left"><h4>List of items currently in production</h4></font>
<tr><th>#</th><th>Item</th><th colspan="2">Completeness</th></tr>
<%

List logLastOps = (List)session.getAttribute("logLastOps");
List percents =(List)session.getAttribute("percents");
it1 = logLastOps.iterator();
Iterator it2 = percents.iterator();
log = null;
Long percent = null;
while(it1.hasNext() && it2.hasNext()){
    percent = (Long)it2.next();
    log = (beans.Log)it1.next();
        %>
<tr>
    <td>
        <%= log.getProducts().getPrId() %>
    </td>
    <td>
        <%= log.getProducts().getPrPassport() %>
    </td>
    <td width="150pt">
        <div style="width: <%= percent %>%; background-color: #BBBBBB;">&nbsp; </div>
    </td>
    <td>
        <%= percent %>%
    </td>
</tr>
        <%
}
    %>
<tr>
    <td colspan="4" align="right">
    <a href="<%= response.encodeURL("/Ralter/Production/Log?act=add") %>" >[Add additional 5 items to the production queue]</a>
    </td>
</tr>
</table>
<br>
<table class="mtable" align="center" border="1">
<font align="left"><h4>List of successfully produced items</h4></font>
<tr><th>#</th><th>Item S/N</th><th>Production date</th></tr>
<%

List readyProds = (List)session.getAttribute("readyProds");
it1 = readyProds.iterator();
Products prod = null;
while(it1.hasNext()){
    prod = (Products)it1.next();
        %>
<tr>
    <td>
        <%= prod.getPrId() %>
    </td>
    <td>
        <%= prod.getPrPassport() %>
    </td>
    <td>
        <%= prod.getPrDate() %>
    </td>
</tr>
        <%
}
    %>
</table>
<br>
<table class="mtable" align="center" border="1">
<font align="left"><h4>Flawed items list</h4></font>
<tr><th>#</th><th>Item S/N</th><th>Flaw confirmation date</th></tr>
<%

List isolProds = (List)session.getAttribute("isolProds");
it1 = isolProds.iterator();
prod = null;
while(it1.hasNext()){
    prod = (Products)it1.next();
        %>
<tr>
    <td>
        <%= prod.getPrId() %>
    </td>
    <td>
        <%= prod.getPrPassport() %>
    </td>
    <td>
        <%= prod.getPrDate() %>
    </td>
</tr>
        <%
}
    %>
</table>
<br>


<%@ include file = "/footer.jsp" %>
</body>
</html>
<%@ include file = "/authfooter.jsp" %>