package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import db.DBConnection;
import db.QueryBuilder;
import models.Role;
import models.User;

public abstract class UserDao {
	
	protected static final String TB_NAME = "users"; 
	protected static final String TB_USER_ROLE = "user_roles";

	public abstract boolean create();
	
	public static User getAdmin() {
		Connection conn = DBConnection.getDBConnection();
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("name", "admin");
		String query = QueryBuilder.getSelectQueryWithWhereClause("users", conditions);
		User admin = null;
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.next()) {
				admin = User.mapUser(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return admin;
	}
	
	public static List<User> getUserList(){
		Connection conn = DBConnection.getDBConnection();
		List<User> users = new ArrayList<User>();
		String query = QueryBuilder.getSelectQueryWithWhereClause("users", null);
		User admin = null;
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);			
			while(rs.next()) {
				users.add(User.mapUser(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return users;
	}
	
	public static User findUser(HashMap<String, Object> conditions) {
		Connection conn = DBConnection.getDBConnection();
		String query = QueryBuilder.getSelectQueryWithWhereClause(TB_NAME, conditions);
		User user = null;
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.next()) {
				user = User.mapUser(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public static boolean isUserHaveRole(User user, Role role) {
		boolean haveRole = false;
		Connection conn = DBConnection.getDBConnection();
		String query = "select count(*) from " + TB_USER_ROLE + " where user_id=" + user.getId() +" and role_id="+ role.getId()+";";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.next()) {
				int count = rs.getInt(1);
				if(count > 0) {
					haveRole = true;
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return haveRole;
	}
	

	public static boolean assignRoleToUser(User user, Role role) {
		Connection conn = DBConnection.getDBConnection();
		String query = "insert into "+TB_USER_ROLE+" (user_id, role_id) values (?,?)";
		try {
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setInt(1, user.getId());
			pst.setInt(2, role.getId());
			if(pst.executeUpdate() > 0) {
				pst.close();
				return true;
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public abstract List<Role> getRoles();

}
