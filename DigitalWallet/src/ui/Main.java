package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.security.auth.login.LoginException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import exceptions.UserException;
import model.User;
import service.UserService;

public class Main
{
	private static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		LoginUser(context, sc);
		context.close();
		sc.close();

	}

	public static void LoginUser(ClassPathXmlApplicationContext context, Scanner sc)
	{
		try
		{
			String username, password;
			System.out.println(Constants.WELCOME_MESSAGE);
			System.out.println(Constants.USER_ENTER);
			username = sc.nextLine();
			System.out.println(Constants.PASSWORD_ENTER);
			password = sc.nextLine();
			User user = new User(username, password);

			UserService service = context.getBean("userService", UserService.class);
			service.login(user);
		}
		catch (UserException ue)
		{
			System.out.println(ue.toString());
			ue.printStackTrace();
			LoginUser(context, sc);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void ChangeUser()
	{
		try
		{
			char action;
			System.out.println(Constants.MAIN_MENU);
			action = sc.next().charAt(0);
			switch(action) {	
			case 'a':
				break;
			case 'b':
				break;
			case 'c':
				break;
			case 'e':	
				System.out.println("Good bye my friend, nice to have you here! :)");
				sc.close();
				context.close();
				System.exit(0);
			
		
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			ChangeUser();
		}
	}
	
	

}
