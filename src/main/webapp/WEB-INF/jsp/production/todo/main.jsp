<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import = "beans.Operations" %>
<%@page import = "beans.Log" %>
<%@page import = "beans.Doop" %>

<% Integer ResId = new Integer(0); %>

<%@ include file = "/authheader.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Task list</title>
</head>
<body>
<%@ include file = "/header.jsp" %>

<center><h2>Task list</h2></center>
<table class="mtable" align="center" border="1">
<tr><th>#</th><th>Operation code</th><th>Name</th><th>Description document</th><th>Time, minutes</th><th></th></tr>
<%

List logs = (List)session.getAttribute("lastOps");
Operations firstOper = (Operations)session.getAttribute("firstOper");

Iterator it1 = logs.iterator();
beans.Log log = null;
while(it1.hasNext()){
    log = (beans.Log)it1.next();
    if(log.getOperations() != null){
        %>
<tr>
    <td>
        <%= log.getProducts().getPrId() %>
    </td>
    <td>
        <%= log.getProducts().getPrPassport() %>
    </td>
    <td>
        <%= log.getOperations().getNextOperation().getOpName() %>
    </td>
    <td>
        <%
            Set doops = log.getOperations().getNextOperation().getDoops();
            if(doops.size()==0) out.println("-");
            Iterator it2 = doops.iterator();
            while(it2.hasNext()){
                Doop theDoop = ((Doop)it2.next());
                out.print("<a href=\""+response.encodeURL("/Ralter/Resources/Documents/Files/"+theDoop.getDocuments().getDoUrl())+"\">");
                out.print(theDoop.getDocuments().getDoName()+"</a><br>");
            }
        %>
    </td>
    <td>
        <%= log.getOperations().getNextOperation().getOpDuration() %>
    </td>
    <td>
        <a href="<%= response.encodeURL("/Ralter/Production/Todo?act=edit&loId="+log.getLoId()) %>">[Execute]</a>
    </td>
</tr>

        <%
    }else{
        %>
<tr>
    <td>
        <%= log.getProducts().getPrId() %>
    </td>
    <td>
        <%= log.getProducts().getPrPassport() %>
    </td>
    <td>
        <%= firstOper.getOpName() %>
    </td>
    <td>
        <%
            Set doops = firstOper.getDoops();
            if(doops.size()==0) out.println("-");
            Iterator it2 = doops.iterator();
            while(it2.hasNext()){
                Doop theDoop = ((Doop)it2.next());
                out.print("<a href=\""+response.encodeURL("/Ralter/Resources/Documents/Files/"+theDoop.getDocuments().getDoUrl())+"\">");
                out.print(theDoop.getDocuments().getDoName()+"</a><br>");
            }
        %>
    </td>
    <td>
        <%= firstOper.getOpDuration() %>
    </td>
    <td>
        <a href="<%= response.encodeURL("/Ralter/Production/Todo?act=edit&loId="+log.getLoId()) %>">[Execute]</a>
    </td>
</tr>

        <%
    }
}
    %>


</table>



<%@ include file = "/footer.jsp" %>
</body>
</html>
<%@ include file = "/authfooter.jsp" %>