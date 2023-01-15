package walletmanager.dal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entity.Coin;
import entity.User;

public class CoinFileDao implements CoinDao {

	public Coin getCoin(String name) throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		List<Coin> coins = getAllCoins();
		
		for(Coin coin : coins) {
			if(coin.getName()==name)
				return coin;
		}
		return null;
		
	}

	public List<Coin> getAllCoins() throws FileNotFoundException, IOException, ClassNotFoundException {
		List<Coin> coins = null;

		// Creating stream to read the object
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("user.txt"));
		coins = (ArrayList<Coin>) in.readObject();
		Collections.sort(coins);
		// closing the stream
		in.close();
		return coins;
	}

	public void addCoin(Coin coin) throws FileNotFoundException, ClassNotFoundException, IOException, Exception {
		// TODO Auto-generated method stub
		List<Coin> coins = getAllCoins();
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
		coins.remove(coin);
		addCoin(coin);
		
	}
	public void setCoinsBack(List<Coin> coins) throws Exception {

		// Creating stream and writing the object
		FileOutputStream fout = new FileOutputStream("coin.txt");
		ObjectOutputStream out = new ObjectOutputStream(fout);
		out.writeObject(coins);
		out.flush();

		// closing the stream
		out.close();
		}

}
