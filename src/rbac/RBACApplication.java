package rbac;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import constants.AdminInstructions;
import constants.UserInstructions;
import models.Action;
import models.User;
import operations.AdminOperation;
import operations.UserOperation;
import dao.UserDao;

public class RBACApplication implements Runnable {

	enum DEFAULT_ACTIONS {
		READ, WRITE, DELETE
	}

	User currentUser = null;
	boolean isAdmin = false;
	boolean loggedIn = false;

	public void run() {
		System.out.println("By Default! System LoggenIn with admin:");
		initializeApp();
		if (loggedIn) {
			while (true) {
				instructions();
				Scanner sc = new Scanner(System.in);
				String s = sc.nextLine();
				if (!isAdmin) {
					handleUserOption(s);
				} else {
					handleAdminOption(s);
				}
			}
		}
	}

	private void initializeApp() {
		if (!User.isAdminExist()) {
			System.out.println("Admin not exists.\n creating admin:");
			UserDao.getAdmin();
			User admin = new User.Builder("admin", "admin@gmail.com", "password").build();
			admin.create();
			currentUser = admin;
			System.out.println("Admin created successfully.\n");
		} else {
			System.out.println("Admin exists.\n");
			currentUser = User.getAdmin();
		}
		isAdmin = true;
		loggedIn = true;
		initializeActions();
	}

	private void initializeActions() {
		for (DEFAULT_ACTIONS action : DEFAULT_ACTIONS.values()) {
			if (!Action.isActionExists(action.toString())) {
				new Action.Builder(action.toString()).build().create();
			}
		}
	}

	private void instructions() {
		if (isAdmin) {
			adminInstructions();
		} else {
			userInstructions();
		}
	}

	private void adminInstructions() {
		LinkedHashMap<String, String> instructions = AdminInstructions.getInstructions();
		for (Map.Entry<String, String> entry : instructions.entrySet()) {
			System.out.println(entry.getValue());
		}
	}

	private void userInstructions() {
		LinkedHashMap<String, String> instructions = UserInstructions.getInstructions();
		for (Map.Entry<String, String> entry : instructions.entrySet()) {
			System.out.println(entry.getValue());
		}
	}

	private void handleUserOption(String s) {
		if (s != null || !s.trim().equals("")) {
			int option = Integer.parseInt(s);
			switch (option) {
			case 1:
				UserOperation.checkPermission(currentUser);
				break;
			case 2:
				UserOperation.getUserAssignedRoles(currentUser);
				break;
			case 5:
				loginUser();
				break;
			case 10:
				UserOperation.quitApp();
				break;
			default:
				break;
			}
		} else {
			System.out.println("Please provide valid Input.");
		}
	}

	private void handleAdminOption(String s) {
		if (s != null || !s.trim().equals("")) {
			int option = Integer.parseInt(s);
			switch (option) {
			case 1:
				AdminOperation.createUser();
				break;
			case 11:
				AdminOperation.getUsers(currentUser);
				break;
			case 12:
				AdminOperation.assignRoleToUser();
				break;
			case 13:
				AdminOperation.checkPermission();
				break;
			case 2:
				AdminOperation.createRole();
				break;
			case 21:
				AdminOperation.getRoles();
				break;
			case 22:
				AdminOperation.giveResourceAccessForPerticularRole();
				break;
			case 3:
				AdminOperation.createResource();
				break;
			case 31:
				AdminOperation.getResources();
				break;
			case 4:
				AdminOperation.getActions();
				break;
			case 5:
				loginUser();
				break;
			case 10:
				AdminOperation.quitApp();
				break;
			default:
				System.out.println("Invalid option.");
			}
		} else {
			System.out.println("Please provide valid Input.");
		}
	}

	private void loginUser() {
		System.out.println("Login User:\nEnter emailId:");
		Scanner sc = new Scanner(System.in);
		String email = sc.nextLine().trim();
		if (currentUser.getEmail().equalsIgnoreCase(email)) {
			System.out.println("User with this email already loggedIn.");
		} else {
			HashMap<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("email", email);
			User user = User.findUser(conditions);
			if (user == null) {
				System.out.println(String.format("User with email[%s] not exists.", email));
				return;
			} else {
				System.out.println("Please enter password:");
				String password = sc.nextLine().trim();
				if (user.getPassword().equals(password)) {
					if (isAdmin) {
						isAdmin = !isAdmin;
					} else {
						if (user.getName().equals("admin")) {
							isAdmin = true;
						}
					}
					currentUser = user;
				} else {
					System.out.println("Login Failed.");
				}
			}
		}

	}
}
