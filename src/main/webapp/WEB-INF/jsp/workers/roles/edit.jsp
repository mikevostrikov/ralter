<%-- 
    Document   : editemp
    Created on : 28.03.2009, 22:29:41
    Author     : Mike V
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>

<%@page import="beans.Webres" %>
<%@page import="beans.Rowe" %>
<%@page import="beans.Roles" %>

<% Integer ResId = new Integer(0); %>
<%@ include file = "/authheader.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit role</title>
</head>
<body>
<%@ include file = "/header.jsp" %>

<center>
<h2>Edit role</h2>
<%
   Roles theRole = (Roles)session.getAttribute("theRole");
   List weList = (List)session.getAttribute("weList");
%>
<h3> <%=theRole.getRoName()%></h3>
<form method="POST" action="<%=response.encodeURL("/Ralter/Workers/Roles") %>" name ="editBasic">
<table>
    <tr>
        <td>
            <b>ID: </b>
        </td>
        <td>
            <input type="text" value="<%= theRole.getRoId() %>" name="RoId" disabled>
        </td>
    </tr>
    <tr>
        <td>
            <b>Role name: </b>
        </td>
        <td>
            <input type="text" value="<%= theRole.getRoName() %>" name="RoName">
        </td>
    </tr>
    <tr>
        <td>
            <b>Web-resources available to the role: </b>
        </td>
        <td>
            <select name="weIds[]" size="5" multiple>
                <%
                    if (theRole.getRowes() != null){
                        Iterator it = theRole.getRowes().iterator();
                        Set curWeSet = new HashSet();
                        for (int i = 0; i < theRole.getRowes().size(); i++){
                            curWeSet.add(((Rowe)it.next()).getWebres().getWeId());
                        }
                        Webres theWeb;
                        for (int i = 0; i < weList.size(); i++) {
                            theWeb = (Webres)weList.get(i);
                            if (curWeSet.contains(theWeb.getWeId())){
                                out.println("<option value=\""+theWeb.getWeId()+"\" selected>"+theWeb.getWeName()+"</option>");
                            } else {
                                out.println("<option value=\""+theWeb.getWeId()+"\">"+theWeb.getWeName()+"</option>");
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
            <a href="javascript:document.location.href='<%=response.encodeURL("/Ralter/Workers/Roles?act=delete") %>'">[Delete]</a>
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