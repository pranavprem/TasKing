
package beans;

import java.awt.Dimension;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;



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
	

	public void SetFixedWidth(JTextArea component, int width){
		component.setLineWrap(true);
		component.setWrapStyleWord(true);
		component.setRows(3);
		component.setSize(new Dimension(width, Short.MAX_VALUE));
		Dimension preferredsize = component.getPreferredSize();
		component.setPreferredSize(new Dimension(width, preferredsize.height));
	}
	
	public NewTaskTableBean (NewTaskBean task){
		super();
		DateFormat df = new SimpleDateFormat(util.Constants.dateFormat);
		this.ID.setText(task.getID().toString());
		this.Name.setText(task.getName());
		this.Description.setText(task.getDescription());
		this.AssignDate.setText(df.format(task.getAssignDate()));
		this.EstimatedDate.setText(df.format(task.getEstimatedDate()));
		this.EstimatedETC.setText(task.getEstimatedETC().toString());
		this.ID.setEditable(false);
		this.Name.setEditable(false);
		this.Description.setEditable(false);
		this.AssignDate.setEditable(false);
		this.EstimatedETC.setEditable(false);
		this.EstimatedDate.setEditable(false);
		
		SetFixedWidth(ActualETC, 40);
		SetFixedWidth(AssignDate, 40);
		SetFixedWidth(Description, 200);
		SetFixedWidth(EstimatedDate, 40);
		SetFixedWidth(EstimatedETC, 40);
		SetFixedWidth(ID, 20);
		SetFixedWidth(Name, 200);
		if(!task.getActualETC().equals(0.0f)){
			this.ActualETC.setText(task.getActualETC().toString());
		}
	}
	
	public ArrayList<JComponent> getJComponents(){
		ArrayList<JComponent> components = new ArrayList<JComponent>();
		components.add(this.Name);
		components.add(this.Description);
		components.add(this.EstimatedETC);
		components.add(this.EstimatedDate);
		components.add(this.AssignDate);
		components.add(this.ActualETC);
		return components;
	}
	
	
	public ArrayList<JComponent> getHeaders(){
		ArrayList<JComponent> components = new ArrayList<JComponent>();
		components.add(new JLabel("Name",SwingConstants.CENTER));
		components.add(new JLabel("Description",SwingConstants.CENTER));
		components.add(new JLabel("Estimated ETC",SwingConstants.CENTER));
		components.add(new JLabel("Estimated Date",SwingConstants.CENTER));
		components.add(new JLabel("Assigned Date",SwingConstants.CENTER));
		components.add(new JLabel("Actual ETC",SwingConstants.CENTER));
		return components;
	}
	
	
}
