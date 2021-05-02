package operations;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import models.Action;
import models.Resource;
import models.Role;
import models.User;

public class AdminOperation extends UserOperation {

	public static void createUser() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter User Name:");
		String name = sc.nextLine();
		System.out.println("Enter User Email:");
		String email = sc.nextLine();
		System.out.println("Enter User Password:");
		String password = sc.nextLine();
		if (new User.Builder(name, email, password).build().create()) {
			System.out.print("User Created successfully.");
		} else {
			System.out.print("User Not Created.");
		}
	}

	public static void getUsers(User self) {
		List<User> users = User.getUserList();
		users = users.stream().filter(u -> !u.getEmail().equals(self.getEmail())).collect(Collectors.toList());
		if (users.size() > 0) {
			System.out.printf("%-20s %-20s %-20s\n", "ID", "NAME", "EMAIL");
			for (User user : users) {
				System.out.printf("%-20s %-20s %-20s\n", user.getId(), user.getName(), user.getEmail());
			}
		} else {
			System.out.println("No Users Exists.");
		}
	}

	public static void createRole() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Role Name:");
		String name = sc.nextLine();
		if (new Role.Builder(name).build().create()) {
			System.out.print("Role Created successfully.");
		} else {
			System.out.print("Role Not Created.");
		}
	}

	public static void getRoles() {
		List<Role> roles = Role.getAll();
		if (roles.size() > 0) {
			System.out.println("Available Roles:");
			System.out.printf("%-20s %s\n", "ID", "NAME");
			for (Role role : roles) {
				System.out.printf("%-20s %s\n", role.getId(), role.getName());
			}
		} else {
			System.out.println("No Roles Exists.");
		}
	}

	public static void createResource() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Resource Name:");
		String name = sc.nextLine();
		if (new Resource.Builder(name).build().create()) {
			System.out.print("Resource Created successfully.");
		} else {
			System.out.print("Resource Not Created.");
		}
	}

	

	
	public static void assignRoleToUser() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter email for user:");
		String email = sc.nextLine();
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("email", email);
		User user = User.findUser(conditions);
		if(user == null) {
			System.out.println("Invalid Email Provided.");
		}else {
			getRoles();
			System.out.println("Enter 'ID' for Role:");
			int roleID = Integer.parseInt(sc.nextLine());	
			Role role = Role.findRoleByID(roleID);
			if(User.isUserHaveRole(user, role)) {
				System.out.println("This role is already assigned to user.");
			}else {
				if(User.assignRoleToUser(user, role)) {
					System.out.println(role.getName()+" role assigned to user named as "+user.getName()+".");
				}else {
					System.out.println("Can't assign role to user "+ user.getName());
				}
			}
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

	public static void giveResourceAccessForPerticularRole() {
		System.out.println("Please follow instructions:");
		Scanner sc = new Scanner(System.in);
		System.out.println("'Id' of roles required for operation.Are you want to see exist Roles?(Yes|Y / NO|N)");
		String roleIN = sc.nextLine();
		if(roleIN.trim().equalsIgnoreCase("YES") || roleIN.trim().equalsIgnoreCase("Y")) {
			getRoles();
		}else {
			System.out.println("Continuing with assuming you know Roles with ID.");
		}
		System.out.println("Please enter role id:");
		int roleID = Integer.parseInt(sc.nextLine());
		System.out.println("'Id' of resources required for operation.Are you want to see exist Resources?(Yes)");
		String resourceIN = sc.nextLine();
		if(resourceIN.trim().equalsIgnoreCase("YES") || resourceIN.trim().equalsIgnoreCase("Y")) {
			getResources();
		}else {
			System.out.println("Continuing with assuming you know Resources with ID.");
		}
		System.out.println("Please enter resource id:");
		int resourceID = Integer.parseInt(sc.nextLine());
		System.out.println("'Id' of action type required for operation.Are you want to see exist actions?(Yes)");
		String actionIN = sc.nextLine();
		if(actionIN.trim().equalsIgnoreCase("YES") || actionIN.trim().equalsIgnoreCase("Y")) {
			getActions();
		}else {
			System.out.println("Continuing with assuming you know actions with ID.");
		}
		System.out.println("Please enter action id:");
		int actionID = Integer.parseInt(sc.nextLine());
		System.out.println("Provided 'Role' id: "+roleID);
		System.out.println("Provided 'Resource' id: "+resourceID);
		System.out.println("Provided 'Action' id: "+actionID);
		System.out.println("Continue to save:(Yes/no)");
		String confirmation = sc.nextLine().trim();
		if(confirmation.equalsIgnoreCase("YES") || confirmation.equalsIgnoreCase("Y")) {
			Role role = Role.findRoleByID(roleID);
			if(role == null) {
				System.out.println("Invalid Role id provided.");
				return;
			}
			Resource resource = Resource.findResourceByID(resourceID);
			if(resource == null) {
				System.out.println("Invalid Resource id provided.");
				return;
			}
			Action action = Action.findActionByID(actionID);
			if(action == null) {
				System.out.println("Invalid action id provided.");
				return;
			}
			if(Role.isResourceActionRecordExist(role, resource, action)) {
				System.out.println("Record already exists.");
			}else {
				if(Role.addResourceAction(role, resource, action)) {
					System.out.println(String.format("Resource[%s], action[%s] added with role[%s].", resource.getName(), action.getName(), role.getName()));
				}else {
					System.out.println("Failed to insert resource_action correspond to role.");
				}
			}
		}
	}

	public static void checkPermission() {
		System.out.println("Please follow instructions carefully:");
		Scanner sc = new Scanner(System.in);
		System.out.println("Please Enter user email:");
		String email = sc.nextLine().trim();
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("email", email);
		User user = User.findUser(conditions);
		if(user == null) {
			System.out.println("No user exist with provided email.");
			return;
		}else {
			System.out.println(user.toString());
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
		
	}
}
