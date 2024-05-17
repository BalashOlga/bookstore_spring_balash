<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title><%=request.getServletContext().getServerInfo() %></title>
        <link href="css/bookstore.css" rel="stylesheet" type="text/css" />
    </head>

    <body>
        <jsp:include page="navbar.jsp"/>
        <img src = "images/home.jpg">
    </body>
</html>