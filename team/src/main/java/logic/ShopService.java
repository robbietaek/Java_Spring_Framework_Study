package logic;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.UserDao;

@Service	//@Component + service 기능
public class ShopService {
	@Autowired
	private UserDao userDao;
	

	public void insert(User user) {
		userDao.insert(user);
	}

	public User getUser(String userid) {
		return userDao.selectOne(userid);
	}


	public void userUpdate(User user) {
		userDao.update(user);
		
	}

	public void userDelete(String userid) {
		userDao.delete(userid);
	}


	public List<User> userlist() {
		return userDao.list();
	}

	public List<User> userList(String[] idchks) {
		return userDao.list(idchks);
	}

}
