package com.project1.dao;

import java.util.List;

import com.project1.model.Reimbursement;
import com.project1.model.User;

public interface ReimbursementDao {
	
	List<Reimbursement>getAllReimbs();
	List<Reimbursement>getAllReimbs(int status);
	List<Reimbursement> getEmployeeReimbs(User user);
	List<Reimbursement> getEmployeeReimbs(User user, int status);
	void updateReimb(Reimbursement reimb);
	void createReimb(Reimbursement reimb);
	void deleteReimb(Reimbursement reimb);
}
