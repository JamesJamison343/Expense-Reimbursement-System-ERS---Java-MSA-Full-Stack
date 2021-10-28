package com.project1.model;

public class UserRole {
	
	private int roleId;
	private String user_role;
	
	public UserRole() {
		
	}

	public UserRole(int roleId, String user_role) {
		super();
		this.roleId = roleId;
		this.user_role = user_role;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}

	@Override
	public String toString() {
		return "UserRole [roleId=" + roleId + ", user_role=" + user_role + "]";
	}
}
