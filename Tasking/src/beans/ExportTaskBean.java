
package beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import services.LoginService;
import util.Utilities;

import util.Constants;

/**
 * @author Pranav
 *
 */
public class ExportTaskBean {
	private Integer ID;
	private String Name;
	private String Description;
	private Float EstimatedETC;
	private Float ActualETC;
	private Date AssignDate;
	private Date CompletionDate;
	private Date EstimatedDate;
	private String owner;
	private String ownerName;
	private Float timeDifference;

	public ExportTaskBean(NewTaskBean task, String user) {
		this.ID = task.getID();
		this.Name = task.getName();
		this.AssignDate = task.getAssignDate();
		this.CompletionDate = task.getCompletedDate();
		this.ActualETC = task.getActualETC();
		this.EstimatedETC = task.getEstimatedETC();
		this.timeDifference = this.EstimatedETC - this.ActualETC;
		this.Description = task.getDescription();
		this.owner = user;
		this.EstimatedDate = task.getEstimatedDate();
		this.ownerName = LoginService.getName(this.owner);
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

	public Date getCompletionDate() {
		return CompletionDate;
	}

	public void setCompletionDate(Date completionDate) {
		CompletionDate = completionDate;
	}

	public Date getEstimatedDate() {
		return EstimatedDate;
	}

	public void setEstimatedDate(Date estimatedDate) {
		EstimatedDate = estimatedDate;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Float getTimeDifference() {
		return timeDifference;
	}

	public void setTimeDifference(Float timeDifference) {
		this.timeDifference = timeDifference;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public static String getHeaders() {
		return "COMPLETED_BY,OWNER,ID,NAME,DESCRIPTION,ASSIGNED_DATE,ESTIMATED_DATE,COMPLETED_DATE,ESTIMATED_ETC,ACTUAL_ETC,TIME_SAVED";
	}

	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat(Constants.dateFormat);
		return owner + Constants.comma + Utilities.cleanString(ownerName) + Constants.comma + ID + Constants.comma
				+ Utilities.cleanString(Name) + Constants.comma + Utilities.cleanString(Description) + Constants.comma
				+ df.format(AssignDate) + Constants.comma + df.format(EstimatedDate) + Constants.comma
				+ df.format(CompletionDate) + Constants.comma + EstimatedETC + Constants.comma + ActualETC
				+ Constants.comma + timeDifference;

	}

}
