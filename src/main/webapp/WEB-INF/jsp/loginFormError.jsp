<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title><Wron Login</title>
        <link href="/css/bookstore.css" rel="stylesheet" type="text/css" />
        <link rel="icon" href="data:,">
    </head>

    <body>
       <jsp:include page="navbar.jsp"/>
       <h1>Wrong login or password</h1>
       <form method="post">
           <input type="hidden" value="login">
           <h2> "enter login" </h2>
           <label for="login">Login: </label>
           <input name="login" type="email"/>
           <br/>
           <label for="enter password">Pasword: </label>
           <input name="password" type="password" minlength="8"/>
           <br/>
           <input type="submit" value="OK">
       </form>
    </body>
</html>