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
        <c:if test="${books.size() > 0}">
            <h1>All Books</h1>
            <table>
                <tr>
                    <th>#</th>
                    <th>Author</th>
                    <th>ISBN</th>
                    <th>Cover</th>
                    <th>Cost</th>
                    <th>Action</th>
                </tr>

                <c:forEach items="${books}" var="book" varStatus="counter">
                    <tr>
                        <th>${counter.count}</th>
                        <th>${book.author}</th>
                        <th>${book.isbn}</th>
                        <th>${book.coverType.name()}</th>
                        <th>${book.cost}</th>
                        <th><a href="controller?command=book&id=${book.id}">Books info</a></th>
                        <th><a href="controller?command=book_edit_form&id=${book.id}">Edit book</a></th>
                        <th><a href="controller?command=book_delete&id=${book.id}">Delete book</a></th>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

        <c:if test="${books.size() == 0}">
            <h1>No books yet</h1>
        </c:if>
    </body>
</html>
