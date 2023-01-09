package ui;

import java.util.Scanner;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import model.User;
import service.UserService;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		 Scanner sc = new Scanner(System.in);
		UserUI(context, sc);
		context.close();
		sc.close();
			
	}
	public static void UserUI(ClassPathXmlApplicationContext context, Scanner sc) 
	{
		try {
			
			    String username, password;
				System.out.println(Constants.WELCOME_MESSAGE);
				System.out.println(Constants.USER_ENTER);
				username = sc.nextLine();
				System.out.println(Constants.PASSWORD_ENTER);
				password = sc.nextLine();
				User user = new User(username, password);
				
				UserService service = context.getBean("userService",UserService.class);
				service.save(user);
				
				
		}
		catch Exception{
			
		}
	}

}
