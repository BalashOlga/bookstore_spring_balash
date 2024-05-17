<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title><%=request.getServletContext().getServerInfo() %></title>
        <link href="css/bookstore.css" rel="stylesheet" type="text/css" />
    </head>

    <body>
        <jsp:include page="../navbar.jsp"/>
        <c:if test="${users.size() > 0}">
            <h1>All Users</h1>
            <table>
                <tr>
                <th>#</th>
                <th>Login</th>
                <th>Role</th>
                <th>Action</th>
                </tr>

                <c:forEach items="${users}" var="user" varStatus="counter">
                    <tr>
                        <th>${counter.count}</th>
                        <th>${user.login}</th>
                        <th>${user.role.name()}</th>
                        <th><a href="controller?command=user&id=${user.id}">Users info</a></th>
                        <th><a href="controller?command=user_edit_form&id=${user.id}">Edit user</a></th>
                        <th><a href="controller?command=user_delete&id=${user.id}">Delete user</a></th>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

        <c:if test="${users.size() == 0}">
            <h1>No users yet</h1>
        </c:if>
    </body>
</html>
