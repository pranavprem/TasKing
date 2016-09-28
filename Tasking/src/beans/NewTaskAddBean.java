
package beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import util.Constants;
import util.Utilities;

/**
 * @author Pranav
 *
 */
public class NewTaskAddBean {
	private JTextArea ID = new JTextArea();
	private JTextArea Name = new JTextArea();
	private JTextArea Description = new JTextArea();
	private JTextArea EstimatedETC = new JTextArea();
	private JTextArea EstimatedDate = new JTextArea();
	private JTextArea AssignDate = new JTextArea();
	

	
	
	public NewTaskAddBean() {
		super();
		DateFormat df= new SimpleDateFormat(Constants.dateFormat);
		this.ID.setText(getTaskID().toString());
		this.ID.setEditable(false);
		initializeComponent(Name);
		initializeComponent(Description);
		initializeComponent(EstimatedETC);
		initializeComponent(EstimatedDate);
		initializeComponent(AssignDate);
		this.EstimatedDate.setText(df.format(new Date()));
		
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
	
	public Integer getTaskID(){
		Integer id=null;
		Scanner sc= null;
		try{
			new Utilities();
			sc= new Scanner(new File(Utilities.getLocation()+Constants.tasknum+Constants.dotTxt));
			id=sc.nextInt();
			id++;
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}finally{
			sc.close();
		}
		
		return id;
	}
	
	public void initializeComponent(JTextArea componenet){
		componenet.setText("");
	}
	@Override
	public String toString() {
		return "NewTaskAddBean [ID=" + ID + ", Name=" + Name + ", Description=" + Description + ", EstimatedETC="
				+ EstimatedETC + ", EstimatedDate=" + EstimatedDate + ", AssignDate=" + AssignDate + "]";
	}
	
	
	
public ArrayList<JComponent> getHeaders(){
	ArrayList<JComponent> Components = new ArrayList<JComponent>();
	JLabel temp = new JLabel();
	temp.setText("Name");
	Components.add(temp);
	JLabel temp1 = new JLabel();
	temp.setText("Description");
	Components.add(temp1);
	JLabel temp2 = new JLabel();
	temp.setText("EstimatedETC");
	Components.add(temp2);
	JLabel temp3 = new JLabel();
	temp.setText("EstimatedDate");
	Components.add(temp3);
	
	return Components;
	
}
	
	
	public ArrayList<JComponent> getComponenets(){
		ArrayList<JComponent> Components = new ArrayList<JComponent>();
		Components.add(this.Name);
		Components.add(this.Description);
		Components.add(this.EstimatedETC);
		Components.add(this.EstimatedDate);
		return Components;
	}
}
