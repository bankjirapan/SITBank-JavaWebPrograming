<%-- 
    Document   : LoginView
    Created on : Sep 22, 2018, 7:29:33 PM
    Author     : bankcom
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Login</h1>
        <form action="Login" method="POST">
        <table border="1">
            <tr>
                <th>Account ID : </th>
                <td><input type="text" name="inAccountID"></td>
            </tr>
            <tr>
                <th>PIN ID : </th>
                <td><input type="password" name="inPin"></td>
            </tr>
            <th><button type="submit">Login</button></th>
        </form>
        </table>
    
            <p style="color : #FF0000">${msg}</p>
    </body>
</html>
