package beans;

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import util.Dates;

/** One task rendered as a row of text areas. */
public class NewTaskTableBean {
  private JTextArea ID = new JTextArea();
  private JTextArea Name = new JTextArea();
  private JTextArea Description = new JTextArea();
  private JTextArea EstimatedETC = new JTextArea();
  private JTextArea ActualETC = new JTextArea();
  private JTextArea AssignDate = new JTextArea();
  private JTextArea EstimatedDate = new JTextArea();

  public NewTaskTableBean() {}

  public NewTaskTableBean(NewTaskBean task) {
    this.ID.setText(task.getID() == null ? "" : task.getID().toString());
    this.Name.setText(task.getName() == null ? "" : task.getName());
    this.Description.setText(task.getDescription() == null ? "" : task.getDescription());
    if (task.getAssignDate() != null) {
      this.AssignDate.setText(Dates.format(task.getAssignDate()));
    }
    if (task.getEstimatedDate() != null) {
      this.EstimatedDate.setText(Dates.format(task.getEstimatedDate()));
    }
    if (task.getEstimatedETC() != null) {
      this.EstimatedETC.setText(task.getEstimatedETC().toString());
    }
    this.ID.setEditable(false);
    this.Name.setEditable(false);
    this.Description.setEditable(false);
    this.AssignDate.setEditable(false);
    this.EstimatedETC.setEditable(false);
    this.EstimatedDate.setEditable(false);

    setFixedWidth(ActualETC, 40);
    setFixedWidth(AssignDate, 40);
    setFixedWidth(Description, 200);
    setFixedWidth(EstimatedDate, 40);
    setFixedWidth(EstimatedETC, 40);
    setFixedWidth(ID, 20);
    setFixedWidth(Name, 200);
    if (task.getActualETC() != null && task.getActualETC() != 0f) {
      this.ActualETC.setText(task.getActualETC().toString());
    }
  }

  public JTextArea getID() {
    return ID;
  }

  public void setID(JTextArea iD) {
    ID = iD;
  }

  public JTextArea getName() {
    return Name;
  }

  public void setName(JTextArea name) {
    Name = name;
  }

  public JTextArea getDescription() {
    return Description;
  }

  public void setDescription(JTextArea description) {
    Description = description;
  }

  public JTextArea getEstimatedETC() {
    return EstimatedETC;
  }

  public void setEstimatedETC(JTextArea estimatedETC) {
    EstimatedETC = estimatedETC;
  }

  public JTextArea getActualETC() {
    return ActualETC;
  }

  public void setActualETC(JTextArea actualETC) {
    ActualETC = actualETC;
  }

  public JTextArea getAssignDate() {
    return AssignDate;
  }

  public void setAssignDate(JTextArea assignDate) {
    AssignDate = assignDate;
  }

  public JTextArea getEstimatedDate() {
    return EstimatedDate;
  }

  public void setEstimatedDate(JTextArea estimatedDate) {
    EstimatedDate = estimatedDate;
  }

  public void setFixedWidth(JTextArea component, int width) {
    component.setLineWrap(true);
    component.setWrapStyleWord(true);
    component.setRows(3);
    component.setSize(new Dimension(width, Short.MAX_VALUE));
    Dimension preferredSize = component.getPreferredSize();
    component.setPreferredSize(new Dimension(width, preferredSize.height));
  }

  public ArrayList<JComponent> getJComponents() {
    ArrayList<JComponent> components = new ArrayList<>();
    components.add(this.Name);
    components.add(this.Description);
    components.add(this.EstimatedETC);
    components.add(this.EstimatedDate);
    components.add(this.AssignDate);
    components.add(this.ActualETC);
    return components;
  }

  public ArrayList<JComponent> getHeaders() {
    ArrayList<JComponent> components = new ArrayList<>();
    components.add(new JLabel("Name", SwingConstants.CENTER));
    components.add(new JLabel("Description", SwingConstants.CENTER));
    components.add(new JLabel("Estimated ETC", SwingConstants.CENTER));
    components.add(new JLabel("Estimated Date", SwingConstants.CENTER));
    components.add(new JLabel("Assigned Date", SwingConstants.CENTER));
    components.add(new JLabel("Actual ETC", SwingConstants.CENTER));
    return components;
  }
}
