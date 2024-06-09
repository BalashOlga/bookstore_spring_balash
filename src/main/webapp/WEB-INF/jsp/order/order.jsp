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
        <c:if test="${order != null}">
            <h1>OrderItems By Order №${order.id}  Info (user name ${order.user.login})</h1>
            <table>
                <tr>
                    <th>#</th>
                    <th>Author</th>
                    <th>ISBN</th>
                    <th>Cover</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>Action</th>
                </tr>

                <c:forEach items="${order.items}" var="item" varStatus="counter">
                    <tr>
                        <th>${counter.count}</th>
                        <th>${item.book.author}</th>
                        <th>${item.book.isbn}</th>
                        <th>${item.book.coverType.name()}</th>
                        <th>${item.quantity}</th>
                        <th>${item.book.cost}</th>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

        <c:if test="${order == null}">
            <h1>The OrderItems was not found</h1>
        </c:if>
    </body>
</html>