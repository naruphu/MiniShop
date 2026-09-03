package util;

import model.Role;
import model.User;

public class RoleUtils {
	
	public static boolean isAdmin(User user) {
		return user != null && user.getRole() == Role.ADMIN;
	}
	
	public static boolean isCustomer(User user) {
		return user != null && user.getRole() == Role.ADMIN.CUSTOMER;
	}
	
	public static boolean isLoggedIn(User user) {
		return user != null;
	}
}
