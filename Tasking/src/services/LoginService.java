package services;

import java.util.ArrayList;

import beans.ExportTaskBean;
import beans.NewTaskBean;
import beans.UserBean;
import dao.ExportDAO;
import dao.TaskDAO;
import dao.UserDAO;

public class LoginService {

	ArrayList<UserBean> users;
	
	
	public static String getName(String id){
		ArrayList <UserBean> Users = UserDAO.getAllUsers();
		for (UserBean user:Users){
			if (user.getId().equals(id)) {
				return user.getName();
			}
		}
		return new String();
	}
	
	
	public boolean authorize(UserBean loginUser){
		users = UserDAO.getAllUsers();
		for(UserBean user:users){
			if((user.getId().equals(loginUser.getId())) && (user.getPassword().equals(loginUser.getPassword()))){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isManager(String loginUser){
		for (UserBean user:UserDAO.getAllUsers()){
			if(user.getManager().equals(loginUser)){
				return true;
			}
		}
		return false;
		
	}
	
	public String getManager(UserBean loginUser){
		users = UserDAO.getAllUsers();
		for(UserBean user:users){
			if(user.getId().equals(loginUser.getId())){
				return user.getManager();
			}
		}
		return null;
	}
	
	public static ArrayList<UserBean> getAssociates(String currentUser){
		ArrayList<UserBean> Users, associates;
		associates = new ArrayList<UserBean>();
		Users = UserDAO.getAllUsers();
		for(UserBean user: Users){
			if(user.getManager().equals(currentUser)){
				associates.add(user);
			}
		}
		return associates;
	}
	
	
	public ArrayList<ExportTaskBean> getList(UserBean manager){
		ArrayList<ExportTaskBean> exportlist = new ArrayList<ExportTaskBean>();
		for(UserBean user : users){
			if(user.getManager().equals(manager.getId()) && !user.getId().equals(manager.getId()) ){
				for(NewTaskBean task:TaskDAO.getAllCompletedTasks(user.getId())){
					exportlist.add(new ExportTaskBean(task, user.getId()));
				}
				if(isManager(user.getId())){
					exportlist.addAll(getList(user));
				}
			}
		}
		
		return exportlist;
	}
	
	public void generate(UserBean manager){
		users=UserDAO.getAllUsers();
		ArrayList<ExportTaskBean> exportList = getList(manager);
		for(NewTaskBean task : TaskDAO.getAllCompletedTasks(manager.getId())){
			exportList.add(new ExportTaskBean(task, manager.getId()));
		}
		ExportDAO.export(exportList);
		
	}
	
	public void changePassword(String id, String password){
		ArrayList<UserBean> Users = UserDAO.getAllUsers();
		for(UserBean user:Users){
			if(user.getId().equals(id)){
				user.setPassword(password);
			}
		}
		UserDAO.putAllUsers(Users);
	}
	
}
