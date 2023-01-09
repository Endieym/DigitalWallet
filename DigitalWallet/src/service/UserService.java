package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import model.User;
import walletmanager.dal.UserDao;

@Component
public class UserService {
	
	@Autowired
	UserDao currentDao;

	public List<User> getAll(){
		return null;
		
	}
	public void save(User user) {
		
	}
	
	
	
}
