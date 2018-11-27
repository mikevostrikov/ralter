<%-- 
    Document   : editemp
    Created on : 28.03.2009, 22:29:41
    Author     : Mike V
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="java.text.DateFormat"%>
<%@page import="beans.Operations" %>
<%@page import="beans.Components" %>
<%@page import="beans.Coop" %>


<% Integer ResId = new Integer(0); %>
<%@ include file = "/authheader.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit operation</title>
</head>
<body>
<%@ include file = "/header.jsp" %>

<center>
<h2>Edit operation</h2>
<h3>Step 3. Define number of parts used.</h3>
<%
   Operations theOp = (Operations)session.getAttribute("theOp");
   List coopList = (List)session.getAttribute("coopList");
%>
<h4> <%=theOp.getOpName()%></h4>
<form name ="basicForm" method="POST" action="<%=response.encodeURL("/Ralter/Process/Operations") %>">
<table>
    <tr>
        <td>
            <b>ID: </b>
        </td>
        <td>
            <input type="text" value="<%= theOp.getOpId() %>" disabled>
        </td>
    </tr>
    <tr>
        <td>
            <b>Name: </b>
        </td>
        <td>
            <input type="text" value="<%= theOp.getOpName() %>" name="OpName" disabled>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <table>
                <% for(int i = 0; i < coopList.size(); i++){
                    %>
                    <tr>
                        <td>
                            <%= ((Coop)coopList.get(i)).getComponents().getCoName() %>
                        </td>
                        <td>
                            <input type="text" name="<%= ((Coop)coopList.get(i)).getCoopId() %>" value="<%= ((Coop)coopList.get(i)).getCoopQuantity() %>"> pcs.
                        </td>
                    </tr>
                    <%
                    } 
                %>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <a href="javascript:document.basicForm.submit()">[Next]</a>
        </td>
        <td align="right">
            <a href="">[Delete]</a>
        </td>
    </tr>
</table>
<input type="hidden"  name="act" value="update2">
</form>
</center>

<%@ include file = "/footer.jsp" %>
</body>
</html>

<%@ include file = "/authfooter.jsp" %>