package events;

import beans.NewTaskAddBean;
import beans.NewTaskTableBean;
import gui.JTasking;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import services.TaskService;

public class TaskEvent implements ActionListener {
  private final JTasking tasking;
  private final TaskService taskService;

  public TaskEvent(JTasking tasking) {
    this.tasking = tasking;
    this.taskService = new TaskService();
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    JButton button = (JButton) event.getSource();
    String action = (String) button.getClientProperty("action");
    if (action == null) {
      return;
    }
    String storeKey = (String) button.getClientProperty("storeKey");
    String taskId = (String) button.getClientProperty("taskId");

    if ("add-task".equals(action) || "add-pool".equals(action)) {
      if (!saveDraft(button, action, storeKey)) {
        return;
      }
    } else if ("delete".equals(action)) {
      taskService.deleteTask(taskId, storeKey);
    } else if ("complete".equals(action)) {
      if (!complete(button, storeKey, taskId)) {
        return;
      }
    } else if ("pull".equals(action)) {
      taskService.pull(taskId, tasking.getUser().getText(), tasking.getManager().getText());
    } else if ("release".equals(action)) {
      taskService.release(taskId, tasking.getUser().getText(), tasking.getManager().getText());
    } else if ("edit".equals(action)) {
      String description =
          JOptionPane.showInputDialog(tasking.getAppFrame(), "Enter new description");
      if (description == null) {
        return;
      }
      taskService.edit(taskId, storeKey, description);
    } else {
      return;
    }
    tasking.refresh();
  }

  private boolean saveDraft(JButton button, String action, String storeKey) {
    Object draftObject = button.getClientProperty("draft");
    if (!(draftObject instanceof NewTaskAddBean)) {
      return false;
    }
    NewTaskAddBean draft = (NewTaskAddBean) draftObject;
    if (draft.getName().getText() == null || draft.getName().getText().trim().isEmpty()) {
      return false;
    }
    String error =
        "add-pool".equals(action)
            ? taskService.addPool(storeKey, draft)
            : taskService.addTask(draft, storeKey);
    if (error != null) {
      JOptionPane.showMessageDialog(
          tasking.getAppFrame(), error, "TasKing", JOptionPane.WARNING_MESSAGE);
      return false;
    }
    return true;
  }

  private boolean complete(JButton button, String storeKey, String taskId) {
    Object rowObject = button.getClientProperty("row");
    if (!(rowObject instanceof NewTaskTableBean)) {
      return false;
    }
    NewTaskTableBean row = (NewTaskTableBean) rowObject;
    String text = row.getActualETC().getText() == null ? "" : row.getActualETC().getText().trim();
    if (text.isEmpty()) {
      JOptionPane.showMessageDialog(
          tasking.getAppFrame(),
          "Enter the actual hours before completing the task.",
          "TasKing",
          JOptionPane.WARNING_MESSAGE);
      return false;
    }
    try {
      taskService.completeTask(taskId, storeKey, Float.parseFloat(text));
      return true;
    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(
          tasking.getAppFrame(),
          "Actual hours must be a number.",
          "TasKing",
          JOptionPane.WARNING_MESSAGE);
      return false;
    }
  }
}
