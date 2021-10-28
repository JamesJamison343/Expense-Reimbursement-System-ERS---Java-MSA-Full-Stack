package com.project1.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Blob;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.project1.dao.ReimbursementDaoImpl;
import com.project1.dao.UserDaoImpl;
import com.project1.model.Reimbursement;
import com.project1.model.ReimbursementStatus;
import com.project1.model.ReimbursementType;
import com.project1.model.User;
import com.project1.service.ReimbursementService;
import com.project1.service.UserService;

public class ReimbServiceTests {
	
	
	@Mock private ReimbursementDaoImpl reimbData;
	@Mock private ReimbursementService reimbServ;
	@Mock private Reimbursement testReimb;
	@Mock private Reimbursement testReimb2;
	@Mock private UserDaoImpl userData;
	@Mock private UserService userServ;
	@Mock private User testUser;
	
	@SuppressWarnings("deprecation")
	@BeforeEach 
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		userServ = new UserService(userData);
		testUser = new User(12, "test", "password", "test", "user", "testuser@test.com", null);
		
	    reimbServ = new ReimbursementService(reimbData);
		testReimb = new Reimbursement(22, 100.00, null, null, "test desc", null, 12, 0, new ReimbursementStatus(1, "Pending"), null);
		testReimb2 = new Reimbursement(23, 200.00, null, null, "test desc2", null, 13, 0, new ReimbursementStatus(1, "Pending"), null);
		
		List<Reimbursement> reimbList = new ArrayList<>();
		reimbList.add(testReimb);
		
		List<Reimbursement> reimbList2 = new ArrayList<>();
		reimbList2.add(testReimb);
		reimbList2.add(testReimb2);
		
		when(reimbData.getEmployeeReimbs(testUser)).thenReturn(reimbList);
		when(reimbData.getAllReimbs()).thenReturn(reimbList2);
	}
	
	@Test
	public void testGetEmpReims() {
		List<Reimbursement> reimbList = new ArrayList<>();
		reimbList.add(testReimb);
		assertEquals(reimbServ.getEmployeeTickets(testUser), reimbList);
	}
	
	@Test
	public void testGetAllReims() {
		List<Reimbursement> reimbList = new ArrayList<>();
		reimbList.add(testReimb);
		reimbList.add(testReimb2);
		assertEquals(reimbServ.getAllTickets(), reimbList);
	}
	
	@Test
	public void testUpdateTicket() {
		Reimbursement testReimb3 = mock(Reimbursement.class);
		ReimbursementService reimbServ1 = mock(ReimbursementService.class);
		reimbServ1.updateTicket(testReimb3);
		verify(reimbServ1).updateTicket(testReimb3);
	}
	
	@Test
	public void testInsertTicket() {
		Reimbursement testReimb4 = mock(Reimbursement.class);
		ReimbursementService reimbServ2 = mock(ReimbursementService.class);
		reimbServ2.submitTicket(testReimb4);
		verify(reimbServ2).submitTicket(testReimb4);
	}
}
