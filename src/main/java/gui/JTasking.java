package gui;

import beans.NewTaskAddBean;
import beans.NewTaskTableBean;
import beans.UserBean;
import events.MenuEvent;
import events.TaskEvent;
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
import services.LoginService;
import services.TaskService;

public class JTasking {
  private JFrame appFrame;
  private JPanel panel;
  private TaskService taskService;
  private ArrayList<NewTaskTableBean> taskTable;
  private ArrayList<NewTaskTableBean> managerTaskTable;
  private ArrayList<NewTaskTableBean> associateTaskTable;
  private ArrayList<NewTaskTableBean> myPoolTaskTable;
  private ArrayList<NewTaskTableBean> myManagerPoolTaskTable;
  private ArrayList<NewTaskTableBean> myCompletedTaskTable;
  private int i;
  private JLabel userLabel;
  private JLabel managerLabel;
  private ArrayList<NewTaskAddBean> newTasks = new ArrayList<>();
  private GridBagConstraints constraints;

  public JTasking(String user, String manager) {
    appFrame = new JFrame("TasKing");
    panel = new JPanel();
    arrangeComponents(user, manager);
    appFrame.setSize(1600, 1000);
    appFrame.setResizable(true);
    appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    switchVisibility();
  }

  public JFrame getAppFrame() {
    return appFrame;
  }

  public JPanel getPanel() {
    return panel;
  }

  public TaskService getTaskService() {
    return taskService;
  }

  public ArrayList<NewTaskTableBean> getTaskTable() {
    return taskTable;
  }

  public ArrayList<NewTaskTableBean> getManagerTaskTable() {
    return managerTaskTable;
  }

  public ArrayList<NewTaskTableBean> getAssociateTaskTable() {
    return associateTaskTable;
  }

  public ArrayList<NewTaskTableBean> getMyPoolTaskTable() {
    return myPoolTaskTable;
  }

  public ArrayList<NewTaskTableBean> getMyManagerPoolTaskTable() {
    return myManagerPoolTaskTable;
  }

  public ArrayList<NewTaskTableBean> getMyCompletedTaskTable() {
    return myCompletedTaskTable;
  }

  public JLabel getUser() {
    return userLabel;
  }

  public JLabel getManager() {
    return managerLabel;
  }

  public ArrayList<NewTaskAddBean> getNewTasks() {
    return newTasks;
  }

  public void switchVisibility() {
    appFrame.setVisible(!appFrame.isVisible());
  }

  public void refresh() {
    arrangeComponents(userLabel.getText(), managerLabel.getText());
    appFrame.getContentPane().revalidate();
    appFrame.getContentPane().repaint();
  }

  public void putHeaders() {
    Border blackline = BorderFactory.createLineBorder(Color.BLACK);
    ++i;
    int column = 0;
    for (JComponent component : new NewTaskTableBean().getHeaders()) {
      constraints.gridx = column;
      constraints.gridy = i;
      component.setBorder(blackline);
      panel.add(component, constraints);
      ++column;
    }
  }

  public int getWidth() {
    return new NewTaskTableBean().getHeaders().size();
  }

