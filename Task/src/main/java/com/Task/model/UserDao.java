package com.Task.model;

import java.util.List;

public interface UserDao {
	User getUserByUsername(String username);

	List<User> getAllUsers();

	boolean addUser(User user);

}
