package com.project1.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.project1.dao.UserDao;
import com.project1.log.LogHelper;
import com.project1.dao.DBConnection;
import com.project1.model.User;
import com.project1.model.UserRole;

public class UserDaoImpl implements UserDao {
	
	private DBConnection dbCon; 
	static private LogHelper log = new LogHelper();
	
	public UserDaoImpl() {
		
	}
	
	public UserDaoImpl(DBConnection dbCon) {
		super();
		this.dbCon = dbCon;
	}

	@Override///get all users
	public List<User> getAllUsers() {
		List<User> userList = new ArrayList<>();
		try(Connection con = dbCon.getDBConnection()) {
			//String sql = "select * from ers_users";
			String sql = "select * from ers_users u full outer join ers_user_roles ur on u.USER_ROLE_ID = ur.ERS_USER_ROLE_ID";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			UserRole userRole = new UserRole();
			while(rs.next()) {
				userRole.setRoleId(rs.getInt(7));
				userRole.setUser_role(rs.getString(9));
				
				/*
				 * switch(userRole.getRoleId()) { case 1: userRole.setUser_role("Employee");
				 * break; case 2: userRole.setUser_role("Manager"); break; }
				 */
				
				userList.add(new User(rs.getInt(1), 
						rs.getString(2), 
						rs.getString(3), 
						rs.getString(4), 
						rs.getString(5), 
						rs.getString(6), 
						userRole));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}

	@Override//get a user 
	public User getUser(String username) {
		log.infoLogger("UserDaoImpl: Retriving user from DB.");
		try(Connection connection = dbCon.getDBConnection()) {
			//String sql = "select * from ers_users where ers_username = ?";
			String sql = "select * from ers_users u full outer join ers_user_roles ur on u.USER_ROLE_ID = ur.ERS_USER_ROLE_ID where ers_username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();
			User user = new User();
			UserRole userRole = new UserRole();
			while(rs.next()) {
				userRole.setRoleId(rs.getInt(7));
				userRole.setUser_role(rs.getString(9));
				user = new User(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						userRole
					);
			}
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			log.ExceptionLog(e);
		}
		return null;
	}

	@Override //update user 
	public void updateUser(User user) {
		try(Connection con = dbCon.getDBConnection()) {
			String sql = "update ers_users set ers_username=?, ers_password=?, user_first_name=?, user_last_name=?, user_email=?, user_role_id=? where ers_username = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFirstName());
			ps.setString(4, user.getLastName());
			ps.setString(5, user.getEmail());
			ps.setInt(6, user.getRole().getRoleId());
			ps.setString(7, user.getUsername());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override //create user
	public void createUser(User user) {
		try(Connection con = dbCon.getDBConnection()) {
			String sql = "insert into ers_users(ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id) values(?, ?, ?, ?, ?, ?)";
			CallableStatement cs = con.prepareCall(sql);
			cs.setString(1, user.getUsername());
			cs.setString(2, user.getPassword());
			cs.setString(3, user.getFirstName());
			cs.setString(4, user.getLastName());
			cs.setString(5, user.getEmail());
			cs.setInt(6, user.getRole().getRoleId());
			cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override //delete user 
	public void deleteUser(User user) {
		try(Connection con = dbCon.getDBConnection()) {
			String sql = "delete from ers_users where ers_users_id = ?";
			CallableStatement cs = con.prepareCall(sql);
			cs.setInt(1, user.getUserId());
			cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
