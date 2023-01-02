package ui;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

			System.out.println(Constants.WELCOME_MESSAGE);
			
	}

}
