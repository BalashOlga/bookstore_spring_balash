<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul class="navbar">
    <li><a href="/home">Home</a></li>
    <li><a href="/books/all">All Books</a></li>
    <c:if test="${sessionScope.user == null}">
        <li><a href="/users/create">Create User</a></li>
        <li><a href="/login">LogIn</a></li>
    </c:if>
    <c:if test="${sessionScope.user != null}">
        <c:if test="${sessionScope.user.role.name() == 'CUSTOMER'}">
            <li><a href="/users/${sessionScope.user.id}/orders">My orders</a></li>
            <li><a href="/cart">My cart</a></li>
        </c:if>
        <c:if test="${sessionScope.user.role.name() == 'MANAGER'}">
            <li><a href="/books/create">Create Book</a></li>
            <li><a href="/users/all">All Users</a></li>
            <li><a href="/orders/all">All Orders</a></li>
        </c:if>
        <li><a href="/logout">LogOut</a></li>
        <b5> ${sessionScope.user.login}/</b5>
        <b5> ${sessionScope.user.role.name()}</b5>
    </c:if>
</ul>