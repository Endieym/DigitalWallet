package walletmanager.dal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import model.Coin;
import model.User;

public interface UserDao {
	
	public User getUser(String id);
	public List<User> getAllUsers() throws FileNotFoundException, IOException, ClassNotFoundException;
	public void addUser(User user) throws FileNotFoundException, ClassNotFoundException, IOException;
	public void deleteUser(User user) throws FileNotFoundException, ClassNotFoundException, IOException, Exception;
	public void updateUser(User user);

	
}
