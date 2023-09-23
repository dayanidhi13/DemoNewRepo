<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Delete Task</title>
<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
	<h2>Delete Task</h2>
	<p>Are you sure you want to delete this task?</p>

	<form action="deleteTask" method="post">
		<input type="hidden" name="id" value="${task.id}">
		<input type="submit" value="Delete">
	</form>

	<a href="TaskController?action=list">Cancel</a>
</body>
</html>