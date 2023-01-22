package  root.walletmanager.dal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import  root.entity.Coin;
import  root.entity.User;

@Component
public class CoinFileDao implements CoinDao {

	public Coin getCoin(String name) throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		List<Coin> coins = getAllCoins();
		if(coins == null)
			return null;
		for(Coin coin : coins) {
			if(coin.getName().equals(name))
				return coin;
		}
		return null;
		
	}

	public List<Coin> getAllCoins() throws FileNotFoundException, IOException, ClassNotFoundException {
		List<Coin> coins = null;
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("coin.txt"));
			coins = (ArrayList<Coin>) in.readObject();
			Collections.sort(coins);
			// closing the stream
			in.close();
		}
		catch (Exception e) {
			
		}
		// Creating stream to read the object
		finally {
			return coins;
		}
		
	}

	public void addCoin(Coin coin) throws FileNotFoundException, ClassNotFoundException, IOException, Exception {
		// TODO Auto-generated method stub
		List<Coin> coins = getAllCoins();
		if(coins == null)
			coins = new ArrayList<>();
		coins.add(coin);
		// now put it back in!!!
		setCoinsBack(coins);
		
	}

	public void deleteCoin(Coin coin) throws FileNotFoundException, ClassNotFoundException, IOException, Exception {
		List<Coin> coins = getAllCoins();
		coins.remove(coin);
		setCoinsBack(coins);
		
	}

	public void updateCoin(Coin coin) throws FileNotFoundException, ClassNotFoundException, IOException, Exception {
		// TODO Auto-generated method stub
		List<Coin> coins = getAllCoins();
		deleteCoin(coin);
		addCoin(coin);
		
	}
	public void setCoinsBack(List<Coin> coins) throws Exception {

		// Creating stream and writing the object
		FileOutputStream fout = new FileOutputStream("coin.txt");
		ObjectOutputStream out = new ObjectOutputStream(fout);
		out.writeObject(coins);

		// closing the stream
		fout.close();
		out.close();
		}

}
