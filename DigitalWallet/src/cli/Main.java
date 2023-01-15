package cli;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.security.auth.login.LoginException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import entity.Coin;
import entity.User;
import exceptions.UserException;
import service.CoinService;
import service.UserService;

public class Main
{
	private static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	private static Scanner sc = new Scanner(System.in);
	private static User currentUser;
	public static void main(String[] args)
	{
		OpenMenu(context, sc);
		context.close();
		sc.close();

	}

	public static void OpenMenu(ClassPathXmlApplicationContext context, Scanner sc) 
	{
		
		System.out.println(Constants.WELCOME_MESSAGE);
		UserAction();
		WalletAction();
		

	}
	
	public static void UserAction() {
		UserService userService = context.getBean("userService", UserService.class);
		CoinService coinService = context.getBean("coinService", CoinService.class);
		try {
			char action;
			System.out.println(Constants.MAIN_MENU);
			action = sc.next().charAt(0);
			switch(action) {
			case 'a':
				System.out.println(Constants.USER_ENTER);
				String username = sc.nextLine();
				System.out.println(Constants.PASSWORD_ENTER);
				String password = sc.nextLine();
				currentUser = userService.login(username, password);
				return;
				
			case 'b':
				System.out.println(Constants.USER_ENTER);
				String registerName = sc.nextLine();
				System.out.println(Constants.PASSWORD_ENTER);
				String registerPassword = sc.nextLine();
				User user = new User(registerName, registerPassword);
				userService.signup(user);
				
				UserAction();
				break;
			case 'c':
				List<User> userList = userService.getAll();
				PrintList(userList);
				break;
			case 'd':
				List<Coin> coinList = coinService.getAll();
				PrintList(coinList);
				break;
				
			case 'e':
				System.out.println("Good bye my friend, nice to have you here! :)");
				sc.close();
				context.close();
				System.exit(0);
			}
			
		}
		catch (UserException ue) {
			System.out.println(ue.toString());

		}
		catch (Exception e) {
			System.out.println(e.toString());

		}
		finally {
			UserAction();
		}
	}
	
	public static void PrintList(List list) {
		int size = list.size();
		for(int i =0; i<size; i++) {
			System.out.println(list.get(i).toString());
		}
	}
	
	public static void PrintWallet(Map<Coin,Integer> map) {
		for( Map.Entry<Coin, Integer> entry : map.entrySet() ){
		    System.out.println( entry.getKey() + " = " + entry.getValue() );
		}
	}
	
	public static void WalletAction()
	{
		UserService userService = context.getBean("userService", UserService.class);
		CoinService coinService = context.getBean("coinService", CoinService.class);
		try
		{
			char action;
			System.out.println(Constants.USER_MENU);
			action = sc.next().charAt(0);
			switch(action) {	
			case 'a':
				PrintWallet(userService.getWallet(currentUser));
				break;
			case 'b':
				System.out.println("Enter coin name to be deposited");
				String depositName = sc.nextLine();
				Coin depositCoin = coinService.GetCoin(depositName);
				System.out.println("Enter amount of coins to be added");
				int depositAmount = sc.nextInt();
				userService.depositToWallet(currentUser, depositCoin, depositAmount);
				break;
			case 'c':
				System.out.println("Enter coin name to be withdrawn");
				String withdrawName = sc.nextLine();
				Coin withdrawCoin = coinService.GetCoin(withdrawName);
				System.out.println("Enter amount of coins to be added");
				int withdrawAmount = sc.nextInt();
				userService.withdrawFromWallet(currentUser, withdrawCoin, withdrawAmount);
				break;
			case 'd':
				System.out.println("Enter coin name to be added to system");
				String newName = sc.nextLine();
				System.out.println("Enter the value of the coin");
				int newAmount = sc.nextInt();
				Coin newCoin = new Coin(newName, newAmount);
				coinService.AddCoin(newCoin);

				break;
			case 'r': 
				System.out.println("Enter coin name to be removed to system");
				String removeName = sc.nextLine();
				Coin removeCoin = coinService.GetCoin(removeName);
				coinService.RemoveCoin(removeCoin);
				

				break;
			case 'u':
				System.out.println("Enter coin name to be updated");
				String updateName = sc.nextLine();
				System.out.println("Enter the new value of the coin");
				int updatedAmount = sc.nextInt();
				Coin updatedCoin = coinService.GetCoin(updateName);
				updatedCoin.setValue(updatedAmount);
				coinService.UpdateCoin(updatedCoin);
				break;
			case 'e':	
				System.out.println("Good bye my friend, nice to have you here! :)");
				sc.close();
				context.close();
				System.exit(0);
			
		
			}
		}
		catch (InputMismatchException ie) {
			System.out.println(ie.toString());
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		finally
		{
			WalletAction();
		}
	}
	
	

}
