package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.ActionDao;
import db.DBConnection;

public class Action extends ActionDao {

	private int id;
	private final String name;

	private Action(Builder builder) {
		this.name = builder.name;
	}

	
	private Action(int id, String name) {
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
		public Action build() {
			return new Action(this);
		}
	}

	@Override
	public boolean create() {
		Connection conn = DBConnection.getDBConnection();
		try {
			PreparedStatement pst = conn.prepareStatement("insert into " + TB_NAME +" (name) values (?)");
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
	
	public static Action mapAction(int id, String name) {
		return new Action(id, name);
	}

}
