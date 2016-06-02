
package beans;

import javax.swing.JTextArea;

/**
 * @author Pranav
 *
 */
public class NewTaskTableBean {
	private JTextArea ID = new JTextArea();
	private JTextArea Name = new JTextArea();
	private JTextArea Description = new JTextArea();
	private JTextArea EstimatedETC = new JTextArea();
	private JTextArea ActualETC = new JTextArea();
	private JTextArea AssignDate = new JTextArea();
	private JTextArea EstimatedDate = new JTextArea();
	
	
	
	public NewTaskTableBean() {
		super();
	}
	public NewTaskTableBean(JTextArea iD, JTextArea name, JTextArea description, JTextArea estimatedETC,
			JTextArea actualETC, JTextArea assignDate, JTextArea estimatedDate) {
		super();
		ID = iD;
		Name = name;
		Description = description;
		EstimatedETC = estimatedETC;
		ActualETC = actualETC;
		AssignDate = assignDate;
		EstimatedDate = estimatedDate;
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
	

	
	
	
}
