<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
</head>
<body>
	 <h1>Something went wrong!</h1>

    <p>
        ${requestScope.errorMessage}
    </p>

    <a href="ProductServlet">Back to Products</a>
</body>
</html>