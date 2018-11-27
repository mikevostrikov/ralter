<%-- 
    Document   : login
    Created on : 23.03.2009, 21:19:52
    Author     : Mike V
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html style="height: 100%;">
    <head>
        <style>
            .button1 {
                border: 1px solid #006; background: #ccf; margin-top: 10px; width: 60px;
            }
        </style>
        <title>This is the main page</title>
    </head>
    <body style="height: 100%; margin: 0;">
        <table border="0" style="height: 100%; width: 100%; max-width: 1000pt;" align="center">
            <tr><td height="70" colspan="2"><img src="${requestScope.VIEW_DATA.appBaseUri}/pics/logo.jpg"></td></tr>
            <tr>
                <td align="left" height="20" colspan="2">
                    <table width="100%" border="0" cellspacing="0">
                        <tr>
                            <td align="left" bgcolor="#006699">
                                <font color="white">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
                            </td>
                            <td width="80" bgcolor="#006699">

                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <form method="POST" action="${requestScope.VIEW_DATA.authenticateLink}">
                        <table align="center" border="0" cellspacing="5">
                            <tr>
                                <td align="center" colspan="2">
                                    <font color="red"><c:if test="${requestScope.VIEW_DATA.authenticationFailed}">Wrong username or password</c:if></font>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" colspan=2>
                                    <h2>Sign in, please</h2>
                                </td>
                             </tr>
                            <tr>
                                <th align="right">Username:</th>
                                <td align="left"><input type="text" name="${requestScope.VIEW_DATA.usernameParam}"></td>
                            </tr>
                            <tr>
                                <th align="right">Password:</th>
                                <td align="left"><input type="password" name="${requestScope.VIEW_DATA.passwordParam}"></td>
                            </tr>
                            <tr>
                                <td align="right"><input type="submit" class="button1" value="Log In"></td>
                                <td align="left"><input class="button1" type="reset" value="Reset"></td>
                            </tr>
                        </table>
                    </form>
                </td>
            </tr>
            <tr>
                <td height="50" colspan="2" style="background-color: #8CAFD5;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <font color="white">Copyright © 2010 Ralter Corporation. All Rights Reserved. Developer: mikevvATmailru</font>
                </td>
            </tr>
        </table>
    </body>
</html>