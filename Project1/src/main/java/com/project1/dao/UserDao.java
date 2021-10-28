package com.project1.dao;

import java.util.List;

import com.project1.model.User;

public interface UserDao {
	
	List<User>getAllUsers();
	User getUser(String username);
	void updateUser(User user);
	void createUser(User user);
	void deleteUser(User user);
}
