<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
</head>
<body>
<h1>Register</h1>
<form action="RegisterServlet" method="post">
	<label for="username">Username:</label>
	<input type="text" name="username" id="username" required>
	<br><br>
	
	<label for="name">Fullname:</label>
	<input type="text" name="name" id="name">
	<br><br>
	
	<label for="email">Email:</label>
	<input type="email" name="email" id="email" required>
	<br><br>
	
	<label for="password">Password:</label>
	<input type="password" name="password" id="password" required>
	<br><br>
	
	<label>Confirm Password:</label>
    <input type="password" name="confirmPassword" required>

    <br><br>
    
	<label for="address">Address:</label>
	<input type="text" name="address" id="address">
	<br><br>
	
	<button type="submit">Register</button>
</form>

</body>
</html>