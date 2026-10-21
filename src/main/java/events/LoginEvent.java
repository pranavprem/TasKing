package events;

import beans.UserBean;
import gui.JLogin;
import gui.JTasking;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JTextField;
import services.LoginService;

public class LoginEvent implements ActionListener {
  private final JLogin login;
  private LoginService loginService;

  public LoginEvent(JLogin login) {
    this.login = login;
  }

  @Override
  public void actionPerformed(ActionEvent action) {
    JTextField id = login.getJtfUsername();
    char[] passwordChars = login.getJtfPassword().getPassword();
    String password = new String(passwordChars);
    Arrays.fill(passwordChars, '\0');

    UserBean user = new UserBean(id.getText().trim(), password);
    loginService = new LoginService();
    if (loginService.authorize(user)) {
      login.switchVisibility();
      new JTasking(user.getId(), loginService.getManager(user));
      return;
    }
    login.getJtfUsername().setBackground(Color.RED);
    login.getJtfPassword().setBackground(Color.RED);
  }
}
