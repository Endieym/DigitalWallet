package walletmanager.dal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import entity.Coin;
import entity.User;

@Component
public class UserFileDao implements UserDao {

	public User getUser(String id) throws FileNotFoundException, ClassNotFoundException, IOException {
		List<User> users = getAllUsers();
		
		
		for(User user : users) {
			if(user.getName()==id)
				return user;
		}
		return null;
		
	}

	public List<User> getAllUsers() throws FileNotFoundException, IOException, ClassNotFoundException {
		List<User> users = null;

// Creating stream to read the object
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("user.txt"));
		users = (ArrayList<User>) in.readObject();
		Collections.sort(users);
// closing the stream
		in.close();
		return users;
	}

	public void addUser(User user) throws Exception {
		List<User> users = getAllUsers();
		users.add(user);
// now put it back in!!!
		setUsersBack(users);
	}

	public void deleteUser(User user) throws Exception {
		List<User> users = getAllUsers();
		users.remove(user);
		setUsersBack(users);
	}

	public void updateUser(User user) throws Exception {
// TODO Auto-generated method stub
		List<User> users = getAllUsers();
		users.remove(user);
		addUser(user);
		
	}

	public void setUsersBack(List<User> users) throws Exception {

// Creating stream and writing the object
		FileOutputStream fout = new FileOutputStream("user.txt");
		ObjectOutputStream out = new ObjectOutputStream(fout);
		out.writeObject(users);
		out.flush();

// closing the stream
		out.close();
	}
}
