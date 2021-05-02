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
import models.Resource;
import models.Role;

public abstract class ResourceDao {

	protected static final String TB_NAME = "resources"; 
	protected static final String TB_ROLE_RESOURCE_ACTION = "role_resource_actions";

	public abstract boolean create();
	
	public static List<Resource> getAll(){
		Connection conn = DBConnection.getDBConnection();
		List<Resource> resources = new ArrayList<Resource>();
		String query = QueryBuilder.getSelectQueryWithWhereClause(TB_NAME, null);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);			
			while(rs.next()) {
				resources.add(Resource.mapResource(rs.getInt(1), rs.getString(2)));
			}
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resources;
	}
	
	public static Resource findResourceByID(int id) {
		Connection conn = DBConnection.getDBConnection();
		Resource resource = null;
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", id);
		String query = QueryBuilder.getSelectQueryWithWhereClause(TB_NAME, conditions);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);			
			if(rs.next()) {
				resource = Resource.mapResource(rs.getInt(1), rs.getString(2));
			}
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resource;
	}
	
	public abstract List<Integer> findPermissionRoles(Action action) ;

}
