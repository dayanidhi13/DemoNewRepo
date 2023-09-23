package com.Task.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Task.model.Task;
import com.Task.model.TaskDao;
import com.Task.model.TaskDaoImpl;

@WebServlet("/addTask")
public class addTask extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String dueDateStr = request.getParameter("dueDate");
		boolean isComplete = Boolean.parseBoolean(request.getParameter("isComplete"));

		// Validate and parse the dueDate
		Date dueDate = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dueDate = dateFormat.parse(dueDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			// Handle date parsing error here
			request.setAttribute("errorMessage", "Invalid due date format. Please use yyyy-MM-dd.");
			request.getRequestDispatcher("/createTask.jsp").forward(request, response);
			return; // Added return statement to stop further execution
		}

		// Create a Task object with the form data
		Task newTask = new Task(0, title, description, new java.sql.Date(dueDate.getTime()), isComplete);

		// You should have a TaskDao implementation to add the task to the database
		TaskDao taskDao = new TaskDaoImpl("jdbc:mysql://localhost:3306/task", "root", "9926");

		boolean success = taskDao.addTask(newTask);

		if (success) {
			// Redirect to the task list page
			response.sendRedirect(request.getContextPath() + "/TaskController?action=list");
		} else {
			// Handle task addition failure (e.g., display an error message)
			response.sendRedirect(request.getContextPath() + "/error.jsp");
		}
	}
}