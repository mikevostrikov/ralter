<%-- 
    Document   : editemp
    Created on : 28.03.2009, 22:29:41
    Author     : Mike V
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<% Integer ResId = new Integer(0); %>
<%@ include file = "/authheader.jsp" %>

<%@page import="beans.Employees" %>
<%@page import="beans.Roles" %>
<%@page import="beans.Users" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User information edit</title>
</head>
<body>
<%@ include file = "/header.jsp" %>

<center>
<h2>User information edit</h2>
<%
   Users theUs = (Users)session.getAttribute("theUs");
   List empList = (List)session.getAttribute("empList");
   List roList = (List)session.getAttribute("roList");
   System.out.println("EditUs Fullfilled");
%>
<h3> <% if(theUs.getEmployees() != null ) out.println(theUs.getEmployees().getEmName()); %></h3>
<form method="POST" action="<%=response.encodeURL("/Ralter/Workers/Users") %>" name ="editBasic">
<table>
    <tr>
        <td>
            <b>ID: </b>
        </td>
        <td>
            <input type="text" value="<%= theUs.getUsId() %>" name="UsId" disabled>
        </td>
    </tr>
    <tr>
        <td>
            <b>Employee: </b>
        </td>
        <td>
            <select name="Index" size="5">
                <%
                    for (int i = 0; i < empList.size(); i++) {
                        Employees theEmp = (Employees)empList.get(i);
                        if ((theUs.getEmployees() != null) && (theEmp.getEmId().equals(theUs.getEmployees().getEmId()))){
                            out.println("<option value=\""+i+"\" selected>"+theEmp.getEmName()+"</option>");
                        } else if (theUs.getEmployees() != null) {
                            out.println("<option value=\""+i+"\">"+theEmp.getEmName()+"</option>");
                        } else {
                            if (i == 0) {
                                out.println("<option value=\""+i+"\" selected>"+theEmp.getEmName()+"</option>");
                            } else {
                                out.println("<option value=\""+i+"\">"+theEmp.getEmName()+"</option>");
                            }
                        }
                    }
                %>
            </select>
        </td>
    </tr>
    <tr>
        <td>
            <b>Username: </b>
        </td>
        <td>
            <input type="text" value="<%= theUs.getUsLogin() %>" name="UsLogin">
        </td>
    </tr>
    <tr>
        <td>
            <b>Password: </b>
        </td>
        <td>
            <input type="text" value="<%= theUs.getUsPassword() %>" name="UsPassword">
        </td>
    </tr>
    <tr>
        <td>
            <b>Role: </b>
        </td>
        <td>
            <select  name="Index2" size="5">
                <%
                    for (int i = 0; i < roList.size(); i++) {
                        Roles theRole = (Roles)roList.get(i);
                        if ((theUs.getRoles() != null)&&(theRole.getRoId()).equals(theUs.getRoles().getRoId())){
                            out.println("<option value=\""+i+"\" selected>"+theRole.getRoName()+"</option>");
                        } else if (theUs.getRoles() != null){
                            out.println("<option value=\""+i+"\">"+theRole.getRoName()+"</option>");
                        } else {
                            if (i == 0) {
                                out.println("<option value=\""+i+"\" selected>"+theRole.getRoName()+"</option>");
                            } else {
                                out.println("<option value=\""+i+"\">"+theRole.getRoName()+"</option>");
                            }
                        }
                    }
                %>
            </select>
        </td>
    </tr>
    <tr>
        <td>
            <a href="javascript:document.editBasic.submit()">[Save]</a>
        </td>
        <td>
            <a href="javascript:document.location.href='<%=response.encodeURL("/Ralter/Workers/Users?act=delete") %>'">[Delete]</a>
        </td>
    </tr>
</table>
<input type="hidden" value="update" name="act">
</form>
</center>

<%@ include file = "/footer.jsp" %>
</body>
</html>

<%@ include file = "/authfooter.jsp" %>