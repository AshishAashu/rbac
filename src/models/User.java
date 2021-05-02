package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import dao.UserDao;

public class User extends UserDao {
	private int id;
	private final String name, email, password;

	private User(Builder builder) {
		this.name = builder.name;
		this.email = builder.email;
		this.password = builder.password;
	}

	
	private User(int id, String name, String email, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public static class Builder {
		private final String name, email, password;

		public Builder(String name, String email, String password) {
			this.name = name;
			this.email = email;
			this.password = password;
		}

		public User build() {
			return new User(this);
		}
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public boolean create() {
		Connection conn = DBConnection.getDBConnection();
		try {
			PreparedStatement pst = conn.prepareStatement("insert into users (name, email, password) values (?,?,?)");
			pst.setString(1, this.getName());
			pst.setString(2, this.getEmail());
			pst.setString(3, this.getPassword());
			if(pst.executeUpdate() > 0) {
				pst.close();
				return true;
			}
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}

	public static User mapUser(int id, String name, String email, String password) {
		return new User(id, name, email, password);
	}
	
	public static boolean isAdminExist() {
		if(UserDao.getAdmin() == null) {
			return false;
		}
		return true;		
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + "]";
	}


	@Override
	public List<Role> getRoles() {
		String query = String.format("select role_id from user_roles where user_id=%d;", this.getId());
		Connection conn = DBConnection.getDBConnection();
		List<Role> userRoles = new ArrayList<Role>(); 
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				Role role = Role.findRoleByID(rs.getInt(1));
				if(role != null) {
					userRoles.add(role);
				}
			}
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return userRoles;
	}	
	
}
