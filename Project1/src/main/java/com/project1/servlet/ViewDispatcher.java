package com.project1.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.project1.controller.ReimbursementController;
import com.project1.controller.UserController;
import com.project1.log.LogHelper;

public class ViewDispatcher {
	
	static private LogHelper log = new LogHelper();
	
	public static String process(HttpServletRequest req) {
		log.infoLogger("ViewDispatcher: " + req.getRequestURI() + " recived.");
		switch(req.getRequestURI()) {
		case "/Project1/login.change":
			log.infoLogger("ViewDispatcher: Sending request to UserController.login()");
			System.out.println("in login.change dispatcher");
			return UserController.login(req);
		case "/Project1/addTicket.change":
			System.out.println("IN ADDTICKET: " + req.getParameter("amount"));
			try {
				log.infoLogger("ViewDispatcher: Sending request to ReimbursementController.addTicket()");
				ReimbursementController.addTicket(req);
			} catch (IOException e) {
				e.printStackTrace();
				log.infoLogger("ViewDispatcher: Routing Failed...");
				log.ExceptionLog(e);
			}
			log.infoLogger("ViewDispatcher: Ticket added, redirecting back to employee.html");
			return "html/employee.html";
		default:
			System.out.println("in default of dispatcher");
			log.infoLogger("ViewDispatcher: Login failed, redirecting to failed_login.html");
			return "html/failed_login.html";
		}
		
	}
	
}
