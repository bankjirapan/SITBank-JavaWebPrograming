<%-- 
    Document   : MyAccountView
    Created on : Sep 22, 2018, 7:29:45 PM
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
        <h1>My Account of ${sessionScope.LoggedIn.name}</h1>
        
        <h3>Your Balance : ${sessionScope.LoggedIn.balance}</h3>

        <ul>
            <li> <a href="Deposit">Deposit</a></li>
            <li> <a href="Withdrew">Withdrew</a></li>
            <li> <a href="History">History</a></li>
            <br><br>
            <li><a href="Logout">Logout</a></li>
        </ul>
    </body>
</html>
