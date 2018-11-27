<%-- 
    Document   : editemp
    Created on : 28.03.2009, 22:29:41
    Author     : Mike V
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="java.text.DateFormat"%>
<%@page import="beans.Operations" %>
<%@page import="beans.Log" %>

<% Integer ResId = new Integer(0); %>
<%@ include file = "/authheader.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Flaw inspection</title>
</head>
<body>
<%@ include file = "/header.jsp" %>

<%
List tpOpList = (List)session.getAttribute("tpOpList");
beans.Log theLog = (beans.Log)session.getAttribute("theLog");
%>

<center>
<h2>Flaw inspection</h2>
<form name ="basicForm" method="POST" action="<%=response.encodeURL("/Ralter/Production/Log") %>">
<table>
    <tr>
        <td>
            Item S/N:
        </td>
        <td>
                <%= theLog.getProducts().getPrPassport() %>
        </td>
    </tr>
    <tr>
        <td>
            Operation:
        </td>
        <td>
            <%= theLog.getOperations().getOpName() %>
        </td>
    </tr>
    <tr>
        <td>
            Comment:
        </td>
        <td>
            <%= theLog.getLoComment() %>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <i>Resolve an issue. Then send to the operation <br>from which the item production should be resumed:</i>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <select name="opId" size="10">
                <%
                    Iterator it1 = tpOpList.iterator();
                    Operations op = null;
                    while(it1.hasNext()){
                        op = (Operations)it1.next();
                        out.println("<option value=\""+op.getOpId()+"\">");
                        out.println(op.getOpName());
                        out.println("</option>");
                    }
                %>
            </select>
        </td>
    </tr>
    <tr>
        <td align="left">
            <a href="javascript: document.basicForm.submit()">[Resolve]</a>
        </td>
        <td align="right">
            <a href="<%=response.encodeURL("/Ralter/Production/Log?act=isolate&loId="+theLog.getLoId()) %>">[Confirm irreparable flaw]</a>
        </td>
    </tr>
</table>
<input type="hidden"  name="act" value="update">
</form>
</center>

<%@ include file = "/footer.jsp" %>
</body>
</html>

<%@ include file = "/authfooter.jsp" %>