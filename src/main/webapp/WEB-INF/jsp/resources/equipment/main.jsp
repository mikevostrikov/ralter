<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import = "beans.Equipment" %>

<% Integer ResId = new Integer(0); %>

<%@ include file = "/authheader.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Equipment management</title>
<script type="text/javascript" src="/Ralter/Resources/Equipment/jquery.min.js"></script>
<script type="text/javascript" src="/Ralter/Resources/Equipment/animatedcollapse.js"></script>


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

<center><h2>Equipment list</h2></center>
<table class="mtable" align="center" border="1">
<tr><th>ID</th><th>Name</th><th>Producer</th><th>Comment</th><th></th></tr>
<%

List eqList = (List)session.getAttribute("eqList");

for (int i = 0; i < eqList.size(); i++) {
    Equipment theEq = (Equipment)eqList.get(i);
    out.println("<tr><td>"+
            theEq.getEqId()+"</td><td>"+
            theEq.getEqName()+"</td><td>"+
            theEq.getEqManufacturer()+"</td><td>"+
            theEq.getEqComment()+"</td><td>"+
            "<a href=\""+
               response.encodeURL("/Ralter/Resources/Equipment?act=edit&eqId=")+
               theEq.getEqId()+
               "\"><img src=\"/Ralter/pics/edit.jpg\" title=\"Edit\"></a>"+
               "<a href=\""+
               response.encodeURL("/Ralter/Resources/Equipment?act=delete&eqId=")+
               theEq.getEqId()+
               "\"><img src=\"/Ralter/pics/del.jpg\" title=\"Delete\"></a>"+
            "</td></tr>");
}

%>
<tr>
    <td colspan="5" align="right">
        <a href="javascript:animatedcollapse.show(['expandable'])">[Add equipment]</a>
    </td>
</tr>
</table>

<center>
    <div id="expandable" style="width: 500px; background: #FFFFCC; display:none">
        <br>
        <form name="editBasic" action="<%=response.encodeURL("/Ralter/Resources/Equipment") %>" method="POST">
            Name:<input type="text" name="EqName" value=""><br>
            Producer:<input type="text" name="EqManufacturer" value=""><br>
            Comment:<input type="text" name="EqComment" value=""><br>
            <input type="hidden" name="act" value="add">
            <a href="javascript: document.editBasic.submit()">[Save]</a>
        </form>
    </div>
</center>



<%@ include file = "/footer.jsp" %>
</body>
</html>
<%@ include file = "/authfooter.jsp" %>