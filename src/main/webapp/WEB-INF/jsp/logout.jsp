<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html style="height: 100%;">
    <head>
        <link REL="stylesheet" TYPE="text/css" href="/Ralter/style.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Logged out.</title>
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
                <td valign="top" align="left" colspan="2">

                    <div style="margin-top: 25%;">
                        <center>
                            <h1>Your have successfully signed out.</h1>
                            <h3><a href ="${requestScope.VIEW_DATA.signInLink}">sign-in again</a></h3>
                        </center>
                    </div>

                </td>
            </tr>
            <tr><td height="50" colspan="2" style="background-color: #8CAFD5;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="white">Copyright © 2010 Ralter Corporation. All Rights Reserved. Developer: mikevvATmailru</font></td></tr>
        </table>

    </body>
</html>
