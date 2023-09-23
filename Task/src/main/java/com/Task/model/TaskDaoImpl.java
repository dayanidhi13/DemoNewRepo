package com.Task.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDaoImpl implements TaskDao {
	private final String jdbcUrl;
	private final String jdbcUsername;
	private final String jdbcPassword;

	public TaskDaoImpl(String jdbcUrl, String jdbcUsername, String jdbcPassword) {
		this.jdbcUrl = "jdbc:mysql://localhost:3306/task";
		this.jdbcUsername = "root";
		this.jdbcPassword = "9926";
	}

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
	}

	@Override
	public List<Task> getAllTasks() {
		List<Task> tasks = new ArrayList<>();
		String query = "SELECT * FROM tasks";

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Task task = new Task(resultSet.getInt("id"), resultSet.getString("title"),
						resultSet.getString("description"), resultSet.getDate("due_date"),
						resultSet.getBoolean("is_complete"));
				tasks.add(task);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to retrieve tasks from the database.", e);
		}

		return tasks;
	}

	@Override
	public Task getTaskById(int taskId) {
		String query = "SELECT * FROM tasks WHERE id = ?";

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, taskId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					Task task = new Task(resultSet.getInt("id"), resultSet.getString("title"),
							resultSet.getString("description"), resultSet.getDate("due_date"),
							resultSet.getBoolean("is_complete"));
					return task;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to retrieve the task from the database.", e);
		}

		return null;
	}

	@Override
	public boolean addTask(Task task) {
		String query = "INSERT INTO tasks (title, description, due_date, is_complete) VALUES (?, ?, ?, ?)";

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, task.getTitle());
			preparedStatement.setString(2, task.getDescription());
			preparedStatement.setDate(3, new java.sql.Date(task.getDueDate().getTime()));
			preparedStatement.setBoolean(4, task.isComplete());

			int rowsInserted = preparedStatement.executeUpdate();
			return rowsInserted > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to add the task to the database.", e);
		}
	}

	@Override
	public boolean updateTask(Task task) {
		String query = "UPDATE tasks SET title = ?, description = ?, due_date = ?, is_complete = ? WHERE id = ?";

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, task.getTitle());
			preparedStatement.setString(2, task.getDescription());
			preparedStatement.setDate(3, new java.sql.Date(task.getDueDate().getTime()));
			preparedStatement.setBoolean(4, task.isComplete());
			preparedStatement.setInt(5, task.getId());

			int rowsUpdated = preparedStatement.executeUpdate();
			return rowsUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to update the task in the database.", e);
		}
	}

	@Override
	public boolean deleteTask(int taskId) {
		String query = "DELETE FROM tasks WHERE id = ?";

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, taskId);

			int rowsDeleted = preparedStatement.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to delete the task from the database.", e);
		}
	}
}