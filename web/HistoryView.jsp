<%-- 
    Document   : HistoryView
    Created on : Sep 22, 2018, 7:30:16 PM
    Author     : bankcom
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>History</h1>
        
        <table border="1">
            
            <tr>
                <th>#</th>
                <th>Date</th>
                <th>Method</th>
                <th>Amount</th>
                <th>Balance</th>
            </tr>
            <td>
                <c:forEach items="${HistoryList}" var="his" varStatus="vs">
                <tr>
                    <td>${vs.count}</td>
                    <td>${his.time}</td>
                    <td>${his.method}</td>
                    <td>${his.amount}</td>
                    <td>${his.balance}</td>
                </tr>
            
            </c:forEach>
        
            </td>
            <a href="MyAccount">Back</a>
        </table>
    </body>
</html>
