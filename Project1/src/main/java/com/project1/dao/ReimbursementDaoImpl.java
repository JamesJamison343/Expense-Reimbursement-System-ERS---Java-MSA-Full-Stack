package com.project1.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.project1.log.LogHelper;
import com.project1.model.Reimbursement;
import com.project1.model.ReimbursementStatus;
import com.project1.model.ReimbursementType;
import com.project1.model.User;

public class ReimbursementDaoImpl implements ReimbursementDao {
	
	private DBConnection dbCon; 
	static private LogHelper log = new LogHelper();
	
	public ReimbursementDaoImpl() {
		
	}
	
	public ReimbursementDaoImpl(DBConnection dbCon) {
		super();
		this.dbCon = dbCon;
	}

	@Override //get all reimbs
	public List<Reimbursement> getAllReimbs() {
		log.infoLogger("ReimbursementDaoImpl: Retriving all reimbs from DB.");
		List<Reimbursement> reimbList = new ArrayList<>();
		try(Connection con = dbCon.getDBConnection()) {
			String sql = "select * from ers_reimbursement";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			//int index = 0;
			while(rs.next()) {
				ReimbursementStatus status = new ReimbursementStatus();
				ReimbursementType type = new ReimbursementType();
				status.setStatusId(rs.getInt(9));
				type.setTypeId(rs.getInt(10));
				
			switch(type.getTypeId()) {
				case 1: type.setType("Lodging");
						break;
				case 2: type.setType("Travel");
						break;
				case 3: type.setType("Food");
						break;
				case 4: type.setType("Other");
						break;
				default: break;
			}
			//System.out.println("Status ID: " + status.getStatusId());
			switch(status.getStatusId()) {
			
				case 1: status.setStatus("Pending");
					break;
				case 2: status.setStatus("Approved");
					break;
				case 3: status.setStatus("Denied");
					break;
				default: break;
			}
			//System.out.println("Status Type: " + status.getStatus());
				reimbList.add(new Reimbursement(
						rs.getInt(1), 
						rs.getDouble(2), 
						rs.getTimestamp(3), 
						rs.getTimestamp(4), 
						rs.getString(5),
						rs.getBlob(6), 
						rs.getInt(7), 
						rs.getInt(8), 
						status, 
						type));
			//System.out.println("STATUSOBJ: " + status + " " + reimbList);
				
				//System.out.println("DAO_WHILE_LOOP: " + reimbList.get(index));
				//index++;
			}
			//////////////////// something happens to the list once the loop ends
			/*
			 * for(Reimbursement reimb: reimbList) { System.out.println("DAO_AFTER_LOOP: " +
			 * reimb); }
			 */
		
			return reimbList;
		} catch (SQLException e) {
			e.printStackTrace();
			log.ExceptionLog(e);
			return null;
		}
	}
	
	@Override //get all reimbs by status
	public List<Reimbursement> getAllReimbs(int statusId) {
		List<Reimbursement> reimbList = new ArrayList<>();
		try(Connection con = dbCon.getDBConnection()) {
			String sql = "select * from ers_reimbursement where reimb_status_id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, statusId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ReimbursementStatus status = new ReimbursementStatus();
				ReimbursementType type = new ReimbursementType();
				status.setStatusId(rs.getInt(9));
				type.setTypeId(rs.getInt(10));
				
			switch(type.getTypeId()) {
				case 1: type.setType("Lodging");
						break;
				case 2: type.setType("Travel");
						break;
				case 3: type.setType("Food");
						break;
				case 4: type.setType("Other");
						break;
				default: break;
			}
			
			switch(status.getStatusId()) {
				case 1: status.setStatus("Pending");
					break;
				case 2: status.setStatus("Approved");
					break;
				case 3: status.setStatus("Denied");
					break;
				default: break;
			}
				reimbList.add(new Reimbursement(
						rs.getInt(1), 
						rs.getDouble(2), 
						rs.getTimestamp(3), 
						rs.getTimestamp(4), 
						rs.getString(5),
						rs.getBlob(6), 
						rs.getInt(7), 
						rs.getInt(8), 
						status, 
						type));
			}
			return reimbList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override //get an employee's reimbs
	public List<Reimbursement> getEmployeeReimbs(User user) {
		log.infoLogger("ReimbursementDaoImpl: Retriving employee reimbs from DB.");
		List<Reimbursement> reimbList = new ArrayList<>();
		try(Connection con = dbCon.getDBConnection()) {
			String sql = "select * from ers_reimbursement where reimb_author = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, user.getUserId());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ReimbursementStatus status = new ReimbursementStatus();
				ReimbursementType type = new ReimbursementType();
				status.setStatusId(rs.getInt(9));
				type.setTypeId(rs.getInt(10));
				switch(type.getTypeId()) {
				case 1: type.setType("Lodging");
						break;
				case 2: type.setType("Travel");
						break;
				case 3: type.setType("Food");
						break;
				case 4: type.setType("Other");
						break;
				default: break;
			}
			
			switch(status.getStatusId()) {
				case 1: status.setStatus("Pending");
					break;
				case 2: status.setStatus("Approved");
					break;
				case 3: status.setStatus("Denied");
					break;
				default: break;
			}
				reimbList.add(new Reimbursement(
						rs.getInt(1), 
						rs.getDouble(2), 
						rs.getTimestamp(3), 
						rs.getTimestamp(4), 
						rs.getString(5),
						rs.getBlob(6), 
						rs.getInt(7), 
						rs.getInt(8), 
						status, 
						type));
			}
			return reimbList;
		} catch (SQLException e) {
			log.ExceptionLog(e);
			e.printStackTrace();
			return null;
		}
	}
	
