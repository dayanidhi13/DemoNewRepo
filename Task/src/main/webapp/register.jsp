<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registration</title>
<link rel="stylesheet" type="text/css" href="styles.css">

</head>
<body>
	<h2>Register</h2>
	<form action="user" method="post">
		<input type="hidden" name="action" value="register"> <label
			for="username">Username:</label> <input type="text" name="username"
			id="username" required><br> <label for="password">Password:</label>
		<input type="password" name="password" id="password" required><br>
		<input type="submit" value="Register">
	</form>
	<p>
		Already have an account? <a href="login.jsp">Login</a>
	</p>
</body>
</html>