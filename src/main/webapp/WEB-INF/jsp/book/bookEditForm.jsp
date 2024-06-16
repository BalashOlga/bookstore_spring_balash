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
           <jsp:include page="../navbar.jsp"/>
           <h1>Edit book: ${book.isbn}</h1>
           <form method="post" action="controller">
               <input type="hidden" value="edit"/>
               <input name="id" type="hidden" value="${book.id}"/>
               <input name="isbn" type="hidden" value="${book.isbn}"/>
               <label for="author">Author "${book.author}"</label>
               <input name="author" type="text" value="${book.author}"/>
               <br/>
               <label for="year">Year "${book.year}"</label>
               <input name="year" type="text" value="${book.year}"/>
               <br/>
               <label for="cost">Cost "${book.cost}"</label>
               <input name="cost" type="text" value="${book.cost}"/>
               <br/>
               <input type="submit" value="OK">
           </form>
    </body>
</html>