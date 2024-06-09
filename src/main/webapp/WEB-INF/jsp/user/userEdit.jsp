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
           <h1>Edit user: ${user.login}</h1>
           <form method="post" action="controller">
               <input type="hidden" value="users"/>
               <input name="id" type="hidden" value="${user.id}"/>
               <input name="login" type="hidden" value="${user.login}"/>
               <br/>
               <label for="firstName">First Name "${user.firstName}"</label>
               <input name="firstName" type="text" value="${user.firstName}"/>
               <br/>
               <label for="lastName">Last Name "${user.lastName}"</label>
               <input name="lastName" type="text" value="${user.lastName}"/>
               <br/>
               <label for="email">Email "${user.email}"</label>
               <input name="email" type="email" value="${user.email}"/>
               <br/>
               <input type="submit" value="OK">
           </form>
    </body>
</html>