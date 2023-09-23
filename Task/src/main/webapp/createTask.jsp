<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create New Task</title>
<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
	<h2>Create New Task</h2>

	<!-- Check if the confirmation message exists -->
	<c:if test="${not empty confirmationMessage}">
		<div class="confirmation">${confirmationMessage}</div>
	</c:if>

	<form action="TaskController?action=add" method="post">
		<!-- Updated form action -->
		<table>
			<tr>
				<td>Title:</td>
				<td><input type="text" name="title" required></td>
			</tr>
			<tr>
				<td>Description:</td>
				<td><textarea name="description" rows="4" required></textarea></td>
			</tr>
			<tr>
				<td>Due Date:</td>
				<td><input type="date" name="dueDate" required></td>
			</tr>
			<tr>
				<td>Status:</td>
				<td><input type="radio" name="isComplete" value="true">
					Complete <input type="radio" name="isComplete" value="false"
					checked> Incomplete</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Create Task">
					<a href="TaskController?action=list">Go Back to Task List</a></td>
			</tr>
		</table>
	</form>
</body>
</html>