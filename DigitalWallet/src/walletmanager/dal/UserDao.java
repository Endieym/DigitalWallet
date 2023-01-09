package walletmanager.dal;

import java.util.List;

import model.Coin;
import model.User;

public interface UserDao {
	
	public User getUser(String id);
	public List<User> getAllUsers();
	public void addUser(User user);
	public void deleteUser(String id);
	public void updateUser(User user);

	
}
