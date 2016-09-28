package services;

import java.util.ArrayList;

import beans.NewTaskBean;
import beans.UserBean;
import dao.TaskDAO;
import dao.UserDAO;

public class NewUserService {
	
	public static void createUser(UserBean addUser){
		ArrayList<NewTaskBean> tasks = new ArrayList<NewTaskBean>();
		ArrayList<UserBean> users = UserDAO.getAllUsers();
		boolean managerExists = false;
		for(UserBean user:users){
			if(user.getManager().equals(addUser.getManager())){
				managerExists=true;
			}
		}
		users.add(addUser);
		UserDAO.putAllUsers(users);
		TaskDAO.putAllTasks(tasks, addUser.getId());
		TaskDAO.putAllTasks(tasks, addUser.getManager()+"-"+addUser.getId());
		TaskDAO.putAllTasks(tasks, addUser.getId()+"-completed");
		
		if(!managerExists){
			TaskDAO.putAllTasks(tasks, addUser.getManager()+"-pool");
		}
	}

}
