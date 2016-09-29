package controller;

import java.io.FileNotFoundException;
import java.util.Scanner;

import beans.UserBean;
import gui.JLogin;
import services.BackupService;
import services.NewUserService;

public class Main {

	
	
public static void main(String[] args) {
	if(args.length!=0){
		Scanner sc= new Scanner(System.in);
		if(args[0].equals("New")){
			UserBean newUser = new UserBean();
			System.out.println("Enter ID:");
			newUser.setId(sc.next());
			System.out.println("Enter Password:");
			newUser.setPassword(sc.next());
			System.out.println("Enter Name:");
			newUser.setName(sc.next());
			System.out.println("Enter Manager ID:");
			newUser.setManager(sc.next());
			NewUserService.createUser(newUser);
		}
		else if (args[0].equals("Flush")){
			try {
				BackupService.flush();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		sc.close();
		
	}
	else
		new JLogin();
	
	
}
}
