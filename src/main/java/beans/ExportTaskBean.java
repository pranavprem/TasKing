package beans;

import services.LoginService;
import util.Constants;
import util.Dates;
import util.Utilities;

/** One completed task flattened into a CSV row. */
public class ExportTaskBean {
  private Integer ID;
  private String Name;
  private String Description;
  private Float EstimatedETC;
  private Float ActualETC;
  private java.util.Date AssignDate;
  private java.util.Date CompletionDate;
  private java.util.Date EstimatedDate;
  private String owner;
  private String ownerName;
  private Float timeDifference;

  public ExportTaskBean(NewTaskBean task, String user) {
    this.ID = task.getID();
    this.Name = task.getName();
    this.AssignDate = task.getAssignDate();
    this.CompletionDate = task.getCompletedDate();
    this.ActualETC = task.getActualETC() == null ? 0f : task.getActualETC();
    this.EstimatedETC = task.getEstimatedETC() == null ? 0f : task.getEstimatedETC();
    this.timeDifference = this.EstimatedETC - this.ActualETC;
    this.Description = task.getDescription();
    this.owner = user;
    this.EstimatedDate = task.getEstimatedDate();
    this.ownerName = LoginService.getName(this.owner);
  }

  public Integer getID() {
    return ID;
  }

  public String getName() {
    return Name;
  }

  public String getDescription() {
    return Description;
  }

  public Float getEstimatedETC() {
    return EstimatedETC;
  }

  public Float getActualETC() {
    return ActualETC;
  }

  public String getOwner() {
    return owner;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public Float getTimeDifference() {
    return timeDifference;
  }

  public static String getHeaders() {
    return "COMPLETED_BY,OWNER,ID,NAME,DESCRIPTION,ASSIGNED_DATE,ESTIMATED_DATE,COMPLETED_DATE,ESTIMATED_ETC,ACTUAL_ETC,TIME_SAVED";
  }

  @Override
  public String toString() {
    return owner
        + Constants.comma
        + Utilities.cleanString(ownerName)
        + Constants.comma
        + ID
        + Constants.comma
        + Utilities.cleanString(Name)
        + Constants.comma
        + Utilities.cleanString(Description)
        + Constants.comma
        + Dates.format(AssignDate)
        + Constants.comma
        + Dates.format(EstimatedDate)
        + Constants.comma
        + Dates.format(CompletionDate)
        + Constants.comma
        + EstimatedETC
        + Constants.comma
        + ActualETC
        + Constants.comma
        + timeDifference;
  }
}