  public void makeTable(
      String user,
      String storeKey,
      ArrayList<NewTaskTableBean> rows,
      boolean completeButton,
      boolean deleteButton,
      boolean pullButton,
      boolean releaseButton,
      boolean associateTaskDeleteButton,
      boolean poolDeleteButton,
      boolean editButton,
      boolean editAssociateButton,
      boolean editPoolButton) {
    Border blackline = BorderFactory.createLineBorder(Color.BLACK);
    ++i;
    for (NewTaskTableBean row : rows) {
      int column = 0;
      for (JComponent component : row.getJComponents()) {
        constraints.ipadx = 70;
        constraints.gridx = column;
        constraints.gridy = i;
        component.setBorder(blackline);
        panel.add(new JScrollPane(component), constraints);
        ++column;
      }
      constraints.fill = GridBagConstraints.BOTH;

      if (completeButton) {
        panel.add(
            taskButton(
                "COMPLETE",
                "Complete " + this.userLabel.getText() + " " + row.getID().getText(),
                "complete",
                storeKey,
                row,
                column,
                i),
            constraints);
        ++column;
      }
      if (deleteButton) {
        panel.add(
            taskButton(
                "DELETE",
                "Delete " + this.userLabel.getText() + " " + row.getID().getText(),
                "delete",
                storeKey,
                row,
                column,
                i),
            constraints);
        ++column;
      }
      if (releaseButton && row.getName().getText().contains("POOLTASK")) {
        panel.add(
            taskButton(
                "RELEASE",
                "Release " + this.userLabel.getText() + " " + row.getID().getText(),
                "release",
                storeKey,
                row,
                column,
                i),
            constraints);
        ++column;
      }
      if (associateTaskDeleteButton) {
        panel.add(
            taskButton(
                "DELETE",
                "Delete Manager " + user + " " + row.getID().getText(),
                "delete",
                storeKey,
                row,
                column,
                i),
            constraints);
        ++column;
      }
      if (poolDeleteButton) {
        panel.add(
            taskButton(
                "DELETE",
                "Remove Pool " + this.userLabel.getText() + " " + row.getID().getText(),
                "delete",
                storeKey,
                row,
                column,
                i),
            constraints);
        ++column;
      }
      if (pullButton) {
        panel.add(
            taskButton(
                "PULL",
                "Pull " + this.userLabel.getText() + " " + row.getID().getText(),
                "pull",
                storeKey,
                row,
                column,
                i),
            constraints);
        ++column;
      }
      if (editButton) {
        panel.add(
            taskButton(
                "EDIT",
                "Edit " + this.userLabel.getText() + " " + row.getID().getText(),
                "edit",
                storeKey,
                row,
                column,
                i),
            constraints);
        ++column;
      }
      if (editAssociateButton) {
        panel.add(
            taskButton(
                "EDIT",
                "Edit Manager " + user + " " + row.getID().getText(),
                "edit",
                storeKey,
                row,
                column,
                i),
            constraints);
        ++column;
      }
      if (editPoolButton) {
        panel.add(
            taskButton(
                "EDIT",
                "Edit Pool " + this.userLabel.getText() + " " + row.getID().getText(),
                "edit",
                storeKey,
                row,
                column,
                i),
            constraints);
        ++column;
      }
      ++i;
      constraints.fill = GridBagConstraints.HORIZONTAL;
    }
  }

  private JButton taskButton(
      String label,
      String tooltip,
      String action,
      String storeKey,
      NewTaskTableBean row,
      int column,
      int rowIndex) {
    JButton button = new JButton(label);
    button.setToolTipText(tooltip);
    button.putClientProperty("action", action);
    button.putClientProperty("storeKey", storeKey);
    button.putClientProperty("taskId", row.getID().getText());
    button.putClientProperty("row", row);
    button.addActionListener(new TaskEvent(this));
    button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    constraints.gridx = column;
    constraints.gridy = rowIndex;
    return button;
  }

  private void newTask(JButton addTask) {
    NewTaskAddBean draft = new NewTaskAddBean();
    addTask.putClientProperty("draft", draft);
    Border blackline = BorderFactory.createLineBorder(Color.BLACK);
    int column = 0;
    for (JComponent component : draft.getComponenets()) {
      constraints.gridx = column;
      constraints.gridy = i;
      component.setBorder(blackline);
      panel.add(component, constraints);
      ++column;
    }
    ++i;
    constraints.gridwidth = column;
    constraints.gridy = i;
    constraints.gridx = 0;
    addTask.setBorder(blackline);
    panel.add(addTask, constraints);
    newTasks.add(draft);
    constraints.gridwidth = 1;
    ++i;
  }

  public void blankline(int columns) {
    constraints.gridx = 0;
    constraints.gridwidth = columns;
    for (int line = 0; line < 2; ++line) {
      constraints.gridy = i;
      panel.add(new JLabel(" "), constraints);
      ++i;
    }
    constraints.gridwidth = 1;
  }

