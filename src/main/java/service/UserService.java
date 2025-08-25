package service;

import java.util.List;


import model.User;

public interface UserService {
	boolean register(User user);
    User login(String username, String password);
    User findById(int id);

    List<User> findAll();
    boolean update(User user);
    boolean delete(int id);
}
