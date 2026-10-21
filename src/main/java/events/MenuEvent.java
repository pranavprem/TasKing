package events;

import beans.UserBean;
import gui.JTasking;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import services.BackupService;
import services.LoginService;

public class MenuEvent implements ActionListener {
  private final JTasking tasking;

  public MenuEvent(JTasking tasking) {
    this.tasking = tasking;
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    JMenuItem source = (JMenuItem) event.getSource();
    if ("Get Data".equals(source.getText())) {
      export();
    } else if ("Refresh".equals(source.getText())) {
      tasking.refresh();
    } else if ("Backup".equals(source.getText())) {
      backup();
    } else if ("Change Password".equals(source.getText())) {
      changePassword();
    }
  }

  private void export() {
    UserBean user = new UserBean();
    user.setId(tasking.getUser().getText());
    try {
      File csv = new LoginService().generate(user);
      JOptionPane.showMessageDialog(
          tasking.getAppFrame(),
          "Exported to\n" + csv.getAbsolutePath(),
          "TasKing",
          JOptionPane.INFORMATION_MESSAGE);
    } catch (RuntimeException ex) {
      JOptionPane.showMessageDialog(
          tasking.getAppFrame(),
          "Export failed: " + ex.getMessage(),
          "TasKing",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private void backup() {
    try {
      File destination = new BackupService().backup();
      JOptionPane.showMessageDialog(
          tasking.getAppFrame(),
          "Backed up to\n" + destination.getAbsolutePath(),
          "TasKing",
          JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException ex) {
      JOptionPane.showMessageDialog(
          tasking.getAppFrame(),
          "Backup failed: " + ex.getMessage(),
          "TasKing",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private void changePassword() {
    String password = JOptionPane.showInputDialog(tasking.getAppFrame(), "Enter New Password");
    if (password == null) {
      return;
    }
    if (password.isEmpty()) {
      JOptionPane.showMessageDialog(
          tasking.getAppFrame(),
          "Password was not changed.",
          "TasKing",
          JOptionPane.WARNING_MESSAGE);
      return;
    }
    new LoginService().changePassword(tasking.getUser().getText(), password);
  }
}
