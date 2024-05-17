<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title><Create New Book</title>
        <link href="css/bookstore.css" rel="stylesheet" type="text/css" />
    </head>

    <body>
       <jsp:include page="../navbar.jsp"/>
       <h1>Create New Book</h1>
       <form method="post" action="controller">
           <input name="command" type="hidden" value="book_create"/>
           <label for="author">Author: </label>
           <input name="author" type="text"/>
           <br/>
           <label for="isbn">ISBN: </label>
           <input name="isbn" type="text" minlength="18"/>
           <br/>
           <label for="year">Year: </label>
           <input name="year" type="text"/>
           <br/>
           <label for="cost">Cost: </label>
           <input name="cost" type="text"/>
           <br/>
           <input type="submit" value="OK">
       </form>
    </body>
</html>