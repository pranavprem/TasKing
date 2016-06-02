package beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import util.Utilities;

import util.Constants;

public class NewTaskBean {
	private Integer ID;
	private String Name;
	private String Description;
	private Float EstimatedETC;
	private Float ActualETC;
	private Date AssignDate;
	private java.util.Date EstimatedDate;
	private Date CompletedDate;

	public NewTaskBean() {
		super();
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Float getEstimatedETC() {
		return EstimatedETC;
	}

	public void setEstimatedETC(Float estimatedETC) {
		EstimatedETC = estimatedETC;
	}

	public Float getActualETC() {
		return ActualETC;
	}

	public void setActualETC(Float actualETC) {
		ActualETC = actualETC;
	}

	public Date getAssignDate() {
		return AssignDate;
	}

	public void setAssignDate(Date assignDate) {
		AssignDate = assignDate;
	}

	public Date getEstimatedDate() {
		return EstimatedDate;
	}

	public void setEstimatedDate(Date estimatedDate) {
		EstimatedDate = estimatedDate;
	}

	public Date getCompletedDate() {
		return CompletedDate;
	}

	public void setCompletedDate(Date completedDate) {
		CompletedDate = completedDate;
	}

	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat(Constants.dateFormat);
		return ID + "," + Name + "," + Description + "," + df.format(AssignDate) + "," + df.format(CompletedDate) + ","
				+ df.format(EstimatedDate) + "," + EstimatedETC + "," + ActualETC;

	}
	
	
	public Integer newTaskID(){
		Integer id =null;
		Scanner sc= null;
		PrintWriter pw=null;
		try{
			sc= new Scanner(new File(Utilities.getLocation()+Constants.tasknum+Constants.dotTxt));
			id=sc.nextInt();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		finally{
			sc.close();
		}
		
		id++;
		try{
			pw = new PrintWriter(new File(Utilities.getLocation()+Constants.tasknum+Constants.dotTxt));
			pw.print(id);
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		finally{
			pw.close();
		}
		return id;
	}
	
	
	public NewTaskBean(NewTaskAddBean task)throws ParseException{
		this.ID=newTaskID();
		this.Name=task.getName().getText();
		this.Description=task.getDescription().getText();
		this.AssignDate=new Date();
		this.EstimatedETC=Float.parseFloat(task.getEstimatedETC().getText());
		this.ActualETC=0f;
		DateFormat df = new SimpleDateFormat(Constants.dateFormat);
		this.EstimatedDate=df.parse(task.getEstimatedDate().getText());
		this.CompletedDate=new Date();
		
		
	}

}
