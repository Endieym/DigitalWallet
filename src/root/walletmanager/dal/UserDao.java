package  root.walletmanager.dal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import  root.entity.Coin;
import  root.entity.User;

public interface UserDao {
	
	public User getUser(String id) throws FileNotFoundException, ClassNotFoundException, IOException;
	public List<User> getAllUsers() throws FileNotFoundException, IOException, ClassNotFoundException;
	public void addUser(User user) throws FileNotFoundException, ClassNotFoundException, IOException, Exception;
	public void deleteUser(User user) throws FileNotFoundException, ClassNotFoundException, IOException, Exception;
	public void updateUser(User user) throws FileNotFoundException, ClassNotFoundException, IOException, Exception;

	
}
