<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title><%=request.getServletContext().getServerInfo() %></title>
        <link href="/css/bookstore.css" rel="stylesheet" type="text/css" />
        <link rel="icon" href="data:,">
    </head>

    <body>
        <jsp:include page="../navbar.jsp"/>
        <c:if test="${order != null}">
            <h5>OrderItems By Order №${order.id}  Info (user name ${order.user.login})</h5>
            <table>
                <tr>
                    <th>#</th>
                    <th>Author</th>
                    <th>ISBN</th>
                    <th>Cover</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>Status</th>
                </tr>
                <c:forEach items="${order.items}" var="item" varStatus="counter">
                    <tr>
                        <th>${counter.count}</th>
                        <th>${item.book.author}</th>
                        <th><a href="/books/isbn/${item.book.isbn}">${item.book.isbn}</a></th>
                        <th>${item.book.coverType.name()}</th>
                        <th>${item.quantity}</th>
                        <th>${item.price}</th>
                        <th>${order.status.name()}</th>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

        <c:if test="${order == null}">
            <h1>The OrderItems was not found</h1>
        </c:if>
    </body>
</html>