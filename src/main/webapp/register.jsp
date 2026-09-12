<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>


<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Register</title>

<jsp:include page="/WEB-INF/include/head.jsp"/>

</head>


<body>


<div class="auth-container">


<h1>Register</h1>


<form class="auth-form"
      action="RegisterServlet"
      method="post">


<div class="form-group">

<label>Username</label>

<input type="text"
       name="username"
       required>

</div>



<div class="form-group">

<label>Full Name</label>

<input type="text"
       name="name">

</div>



<div class="form-group">

<label>Email</label>

<input type="email"
       name="email"
       required>

</div>

	

<div class="form-group">

<label>Password</label>

<input type="password"
       name="password"
       required>

</div>



<div class="form-group">

<label>Confirm Password</label>

<input type="password"
       name="confirmPassword"
       required>

</div>



<div class="form-group">

<label>Address</label>

<input type="text"
       name="address">

</div>



<button class="save-btn"
        type="submit">

Register

</button>


</form>


<p class="auth-link">

Already have account?

<a href="login.jsp">
Login
</a>

</p>


</div>


</body>

</html>