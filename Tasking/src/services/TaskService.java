package services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import beans.NewTaskAddBean;
import beans.NewTaskBean;
import beans.NewTaskTableBean;
import beans.UserBean;
import dao.TaskDAO;

public class TaskService {
	
	ArrayList<NewTaskBean> tasks;
	
	public void addTask(NewTaskAddBean taskTable, String user){
		tasks = TaskDAO.getAllTasks(user);
		NewTaskBean task = null;
		try {
			task = new NewTaskBean(taskTable);
			tasks.add(task);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TaskDAO.putAllTasks(tasks, user);
		
		
	}
	
	
	public static boolean isEight(String user){
		DateFormat df = new SimpleDateFormat(util.Constants.dateFormat);
		Float hours = 0.0f;
		ArrayList<NewTaskBean> tasks = TaskDAO.getAllTasks(user);
		for(NewTaskBean task: tasks){
			hours += task.getEstimatedETC();
		}
		UserBean loginUser = new UserBean();
		loginUser.setId(user);
		tasks = TaskDAO.getAllTasks(new LoginService().getManager(loginUser)+"-"+user);
		for(NewTaskBean task: tasks){
			hours+=task.getEstimatedETC();
		}
		tasks=TaskDAO.getAllTasks(user+"-completed");
		for(NewTaskBean task: tasks){
			if(df.format(task.getEstimatedDate()).equals(df.format(new Date()))){
				hours+=task.getActualETC();
			}
		}
		
		if(hours>=8.0)
			return true;
		
		else
			return false;
	}
	
	public ArrayList <NewTaskTableBean> getTask(String user){
		ArrayList<NewTaskTableBean> taskTable = new ArrayList<NewTaskTableBean>();
		ArrayList<NewTaskBean> tasks = TaskDAO.getAllTasks(user);
		for(NewTaskBean task: tasks){
			taskTable.add(new NewTaskTableBean(task));
		}
		return taskTable;
	}
	
	public void deleteTask (String ID, String user){
		ArrayList<NewTaskBean> tasks = TaskDAO.getAllTasks(user);
		for(NewTaskBean task:tasks){
			if(task.getID().equals(Integer.parseInt(ID))){
				tasks.remove(task);
				break;
			}
		}
		TaskDAO.putAllTasks(tasks, user);
	}
	
	
	public void completeTask(String ID, String user, Float ActualETC){
		tasks = TaskDAO.getAllTasks(user);
		for (NewTaskBean task : tasks){
			if(task.getID().equals(Integer.parseInt(ID))){
				task.setActualETC(ActualETC);
				task.setCompletedDate(new Date());
				TaskDAO.complete(task, user.substring(user.length()-8));
				break;
			}
		}
	}
	
	
	
	
	public ArrayList<NewTaskTableBean> getpool (String user){
		tasks=TaskDAO.getAllTasks(user+"-pool");
		ArrayList<NewTaskTableBean> taskTable= new ArrayList<NewTaskTableBean>();
		for(NewTaskBean task:tasks){
			taskTable.add(new NewTaskTableBean(task));
		}
		return taskTable;
	}

	
	public void addPool(String user, NewTaskAddBean taskTable){
		tasks = TaskDAO.getAllTasks(user+"-pool");
		NewTaskBean task = null;
		
		try {
			task = new NewTaskBean(taskTable);
			tasks.add(task);
		}catch(ParseException e){
			e.printStackTrace();
		}
		TaskDAO.putAllTasks(tasks, user+"-pool");
	}
	
	public void deletePool(String ID, String user){
		ArrayList<NewTaskBean> tasks = TaskDAO.getAllTasks(user+"-pool");
		Iterator<NewTaskBean> it = tasks.iterator();
		NewTaskBean task;
		while(it.hasNext()){
			task=it.next();
			if(task.getID().equals(Integer.parseInt(ID))){
				System.out.println("HERE");
				it.remove();
				break;
			}
			
		}
		/*for(NewTaskBean task: tasks){
			if(task.getID().equals(Integer.parseInt(ID))){
				System.out.println("HERE");
				tasks.remove(task);
				break;
			}*/
		TaskDAO.putAllTasks(tasks, user+"-pool");
		
	}
	
	public void pull(String ID, String user, String manager){
		ArrayList<NewTaskBean> poolTasks = TaskDAO.getAllTasks(manager+"-pool");
		ArrayList<NewTaskBean> managerTasks = TaskDAO.getAllTasks(manager+"-"+user);
		Iterator<NewTaskBean> it = poolTasks.iterator();
		NewTaskBean task = new NewTaskBean();
		while(it.hasNext()){
			task = it.next();
			if(task.getID().equals(Integer.parseInt(ID))){
				task.setName(task.getName()+"--POOLTASK");
				managerTasks.add(task);
				it.remove();
			}
		}
		TaskDAO.putAllTasks(managerTasks, manager+"-"+user);
		TaskDAO.putAllTasks(poolTasks, manager+"-pool");
		
	}
	
	public static ArrayList<NewTaskTableBean> getComplteted(String user){
		DateFormat df = new SimpleDateFormat(util.Constants.dateFormat);
		ArrayList<NewTaskTableBean> todays = new ArrayList<NewTaskTableBean>();
		ArrayList<NewTaskBean> completedTasks = TaskDAO.getAllCompletedTasks(user);
		for(NewTaskBean task:completedTasks){
			if(df.format(task.getCompletedDate()).equals(df.format(new Date()))){
				todays.add(new NewTaskTableBean(task));
			}
		}
		return todays;
	}
	
	public static Integer isProSaver(String user){
		float hours = 0;
		ArrayList<NewTaskBean> completedTasks = TaskDAO.getAllCompletedTasks(user);
		for(NewTaskBean task: completedTasks){
			hours +=(task.getEstimatedETC()-task.getActualETC());
		}
		if(hours > 20){
			return 2;
		}
		else if(hours>10){
			return 1;
		}
		else
			return 0;
	}
	
	
	public void release(String ID, String user, String manager){
		ArrayList<NewTaskBean> poolTasks= TaskDAO.getAllTasks(manager+"-pool");
		ArrayList<NewTaskBean> managerTasks = TaskDAO.getAllTasks(manager+"-"+user);
		Iterator<NewTaskBean> it= managerTasks.iterator();
		NewTaskBean task = new NewTaskBean();
		while(it.hasNext()){
			task= it.next();
			if(task.getID().equals(Integer.parseInt(ID))){
				task.setName(task.getName().substring(0, task.getName().length()-10));
				poolTasks.add(task);
				it.remove();
			}
		}
		TaskDAO.putAllTasks(managerTasks, manager+"-"+user);
		TaskDAO.putAllTasks(poolTasks, manager+"-pool");		
	}
	
	public void edit(String ID, String user, String description){
		ArrayList<NewTaskBean> tasks = TaskDAO.getAllTasks(user);
		Iterator<NewTaskBean> it = tasks.iterator();
		NewTaskBean task = new NewTaskBean();
		while(it.hasNext()){
			task=it.next();
			if(task.getID().equals(Integer.parseInt(ID))){
				task.setDescription(description);
			}
		}
		TaskDAO.putAllTasks(tasks, user);
	}
	
	
	
	
}
