package com.project1.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project1.dao.DBConnection;
import com.project1.dao.ReimbursementDaoImpl;
import com.project1.log.LogHelper;
import com.project1.model.Reimbursement;
import com.project1.model.ReimbursementStatus;
import com.project1.model.ReimbursementType;
import com.project1.model.User;
import com.project1.service.ReimbursementService;

public class ReimbursementController {
	static DBConnection con = new DBConnection();
	static ReimbursementDaoImpl reimbData = new ReimbursementDaoImpl(con);
	static ReimbursementService reimbServ = new ReimbursementService(reimbData);
	static private LogHelper log = new LogHelper();
	
	//add ticket to db
	public static void addTicket(HttpServletRequest req) throws JsonProcessingException, IOException {
		log.infoLogger("ReimbursementController: In addTicket method, invoking reimbServ.submitTicket()");
		User sessUser = (User)req.getSession().getAttribute("currentUser");
		Reimbursement newTicket = new Reimbursement();
		double amount = Double.parseDouble(req.getParameter("amount"));
		String description = req.getParameter("description");
		int author = sessUser.getUserId();
		String type = req.getParameter("typeSelect");
		newTicket.setAmmount(amount);
		newTicket.setAuthor(author);
		newTicket.setDescription(description);
		ReimbursementType newType = new ReimbursementType(0, type);
		ReimbursementStatus newStatus = new ReimbursementStatus();
		newStatus.setStatusId(1);
		newStatus.setStatus("Pending");
		newTicket.setStatus(newStatus);
		newTicket.setType(newType);
		reimbServ.submitTicket(newTicket);
	}
	
	//get user tickets
	public static void getEmployeeTickets(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException {
		log.infoLogger("ReimbursementController: In getEmployeeTickets method, invoking reimbServ.getEmployeeTickets().");
		User sessUser = (User)req.getSession().getAttribute("currentUser");
		List<Reimbursement> employeeTickets = new ArrayList<>();
		employeeTickets = reimbServ.getEmployeeTickets(sessUser);
		log.infoLogger("ReimbursementController: Resonding with employeeTickets JSON.");
		res.getWriter().write(new ObjectMapper().writeValueAsString(employeeTickets));
	}
	
	//get all tickets
	public static void getAllTickets(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException {
		log.infoLogger("ReimbursementController: In getAllTickets method, invoking reimbServ.getAllTickets().");
		List<Reimbursement> allTickets = new ArrayList<>();
		allTickets = reimbServ.getAllTickets();
		System.out.println("CONTROLLER: " + allTickets);
		log.infoLogger("ReimbursementController: Resonding with allTickets JSON.");
		res.getWriter().write(new ObjectMapper().writeValueAsString(allTickets));
	}
	
	//approve ticket
	public static void approveTicket(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException {
		log.infoLogger("ReimbursementController: In getAllTickets method, invoking reimbServ.updateTicket().");
		BufferedReader reader = req.getReader();
		ObjectMapper mapper = new ObjectMapper();
		Reimbursement reimb = mapper.readValue(reader.readLine(), Reimbursement.class);
		System.out.println(reimb); 
		reimb.getStatus().setStatusId(2);
		//reimb.getStatus().setStatus("Approved");
		reimbServ.updateTicket(reimb);
	}
		
	//deny ticket
	public static void denyTicket(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException, IOException {
		log.infoLogger("ReimbursementController: In getAllTickets method, invoking reimbServ.updateTicket().");
		BufferedReader reader = req.getReader();
		ObjectMapper mapper = new ObjectMapper();
		Reimbursement reimb = mapper.readValue(reader.readLine(), Reimbursement.class);
		System.out.println(reimb); 
		reimb.getStatus().setStatusId(3);
		//reimb.getStatus().setStatus("Denied");
		reimbServ.updateTicket(reimb);
	}
}
