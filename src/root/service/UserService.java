package  root.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import  root.entity.Coin;
import  root.entity.User;
import  root.exceptions.CoinNotFoundException;
import root.exceptions.EmptyWalletException;
import root.exceptions.IncorrectPasswordException;
import root.exceptions.InvalidCredentialsException;
import  root.exceptions.NameTooLongException;
import  root.exceptions.UserAlreadyExistsException;
import  root.exceptions.UserException;
import  root.walletmanager.dal.UserDao;

@Component
public class UserService
{
	
	
	@Autowired
	UserDao currentDao;

	public List<User> getAll() throws FileNotFoundException, ClassNotFoundException, IOException{
		return currentDao.getAllUsers();
		
	}

	public boolean signup(User user) throws Exception { // returns if signup worked
		List<User> userList = currentDao.getAllUsers();
		if(userList != null) 
		{
			if (userList.contains(user))
				throw new UserAlreadyExistsException("Username is already used!");
			
		
		if(user.getName().length()>10)
			throw new NameTooLongException("Name cannot be over 10 characters.");
		}
		
		
		currentDao.addUser(user);
		return true;
		
	}
	public User login(String name, String password) throws UserException, FileNotFoundException, ClassNotFoundException, IOException { // returns if login worked
		
		List<User> userList = currentDao.getAllUsers();
		User foundUser = currentDao.getUser(name);
		if(foundUser == null)
			throw new InvalidCredentialsException("Username or password are incorrect!");
		else {
			if(foundUser.getPassword().equals(password))
				return foundUser;
			else {
				throw new InvalidCredentialsException("Username or password are incorrect!");
			}
		}
		
			
	}
	public void deleteUser(User user, String password) throws Exception { // deletes user
		
		if(password.equals(user.getPassword()))
			currentDao.deleteUser(user);
		else
			throw new IncorrectPasswordException("Password is incorrect! ");
			
	}
	
	
	public void updateCoinUser(User user, Coin coin) throws Exception 
	{
		Map<Coin, Float> wallet = user.getWallet();
		
		if(wallet == null || wallet.isEmpty())
			return;
		
		if(!wallet.containsKey(coin)) 
			return;
		
		float prevAmount = wallet.get(coin);
		wallet.remove(coin);
		wallet.put(coin, prevAmount);
		
		currentDao.updateUser(user);

		
	}
	public void deleteCoinUser(User user, Coin coin) throws Exception 
	{
		Map<Coin, Float> wallet = user.getWallet();
		
		if(wallet == null || wallet.isEmpty())
			return;
		
		if(!wallet.containsKey(coin)) 
			return;
		
		wallet.remove(coin);
			
		currentDao.updateUser(user);

		
	}
	
	public float withdrawFromWallet(User user, Coin coin, float amount) throws Exception {
		Map<Coin, Float> wallet = user.getWallet();
		
		if(wallet == null || wallet.isEmpty())
			throw new EmptyWalletException(String.format("%s's wallet is empty!", user.getName()));
		
		if(!wallet.containsKey(coin)) {
			throw new CoinNotFoundException("Coin does not exist in wallet! you can't withdraw nothing");
		}
		
		float prev = wallet.get(coin);		
		float finalAmount = prev - amount;
		
		if(finalAmount <= 0) 
			{
			wallet.remove(coin);
			finalAmount = prev;
			}
			
		else
			wallet.put(coin, finalAmount);
		
		currentDao.updateUser(user);
		
		return finalAmount;

	}
	
	public void depositToWallet(User user, Coin coin, float amount) throws Exception {
		Map<Coin, Float> wallet = user.getWallet();
		
		if(!wallet.containsKey(coin)) {
			wallet.put(coin, amount);
		}
		else {
			
			wallet.put(coin, amount+wallet.get(coin));
		}
		currentDao.updateUser(user);
		
		
		
	}
	
	
	
	
}
