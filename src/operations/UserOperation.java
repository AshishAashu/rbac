package operations;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import db.DBConnection;
import models.Action;
import models.Resource;
import models.Role;
import models.User;

public class UserOperation {
	
	public static void getResources() {
		List<Resource> resources = Resource.getAll();
		if (resources.size() > 0) {
			System.out.println("Available Resources:");
			System.out.printf("%-20s %s\n", "ID", "NAME");
			for (Resource resource : resources) {
				System.out.printf("%-20s %s\n", resource.getId(), resource.getName());
			}
		} else {
			System.out.println("No Resource Exists.");
		}
	}

	public static void getActions() {
		List<Action> actions = Action.getAll();
		if (actions.size() > 0) {
			System.out.println("Available Actions:");
			System.out.printf("%-20s %s\n", "ID", "NAME");
			for (Action action : actions) {
				System.out.printf("%-20s %s\n", action.getId(), action.getName());
			}
		} else {
			System.out.println("No Action Exists.");
		}
	}
	
	public static void quitApp() {
		DBConnection.closeConnection();
		System.out.println("Thanks for using 'RBAC' APP. Your Feedback is valuable for us.");
		System.exit(1);
	}
	
	public static void checkPermission(User user) {
		System.out.println("Please follow instructions carefully:");
		Scanner sc = new Scanner(System.in);
		List<Role> userRoles = user.getRoles();
		if(userRoles.size() == 0) {
			System.out.println("There is no role assign to user.");
		}else {
			System.out.println("'Id' of resources required for operation.Are you want to see exist Resources?(Yes)");
			String resourceIN = sc.nextLine();
			if(resourceIN.trim().equalsIgnoreCase("YES") || resourceIN.trim().equalsIgnoreCase("Y")) {
				getResources();
			}else {
				System.out.println("Continuing with assuming you know Resources with ID.");
			}
			System.out.println("Please enter resource id:");
			int resourceID = Integer.parseInt(sc.nextLine());
			Resource resource = Resource.findResourceByID(resourceID);
			if(resource == null) {
				System.out.println("Wrong id entered for resource.");
				return;
			}else {
				System.out.println("'Id' of action type required for operation.Are you want to see exist actions?(Yes)");
				String actionIN = sc.nextLine();
				if(actionIN.trim().equalsIgnoreCase("YES") || actionIN.trim().equalsIgnoreCase("Y")) {
					getActions();
				}else {
					System.out.println("Continuing with assuming you know actions with ID.");
				}
				System.out.println("Please enter action id:");
				int actionID = Integer.parseInt(sc.nextLine());
				Action action = Action.findActionByID(actionID);
				if(action == null) {
					System.out.println("Wrong id entered for action.");
					return;
				}else {
					System.out.println("Sit back! System is checking for permission...");
					List<Integer> permitedRoleIds = resource.findPermissionRoles(action);
					List<Integer> userRoleIds = userRoles.stream().map(Role::getId).collect(Collectors.toList());
					userRoleIds.retainAll(permitedRoleIds);
					if(userRoleIds.size() == 0) {
						System.out.println(
								String.format("User is not permitted to perform %s action on resource %s",
												action.getName(), resource.getName()));
					}else {
						System.out.println(
								String.format("User is permitted to perform %s action on resource %s",
												action.getName(), resource.getName()));
					}
				}
			}
		}
	}
	
	public static void getUserAssignedRoles(User user) {
		List<Role> userRoles = user.getRoles();
		if(userRoles.size() == 0) {
			System.out.println(String.format("No role assigned to [%s].",user.getEmail()));
		}else {
			System.out.println(String.format("Below roles are assigned to user with email[%s]:",user.getEmail()));
			System.out.printf("%s\n", "NAME");
			for (Role role : userRoles) {
				System.out.printf("%s\n", role.getName());
			}
		}
	}
}
