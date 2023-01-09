package model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public class User implements Comparable<User>{
	
	private String name, password;
	
	private Map<Coin,Integer> wallet;

	
	
	public String getName() {
		return name;
	}

	public User(String name, String password) {
		super();
		this.name = name;
		this.password = password;
		this.wallet = new HashMap();
	}
	
	public void addCoin(Coin coin, int amount) {
		
		if(!this.wallet.containsKey(coin)) {
			this.wallet.put(coin,amount);
			System.out.println("Added new coin to wallet!");
		}
		else {
			this.wallet.replace(coin,wallet.get(coin)+amount);
			System.out.println(String.format("Updated %s amount",coin.getName()));
		}
		
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", password=" + password + ", wallet=" + wallet + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (wallet == null) {
			if (other.wallet != null)
				return false;
		} else if (!wallet.equals(other.wallet))
			return false;
		return true;
	}

	public int compareTo(User o) {
		return this.name.compareTo(o.getName());
	}
	
	
	
	

}
