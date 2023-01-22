package  root.walletmanager.dal;

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

import root.entity.Coin;
import  root.entity.User;

@Component
public class UserFileDao implements UserDao {

	public User getUser(String id) throws FileNotFoundException, ClassNotFoundException, IOException {
		
		List<User> users = getAllUsers();
		if(users == null)
			return null;
		for(User user : users) {
			if(user.getName().equals(id))
				return user;
		}
		return null;
		
	}

	public List<User> getAllUsers() throws FileNotFoundException, IOException, ClassNotFoundException {
		List<User> users = null;
		try {
			// Creating stream to read the object
			FileInputStream file = new FileInputStream("user.txt");
			ObjectInputStream in = new ObjectInputStream(file);
			
			users = (ArrayList<User>) in.readObject();
			Collections.sort(users);
	// closing the stream
			in.close();
			
		}
		catch(Exception e) {
			
			
			
		}
		finally {
			return users;
		}

	}

	public void addUser(User user) throws Exception {
		List<User> users = getAllUsers();
		if(users == null) {
			users = new ArrayList<User>();
		}
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
		users.add(user);
		setUsersBack(users);
	}

	public void setUsersBack(List<User> users) throws Exception {

// Creating stream and writing the object
		FileOutputStream fout = new FileOutputStream("user.txt");
		ObjectOutputStream out = new ObjectOutputStream(fout);
		out.writeObject(users);
		

// closing the stream
		fout.close();
		out.close();
	}
}
