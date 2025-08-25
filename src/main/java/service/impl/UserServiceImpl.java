package service.impl;

import java.util.List;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.Music;
import model.User;
import service.UserService;
import util.PasswordUtil;

public class UserServiceImpl implements UserService{
	private UserDao userDao = new UserDaoImpl();
	public static void main(String[] args) {
	
	}

	@Override
	public boolean register(User user) {

		return userDao.save(user);
	}

	@Override
	public User login(String username, String password) {

		User user = userDao.findByUsername(username);
        if (user != null && PasswordUtil.checkPassword(password, user.getPassword())) {
            return user;
        }
        return null;
	}

	@Override
	public User findById(int id) {

		return userDao.findById(id);
	}

	@Override
	public List<User> findAll() {

		return userDao.findAll();
	}

	@Override
	public boolean update(User user) {

		return userDao.update(user);
	}

	@Override
	public boolean delete(int id) {
		
		return userDao.deleteById(id);
	}

}
