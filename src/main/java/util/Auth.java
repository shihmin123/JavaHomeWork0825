package util;
import model.User;


public class Auth {
	
	public static boolean isAdmin(User u) {
		
	return u != null && "admin".equalsIgnoreCase(u.getRole());
	}
	
}
