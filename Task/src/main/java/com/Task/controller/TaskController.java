package com.Task.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Task.model.Task;
import com.Task.model.TaskDao;
import com.Task.model.TaskDaoImpl;

@WebServlet("/TaskController")
public class TaskController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TaskDao taskDao;
    private static final Logger logger = Logger.getLogger(TaskController.class.getName()); // Create the logger

	public void init() {
		// Initialize the taskDao with database connection details
		String jdbcUrl = "jdbc:mysql://localhost:3306/task";
		String jdbcUsername = "root";
		String jdbcPassword = "9926";
		taskDao = new TaskDaoImpl(jdbcUrl, jdbcUsername, jdbcPassword);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action == null) {
			action = "list"; // Default action
		}

		if ("view".equals(action) || "edit".equals(action)) {
			// Your existing code for view and edit actions
		} else if ("delete".equals(action)) {
			deleteTask(request, response);
			return; // Added return statement to stop further execution
		}

		// Default action: Display the task list
		listTasks(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action == null) {
			action = "list"; // Default action
		}

		switch (action) {
		case "add":
			addTask(request, response);
			break;
		case "update":
			updateTask(request, response);
			break;
		case "delete":
			deleteTask(request, response); // Handle delete action
			break;
		default:
			listTasks(request, response);
			break;
		}
	}

	private void listTasks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Task> taskList = taskDao.getAllTasks();
		request.setAttribute("taskList", taskList);
		request.getRequestDispatcher("/tasks.jsp").forward(request, response);
	}

	private void addTask(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String dueDateStr = request.getParameter("dueDate");
		boolean isComplete = Boolean.parseBoolean(request.getParameter("isComplete"));

		// Validate and parse the dueDate string into a Date object
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dueDate = null;
		try {
			dueDate = dateFormat.parse(dueDateStr);
		} catch (ParseException e) {
			// Handle date parsing error
			request.setAttribute("error", "Invalid date format");
			request.getRequestDispatcher("/createTask.jsp").forward(request, response);
			return; // Added return statement to stop further execution
		}

		// Convert the java.util.Date to java.sql.Date
		java.sql.Date sqlDueDate = new java.sql.Date(dueDate.getTime());

		// Create a Task object with the retrieved data
		Task newTask = new Task(0, title, description, sqlDueDate, isComplete);

		// Call the addTask method of your TaskDao to insert the task into the database
		boolean success = taskDao.addTask(newTask);

		if (success) {
			// Task added successfully, redirect to the task listing page
			response.sendRedirect(request.getContextPath() + "/TaskController?action=list");
		} else {
			// Handle task addition failure (e.g., display an error message)
			response.sendRedirect(request.getContextPath() + "/error.jsp");
		}
	}

	private void updateTask(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idParam = request.getParameter("id");

		// Check if the "id" parameter is not empty or null
		if (idParam != null && !idParam.isEmpty()) {
			int taskId = Integer.parseInt(idParam);

			// Retrieve the task from your DAO
			Task existingTask = taskDao.getTaskById(taskId);

			if (existingTask != null) {
				// Update task details based on the form data
				String title = request.getParameter("title");
				String description = request.getParameter("description");
				String dueDateStr = request.getParameter("dueDate");
				boolean isComplete = Boolean.parseBoolean(request.getParameter("isComplete"));

				// Validate and parse the dueDate string into a Date object
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date dueDate = null;
				try {
					dueDate = dateFormat.parse(dueDateStr);
				} catch (ParseException e) {
					// Handle date parsing error here
					response.sendRedirect(request.getContextPath() + "/error.jsp");
					return; // Added return statement to stop further execution
				}

				// Convert the java.util.Date to java.sql.Date
				java.sql.Date sqlDueDate = new java.sql.Date(dueDate.getTime());

				// Update the existing task
				existingTask.setTitle(title);
				existingTask.setDescription(description);
				existingTask.setDueDate(sqlDueDate); // Set the correct due date
				existingTask.setComplete(isComplete);

				// Call the updateTask method of your TaskDao to update the task in the database
				boolean success = taskDao.updateTask(existingTask);

				if (success) {
					// Task updated successfully, redirect to the task listing page
					response.sendRedirect(request.getContextPath() + "/TaskController?action=list");
				} else {
					// Handle task update failure (e.g., display an error message)
					response.sendRedirect(request.getContextPath() + "/error.jsp");
				}
			} else {
				// Handle task not found (e.g., show an error page or redirect to the task list)
				response.sendRedirect(request.getContextPath() + "/TaskController?action=list");
			}
		} else {
			// Handle invalid or missing "id" parameter
			response.sendRedirect(request.getContextPath() + "/TaskController?action=list");
		}
	}

	private void deleteTask(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idParam = request.getParameter("id");
	    logger.info("Received delete request for task ID: " + idParam); // Log the received task ID

		if (idParam != null && !idParam.isEmpty()) {
			try {
				int taskId = Integer.parseInt(idParam);

				// Delete the task from your DAO
				boolean success = taskDao.deleteTask(taskId);

				if (success) {
					// Task deleted successfully, redirect to the task list
					response.sendRedirect(request.getContextPath() + "/TaskController?action=list");
				} else {
					// Handle task deletion failure (e.g., show an error page or redirect with an
					// error message)
					response.sendRedirect(request.getContextPath() + "/error.jsp");
				}
			} catch (NumberFormatException e) {
				// Handle invalid ID format
				e.printStackTrace();
				response.sendRedirect(request.getContextPath() + "/error.jsp");
			} catch (Exception e) {
				e.printStackTrace();
				// Log the exception details for debugging
				// You can also forward to an error page or handle the exception as needed
				response.sendRedirect(request.getContextPath() + "/error.jsp");
			}
		} else {
			// Handle invalid or missing "id" parameter
			response.sendRedirect(request.getContextPath() + "/TaskController?action=list");
		}
	}
}