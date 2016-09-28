package events;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import beans.NewTaskAddBean;
import beans.NewTaskTableBean;
import gui.JTasking;
import services.TaskService;

public class TaskEvent implements ActionListener{
	JTasking tasking;
	
	TaskService taskService;
	
	public TaskEvent(JTasking tasking){
		this.tasking=tasking;
		taskService = new TaskService();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton buttonPressed = (JButton) e.getSource();
		String taskAddUser;
		if(buttonPressed.getToolTipText().contains("Add Task")){
			taskAddUser = buttonPressed.getToolTipText().substring(8);
			for(NewTaskAddBean task:tasking.getNewTasks()){
				if(!task.getName().getText().equals("")){
					if(taskAddUser.equals(tasking.getUser().getText())){
						taskService.addTask(task, tasking.getUser().getText());
					}
					else{
						taskService.addTask(task, tasking.getUser().getText()+"-"+taskAddUser);
					}
				}
			}
		}
		else if(buttonPressed.getToolTipText().contains("Delete")){
			String user = null, task = null;
			if(buttonPressed.getToolTipText().contains("manager")){
				user = tasking.getUser().getText()+"-"+buttonPressed.getToolTipText().substring(15, 23);
				task = buttonPressed.getToolTipText().substring(24);
			}
			else{
				user = tasking.getUser().getText();
				task = buttonPressed.getToolTipText().substring(16);
			}
			taskService.deleteTask(task, user);
		}
		else if(buttonPressed.getToolTipText().contains("Complete")){
			String user = null, task = null;
			user = tasking.getUser().getText();
			task = buttonPressed.getToolTipText().substring(18);
			Float ActualETC;
			for(NewTaskTableBean row:tasking.getTaskTable()){
				if(row.getID().getText().equals(task)){
					ActualETC=Float.parseFloat(row.getActualETC().getText());
					taskService.completeTask(task, user, ActualETC);
					taskService.deleteTask(task, user);
				}
			}
			for(NewTaskTableBean row:tasking.getManagerTaskTable()){
				if(row.getID().getText().equals(task)){
					ActualETC=Float.parseFloat(row.getActualETC().getText());
					taskService.completeTask(task, tasking.getManager().getText()+"-"+user, ActualETC);
					taskService.deleteTask(task, tasking.getManager().getText()+"-"+user);
				}
			}
		}
		
		else if(buttonPressed.getToolTipText().contains("Add Pool")){
			taskAddUser = buttonPressed.getToolTipText().substring(8);
			for(NewTaskAddBean task : tasking.getNewTasks()){
				if(!task.getName().getText().equals("")){
					taskService.addPool(tasking.getUser().getText(), task);
				}
			}
		}
		else if(buttonPressed.getToolTipText().contains("Remove Pool")){
			String user = null, task =null;
			user = tasking.getUser().getText();
			task = buttonPressed.getToolTipText().substring(21);
			taskService.deletePool(task, user);
		}
		else if(buttonPressed.getToolTipText().contains("Pull")){
			String manager =null,user=null,task=null;
			user = tasking.getUser().getText();
			manager = tasking.getManager().getText();
			task = buttonPressed.getToolTipText();
			task = task.substring(14);
			taskService.pull(task, user, manager);
		}
		else if(buttonPressed.getToolTipText().contains("Release")){
			String manager =null,user=null,task=null;
			user = tasking.getUser().getText();
			manager = tasking.getManager().getText();
			task = buttonPressed.getToolTipText();
			task = task.substring(17);
			taskService.pull(task, user, manager);
		}
		else if(buttonPressed.getToolTipText().contains("Edit")){
			String user = null, task = null, description = "";
			task = buttonPressed.getToolTipText();
			description= JOptionPane.showInputDialog(new Frame(),"Enter new description");
			if(buttonPressed.getToolTipText().contains("Manager")){
				user= tasking.getUser().getText()+"-"+task.substring(13, 21);
				task=task.substring(22);
			}
			else if (buttonPressed.getToolTipText().contains("Pool")){
				user = tasking.getUser().getText()+"-pool";
				task = task.substring(19);
			}
			else{
				task = task.substring(14);
				user = tasking.getUser().getText();
			}
			
			if(description!=null){
				taskService.edit(task, user, description);
			}
		}
		
		tasking.getAppFrame().getContentPane().removeAll();
		tasking.arrangeComponents(tasking.getUser().getText(), tasking.getManager().getText());
		tasking.getAppFrame().getContentPane().validate();
		tasking.getAppFrame().getContentPane().repaint();
	}
	
	

}
