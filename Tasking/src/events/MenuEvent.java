package events;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import beans.UserBean;
import gui.JTasking;
import services.BackupService;
import services.LoginService;

public class MenuEvent implements ActionListener{
	JTasking tasking;
	
	public MenuEvent(JTasking tasking){
		this.tasking=tasking;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) e.getSource();
		if(source.getText().equals("Get Data")){
			UserBean user = new UserBean();
			user.setId(tasking.getUser().getText());
			new LoginService().generate(user);
		}
		
		else if (source.getText().equals("Refresh")){
			tasking.getAppFrame().getContentPane().removeAll();
			tasking.arrangeComponents(tasking.getUser().getText(), tasking.getManager().getText());
			tasking.getAppFrame().getContentPane().validate();
			tasking.getAppFrame().getContentPane().repaint();
		}
		else if (source.getText().equals("Backup")){
			new BackupService().backup();
		}
		else if (source.getText().equals("Change Password")){
			String password = JOptionPane.showInputDialog(new Frame(),"Enter New Password");
			if(password!=null){
				new LoginService().changePassword(tasking.getUser().getText(), password);
			}
		}
		
	}

	
	
}
