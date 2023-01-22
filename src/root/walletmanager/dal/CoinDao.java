package  root.walletmanager.dal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import  root.entity.Coin;
import  root.entity.User;

public interface CoinDao {
	
	public Coin getCoin(String name) throws FileNotFoundException, ClassNotFoundException, IOException;
	public List<Coin> getAllCoins() throws FileNotFoundException, IOException, ClassNotFoundException;
	public void addCoin(Coin coin) throws FileNotFoundException, ClassNotFoundException, IOException, Exception;
	public void deleteCoin(Coin coin) throws FileNotFoundException, ClassNotFoundException, IOException, Exception;
	public void updateCoin(Coin coin) throws FileNotFoundException, ClassNotFoundException, IOException, Exception;

}