  public void myTasks(String user) {
    Border blackline = BorderFactory.createLineBorder(Color.BLACK);
    this.userLabel = new JLabel(user, SwingConstants.CENTER);
    JButton addTask = new JButton("ADD");
    addTask.setToolTipText("Add Task" + user);
    addTask.putClientProperty("action", "add-task");
    addTask.putClientProperty("storeKey", user);
    addTask.addActionListener(new TaskEvent(this));
    taskService = new TaskService();
    taskTable = taskService.getTask(user);
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.gridx = 0;
    constraints.gridy = i;
    JLabel myName = new JLabel(LoginService.getName(user), SwingConstants.CENTER);
    myName.setBorder(blackline);
    myName.setBackground(TaskService.isEight(user) ? Color.GREEN : Color.RED);
    myName.setOpaque(true);
    panel.add(myName, constraints);
    ++i;

    putHeaders();
    makeTable(user, user, taskTable, true, true, false, false, false, false, true, false, false);
    newTask(addTask);
    blankline(getWidth());
  }

  public void managerTasks(String user) {
    Border blackline = BorderFactory.createLineBorder(Color.BLACK);
    this.managerLabel = new JLabel(user, SwingConstants.CENTER);
    int columns = getWidth();
    taskService = new TaskService();
    String storeKey = user + "-" + userLabel.getText();
    managerTaskTable = taskService.getTask(storeKey);
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.gridwidth = columns;
    constraints.gridy = i;
    ++i;
    JLabel myManager =
        new JLabel(LoginService.getName(user) + "'s Tasks to Me", SwingConstants.CENTER);
    myManager.setBorder(blackline);
    myManager.setBackground(Color.GREEN);
    myManager.setOpaque(true);
    panel.add(myManager, constraints);
    ++i;
    constraints.gridwidth = 1;
    putHeaders();
    makeTable(
        user,
        storeKey,
        managerTaskTable,
        true,
        false,
        false,
        true,
        false,
        false,
        false,
        false,
        false);
    blankline(getWidth());
  }

  public void myCompletedTasks(String user) {
    Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
    myCompletedTaskTable = TaskService.getCompletedToday(user);
    int columns = getWidth();
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.gridwidth = columns;
    constraints.gridx = 0;
    constraints.gridy = i;

    JLabel myCompleted = new JLabel("Tasks Completed Today", SwingConstants.CENTER);
    myCompleted.setBorder(blackLine);
    myCompleted.setBackground(Color.PINK);
    myCompleted.setOpaque(true);
    panel.add(myCompleted, constraints);
    constraints.gridwidth = 1;
    putHeaders();
    makeTable(
        user,
        user + "-completed",
        myCompletedTaskTable,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false);
    blankline(columns);
  }

  public void myAssociate(String user) {
    Border blackline = BorderFactory.createLineBorder(Color.BLACK);
    taskService = new TaskService();
    associateTaskTable = taskService.getTask(user);
    JButton addTask = new JButton("ADD");
    addTask.setToolTipText("Add Task" + user);
    addTask.putClientProperty("action", "add-task");
    addTask.putClientProperty("storeKey", this.userLabel.getText() + "-" + user);
    addTask.addActionListener(new TaskEvent(this));
    addTask.setBorder(blackline);

    int columns = getWidth();
    constraints.gridwidth = columns;
    constraints.gridx = 0;
    constraints.gridy = i;

    JLabel associate = new JLabel(LoginService.getName(user), SwingConstants.CENTER);
    associate.setBorder(blackline);
    associate.setBackground(TaskService.isEight(user) ? Color.CYAN : Color.RED);
    associate.setOpaque(true);
    panel.add(associate, constraints);
    constraints.gridwidth = 1;
    putHeaders();

    makeTable(
        user,
        user,
        associateTaskTable,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false);
    associateTaskTable = taskService.getTask(this.userLabel.getText() + "-" + user);
    makeTable(
        user,
        this.userLabel.getText() + "-" + user,
        associateTaskTable,
        false,
        false,
        false,
        false,
        true,
        false,
        false,
        true,
        false);
    associateTaskTable = TaskService.getCompletedToday(user);
    makeTable(
        user,
        user + "-completed",
        associateTaskTable,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false);

    newTask(addTask);
    blankline(columns);
  }

