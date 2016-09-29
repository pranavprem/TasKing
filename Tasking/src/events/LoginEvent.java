package events;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import beans.UserBean;
import gui.JLogin;
import gui.JTasking;
import services.LoginService;

public class LoginEvent implements ActionListener{
	
	JLogin Login;
	LoginService loginService;
	
	
	public LoginEvent(JLogin Login) {
		this.Login = Login;
	}
	
	@Override
	public void actionPerformed(ActionEvent action) {
		JTextField id = Login.getJtfUsername();
		JTextField password = Login.getJtfPassword();
		JButton buttonPressed = (JButton) action.getSource();
		
		UserBean user = new UserBean(id.getText(),password.getText());
		loginService = new LoginService();
		if(loginService.authorize(user)){
			if(buttonPressed.getText().contains("Get Data")){
				loginService.generate(user);
			}
			else{
				Login.switchVisibility();
				new JTasking(id.getText(),loginService.getManager(user));
			}
		}
		else{
			Login.getJtfUsername().setBackground(Color.RED);
			Login.getJtfPassword().setBackground(Color.RED);
		}
		
	}
	
	
	

}
