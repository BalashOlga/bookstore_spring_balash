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
        <h1>  Error occured ...</h1>
        <p>Status: ${status}</p>
        <p>Reason: ${reason}</p>
    </body>
</html>
