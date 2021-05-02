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
import models.Action;
import models.Resource;
import models.Role;

public abstract class RoleDao {
	
	protected static final String TB_NAME = "roles";
	protected static final String TB_ROLE_RESOURCE_ACTION = "role_resource_actions";

	public abstract boolean create();
	
	public static List<Role> getAll(){
		Connection conn = DBConnection.getDBConnection();
		List<Role> roles = new ArrayList<Role>();
		String query = QueryBuilder.getSelectQueryWithWhereClause(TB_NAME, null);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);			
			while(rs.next()) {
				roles.add(Role.mapRole(rs.getInt(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return roles;
	}
	
	public static Role findRoleByID(int id) {
		Connection conn = DBConnection.getDBConnection();
		Role role = null;
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", id);
		String query = QueryBuilder.getSelectQueryWithWhereClause(TB_NAME, conditions);
		try { 
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);			
			if(rs.next()) {
				role = Role.mapRole(rs.getInt(1), rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return role;
	}
	
	public static boolean isResourceActionRecordExist(Role role, Resource resource, Action action) {
		Connection conn = DBConnection.getDBConnection();
		int count = 0;
		String query = String.format("select count(*) from %s where role_id=%d and resource_id=%d and action_id=%d;", TB_ROLE_RESOURCE_ACTION, role.getId(), resource.getId(), action.getId());
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.next()) {
				count = rs.getInt(1);
				if(count > 0) {
					return true;
				}
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public static boolean addResourceAction(Role role, Resource resource, Action action) {
		Connection conn = DBConnection.getDBConnection();
		String query = "insert into "+TB_ROLE_RESOURCE_ACTION+" (role_id, resource_id, action_id) values (?,?,?)";
		try {
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setInt(1, role.getId());
			pst.setInt(2, resource.getId());
			pst.setInt(3,  action.getId());
			if(pst.executeUpdate() > 0) {
				pst.close();
				return true;
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
}
