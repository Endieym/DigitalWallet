package model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public class User {
	
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
	
	

}
