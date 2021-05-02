package constants;

import java.util.LinkedHashMap;

public class AdminInstructions {
	enum ALLOWED_OPERATIONS{
		USER_CREATE("USER_CREATE", "PRESS '1'	: TO CREATE USER."),
		GET_USERS("GET_USERS", "PRESS '11'	: TO GET LIST OF USERS."),
		ASSIGN_ROLE("ASSIGN_ROLE", "PRESS '12'	: TO ASSIGN ROLE TO USER."),
		HAS_PERMIT("HAS_PERMIT", "PRESS '13'	: TO CHECK PERMISSION FOR USER FOR PERTICULER RESOURCE & ACTION"),
		ROLE_CREATE("ROLE_CREATE", "PRESS '2'	: TO CREATE ROLE"),
		GET_ROLES("GET_ROLES", "PRESS '21'	: TO GET LIST OF ROLES."),
		GIVE_ACCESS_TO_ROLE_FOR_RESOURCE("GIVE_ACCESS_FOR_RESOURCE_TO_ROLE", "PRESS '22'	: TO GIVE ACCESS TO ROLE FOR RESOURCE WITH ACTION"),
		RESOURCE_CREATE("RESOURCE_CREATE", "PRESS '3'	: TO CREATE RESOURCE"),
		GET_RESOURCES("GET_RESOURCES", "PRESS '31'	: TO GET LIST OF RESOURCES."),
		GET_ACTIONS("GET_ACTIONS", "PRESS '4'	: TO GET LIST OF ACTIONS."),
		LOGIN_AS_ANOTHER_USER("LOGIN_AS_ANOTHER_USER", "PRESS '5'	: TO LOGIN AS ANOTHER USER"),
		EXIT("EXIT", "PRESS '10'	: TO CLOSE APP.");
		
	    private final String key;
	    private final String value;

	    ALLOWED_OPERATIONS(String key, String value) {
	        this.key = key;
	        this.value = value;
	    }

	    public String getKey() {
	        return key;
	    }
	    public String getValue() {
	        return value;
	    }
	}
	
	static LinkedHashMap<String, String> instructions = null;
	
	static {
		if(instructions == null) {
			instructions = new LinkedHashMap<String, String>();
		}
		for(ALLOWED_OPERATIONS op: ALLOWED_OPERATIONS.values()) {
			instructions.put(op.getKey(), op.getValue());
		}
	}
	
	public static LinkedHashMap<String, String> getInstructions(){
		return instructions;
	}
}
