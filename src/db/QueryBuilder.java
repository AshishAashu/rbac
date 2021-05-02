package db;

import java.util.Map;

public class QueryBuilder {

	public static String getSelectQueryWithWhereClause(String tableName, Map<String, Object> conditions) {
		StringBuilder query = new StringBuilder();
        query.append(" SELECT * FROM " + tableName);
		if(conditions != null) {
			query.append(" where ");
			for(Map.Entry<String, Object> entry: conditions.entrySet()) {
				if (entry.getValue() instanceof String)
	            {
	                query.append(entry.getKey() + " = '" + entry.getValue() + "'");
	            }
	            else if (entry.getValue() instanceof Number)
	            {
	                query.append(entry.getKey() + "=" + entry.getValue());
	            }
	            else if (entry.getValue() == null)
	            {
	                query.append(entry.getKey() + " is null ");
	            }
			}
		}
		query.append(";");
		return query.toString();
	}

}
