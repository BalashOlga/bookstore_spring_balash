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
        <c:if test="${user != null}">
            <h1>Users Info</h1>
            <table>
                <tr>
                <th>Login</th>
                <th>FirstName</th>
                <th>LastName</th>
                <th>Email</th>
                <th>Role</th>
                <th>Action</th>
                </tr>

                <tr>
                    <th>${user.login}</th>
                    <th>${user.firstName}</th>
                    <th>${user.lastName}</th>
                    <th>${user.email}</th>
                    <th>${user.role.name()}</th>
                    <th><a href="controller?command=users">All users</a></th>
                    <th><a href="controller?command=user_edit_form&id=${user.id}">Edit user</a></th>
                    <th><a href="controller?command=user_orders&id=${user.id}">Users order</a></th>
                </tr>
            </table>
        </c:if>

        <c:if test="${user == null}">
            <h1>The user was not found</h1>
        </c:if>
    </body>
</html>