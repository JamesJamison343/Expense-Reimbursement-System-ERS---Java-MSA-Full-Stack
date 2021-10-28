package com.project1.service;

import com.project1.dao.UserDaoImpl;
import com.project1.log.LogHelper;
import com.project1.model.User;

public class UserService {
	
	private UserDaoImpl userData;
	static private LogHelper log = new LogHelper();
	
	public UserService() {
		
	}

	public UserService(UserDaoImpl userData) {
		super();
		this.userData = userData;
	}
	
	//verify Login
	public User getUserVerify(String username, String password) {
		log.infoLogger("UserService: In getUserVerify method, returning login verification.");
		try {
			User matchedUser = userData.getUser(username);
			if(matchedUser != null && matchedUser.getUsername().equals(username) && matchedUser.getPassword().equals(password)) {
				return matchedUser;
			} else {
				return null;
			}
		} catch(Exception e) {
			e.printStackTrace();
			log.ExceptionLog(e);
			return null;
		}
	}
	
}
