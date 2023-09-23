package com.Task.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Task.model.TaskDao;
import com.Task.model.TaskDaoImpl;

@WebServlet("/deleteTask")
public class DeleteTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Get the task ID to be deleted from the request parameter
		String idParam = request.getParameter("id");

		if (idParam != null && !idParam.isEmpty()) {
			try {
				int taskId = Integer.parseInt(idParam);

				// You should have a TaskDao implementation to delete the task from the database
				TaskDao taskDao = new TaskDaoImpl("jdbc:mysql://localhost:3306/task", "root", "9926");

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