	@Override //get an employee's reimbs by status 
	public List<Reimbursement> getEmployeeReimbs(User user, int statusId) {
		List<Reimbursement> reimbList = new ArrayList<>();
		try(Connection con = dbCon.getDBConnection()) {
			String sql = "select * from ers_reimbursement where reimb_author=? and reimb_status_id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, user.getUserId());
			ps.setInt(2, statusId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ReimbursementStatus status = new ReimbursementStatus();
				ReimbursementType type = new ReimbursementType();
				status.setStatusId(rs.getInt(9));
				type.setTypeId(rs.getInt(10));
				switch(type.getTypeId()) {
				case 1: type.setType("Lodging");
						break;
				case 2: type.setType("Travel");
						break;
				case 3: type.setType("Food");
						break;
				case 4: type.setType("Other");
						break;
				default: break;
			}
			
			switch(status.getStatusId()) {
				case 1: status.setStatus("Pending");
					break;
				case 2: status.setStatus("Approved");
					break;
				case 3: status.setStatus("Denied");
					break;
				default: break;
			}
				reimbList.add(new Reimbursement(
						rs.getInt(1), 
						rs.getDouble(2), 
						rs.getTimestamp(3), 
						rs.getTimestamp(4), 
						rs.getString(5),
						rs.getBlob(6), 
						rs.getInt(7), 
						rs.getInt(8), 
						status, 
						type));
			}
			return reimbList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override//update reimb
	public void updateReimb(Reimbursement reimb) {
		log.infoLogger("ReimbursementDaoImpl: Updating reimb in DB.");
		try(Connection con = dbCon.getDBConnection()) {
			String sql = "update ers_reimbursement set reimb_resolved=?, reimb_resolver=?, reimb_status_id=? where reimb_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setTimestamp(1, reimb.getResolved());
			ps.setInt(2, reimb.getResolver());
			ps.setInt(3, reimb.getStatus().getStatusId());
			ps.setInt(4, reimb.getReimbursementId());
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			log.ExceptionLog(e);
		}
	}

	@Override//create new reimb
	public void createReimb(Reimbursement reimb) {
		log.infoLogger("ReimbursementDaoImpl: Inserting reimb in DB.");
		try(Connection con = dbCon.getDBConnection()) {
			//String sql = "insert into ers_reimbursement(reimb_ammount, reimb_submitted, reimb_description, reimb_author, reimb_status_id, reimb_type_id) values(?, ?, ?, ?, ?, ?)";
			String sql = "{? = call insert_ticket(?,?,?,?,?,?)}";
			//String sql = "{? = call insert_user(?,?,?,?,?,?)}";
			CallableStatement cs = con.prepareCall(sql);
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.setDouble(2, reimb.getAmmount());
			cs.setTimestamp(3, reimb.getSubmitted());
			cs.setString(4, reimb.getDescription());
			cs.setInt(5, reimb.getAuthor());
			cs.setInt(6, reimb.getStatus().getStatusId());
			cs.setInt(7, reimb.getType().getTypeId());
			cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			log.ExceptionLog(e);
		}
	}

	@Override//delete reimb
	public void deleteReimb(Reimbursement reimb) {
		try(Connection con = dbCon.getDBConnection()) {
			String sql = "delete from ers_reimbursement where reimb_id = ?";
			CallableStatement cs = con.prepareCall(sql);
			cs.setInt(1, reimb.getReimbursementId());
			cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