  public void myPool(String user) {
    JLabel poolId = new JLabel("My Pool", SwingConstants.CENTER);
    Border blackLine = BorderFactory.createLineBorder(Color.BLACK);
    JButton addTask = new JButton("ADD");
    addTask.setToolTipText("Add Pool" + user);
    addTask.putClientProperty("action", "add-pool");
    addTask.putClientProperty("storeKey", user);
    addTask.addActionListener(new TaskEvent(this));
    taskService = new TaskService();
    myPoolTaskTable = taskService.getpool(user);
    constraints.fill = GridBagConstraints.HORIZONTAL;

    int columns = getWidth();
    constraints.gridwidth = columns;
    constraints.gridy = i;
    constraints.gridx = 0;
    poolId.setBorder(blackLine);
    poolId.setBackground(Color.ORANGE);
    poolId.setOpaque(true);
    panel.add(poolId, constraints);
    constraints.gridwidth = 1;

    putHeaders();
    makeTable(
        user,
        user + "-pool",
        myPoolTaskTable,
        false,
        false,
        false,
        false,
        false,
        true,
        false,
        false,
        true);
    newTask(addTask);
    blankline(columns);
  }

  public void managerPool(String manager) {
    JLabel poolId = new JLabel(LoginService.getName(manager) + "'s Pool", SwingConstants.CENTER);
    Border blackline = BorderFactory.createLineBorder(Color.BLACK);
    taskService = new TaskService();
    myManagerPoolTaskTable = taskService.getpool(manager);

    constraints.fill = GridBagConstraints.HORIZONTAL;
    int columns = getWidth();
    constraints.gridwidth = columns;
    constraints.gridx = 0;
    constraints.gridy = i;
    poolId.setBorder(blackline);
    poolId.setBackground(Color.PINK);
    poolId.setOpaque(true);
    panel.add(poolId, constraints);
    constraints.gridwidth = 1;

    putHeaders();
    makeTable(
        manager,
        manager + "-pool",
        myManagerPoolTaskTable,
        false,
        false,
        true,
        false,
        false,
        false,
        false,
        false,
        false);
    ++i;
  }

  public void addMenuBar() {
    JMenuBar menu = new JMenuBar();
    JMenu file = new JMenu("File");
    JMenu edit = new JMenu("Edit");
    JMenuItem getData = new JMenuItem("Get Data");
    JMenuItem refresh = new JMenuItem("Refresh");
    JMenuItem backup = new JMenuItem("Backup");
    JMenuItem changePassword = new JMenuItem("Change Password");
    file.add(getData);
    file.add(changePassword);
    edit.add(refresh);
    edit.add(backup);
    menu.add(file);
    menu.add(edit);
    MenuEvent menuEvent = new MenuEvent(this);
    refresh.addActionListener(menuEvent);
    getData.addActionListener(menuEvent);
    backup.addActionListener(menuEvent);
    changePassword.addActionListener(menuEvent);
    appFrame.setJMenuBar(menu);
  }

  public void addHeading() {
    JLabel heading = new JLabel("TasKing", SwingConstants.CENTER);
    heading.setText(
        "<html><h1 style='font-family:Segoe UI,sans-serif; font-size:30px;'>TasKing</h1></html>");
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

  /**
   * Badges look for timesaver.png and crown.png in the working directory. The call stays optional.
   */
  public void addBadge(String user) {
    if (TaskService.isProSaver(user) > 0) {
      try {
        File imageFile =
            TaskService.isProSaver(user).equals(1)
                ? new File("timesaver.png")
                : new File("crown.png");
        BufferedImage myPicture = ImageIO.read(imageFile);
        JLabel picLabel = new JLabel(new ImageIcon(myPicture), SwingConstants.CENTER);
        constraints.gridwidth = getWidth();
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
    appFrame.getContentPane().removeAll();
    panel.removeAll();
    panel.setLayout(new GridBagLayout());
    constraints = new GridBagConstraints();
    newTasks = new ArrayList<>();

    // addBadge(user);
    addHeading();
    addMenuBar();

    myTasks(user);
    managerTasks(manager);
    ArrayList<UserBean> associates = LoginService.getAssociates(user);
    for (UserBean associate : associates) {
      myAssociate(associate.getId());
    }
    myCompletedTasks(user);
    if (LoginService.isManager(user)) {
      myPool(user);
    }
    managerPool(manager);

    JScrollPane container = new JScrollPane(panel);
    container.setWheelScrollingEnabled(true);
    appFrame.getContentPane().add(container);
  }
}
