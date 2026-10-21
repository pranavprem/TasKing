package beans;

import java.util.ArrayList;
import java.util.Date;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import util.Dates;
import util.Utilities;

/** Blank row used to type a new task on the board. */
public class NewTaskAddBean {
  private JTextArea ID = new JTextArea();
  private JTextArea Name = new JTextArea();
  private JTextArea Description = new JTextArea();
  private JTextArea EstimatedETC = new JTextArea();
  private JTextArea EstimatedDate = new JTextArea();
  private JTextArea AssignDate = new JTextArea();

  public NewTaskAddBean() {
    this.ID.setText(Integer.toString(Utilities.peekNextTaskId()));
    this.ID.setEditable(false);
    initializeComponent(Name);
    initializeComponent(Description);
    initializeComponent(EstimatedETC);
    initializeComponent(EstimatedDate);
    this.EstimatedDate.setText(Dates.format(new Date()));
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

  public JTextArea getEstimatedDate() {
    return EstimatedDate;
  }

  public void setEstimatedDate(JTextArea estimatedDate) {
    EstimatedDate = estimatedDate;
  }

  public JTextArea getAssignDate() {
    return AssignDate;
  }

  public void setAssignDate(JTextArea assignDate) {
    AssignDate = assignDate;
  }

  public void initializeComponent(JTextArea component) {
    component.setText("");
  }

  public ArrayList<JComponent> getComponenets() {
    ArrayList<JComponent> components = new ArrayList<>();
    components.add(this.Name);
    components.add(this.Description);
    components.add(this.EstimatedETC);
    components.add(this.EstimatedDate);
    return components;
  }
}
