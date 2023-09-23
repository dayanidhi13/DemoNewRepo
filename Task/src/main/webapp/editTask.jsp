<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit Task</title>
<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
	<h2>Edit Task</h2>
	<form action="TaskController?action=update" method="post">
		<input type="hidden" name="action" value="update">
		<!-- Your edit task form fields go here -->
		<label for="title">Title:</label> <input type="text" name="title"
			id="title" required><br> <label for="description">Description:</label>
		<textarea name="description" id="description" rows="4" required></textarea>
		<br> <label for="dueDate">Due Date:</label> <input type="date"
			name="dueDate" id="dueDate" required><br> <label
			for="isComplete">Is Complete:</label> <input type="checkbox"
			name="isComplete" id="isComplete"><br> <input
			type="submit" value="Save">
	</form>
	<a href="TaskController?action=list">Back to Task List</a>
</body>
</html>