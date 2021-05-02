package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.RoleDao;
import db.DBConnection;

public class Role extends RoleDao {

	private int id;
	private final String name;

	private Role(Builder builder) {
		this.name = builder.name;
	}

	
	private Role(int id, String name) {
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
		public Role build() {
			return new Role(this);
		}
	}

	@Override
	public boolean create() {
		Connection conn = DBConnection.getDBConnection();
		try {
			PreparedStatement pst = conn.prepareStatement("insert into roles (name) values (?)");
			pst.setString(1, this.getName());
			if(pst.executeUpdate() > 0) {
				pst.close();
				return true;
			}
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}
	
	public static Role mapRole(int id, String name) {
		return new Role(id, name);
	}

}
