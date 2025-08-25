package dao;

import java.util.List;

import model.User;

public interface UserDao {
	boolean save(User user);                    
    User findByUsername(String username);       
    User findById(int id);   
    List<User> findAll();
    boolean update(User user);                  
    boolean deleteById(int id);                 
}
