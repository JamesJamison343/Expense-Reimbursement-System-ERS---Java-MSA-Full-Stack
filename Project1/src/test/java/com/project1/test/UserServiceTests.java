package com.project1.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.project1.dao.DBConnection;
import com.project1.dao.UserDaoImpl;
import com.project1.model.Reimbursement;
import com.project1.model.User;
import com.project1.model.UserRole;
import com.project1.service.ReimbursementService;
import com.project1.service.UserService;

public class UserServiceTests {
	
	@Mock
	
	private UserDaoImpl userData;
	private UserService userServ;
	private User testUser;
	
	@SuppressWarnings("deprecation")
	@BeforeEach 
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
	    userServ = new UserService(userData);
		testUser = new User(12, "test", "password", "test", "user", "testuser@test.com", null);
		when(userData.getUser("test")).thenReturn(testUser);
	}
	
	@Test
	public void testGetUserVerifyInvokes() {
		User testUser2 = mock(User.class);
		UserService userServ2 = mock(UserService.class);
		userServ2.getUserVerify(testUser2.getUsername(), testUser2.getPassword());
		verify(userServ2).getUserVerify(testUser2.getUsername(), testUser2.getPassword());
	}
	
	@Test
	public void testGetUserVerify() {
		assertEquals(userServ.getUserVerify("test", "password"), testUser);
	}
	
	@Test
	public void testFailUserVerify() {
		assertEquals(userServ.getUserVerify("badUsername", "badPassword"), null);
	}
}
