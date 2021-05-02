package rbac;

public class Rbac {	
	
	public static void main(String[] args) {
		System.out.println("Hello! welcome to RBAC Application...");
		Thread rbacThread = new Thread(new RBACApplication());
		rbacThread.start();
	}

	public static void instructions() {
		System.out.println();
	}
}
