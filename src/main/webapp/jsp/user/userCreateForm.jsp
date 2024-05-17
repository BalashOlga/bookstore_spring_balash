<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title><Create New User</title>
        <link href="css/bookstore.css" rel="stylesheet" type="text/css" />
    </head>

    <body>
       <jsp:include page="../navbar.jsp"/>
       <h1>Create New User</h1>
       <form method="post" action="controller">
           <input name="command" type="hidden" value="user_create"/>
           <h2> "enter the email address as your login" </h2>
           <label for="login">Login: </label>
           <input name="login" type="email"/>
           <br/>
           <label for="password">Pasword: </label>
           <input name="password" type="password" minlength="8"/>
           <br/>
           <input type="submit" value="OK">
       </form>
    </body>
</html>