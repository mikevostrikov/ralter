<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import = "beans.Webres" %>

<% Integer ResId = new Integer(0); %>

<%@ include file = "/authheader.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Manage web-resources</title>
<script type="text/javascript" src="/Ralter/Workers/Webres/jquery.min.js"></script>
<script type="text/javascript" src="/Ralter/Workers/Webres/animatedcollapse.js"></script>
<script type="text/javascript">
animatedcollapse.addDiv('expandable', 'fade=1,height=120px')
animatedcollapse.ontoggle=function($, divobj, state){ //fires each time a DIV is expanded/contracted
	//$: Access to jQuery
	//divobj: DOM reference to DIV being expanded/ collapsed. Use "divobj.id" to get its ID
	//state: "block" or "none", depending on state
}
animatedcollapse.init()
</script>

</head>
<body>
<%@ include file = "/header.jsp" %>

<center><h2>Web-resources list</h2></center>
<table class="mtable" align="center" border="1">
<tr><th>ID</th><th>Name</th><th>Location</th><th></th></tr>
<%

List weList = (List)session.getAttribute("weList");

for (int i = 0; i < weList.size(); i++) {
    Webres theWeb = (Webres)weList.get(i);
    out.println("<tr><td>"+
            theWeb.getWeId()+"</td><td>"+
            theWeb.getWeName()+"</td><td>"+
            theWeb.getWeLocation()+"</td><td>"+
            "<a href=\""+
               response.encodeURL("/Ralter/Workers/Webres?act=edit&weId=")+
               theWeb.getWeId()+
               "\"><img src=\"/Ralter/pics/edit.jpg\" title=\"Edit\"></a>"+
               "<a href=\""+
               response.encodeURL("/Ralter/Workers/Webres?act=delete&weId=")+
               theWeb.getWeId()+
               "\"><img src=\"/Ralter/pics/del.jpg\" title=\"Delete\"></a>"+
            "</td></tr>");
}

%>
<tr>
    <td colspan="2" align="right">
        <a href="<%= response.encodeURL("/Ralter/Workers/Webres?act=synchronize") %>">[Reconstruct from web.xml]</a>
    </td>
    <td colspan="2" align="right">
        <a href="javascript:animatedcollapse.show(['expandable'])">[Add resource]</a>
    </td>
</tr>
</table>
<center>
    <div id="expandable" style="width: 300px; background: #FFFFCC; display:none">
        <br>
        <form name="editBasic" action="<%=response.encodeURL("/Ralter/Workers/Webres") %>" method="POST">
            Id:<input type="text" name="weId" value="" title="В соотв. с файлом конфигурации web.xml"><br>
            Name<input type="text" name="weName" value=""><br>
            Location:<input type="text" name="weLocation" value=""><br>
            <input type="hidden" name="act" value="add">
            <a href="javascript: document.editBasic.submit()">[Save]</a>
        </form>
    </div>
</center>

<%@ include file = "/footer.jsp" %>
</body>
</html>
<%@ include file = "/authfooter.jsp" %>