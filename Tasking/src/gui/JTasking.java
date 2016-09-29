package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import beans.NewTaskAddBean;
import beans.NewTaskTableBean;
import beans.UserBean;
import events.MenuEvent;
import events.TaskEvent;
import services.LoginService;
import services.TaskService;

public class JTasking {

	JFrame appFrame;
	JPanel panel;
	TaskService taskService;
	ArrayList<NewTaskTableBean> taskTable, managerTaskTable, associateTaskTable, myPoolTaskTable,
			myManagerPoolTaskTable, myCompletedTaskTable;
	int i;
	JLabel User;
	JLabel Manager;
	ArrayList<NewTaskAddBean> newTasks = new ArrayList<NewTaskAddBean>();
	GridBagConstraints constraints;

	public JFrame getAppFrame() {
		return appFrame;
	}

	public void setAppFrame(JFrame appFrame) {
		this.appFrame = appFrame;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public ArrayList<NewTaskTableBean> getTaskTable() {
		return taskTable;
	}

	public void setTaskTable(ArrayList<NewTaskTableBean> taskTable) {
		this.taskTable = taskTable;
	}

	public ArrayList<NewTaskTableBean> getManagerTaskTable() {
		return managerTaskTable;
	}

	public void setManagerTaskTable(ArrayList<NewTaskTableBean> managerTaskTable) {
		this.managerTaskTable = managerTaskTable;
	}

	public ArrayList<NewTaskTableBean> getAssociateTaskTable() {
		return associateTaskTable;
	}

	public void setAssociateTaskTable(ArrayList<NewTaskTableBean> associateTaskTable) {
		this.associateTaskTable = associateTaskTable;
	}

	public ArrayList<NewTaskTableBean> getMyPoolTaskTable() {
		return myPoolTaskTable;
	}

	public void setMyPoolTaskTable(ArrayList<NewTaskTableBean> myPoolTaskTable) {
		this.myPoolTaskTable = myPoolTaskTable;
	}

	public ArrayList<NewTaskTableBean> getMyManagerPoolTaskTable() {
		return myManagerPoolTaskTable;
	}

	public void setMyManagerPoolTaskTable(ArrayList<NewTaskTableBean> myManagerPoolTaskTable) {
		this.myManagerPoolTaskTable = myManagerPoolTaskTable;
	}

	public ArrayList<NewTaskTableBean> getMyCompletedTaskTable() {
		return myCompletedTaskTable;
	}

	public void setMyCompletedTaskTable(ArrayList<NewTaskTableBean> myCompletedTaskTable) {
		this.myCompletedTaskTable = myCompletedTaskTable;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public JLabel getUser() {
		return User;
	}

	public void setUser(JLabel user) {
		User = user;
	}

	public JLabel getManager() {
		return Manager;
	}

	public void setManager(JLabel manager) {
		Manager = manager;
	}

	public ArrayList<NewTaskAddBean> getNewTasks() {
		return newTasks;
	}

	public void setNewTasks(ArrayList<NewTaskAddBean> newTasks) {
		this.newTasks = newTasks;
	}

	public GridBagConstraints getConstraints() {
		return constraints;
	}

	public void setConstraints(GridBagConstraints constraints) {
		this.constraints = constraints;
	}

	public void switchVisibility() {
		if (appFrame.isVisible())
			appFrame.setVisible(false);
		else
			appFrame.setVisible(true);

	}

	public void putHeaders() {
		int j;
		Border blackline = BorderFactory.createLineBorder(Color.BLACK);
		++i;
		j = 0;
		for (JComponent component : new NewTaskAddBean().getHeaders()) {
			constraints.gridx = j;
			constraints.gridy = i;
			component.setBorder(blackline);
			panel.add(component, constraints);
			++j;
		}

	}
	
	@SuppressWarnings("unused")
	public int getWidth(){
		int j=0;;
		for (JComponent component : new NewTaskTableBean().getHeaders()) {
			++j;
		}
		return j;
	}

	public void makeTable(String user, ArrayList<NewTaskTableBean> taskTable, boolean completeButton,
			boolean deleteButton, boolean pullButton, boolean releaseButton, boolean associateTaskDeleteButton,
			boolean poolDeleteButton, boolean editButton, boolean editAssociateButton, boolean editPoolButton) {
		Border blackline = BorderFactory.createLineBorder(Color.BLACK);
		JButton temp;
		int j = 0;
		++i;

		for (NewTaskTableBean row : taskTable) {
			j = 0;
			for (JComponent component : row.getJComponents()) {
				constraints.ipadx = 70;
				constraints.gridx = j;
				constraints.gridy = i;
				component.setBorder(blackline);
				panel.add(new JScrollPane(component), constraints);
				++j;
			}
			constraints.fill = GridBagConstraints.BOTH;

			if (completeButton) {
				constraints.gridx = j;
				constraints.gridy = i;
				temp = new JButton("COMPLETE");
				temp.setToolTipText("Complete " + this.User.getText() + " " + row.getID().getText());
				temp.addActionListener(new TaskEvent(this));
				temp.setBorder(blackline);
				panel.add(temp, constraints);
				++j;
			}

			if (deleteButton) {
				constraints.gridx = j;
				constraints.gridy = i;
				temp = new JButton("DELETE");
				temp.setToolTipText("Delete " + this.User.getText() + " " + row.getID().getText());
				temp.addActionListener(new TaskEvent(this));
				temp.setBorder(blackline);
				panel.add(temp, constraints);
				++j;
			}

			if (releaseButton) {
				if (row.getName().getText().contains("POOLTASK")) {
					constraints.gridx = j;
					constraints.gridy = i;
					temp = new JButton("RELEASE");
					temp.setToolTipText("Release " + this.User.getText() + " " + row.getID().getText());
					temp.addActionListener(new TaskEvent(this));
					temp.setBorder(blackline);
					panel.add(temp, constraints);
					++j;
				}
			}

			if (associateTaskDeleteButton) {
				constraints.gridx = j;
				constraints.gridy = i;
				temp = new JButton("DELETE");
				temp.setToolTipText("Delete Manager " + this.User.getText() + " " + row.getID().getText());
				temp.addActionListener(new TaskEvent(this));
				temp.setBorder(blackline);
				panel.add(temp, constraints);
				++j;
			}

			if (poolDeleteButton) {
				constraints.gridx = j;
				constraints.gridy = i;
				temp = new JButton("DELETE");
				temp.setToolTipText("Remove Pool " + this.User.getText() + " " + row.getID().getText());
				temp.addActionListener(new TaskEvent(this));
				temp.setBorder(blackline);
				panel.add(temp, constraints);
				++j;
			}

			if (pullButton) {
				constraints.gridx = j;
				constraints.gridy = i;
				temp = new JButton("PULL");
				temp.setToolTipText("Pull " + this.User.getText() + " " + row.getID().getText());
				temp.addActionListener(new TaskEvent(this));
				temp.setBorder(blackline);
				panel.add(temp, constraints);
				++j;
			}

			if (editButton) {
				constraints.gridx = j;
				constraints.gridy = i;
				temp = new JButton("EDIT");
				temp.setToolTipText("Edit " + this.User.getText() + " " + row.getID().getText());
				temp.addActionListener(new TaskEvent(this));
				temp.setBorder(blackline);
				panel.add(temp, constraints);
				++j;
			}

			if (editAssociateButton) {
				constraints.gridx = j;
				constraints.gridy = i;
				temp = new JButton("EDIT");
				temp.setToolTipText("Edit Manager " + this.User.getText() + " " + row.getID().getText());
				temp.addActionListener(new TaskEvent(this));
				temp.setBorder(blackline);
				panel.add(temp, constraints);
				++j;
			}

			if (editPoolButton) {
				constraints.gridx = j;
				constraints.gridy = i;
				temp = new JButton("EDIT");
				temp.setToolTipText("Edit Pool " + this.User.getText() + " " + row.getID().getText());
				temp.addActionListener(new TaskEvent(this));
				temp.setBorder(blackline);
				panel.add(temp, constraints);
				++j;
			}

			++i;
			constraints.fill = GridBagConstraints.HORIZONTAL;

		}
	}

	private void newTask(JButton addtask) {
		NewTaskAddBean newTask = new NewTaskAddBean();
		Border blackline = BorderFactory.createLineBorder(Color.black);
		int j = 0;
		for (JComponent component : newTask.getComponenets()) {
			constraints.gridx = j;
			constraints.gridy = i;
			component.setBorder(blackline);
			panel.add(component, constraints);
			++j;
		}
		++i;
		constraints.gridwidth = j;
		constraints.gridy = i;
		constraints.gridx = 0;
		addtask.setBorder(blackline);
		panel.add(addtask, constraints);
		newTasks.add(newTask);
		constraints.gridwidth = 1;
		++i;
	}

	public void blankline(int j) {
		constraints.gridx = 0;
		constraints.gridy = i;
		constraints.gridwidth = j;
		JLabel temp;
		for (int line = 0; line < 2; ++line) {
			constraints.gridy = i;
			temp = new JLabel(" ");
			panel.add(temp, constraints);
			++i;
		}
		constraints.gridwidth = 1;
	}

	public void myTasks(String user) {
		Border blackline = BorderFactory.createLineBorder(Color.black);
		this.User = new JLabel(user, SwingConstants.CENTER);
		int j = 0;
		JButton addtask = new JButton("ADD");
		addtask.setToolTipText("Add Task" + User.getText());
		addtask.addActionListener(new TaskEvent(this));
		taskService = new TaskService();
		taskTable = taskService.getTask(User.getText());
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = j;
		constraints.gridy = i;
		JLabel myName = new JLabel(LoginService.getName(user), SwingConstants.CENTER);
		myName.setBorder(blackline);
		if (TaskService.isEight(user)) {
			myName.setBackground(Color.GREEN);
		} else {
			myName.setBackground(Color.RED);
		}

		myName.setOpaque(true);
		panel.add(myName, constraints);
		++i;

		putHeaders();
		makeTable(user, taskTable, true, true, false, false, false, false, true, false, false);
		newTask(addtask);
		blankline(j);
	}

	public void managerTasks(String user) {
		Border blackline = BorderFactory.createLineBorder(Color.BLACK);
		this.Manager = new JLabel(user, SwingConstants.CENTER);
		int j = 0;
		j=getWidth();

		taskService = new TaskService();
		managerTaskTable = taskService.getTask(Manager.getText() + "-" + User.getText());
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = j;
		constraints.gridy = i;
		++i;
		JLabel myManager = new JLabel(LoginService.getName(user) + "'s Tasks to Me", SwingConstants.CENTER);
		myManager.setBorder(blackline);
		myManager.setBackground(Color.GREEN);
		myManager.setOpaque(true);
		panel.add(myManager, constraints);
		++i;
		constraints.gridwidth = 1;
		putHeaders();
		makeTable(user, managerTaskTable, true, false, false, true, false, false, false, false, false);
		j = 0;
		j=getWidth();
		blankline(j);
	}

	public void myCompletedTasks(String user) {

		int j = 0;
		Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
		myCompletedTaskTable = TaskService.getComplteted(user);

		j=getWidth();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = j;
		constraints.gridx = 0;
		constraints.gridy = i;

		JLabel myCompleted = new JLabel("Tasks Completed Today", SwingConstants.CENTER);
		myCompleted.setBorder(blackLine);
		myCompleted.setBackground(Color.PINK);
		myCompleted.setOpaque(true);
		panel.add(myCompleted, constraints);
		constraints.gridwidth = 1;
		putHeaders();
		makeTable(user, myCompletedTaskTable, false, false, false, false, false, false, false, false, false);
		blankline(j);

	}

	public void myAssociate(String user) {
		int j = 0;
		Border blackline = BorderFactory.createLineBorder(Color.BLACK);
		JButton addtask;
		taskService = new TaskService();
		associateTaskTable = taskService.getTask(user);
		addtask = new JButton("ADD");
		addtask.setToolTipText("Add Task" + user);
		addtask.addActionListener(new TaskEvent(this));
		addtask.setBorder(blackline);

		j=getWidth();
		constraints.gridwidth = j;
		constraints.gridx = 0;
		constraints.gridy = i;

		JLabel associate = new JLabel(LoginService.getName(user));
		associate.setBorder(blackline);
		if (TaskService.isEight(user)) {
			associate.setBackground(Color.CYAN);
		} else {
			associate.setBackground(Color.RED);
		}

		associate.setOpaque(true);
		panel.add(associate, constraints);
		constraints.gridwidth = 1;
		putHeaders();

		makeTable(user, associateTaskTable, false, false, false, false, false, false, false, false, false);
		associateTaskTable = taskService.getTask(this.User.getText() + "-" + user);

		makeTable(user, associateTaskTable, false, false, false, false, true, false, false, true, false);
		associateTaskTable = TaskService.getComplteted(user);

		makeTable(user, associateTaskTable, false, false, false, false, false, false, false, false, false);

		newTask(addtask);
		blankline(j);

	}

	public void myPool(String user) {
		JLabel poolID = new JLabel("My Pool", SwingConstants.CENTER);
		Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
		int j = 0;
		JButton addtask = new JButton("ADD");
		addtask.setToolTipText("Add Pool" + user);
		addtask.addActionListener(new TaskEvent(this));
		taskService = new TaskService();
		myPoolTaskTable = taskService.getpool(user);
		constraints.fill = GridBagConstraints.HORIZONTAL;

		j=getWidth();

		constraints.gridwidth = j;
		constraints.gridy = i;
		constraints.gridx = 0;
		poolID.setBorder(blackLine);
		poolID.setBackground(Color.ORANGE);
		poolID.setOpaque(true);
		panel.add(poolID, constraints);
		constraints.gridwidth = 1;

		putHeaders();
		makeTable(user, myPoolTaskTable, false, false, false, false, false, true, false, false, true);
		newTask(addtask);
		blankline(j);
	}

	public void managerPool(String manager) {
		JLabel poolID = new JLabel(LoginService.getName(manager) + "'s Pool", SwingConstants.CENTER);
		Border blackline = BorderFactory.createLineBorder(Color.BLACK);

		int j = 0;
		taskService = new TaskService();

		myManagerPoolTaskTable = taskService.getpool(manager);

		constraints.fill = GridBagConstraints.HORIZONTAL;
		j=getWidth();
		constraints.gridwidth = j;
		constraints.gridx = 0;
		constraints.gridy = i;
		poolID.setBorder(blackline);
		poolID.setBackground(Color.PINK);
		poolID.setOpaque(true);
		panel.add(poolID, constraints);
		constraints.gridwidth = 1;

		putHeaders();
		makeTable(manager, myManagerPoolTaskTable, false, false, true, false, false, false, false, false, false);
		++i;

	}

	public void addMenuBar() {
		JMenuBar menu = new JMenuBar();
		JMenu File = new JMenu("File");
		JMenu Edit = new JMenu("Edit");
		JMenuItem GetData = new JMenuItem("Get Data");
		JMenuItem Refresh = new JMenuItem("Refresh");
		JMenuItem Backup = new JMenuItem("Backup");
		JMenuItem ChangePassword = new JMenuItem("Change Password");
		File.add(GetData);
		File.add(ChangePassword);
		Edit.add(Refresh);
		Edit.add(Backup);
		menu.add(File);
		menu.add(Edit);
		Refresh.addActionListener(new MenuEvent(this));
		GetData.addActionListener(new MenuEvent(this));
		Backup.addActionListener(new MenuEvent(this));
		ChangePassword.addActionListener(new MenuEvent(this));
		appFrame.setJMenuBar(menu);
	}

	public void addHeading() {
		JLabel heading = new JLabel("TasKing", SwingConstants.CENTER);
		heading.setText("<html><h1 style='font-familt:Segoe UI; font-size 30px;'>TasKing</h1></html>");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 7;
		constraints.ipady = 40;
		constraints.gridx = 0;
		constraints.gridy = i;
		panel.add(heading, constraints);
		constraints.ipady = 0;
		constraints.gridwidth = 1;
		++i;
	}

	public void addBadge(String user) {
		int j = 0;

		if (TaskService.isProSaver(user) > 0) {
			BufferedImage myPicture;
			try {
				if (TaskService.isProSaver(user).equals(1)) {
					myPicture = ImageIO.read(new File("timesaver.png"));
				} else
					myPicture = ImageIO.read(new File("crown.png"));
				JLabel picLabel = new JLabel(new ImageIcon(myPicture), SwingConstants.CENTER);
				j=getWidth();
				constraints.gridwidth = j;
				constraints.gridy = i;
				constraints.gridx = 0;
				panel.add(picLabel, constraints);

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		++i;
	}

	public void arrangeComponents(String user, String manager) {
		i = 0;
		panel.removeAll();
		panel.setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		newTasks = new ArrayList<NewTaskAddBean>();

		//addBadge(user);
		addHeading();
		addMenuBar();

		myTasks(user);
		managerTasks(manager);
		ArrayList<UserBean> associate = LoginService.getAssociates(user);
		for (UserBean ass : associate) {
			myAssociate(ass.getId());
		}
		myCompletedTasks(user);
		if (LoginService.isManager(user)) {
			myPool(user);
		}

		managerPool(manager);

		JScrollPane container = new JScrollPane(panel);
		container.setWheelScrollingEnabled(true);
		appFrame.add(container);
	}

	public JTasking(String user, String manager) {
		appFrame = new JFrame();
		panel = new JPanel();
		arrangeComponents(user, manager);
		appFrame.setSize(1600, 1000);
		appFrame.setResizable(true);
		appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.switchVisibility();
	}

}
