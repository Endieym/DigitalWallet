package walletmanager.dal;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import model.Coin;
import model.User;

@Component
public class UserFileDao implements UserDao {

	public User getUser(String id) {

		return null;
	}

	public List<User> getAllUsers() throws FileNotFoundException, IOException, ClassNotFoundException {
		List<User> users = null;

// Creating stream to read the object
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("f.txt"));
		users = (ArrayList<User>) in.readObject();

// closing the stream
		in.close();
		return users;
	}

	public void addUser(User user) throws FileNotFoundException, ClassNotFoundException, IOException {
		List<User> users = getAllUsers();
		users.add(user);
// now put it back in!!!
	}

	public void deleteUser(User user) throws Exception {
		List<User> users = getAllUsers();
		users.remove(user);
		setUsersBack(users);
	}

	public void updateUser(User user) {
// TODO Auto-generated method stub
	}

	public void setUsersBack(List<User> users) throws Exception {

// Creating stream and writing the object
		FileOutputStream fout = new FileOutputStream("f.txt");
		ObjectOutputStream out = new ObjectOutputStream(fout);
		out.writeObject(users);
		out.flush();

// closing the stream
		out.close();
	}
}
