<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import = "beans.Documents" %>

<% Integer ResId = new Integer(0); %>

<%@ include file = "/authheader.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Documents management</title>
<script type="text/javascript" src="/Ralter/Resources/Documents/jquery.min.js"></script>
<script type="text/javascript" src="/Ralter/Resources/Documents/animatedcollapse.js"></script>
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

<center><h2>Documents list</h2></center>
<table class="mtable" align="center" border="1">
<tr><th>ID</th><th>Name</th><th>URI</th><th></th></tr>
<%

List doList = (List)session.getAttribute("doList");

for (int i = 0; i < doList.size(); i++) {
    Documents theDoc = (Documents)doList.get(i);
    theDoc.cutNulls();
    theDoc.trimStrings();
    out.print("<tr");
    if ((request.getParameter("doId") != null)&&((new Long(request.getParameter("doId"))).equals(theDoc.getDoId()))) 
        out.print(" bgcolor=\"yellow\"");    
    out.println("><td>"+
            theDoc.getDoId()+"</td><td>"+
            theDoc.getDoName()+"</td><td>"+
            "<a href=\""+response.encodeURL("Documents/Files/"+theDoc.getDoUrl())+"\">"+theDoc.getDoUrl()+"</a></td><td>"+
            "<a href=\""+
               response.encodeURL("/Ralter/Resources/Documents?act=edit&doId=")+
               theDoc.getDoId()+
               "\"><img src=\"/Ralter/pics/edit.jpg\" title=\"Edit\"></a>"+
               "<a href=\""+
               response.encodeURL("/Ralter/Resources/Documents?act=delete&doId=")+
               theDoc.getDoId()+
               "\"><img src=\"/Ralter/pics/del.jpg\" title=\"Delete\"></a>"+
            "</td></tr>");
}

%>
<tr>
    <td colspan="5" align="right">
        <a href="javascript:animatedcollapse.show(['expandable'])">[Add document]</a>
    </td>
</tr>
</table>

<center>
    <div id="expandable" style="width: 500px; background: #FFFFCC; display:none">
        <br>
        <form name="editBasic" action="<%=response.encodeURL("/Ralter/Resources/Documents") %>" method="POST" enctype="multipart/form-data">
            <table>
                <tr>
                    <td>
                         <input type="hidden" name="act" value="add">
                         Document name:
                    </td>
                    <td>
                         <input name="DoName" type="text" size="50"><br>
                    </td>
                </tr>
                <tr>
                    <td>
                        Upload file:
                    </td>
                    <td>
                        <input name="File" type="File" size="50">
                    </td>
                </tr>
                <tr>
                    <td align="right" colspan="2">
                        <a href="javascript: document.editBasic.submit()">[Save]</a>
                    </td>
                </tr>

            </table>
        </form>
    </div>
</center>



<%@ include file = "/footer.jsp" %>
</body>
</html>
<%@ include file = "/authfooter.jsp" %>