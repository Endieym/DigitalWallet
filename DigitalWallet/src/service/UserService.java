package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import exceptions.NameTooLongException;
import exceptions.UserException;
import model.Coin;
import model.User;
import walletmanager.dal.UserDao;

@Component
public class UserService {
	
	
	@Autowired
	UserDao currentDao;

	public List<User> getAll(){
		return null;
		
	}

	public boolean signup(User user) throws UserException { // returns if signup worked
		List<User> userList = currentDao.getAllUsers();
		for(User u : userList) {
			if(u.equals(user))
				return true;
			
		}
		if(user.getName().length()>10)
			throw new NameTooLongException("Name cannot be over 10 characters.");
		
		currentDao.addUser(user);
		return true;
		
	}
	public boolean login(User user) throws UserException { // returns if login worked
		List<User> userList = currentDao.getAllUsers();
		for(User u : userList) {
			if(u.equals(user))
				return true;
			
		}
		
		return true;
		
	}
	
	public Map<Coin, Integer> getWallet(User user) {  // check later exception
		List<User> userList = currentDao.getAllUsers();
		for(User u : userList) {
			if(u.equals(user))
				return currentDao.getUser(user.getName()).getWallet();
			
		}
		return null;
		
	}
	
	public void withdrawFromWallet(User user) {
		HashMap<Coin, Integer> wallet = (HashMap<Coin, Integer>) this.getWallet(user);
		wallet.get(wallet);
	}
	
	public void depositFromWallet(User user) {
		
	}
	
	
	
	
}
