<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Error Page</title>
        <link href="/css/bookstore.css" rel="stylesheet" type="text/css" />
        <link rel="icon" href="data:,">
    </head>
    <body>
        <jsp:include page="navbar.jsp"/>
        <h1>  Error!!!  </h1>
         <c:if test="${message != null}">
           <h1>${message}</h1>
         </c:if>
         <c:if test="${message = null}">
            <h1> Something went wrong ...}</h1>
         </c:if>
    </body>
</html>
