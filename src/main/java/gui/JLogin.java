package gui;

import dao.UserDAO;
import events.LoginEvent;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class JLogin {
  private JFrame appFrame;
  private JLabel jlUsername;
  private JLabel jlPassword;
  private JTextField jtfUsername;
  private JPasswordField jtfPassword;
  private JPanel panel;
  private JButton jbLogin;

  public JLogin() {
    createGUI();
  }

  public JFrame getAppFrame() {
    return appFrame;
  }

  public JLabel getJlUsername() {
    return jlUsername;
  }

  public JLabel getJlPassword() {
    return jlPassword;
  }

  public JTextField getJtfUsername() {
    return jtfUsername;
  }

  public JPasswordField getJtfPassword() {
    return jtfPassword;
  }

  public JPanel getPanel() {
    return panel;
  }

  public JButton getJbLogin() {
    return jbLogin;
  }

  public void switchVisibility() {
    appFrame.setVisible(!appFrame.isVisible());
  }

  public void createGUI() {
    appFrame = new JFrame("TasKing");
    panel = new JPanel();
    arrangeComponents();
    appFrame.setSize(520, 230);
    appFrame.setResizable(false);
    appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    appFrame.getRootPane().setDefaultButton(jbLogin);
    centerFrame();
    switchVisibility();
  }

  public void centerFrame() {
    Dimension windowSize = appFrame.getSize();
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Point centerPoint = ge.getCenterPoint();
    int dx = centerPoint.x - windowSize.width / 2;
    int dy = centerPoint.y - windowSize.height / 2;
    appFrame.setLocation(dx, dy);
  }

  public void arrangeComponents() {
    jlUsername = new JLabel("Username");
    jlPassword = new JLabel("Password");
    jtfUsername = new JTextField();
    jtfPassword = new JPasswordField();
    jbLogin = new JButton("Login");
    JLabel hint = new JLabel(" ");
    if (UserDAO.getAllUsers().isEmpty()) {
      hint.setText("No users yet. Create one with: java -jar Tasking.jar New");
    }

    panel.setLayout(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();

    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.ipadx = 50;
    constraints.fill = GridBagConstraints.BOTH;
    panel.add(jlUsername, constraints);

    constraints.gridx = 1;
    constraints.gridy = 0;
    constraints.ipadx = 200;
    panel.add(jtfUsername, constraints);

    constraints.ipadx = 0;
    constraints.gridx = 0;
    constraints.gridy = 1;
    panel.add(jlPassword, constraints);

    constraints.gridx = 1;
    constraints.ipadx = 200;
    panel.add(jtfPassword, constraints);

    constraints.ipadx = 0;
    constraints.gridy = 2;
    constraints.gridx = 0;
    constraints.gridwidth = 2;
    jbLogin.addActionListener(new LoginEvent(this));
    panel.add(jbLogin, constraints);

    constraints.gridy = 3;
    panel.add(hint, constraints);
    appFrame.add(panel);
  }
}
