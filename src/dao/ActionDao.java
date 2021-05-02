package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import db.DBConnection;
import db.QueryBuilder;
import models.Action;
import models.Action;

public abstract class ActionDao {
	
	

	protected static final String TB_NAME = "actions"; 
	

	public abstract boolean create();
	
	public static List<Action> getAll(){
		Connection conn = DBConnection.getDBConnection();
		List<Action> actions = new ArrayList<Action>();
		String query = QueryBuilder.getSelectQueryWithWhereClause(TB_NAME, null);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);			
			while(rs.next()) {
				actions.add(Action.mapAction(rs.getInt(1), rs.getString(2)));
			}
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return actions;
	}
	
	public static boolean isActionExists(String name) {
		boolean actionExists = false;
		Connection conn = DBConnection.getDBConnection();
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("name", name);
		String query = QueryBuilder.getSelectQueryWithWhereClause(TB_NAME, conditions);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);			
			if(rs.next()) {
				actionExists = true;
			}
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return actionExists;
	}
	
	public static Action findActionByID(int id) {
		Connection conn = DBConnection.getDBConnection();
		Action action = null;
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", id);
		String query = QueryBuilder.getSelectQueryWithWhereClause(TB_NAME, conditions);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);			
			while(rs.next()) {
				action = Action.mapAction(rs.getInt(1), rs.getString(2));
			}
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return action;
	}
}
