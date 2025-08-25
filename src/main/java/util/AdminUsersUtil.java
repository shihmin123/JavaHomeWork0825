package util;

import java.util.List;

import javax.swing.DefaultListModel;

import model.User;
import service.UserService;

public class AdminUsersUtil {
	
	public static void refreshUserList(DefaultListModel<String> listModel, UserService userService) {
        List<User> users = userService.findAll();
        listModel.clear();
        for (User u : users) {
            listModel.addElement(
                "ID:" + u.getId() +
                " | Name:" + u.getName() +
                " | Username:" + u.getUsername()
            );
        }
    }
}
