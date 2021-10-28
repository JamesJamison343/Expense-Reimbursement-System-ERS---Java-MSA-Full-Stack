package com.project1.servlet;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1.controller.ReimbursementController;
import com.project1.controller.UserController;
import com.project1.log.LogHelper;
import com.project1.model.User;

public class JSONDispatcher {
	
	static private LogHelper log = new LogHelper();
	
	public static void process(HttpServletRequest req, HttpServletResponse res) throws IOException {
		log.infoLogger("JSONDispatcher: " + req.getRequestURI() + " recived.");
		switch(req.getRequestURI()) {
		case "/Project1/getsessionuser.json":
			System.out.println("In get session user case, dispatcher");
			log.infoLogger("JSONDispatcher: Sending Request to UserController.getSessionUser()");
			UserController.getSessionUser(req, res);
			break;
		case "/Project1/getemployeetickets.json":
			System.out.println("getting empyee tickets");
			log.infoLogger("JSONDispatcher: Sending Request to ReimbursementController.getEmployeeTickets()");
			ReimbursementController.getEmployeeTickets(req, res);
			break;
		case "/Project1/getalltickets.json":
			System.out.println("Admin get all tickets");
			log.infoLogger("JSONDispatcher: Sending Request to ReimbursementController.getAllTickets()");
			ReimbursementController.getAllTickets(req, res);
			break;
		case "/Project1/approveTicket.json":
			System.out.println("In approve ticket");
			log.infoLogger("JSONDispatcher: Sending Request to ReimbursementController.approveTicket()");
			ReimbursementController.approveTicket(req, res);
			res.getWriter().write("Ticket approved");
			break;
		case "/Project1/denyTicket.json":
			System.out.println("In deny ticket");
			log.infoLogger("JSONDispatcher: Sending Request to ReimbursementController.denyTicket()");
			ReimbursementController.denyTicket(req, res);
			res.getWriter().write("Ticket denied");
			break;
		default:
			System.out.println("In JSON default");
			log.infoLogger("JSONDispatcher: Invalid request, responding with null object.");
			res.getWriter().write(new ObjectMapper().writeValueAsString(null));
		}
		
	}
	
}
