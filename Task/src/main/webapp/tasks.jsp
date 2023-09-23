<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Task List</title>
<link rel="stylesheet" type="text/css" href="styles.css">

</head>
<body>
	<h2>Task List</h2>
	<table border="1">
		<tr>
			<th>ID</th>
			<th>Title</th>
			<th>Description</th>
			<th>Due Date</th>
			<th>Status</th>
			<th>Action</th>
		</tr>
		<c:forEach var="task" items="${taskList}">
			<tr>
				<td>${task.id}</td>
				<td>${task.title}</td>
				<td>${task.description}</td>
				<td>${task.dueDate}</td>
				<td><c:choose>
						<c:when test="${task.complete}">
                            Complete
                        </c:when>
						<c:otherwise>
                            Incomplete
                        </c:otherwise>
					</c:choose></td>
				<td><a href="editTask.jsp?id=${task.id}">Edit</a> | <a
					href="deleteTask.jsp?id=${task.id}">Delete</a></td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<a href="createTask.jsp">Create New Task</a>


</body>
</html>