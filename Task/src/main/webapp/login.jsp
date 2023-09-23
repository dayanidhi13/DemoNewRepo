<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<link rel="stylesheet" type="text/css" href="styles.css">

</head>
<body>
	<h2>Login</h2>
	<form action="user" method="post">
		<input type="hidden" name="action" value="login"> <label
			for="username">Username:</label> <input type="text" name="username"
			id="username" required><br> <label for="password">Password:</label>
		<input type="password" name="password" id="password" required><br>
		<input type="submit" value="Login">
	</form>
	<p>
		Don't have an account? <a href="register.jsp">Register</a>
	</p>

</body>
</html>