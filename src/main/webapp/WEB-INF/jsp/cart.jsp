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
        <jsp:include page="navbar.jsp"/>
        <c:if test="${sessionScope.cart.size() > 0}">
            <h1>Your Cart</h1>
            <table>
                <tr>
                <th>#</th>
                <th>bookId</th>
                <th>Quantity</th>
                </tr>

                <c:forEach items="${sessionScope.cart}" var="item" varStatus="counter">
                    <tr>
                        <th>${counter.count}</th>
                        <th>${item.key}</th>
                        <th>${item.value}</th>
                    </tr>
                </c:forEach>
                <th>
                  <form action="/orders" method="post">
                      <button type="submit"> Set the order </button>
                  </form>
                </th>
            </table>
        </c:if>


        <c:if test="${sessionScope.cart.size() == 0}">
            <h1>The cart is empty</h1>
        </c:if>
    </body>
</html>
