package com.Task.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Task.model.Task;
import com.Task.model.User;
import com.Task.model.UserDao;
import com.Task.model.UserDaoImpl;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserDao userDao;

	public void init() {
		// Initialize the userDao with a valid database connection
		userDao = new UserDaoImpl("jdbc:mysql://localhost:3306/task", "root", "9926");
	}

	public UserController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action.equals("login")) {
			loginUser(request, response);
		} else if (action.equals("register")) {
			registerUser(request, response);
		}
	}

	private void loginUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		User user = userDao.getUserByUsername(username);

		if (user != null && user.getPassword().equals(password)) {
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			response.sendRedirect("dashboard.jsp");
		} else {
			// Handle incorrect login credentials
			response.sendRedirect("login.jsp?error=1");
		}
	}

	private void registerUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		User existingUser = userDao.getUserByUsername(username);

		if (existingUser == null) {
			User newUser = new User();
			newUser.setUsername(username);
			newUser.setPassword(password);

			boolean success = userDao.addUser(newUser);

			if (success) {
				// Registration successful, redirect to login page
				response.sendRedirect("login.jsp");
			} else {
				// Handle registration failure
				response.sendRedirect("register.jsp?error=2");
			}
		} else {
			// Handle duplicate username
			response.sendRedirect("register.jsp?error=1");
		}

	}

}
