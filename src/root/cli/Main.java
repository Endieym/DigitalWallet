package root.cli;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.security.auth.login.LoginException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import root.entity.Coin;
import root.entity.User;
import root.exceptions.IncorrectPasswordException;
import root.exceptions.UserException;
import root.service.CoinService;
import root.service.UserService;

public class Main {
	private static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	private static Scanner sc = new Scanner(System.in);
	private static User currentUser;

	public static void main(String[] args) {
		sc.useDelimiter(System.lineSeparator());
		OpenMenu(context, sc);
		context.close();
		sc.close();

	}

	public static void OpenMenu(ClassPathXmlApplicationContext context, Scanner sc) {

		System.out.println(Constants.WELCOME_MESSAGE);
		while (true) {
			UserAction();
			WalletAction();
		}

	}

	public static void UserAction() {
		UserService userService = context.getBean("userService", UserService.class);
		CoinService coinService = context.getBean("coinService", CoinService.class);
		try {
			char action;
			System.out.println(Constants.MAIN_MENU);
			action = sc.next().charAt(0);
			switch (action) {
			case 'a':
				System.out.println(Constants.USER_ENTER);
				String username = sc.next();
				System.out.println(Constants.PASSWORD_ENTER);
				String password = sc.next();
				currentUser = userService.login(username, password);
				System.out.println(String.format("Welcome back, %s :D", username));

				return;

			case 'b':
				System.out.println(Constants.USER_ENTER);
				String registerName = sc.next();
				System.out.println(Constants.PASSWORD_ENTER);
				String registerPassword = sc.next();
				User user = new User(registerName, registerPassword);
				userService.signup(user);
				System.out.println(String.format("Welcome, %s! We hope you enjoy our program", registerName));

				UserAction();
				break;
			case 'c':
				List<User> userList = userService.getAll();
				if (userList == null || userList.isEmpty())
					System.out.println("There are no users");
				else {
					PrintList(userList);
				}
				UserAction();
				break;
			case 'd':
				List<Coin> coinList = coinService.getAll();
				if (coinList == null || coinList.isEmpty())
					System.out.println("There are no coins registered in the system");
				else {
					PrintList(coinList);
				}
				UserAction();
				break;

			case 'e':
				System.out.println("Goodbye my friend, nice to have you here! :)");
				terminate();

			default:
				System.out.println("Incorrect character");
				UserAction();
			}

		} catch (UserException ue) {
			System.err.println(ue.toString());
			UserAction();

		} catch (Exception e) {
			System.err.println(e.toString());
			e.printStackTrace();

		}

	}

	public static void PrintList(List list) {
		int size = list.size();
		for (int i = 0; i < size; i++) {
			System.out.println(list.get(i).toString());
		}
	}

	public static void PrintWallet(Map<Coin, Float> map) {
		for (Map.Entry<Coin, Float> entry : map.entrySet()) {
			System.out.println(entry.getKey().getValue() * entry.getValue() + "$ Worth of " + entry.getKey().getName()
					+ String.format(" (%.1f coins)", entry.getValue()));
		}
	}

	public static void WalletAction() {
		UserService userService = context.getBean("userService", UserService.class);
		CoinService coinService = context.getBean("coinService", CoinService.class);
		try {
			char action;
			System.out.println(Constants.USER_MENU);
			action = sc.next().charAt(0);
			switch (action) {
			case 'a':
				Map<Coin, Float> wallet = currentUser.getWallet();
				if (wallet == null || wallet.isEmpty()) {
					System.out.println("Wallet is empty");
				}

				else {
					PrintWallet(wallet);
				}

				break;
			case 'b':
				System.out.println("Enter coin name to be deposited");
				String depositName = sc.next().toLowerCase();
				Coin depositCoin = coinService.GetCoin(depositName);
				System.out.println("Enter amount of coins to be added");
				float depositAmount = sc.nextFloat();
				userService.depositToWallet(currentUser, depositCoin, depositAmount);
				break;
			case 'c':
				System.out.println("Enter coin name to be withdrawn");
				String withdrawName = sc.next().toLowerCase();
				Coin withdrawCoin = coinService.GetCoin(withdrawName);
				System.out.println("Enter amount of coins be withdrawn");
				float withdrawAmount = sc.nextFloat();
				float currentAmount = userService.withdrawFromWallet(currentUser, withdrawCoin, withdrawAmount);
				System.out.println(String.format("Withdrawn %.1f$ %s from wallet" + " (Or %.1f coins)",
						withdrawCoin.getValue() * currentAmount, withdrawName, currentAmount));

				break;
			case 'd':
				System.out.println("Enter coin name to be added to system");
				String newName = sc.next().toLowerCase();
				System.out.println("Enter the value of the coin");
				float newAmount = sc.nextFloat();
				Coin newCoin = new Coin(newName, newAmount);
				coinService.AddCoin(newCoin);
				System.out.println(String.format("Added %s with the value of %f", newName, newAmount));

				break;
			case 'r':
				System.out.println("Enter coin name to be removed from the  system");
				String removeName = sc.next().toLowerCase();
				Coin removeCoin = coinService.GetCoin(removeName);
				coinService.RemoveCoin(removeCoin);
				userService.deleteCoinUser(currentUser, removeCoin);
				System.out.println("Removed " + removeName + " from system!");

				break;
			case 'u':

				System.out.println("Enter coin name to be updated");
				String updateName = sc.next().toLowerCase();
				System.out.println("Enter the new value of the coin");
				float updatedAmount = sc.nextFloat();
				Coin updatedCoin = coinService.GetCoin(updateName);
				updatedCoin.setValue(updatedAmount);
				coinService.UpdateCoin(updatedCoin);
				userService.updateCoinUser(currentUser, updatedCoin);
				System.out.println("Updated " + updateName + "'s value to " + updatedAmount);

				break;
			case 'x':
				System.err.println("Are you sure you want to delete your user?");
				System.out.println("Y/N");
				if (sc.next().toLowerCase().equals("y")) {
					System.out.println("Enter your password to delete");
					try {
						userService.deleteUser(currentUser, sc.next());
						System.err.println("Deleted user " + currentUser.getName());
						
						terminate();
					} catch (IncorrectPasswordException e) {
						System.out.println("User not deleted");
						terminate();
					}

				}
			case 'l':
				System.out.println("Are you sure you want to log out?");
				System.out.println("Y/N");
				if (sc.next().toLowerCase().equals("y")) {
					System.out.println("Logging off...");
					currentUser = null;
					return;
				}

				break;
			case 'e':
				System.out.println("Good bye my friend, nice to have you here! :)");

				terminate();

			default:
				System.out.println("Incorrect character");

			}
		} catch (InputMismatchException ie) {
			System.err.println(ie.toString());
		} catch (Exception e) {
			System.err.println(e.toString());
		}

		finally {
			if (currentUser != null)
				WalletAction();
		}
	}

	public static void terminate() {
		System.out.println("Exiting...");
		sc.close();
		context.close();
		System.exit(0);
	}

}
