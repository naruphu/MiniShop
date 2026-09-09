<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>


<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Login</title>

<jsp:include page="/WEB-INF/include/head.jsp"/>

</head>


<body>


<div class="auth-container">


<h1>Login</h1>


<form class="auth-form"
      action="LoginServlet"
      method="post">


<div class="form-group">

<label>Username</label>

<input type="text"
       name="username">

</div>



<div class="form-group">

<label>Password</label>

<input type="password"
       name="password">

</div>



<button class="save-btn"
        type="submit">

Login

</button>


</form>


<p class="auth-link">

Don't have account?

<a href="register.jsp">
Register
</a>

</p>


</div>



</body>

</html>