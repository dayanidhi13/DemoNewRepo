<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome to Your Dashboard</title>
<link rel="stylesheet" type="text/css" href="styles.css">

</head>
<body>
	<p>
		Hello,
		<%=session.getAttribute("username")%>!
	</p>
	<!-- Add your task management interface here -->
	<p>Manage Your Tasks:</p>
	<ul>
		<li><a href="TaskController?action=list">View Task List</a></li>
		<li><a href="createTask.jsp">Create New Task</a></li>
	</ul>
	<a href="logout">Logout</a>

</body>
</html>