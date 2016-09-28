package gui;

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

import events.LoginEvent;

public class JLogin {
	
	JFrame appFrame;
	JLabel jlUsername,jlPassword;
	JTextField jtfUsername;
	JPasswordField jtfPassword;
	JPanel panel;
	JButton jbLogin;
	public JFrame getAppFrame() {
		return appFrame;
	}
	public void setAppFrame(JFrame appFrame) {
		this.appFrame = appFrame;
	}
	public JLabel getJlUsername() {
		return jlUsername;
	}
	public void setJlUsername(JLabel jlUsername) {
		this.jlUsername = jlUsername;
	}
	public JLabel getJlPassword() {
		return jlPassword;
	}
	public void setJlPassword(JLabel jlPassword) {
		this.jlPassword = jlPassword;
	}
	public JTextField getJtfUsername() {
		return jtfUsername;
	}
	public void setJtfUsername(JTextField jtfUsername) {
		this.jtfUsername = jtfUsername;
	}
	public JPasswordField getJtfPassword() {
		return jtfPassword;
	}
	public void setJtfPassword(JPasswordField jtfPassword) {
		this.jtfPassword = jtfPassword;
	}
	public JPanel getPanel() {
		return panel;
	}
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	public JButton getJbLogin() {
		return jbLogin;
	}
	public void setJbLogin(JButton jbLogin) {
		this.jbLogin = jbLogin;
	}
	
	
	
	public JLogin(){
		createGUI();
	}
	
	
	public void switchVisibility(){
		if(appFrame.isVisible())
			appFrame.setVisible(false);
		else
			appFrame.setVisible(true);
		
	}
	
	
	
	public void createGUI(){
		appFrame = new JFrame();
		panel = new JPanel();
		
		
		arrangeComponents();
		
		
		appFrame.setSize(500, 200);
		appFrame.setResizable(false);
		appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		centerFrame();
		switchVisibility();
	}
	
	
	public void centerFrame(){
		Dimension windowSize = appFrame.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x - windowSize.width/2;
		int dy = centerPoint.y - windowSize.height/2;
		
		appFrame.setLocation(dx, dy);
	}
	
	
	public void arrangeComponents(){
		GridBagConstraints constraints;
		jlUsername = new JLabel("Username");
		jlPassword = new JLabel("Passowrd");
		jtfUsername = new JTextField();
		jtfPassword = new JPasswordField();
		jbLogin = new JButton("Login");
		
		panel.setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.ipadx = 50;
		constraints.fill=GridBagConstraints.BOTH;
		panel.add(jlUsername,constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.ipadx = 200;
		panel.add(jtfUsername,constraints);
		
		constraints.ipadx=0;
		constraints.gridx=0;
		constraints.gridy=1;
		panel.add(jlPassword,constraints);
		
		constraints.gridx=1;
		constraints.ipadx=200;
		panel.add(jtfPassword, constraints);
		
		constraints.ipadx=0;
		constraints.gridy=2;
		constraints.gridx=0;
		constraints.gridwidth=2;
		jbLogin.addActionListener(new LoginEvent(this));
		panel.add(jbLogin, constraints);
		
		appFrame.add(panel);
		
		
	
		
	}
	
	

	

}
