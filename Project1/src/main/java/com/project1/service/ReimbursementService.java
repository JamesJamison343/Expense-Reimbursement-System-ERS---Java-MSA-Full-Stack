package com.project1.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.project1.dao.ReimbursementDaoImpl;
import com.project1.log.LogHelper;
import com.project1.model.Reimbursement;
import com.project1.model.User;

public class ReimbursementService {
	
	private ReimbursementDaoImpl reimbData;
	static private LogHelper log = new LogHelper();
	
	public ReimbursementService() {
		
	}

	public ReimbursementService(ReimbursementDaoImpl reimbData) {
		super();
		this.reimbData = reimbData;
	}
	
	//submit a ticket
	public void submitTicket(Reimbursement reimb) {
		log.infoLogger("ReimbursementService: In submitTicket method, invoking reimbData.createReimb().");
		LocalDateTime now = LocalDateTime.now(); 
		@SuppressWarnings("deprecation")
		Timestamp timeStamp = new Timestamp(
				now.getYear() - 1900, 
				now.getMonthValue() - 1, 
				now.getDayOfWeek().getValue() + 10, 
				now.getHour(), 
				now.getMinute(), 
				0, 
				0);
		reimb.setSubmitted(timeStamp);
		
		String ticketID = reimb.getType().getType();
		
		switch(ticketID) {
			case "Lodging" :
				reimb.getType().setTypeId(1);
				break;
			case "Travel" :
				reimb.getType().setTypeId(2);
				break;
			case "Food" :
				reimb.getType().setTypeId(3);
				break;
			case "Other":
				reimb.getType().setTypeId(4);
				break;
		}
		
		try {
			reimbData.createReimb(reimb);
		} catch(Exception e) {
			e.printStackTrace();
			log.ExceptionLog(e);
		}
	}
	
	//view past tickets of a single employee
	public List<Reimbursement> getEmployeeTickets(User user) {
		log.infoLogger("ReimbursementService: In getEmployeeTickets method, invoking reimbData.getEmployeeTickets().");
		try {
			return reimbData.getEmployeeReimbs(user);
		} catch(Exception e) {
			e.printStackTrace();
			log.ExceptionLog(e);
			return null;
		}
	}
	
	//get all tickets
	public List<Reimbursement> getAllTickets() {
		log.infoLogger("ReimbursementService: In getAllTickets method, invoking reimbData.getAllReimbs().");
		try {
			System.out.println("SERVICE: " + reimbData.getAllReimbs());
			return reimbData.getAllReimbs();
		} catch(Exception e) {
			e.printStackTrace();
			log.ExceptionLog(e);
			return null;
		}
	}
	
	//update ticket 
	public void updateTicket(Reimbursement reimb) {
		log.infoLogger("ReimbursementService: In updateTicket method, invoking reimbData.updateReimb().");
		LocalDateTime now = LocalDateTime.now(); 
		@SuppressWarnings("deprecation")
		Timestamp timeStamp = new Timestamp(
				now.getYear() - 1900, 
				now.getMonthValue() - 1, 
				now.getDayOfWeek().getValue() + 10, 
				now.getHour(), 
				now.getMinute(), 
				0, 
				0);
		reimb.setResolved(timeStamp);
		try {
			reimbData.updateReimb(reimb);
		}catch (Exception e) {
			e.printStackTrace();
			log.ExceptionLog(e);
		}
	}
	
}
