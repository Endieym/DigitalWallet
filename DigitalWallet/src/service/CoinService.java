package service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import entity.Coin;
import exceptions.CoinAlreadyExistsException;
import exceptions.CoinException;
import exceptions.CoinNotFoundException;
import walletmanager.dal.CoinDao;

@Component
public class CoinService {
	
	@Autowired
	CoinDao currentDao;
	
	public Coin GetCoin(String coinName) throws FileNotFoundException, ClassNotFoundException, IOException, CoinException {
		Coin coin = currentDao.getCoin(coinName);
		if(coin==null) {
			throw new CoinNotFoundException("Coin does not exist in the system");
		}
		else {
			return coin;
		}
	}

	public List<Coin> getAll() throws FileNotFoundException, ClassNotFoundException, IOException{
		return currentDao.getAllCoins();
		
	}
	
	public void AddCoin(Coin coin) throws Exception {
		List<Coin> coinList = currentDao.getAllCoins();
		
		if(coinList.contains(coin))
				throw new CoinAlreadyExistsException("Coin already exists in the system!");
			
		else {
			currentDao.addCoin(coin);
		}
		

	}
	public void RemoveCoin(Coin coin) throws Exception {
		List<Coin> coinList = currentDao.getAllCoins();
		
		if(coinList.contains(coin))
				currentDao.deleteCoin(coin);	
			
		else {
			throw new CoinNotFoundException("You cannot delete something that doesn't"
					+ "exist!!");
		}
		
		
		
	}
	public boolean UpdateCoin(Coin coin) throws Exception {
		
		List<Coin> coinList = currentDao.getAllCoins();
		
		if(coinList.contains(coin)) {
			currentDao.updateCoin(coin);
			return true;
		}
			
		else {
			throw new CoinNotFoundException("Coin not found in the user's wallet");
		}

		
	}

}
