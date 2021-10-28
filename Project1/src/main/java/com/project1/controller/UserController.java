package com.project1.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1.dao.DBConnection;
import com.project1.dao.UserDaoImpl;
import com.project1.log.LogHelper;
import com.project1.model.Reimbursement;
import com.project1.model.ReimbursementStatus;
import com.project1.model.ReimbursementType;
import com.project1.model.User;
import com.project1.service.UserService;

public class UserController {
	static DBConnection con = new DBConnection();
	static UserDaoImpl userData = new UserDaoImpl(con);
	static UserService userServ = new UserService(userData);
	static private LogHelper log = new LogHelper();
	
	public static String login(HttpServletRequest req) {
		log.infoLogger("UserController: In log in method.");
		if(!req.getMethod().equals("POST")) { //make sure post method was used
			System.out.println("in not post");
			log.infoLogger("UserController: Post method not used, redirecting to failed_login.html.");
			return "html/failed_login.html";
		}
		//get user 
		log.infoLogger("UserController: Sending request to userServ.geUserVerify.");
		User user = userServ.getUserVerify(req.getParameter("username"), req.getParameter("userpassword"));
		System.out.println(req.getParameter("username") + "  " + req.getParameter("userpassword"));
		
		if(user == null) {
			System.out.println("in user is null");
			log.infoLogger("UserController: User does not exist, redirecting to wrongcreds.change.");
			return "wrongcreds.change";
		}else if (user.getRole().getRoleId() == 2) {
			System.out.println("in manager case");
			log.infoLogger("UserController: Manager login, adding user to session, redirecting to html/manager.html");
			req.getSession().setAttribute("currentUser", user);
			return "html/manager.html";
		}else if (user.getRole().getRoleId() == 1) {
			System.out.println("in user case");
			log.infoLogger("UserController: Employee login, adding user to session, redirecting to html/employee.html");
			req.getSession().setAttribute("currentUser", user);
			return "html/employee.html";
		}
		log.infoLogger("UserController: Failed Login, redirect to html/failed_login.html");
		return "html/failed_login.html";
	}
	
	//get session user 
	public static void getSessionUser(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException {
		log.infoLogger("UserController: Adding user to session");
		User sessUser = (User)req.getSession().getAttribute("currentUser");
		res.getWriter().write(new ObjectMapper().writeValueAsString(sessUser));
	}
}
