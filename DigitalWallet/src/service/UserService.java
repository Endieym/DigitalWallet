package service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import entity.Coin;
import entity.User;
import exceptions.CoinException;
import exceptions.CoinNotFoundException;
import exceptions.InvalidPasswordException;
import exceptions.NameTooLongException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserException;
import exceptions.UserNotFoundException;
import walletmanager.dal.UserDao;

@Component
public class UserService {
	
	
	@Autowired
	UserDao currentDao;

	public List<User> getAll() throws FileNotFoundException, ClassNotFoundException, IOException{
		return currentDao.getAllUsers();
		
	}

	public boolean signup(User user) throws Exception { // returns if signup worked
		List<User> userList = currentDao.getAllUsers();
		if (userList.contains(user))
				throw new UserAlreadyExistsException("User already exists!");
			
		
		if(user.getName().length()>10)
			throw new NameTooLongException("Name cannot be over 10 characters.");
		
		currentDao.addUser(user);
		return true;
		
	}
	public User login(String name, String password) throws UserException, FileNotFoundException, ClassNotFoundException, IOException { // returns if login worked
		List<User> userList = currentDao.getAllUsers();
		User foundUser = currentDao.getUser(name);
		if(foundUser == null)
			throw new UserNotFoundException("User does not exist");
		else {
			if(foundUser.getPassword() == password)
				return foundUser;
			else {
				throw new InvalidPasswordException("Password is incorrect!");
			}
		}
			
	}
	
	public Map<Coin, Integer> getWallet(User user) throws FileNotFoundException, ClassNotFoundException, IOException {  // check later exception
		List<User> userList = currentDao.getAllUsers();
		for(User u : userList) {
			if(u.equals(user))
				return currentDao.getUser(user.getName()).getWallet();
			
		}
		return null;
		
	}
	
	public void withdrawFromWallet(User user, Coin coin, int amount) throws Exception {
		HashMap<Coin, Integer> wallet = (HashMap<Coin, Integer>) this.getWallet(user);
		
		if(!wallet.containsKey(coin)) {
			throw new CoinNotFoundException("Coin does not exist in wallet! you can't withdraw nothing");
		}
		
		int prev = wallet.get(coin);
		
			
		if(amount - prev <= 0)
			wallet.put(coin, 0);
		else
			wallet.put(coin, prev-amount);
		
		currentDao.updateUser(user);

	}
	
	public void depositToWallet(User user, Coin coin, int amount) throws Exception {
		HashMap<Coin, Integer> wallet = (HashMap<Coin, Integer>) this.getWallet(user);
		
		if(!wallet.containsKey(coin)) {
			wallet.put(coin, amount);
		}
		else {
			
			wallet.put(coin, amount);
		}
		currentDao.updateUser(user);
		
		
		
	}
	
	
	
	
}
