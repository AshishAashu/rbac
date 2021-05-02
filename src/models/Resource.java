package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.ResourceDao;
import db.DBConnection;
import models.Resource.Builder;

public class Resource extends ResourceDao{

	private int id;
	private final String name;

	private Resource(Builder builder) {
		this.name = builder.name;
	}

	
	private Resource(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}


	public String getName() {
		return name;
	}

	public static class Builder {
		private final String name;

		public Builder(String name) {
			this.name = name;
		}
		public Resource build() {
			return new Resource(this);
		}
	}

	@Override
	public boolean create() {
		Connection conn = DBConnection.getDBConnection();
		try {
			PreparedStatement pst = conn.prepareStatement("insert into "+ TB_NAME +" (name) values (?)");
			pst.setString(1, this.getName());
			if(pst.executeUpdate() > 0) {
				return true;
			}
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}
	
	public static Resource mapResource(int id, String name) {
		return new Resource(id, name);
	}


	@Override
	public List<Integer> findPermissionRoles(Action action) {
		String query = String.format("select distinct(role_id) from %s where resource_id=%d and action_id=%d;", TB_ROLE_RESOURCE_ACTION, this.getId(), action.getId());
		Connection conn = DBConnection.getDBConnection();
		List<Integer> permitRoleIds = new ArrayList<Integer>();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);			
			while(rs.next()) {
				permitRoleIds.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return permitRoleIds;
	}
}
