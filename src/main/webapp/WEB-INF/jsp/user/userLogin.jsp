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
        <h1>Сongratulation Dear  ${user.login}  !!! </h1>
        <a href="/users/edit/${user.id}">Add personal data</a>
    </body>
</html>