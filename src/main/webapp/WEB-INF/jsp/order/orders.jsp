<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title><%=request.getServletContext().getServerInfo() %></title>
        <link href="/css/bookstore.css" rel="stylesheet" type="text/css" />
    </head>

    <body>
        <jsp:include page="../navbar.jsp"/>
        <c:if test="${orders.size() > 0}">
            <h1>All Orders</h1>
            <table>
                <tr>
                    <th>#</th>
                    <th>Id</th>
                    <th>Cost</th>
                    <th>Action</th>
                </tr>

                <c:forEach items="${orders}" var="order" varStatus="counter">
                    <tr>
                        <th>${counter.count}</th>
                        <th>${order.id}</th>
                        <th>${order.cost}</th>
                        <th><a href="/orders/${order.id}">Orders info</a></th>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

        <c:if test="${orders.size() == 0}">
            <h1>No orders yet</h1>
        </c:if>
    </body>
</html>
