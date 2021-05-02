package constants;

import java.util.LinkedHashMap;

public class UserInstructions {

	enum ALLOWED_OPERATIONS{
		HAS_PERMIT("HAS_PERMIT", "PRESS '1'	: TO CHECK PERMISSION FOR USER FOR PERTICULER RESOURCE & ACTION"),
		GET_MY_ROLES("GET_MY_ROLES", "PRESS '2'	: TO GET LIST OF ASSIGNED ROLES."),
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
